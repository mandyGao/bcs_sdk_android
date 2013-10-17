/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.model;

public class Resource {
	private String bucket;
	private String object;

	public Resource(String paramString1, String paramString2) {
		this.bucket = paramString1;
		this.object = paramString2;
	}

	public String getBucket() {
		return this.bucket;
	}

	public String getObject() {
		return this.object;
	}

	public void setBucket(String paramString) {
		this.bucket = paramString;
	}

	public void setObject(String paramString) {
		this.object = paramString;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.model.Resource
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */