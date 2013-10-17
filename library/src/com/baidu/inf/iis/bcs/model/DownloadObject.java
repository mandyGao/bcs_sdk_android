/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.model;

import java.io.InputStream;

public class DownloadObject {
	private String object;
	private String bucket;
	private ObjectMetadata objectMetadata;
	private InputStream content;

	public DownloadObject() {
		this.objectMetadata = new ObjectMetadata();
	}

	public String getBucket() {
		return this.bucket;
	}

	public InputStream getContent() {
		return this.content;
	}

	public String getObject() {
		return this.object;
	}

	public ObjectMetadata getObjectMetadata() {
		return this.objectMetadata;
	}

	public void setBucket(String paramString) {
		this.bucket = paramString;
	}

	public void setContent(InputStream paramInputStream) {
		this.content = paramInputStream;
	}

	public void setObject(String paramString) {
		this.object = paramString;
	}

	public void setObjectMetadata(ObjectMetadata paramObjectMetadata) {
		this.objectMetadata = paramObjectMetadata;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.model.DownloadObject
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */