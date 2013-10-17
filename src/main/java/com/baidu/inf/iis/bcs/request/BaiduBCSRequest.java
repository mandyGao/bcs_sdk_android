package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.utils.StringUtils;

public abstract class BaiduBCSRequest {
	protected String bucket = null;
	protected String object = null;
	protected HttpMethodName httpMethod = null;

	public BaiduBCSRequest(String paramString,
			HttpMethodName paramHttpMethodName) {
		this.bucket = paramString;
		this.object = "/";
		this.httpMethod = paramHttpMethodName;
	}

	public BaiduBCSRequest(String paramString1, String paramString2,
			HttpMethodName paramHttpMethodName) {
		this.bucket = paramString1;
		this.object = StringUtils.trimSlash(paramString2);
		this.httpMethod = paramHttpMethodName;
	}

	public String getBucket() {
		return this.bucket;
	}

	public HttpMethodName getHttpMethod() {
		return this.httpMethod;
	}

	public String getObject() {
		return this.object;
	}

	public void setBucket(String paramString) {
		this.bucket = paramString;
	}

	public void setHttpMethod(HttpMethodName paramHttpMethodName) {
		this.httpMethod = paramHttpMethodName;
	}

	public void setObject(String paramString) {
		this.object = StringUtils.trimSlash(paramString);
	}
}
