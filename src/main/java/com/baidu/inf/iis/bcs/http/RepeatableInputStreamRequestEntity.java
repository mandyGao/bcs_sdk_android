package com.baidu.inf.iis.bcs.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.InputStreamEntity;

import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

class RepeatableInputStreamRequestEntity extends BasicHttpEntity {
	
	private static final Logger log = LoggerFactory.getLogger(RepeatableInputStreamRequestEntity.class);
	
	private boolean firstAttempt = true;
	private InputStreamEntity inputStreamRequestEntity;
	private InputStream content;

	RepeatableInputStreamRequestEntity(BCSHttpRequest paramBCSHttpRequest) {
		setChunked(false);

		long l = -1L;
		try {
			String str1 = (String) paramBCSHttpRequest.getHeaders().get("Content-Length");
			if (str1 != null)
				l = Long.parseLong(str1);
		} catch (NumberFormatException localNumberFormatException) {
			log.warn("Unable to parse content length from request.  Buffering contents in memory.");
		}

		String str2 = (String) paramBCSHttpRequest.getHeaders().get("Content-Type");

		this.inputStreamRequestEntity = new InputStreamEntity(paramBCSHttpRequest.getContent(), l);
		this.inputStreamRequestEntity.setContentType(str2);
		this.content = paramBCSHttpRequest.getContent();

		setContent(this.content);
		setContentType(str2);
		setContentLength(l);
	}

	public boolean isChunked() {
		return false;
	}

	public boolean isRepeatable() {
		return ((this.content.markSupported()) || (this.inputStreamRequestEntity.isRepeatable()));
	}

	public void writeTo(OutputStream paramOutputStream) throws IOException {
		if ((!(this.firstAttempt)) && (isRepeatable())) {
			this.content.reset();
		}

		this.firstAttempt = false;
		this.inputStreamRequestEntity.writeTo(paramOutputStream);
	}
}
