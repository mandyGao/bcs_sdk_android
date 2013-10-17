package com.baidu.inf.iis.bcs.http;

import java.io.IOException;
import java.io.InputStream;

import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class RepeatableInputStream extends InputStream {
	private static final Logger log = LoggerFactory.getLogger(RepeatableInputStream.class);

	private InputStream is = null;
	private int bufferSize = 0;
	private int bufferOffset = 0;
	private long bytesReadPastMark = 0L;
	private byte[] buffer = null;

	public RepeatableInputStream(InputStream paramInputStream, int paramInt) {
		if (paramInputStream == null) {
			throw new IllegalArgumentException("InputStream cannot be null");
		}

		this.is = paramInputStream;
		this.bufferSize = paramInt;
		this.buffer = new byte[this.bufferSize];

		log.debug("Underlying input stream will be repeatable up to " + this.buffer.length + " bytes");
	}

	public int available() throws IOException {
		return this.is.available();
	}

	public void close() throws IOException {
		this.is.close();
	}

	public InputStream getWrappedInputStream() {
		return this.is;
	}

	public synchronized void mark(int paramInt) {
		log.debug("Input stream marked at " + this.bytesReadPastMark + " bytes");
		if ((this.bytesReadPastMark <= this.bufferSize) && (this.buffer != null)) {
			byte[] arrayOfByte = new byte[this.bufferSize];
			System.arraycopy(this.buffer, this.bufferOffset, arrayOfByte, 0, (int) (this.bytesReadPastMark - this.bufferOffset));
			this.buffer = arrayOfByte;
			this.bytesReadPastMark -= this.bufferOffset;
			this.bufferOffset = 0;
		} else {
			this.bufferOffset = 0;
			this.bytesReadPastMark = 0L;
			this.buffer = new byte[this.bufferSize];
		}
	}

	public boolean markSupported() {
		return true;
	}

	public int read() throws IOException {
		byte[] arrayOfByte = new byte[1];
		int i = read(arrayOfByte);
		if (i != -1) {
			return arrayOfByte[0];
		}
		return i;
	}

	public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
		byte[] arrayOfByte = new byte[paramInt2];

		int i;
		if ((this.bufferOffset < this.bytesReadPastMark) && (this.buffer != null)) {
			i = arrayOfByte.length;
			if (this.bufferOffset + i > this.bytesReadPastMark) {
				i = (int) this.bytesReadPastMark - this.bufferOffset;
			}

			System.arraycopy(this.buffer, this.bufferOffset, paramArrayOfByte, paramInt1, i);
			this.bufferOffset += i;
			return i;
		}

		i = this.is.read(arrayOfByte);

		if (i <= 0) {
			return i;
		}

		if (this.bytesReadPastMark + i <= this.bufferSize) {
			System.arraycopy(arrayOfByte, 0, this.buffer, (int) this.bytesReadPastMark, i);
			this.bufferOffset += i;
		} else if (this.buffer != null) {
			log.debug("Buffer size " + this.bufferSize + " has been exceeded and the input stream " + "will not be repeatable until the next mark. Freeing buffer memory");
			this.buffer = null;
		}

		System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt1, i);
		this.bytesReadPastMark += i;

		return i;
	}

	public void reset() throws IOException {
		if (this.bytesReadPastMark <= this.bufferSize) {
			log.debug("Reset after reading " + this.bytesReadPastMark + " bytes.");
			this.bufferOffset = 0;
		} else {
			throw new IOException("Input stream cannot be reset as " + this.bytesReadPastMark + " bytes have been written, exceeding the available buffer size of " + this.bufferSize);
		}
	}
}
