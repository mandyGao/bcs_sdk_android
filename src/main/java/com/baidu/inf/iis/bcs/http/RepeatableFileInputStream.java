package com.baidu.inf.iis.bcs.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class RepeatableFileInputStream extends InputStream {
	private static final Logger log = LoggerFactory.getLogger(RepeatableFileInputStream.class);
	private File file = null;
	private FileInputStream fis = null;
	private long bytesReadPastMarkPoint = 0L;
	private long markPoint = 0L;

	public RepeatableFileInputStream(File paramFile)
			throws FileNotFoundException {
		if (paramFile == null) {
			throw new IllegalArgumentException("File cannot be null");
		}
		this.fis = new FileInputStream(paramFile);
		this.file = paramFile;
	}

	public int available() throws IOException {
		return this.fis.available();
	}

	public void close() throws IOException {
		this.fis.close();
	}

	public InputStream getWrappedInputStream() {
		return this.fis;
	}

	public void mark(int paramInt) {
		this.markPoint += this.bytesReadPastMarkPoint;
		this.bytesReadPastMarkPoint = 0L;
		log.debug("Input stream marked at " + this.markPoint + " bytes");
	}

	public boolean markSupported() {
		return true;
	}

	public int read() throws IOException {
		int i = this.fis.read();
		if (i != -1) {
			this.bytesReadPastMarkPoint += 1L;
			return i;
		}
		return -1;
	}

	public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
			throws IOException {
		int i = this.fis.read(paramArrayOfByte, paramInt1, paramInt2);
		this.bytesReadPastMarkPoint += i;
		return i;
	}

	public void reset() throws IOException {
		this.fis.close();
		this.fis = new FileInputStream(this.file);

		long l1 = 0L;
		long l2 = this.markPoint;
		while (l2 > 0L) {
			l1 = this.fis.skip(l2);
			l2 -= l1;
		}

		log.debug("Reset to mark point " + this.markPoint + " after returning " + this.bytesReadPastMarkPoint + " bytes");

		this.bytesReadPastMarkPoint = 0L;
	}

	public long skip(long paramLong) throws IOException {
		long l = this.fis.skip(paramLong);
		this.bytesReadPastMarkPoint += l;
		return l;
	}
}
