package com.baidu.inf.iis.bcs.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class ObjectMetadata {
	
	private static Logger logger = LoggerFactory.getLogger(ObjectMetadata.class); 
	
	private Map<String, String> userMetadata;
	private Map<String, Object> metadata;

	public ObjectMetadata() {
		this.userMetadata = new HashMap<String, String>();
		this.metadata = new HashMap<String, Object>();
	}

	public void addUserMetadata(String paramString1, String paramString2) {
		this.userMetadata.put(paramString1, paramString2);
	}

	public String getCacheControl() {
		return (String) metadata.get("Cache-Control");
	}

	public String getContentDisposition() {
		return (String) metadata.get("Content-Disposition");
	}

	public String getContentEncoding() {
		return (String) metadata.get("Content-Encoding");
	}

	public long getContentLength() {
		String length = (String) metadata.get("Content-Length");
		if (length == null) {
			return -1L;
		}
		Long longLength = -1L;
		try {
			longLength = Long.decode((String) length);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException:" + length, e);
		}
		return longLength;
	}

	public String getContentMD5() {
		return (String) metadata.get("Content-MD5");
	}

	public String getContentType() {
		return (String) metadata.get("Content-Type");
	}

	public String getETag() {
		return (String) metadata.get("ETag");
	}

	public Date getLastModified() {
		return (Date) metadata.get("Last-Modified");
	}

	public Map<String, Object> getRawMetadata() {
		return Collections.unmodifiableMap(metadata);
	}

	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	public String getVersionId() {
		return (String) this.metadata.get("x-bs-version");
	}

	public void setCacheControl(String paramString) {
		this.metadata.put("Cache-Control", paramString);
	}

	public void setContentDisposition(String paramString) {
		this.metadata.put("Content-Disposition", paramString);
	}

	public void setContentEncoding(String paramString) {
		this.metadata.put("Content-Encoding", paramString);
	}

	public void setContentLength(long paramLong) {
		this.metadata.put("Content-Length", String.valueOf(paramLong));
	}

	public void setContentMD5(String paramString) {
		this.metadata.put("Content-MD5", paramString);
	}

	public void setContentType(String paramString) {
		this.metadata.put("Content-Type", paramString);
	}

	public void setHeader(String paramString, Object paramObject) {
		this.metadata.put(paramString, paramObject);
	}

	public void setLastModified(Date paramDate) {
		this.metadata.put("Last-Modified", paramDate);
	}

	public void setUserMetadata(Map<String, String> paramMap) {
		this.userMetadata = paramMap;
	}

	public String toString() {
		return "ObjectMetadata [userMetadata=" + this.userMetadata + ", metadata=" + this.metadata + "]";
	}
}
