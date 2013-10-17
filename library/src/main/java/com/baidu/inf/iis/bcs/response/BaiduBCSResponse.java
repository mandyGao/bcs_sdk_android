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
