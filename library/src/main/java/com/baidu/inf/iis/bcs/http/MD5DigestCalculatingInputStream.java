package com.baidu.inf.iis.bcs.http;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5DigestCalculatingInputStream extends FilterInputStream {
	private MessageDigest digest;

	public MD5DigestCalculatingInputStream(InputStream paramInputStream) throws NoSuchAlgorithmException {
		super(paramInputStream);

		this.digest = MessageDigest.getInstance("MD5");
	}

	public byte[] getMd5Digest() {
		return this.digest.digest();
	}

	public int read() throws IOException {
		int i = this.in.read();
		if (i != -1) {
			this.digest.update((byte) i);
		}
		return i;
	}

	public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
			throws IOException {
		int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
		if (i != -1) {
			this.digest.update(paramArrayOfByte, paramInt1, i);
		}
		return i;
	}

	public synchronized void reset() throws IOException {
		try {
			this.digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
		}
		this.in.reset();
	}
}
