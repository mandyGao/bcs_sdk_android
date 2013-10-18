/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BCSHttpResponse {
	private BCSHttpRequest request;
	private String statusText;
	private int statusCode;
	private InputStream content;
	private Map<String, String> headers;

	public BCSHttpResponse() {
		this.headers = new HashMap();
	}

	public void addHeader(String paramString1, String paramString2) {
		this.headers.put(paramString1, paramString2);
	}

	public InputStream getContent() {
		return this.content;
	}

	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public BCSHttpRequest getRequest() {
		return this.request;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public String getStatusText() {
		return this.statusText;
	}

	public void setContent(InputStream paramInputStream) {
		this.content = paramInputStream;
	}

	public void setRequest(BCSHttpRequest paramBCSHttpRequest) {
		this.request = paramBCSHttpRequest;
	}

	public void setStatusCode(int paramInt) {
		this.statusCode = paramInt;
	}

	public void setStatusText(String paramString) {
		this.statusText = paramString;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.http.BCSHttpResponse
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */