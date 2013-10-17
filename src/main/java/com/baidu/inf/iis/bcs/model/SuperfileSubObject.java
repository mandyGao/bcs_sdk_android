/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.model;

public class SuperfileSubObject {
	private String bucket;
	private String object;
	private String etag;

	public SuperfileSubObject(String paramString1, String paramString2,
			String paramString3) {
		this.bucket = paramString1;
		this.object = paramString2;
		this.etag = paramString3;
	}

	public String getBucket() {
		return this.bucket;
	}

	public String getEtag() {
		return this.etag;
	}

	public String getObject() {
		return this.object;
	}

	public void setBucket(String paramString) {
		this.bucket = paramString;
	}

	public void setEtag(String paramString) {
		this.etag = paramString;
	}

	public void setObject(String paramString) {
		this.object = paramString;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.model.SuperfileSubObject
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */