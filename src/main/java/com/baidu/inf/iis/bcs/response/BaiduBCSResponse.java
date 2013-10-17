/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.response;

public class BaiduBCSResponse<T> {
	private T result;
	private String requestId;

	public String getRequestId() {
		return this.requestId;
	}

	public T getResult() {
		return this.result;
	}

	public void setRequestId(String paramString) {
		this.requestId = paramString;
	}

	public void setResult(T paramT) {
		this.result = paramT;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.response.BaiduBCSResponse
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */