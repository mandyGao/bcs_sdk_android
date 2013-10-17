package com.baidu.inf.iis.bcs.http;

import com.baidu.inf.iis.bcs.request.BaiduBCSRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DefaultBCSHttpRequest implements BCSHttpRequest {
	private String resourcePath;
	private Map<String, String> parameters;
	private Map<String, String> headers;
	private String endpoint;
	private String serviceName;
	private final BaiduBCSRequest originalRequest;
	private HttpMethodName httpMethod;
	private InputStream content;

	public DefaultBCSHttpRequest() {
		this(null);
	}

	public DefaultBCSHttpRequest(BaiduBCSRequest paramBaiduBCSRequest) {
		this.parameters = new HashMap<String, String>();
		this.headers = new HashMap<String, String>();

		this.originalRequest = paramBaiduBCSRequest;
	}

	public void addHeader(String paramString1, String paramString2) {
		this.headers.put(paramString1, paramString2);
	}

	public void addParameter(String paramString1, String paramString2) {
		this.parameters.put(paramString1, paramString2);
	}

	public InputStream getContent() {
		return this.content;
	}

	public String getEndpoint() {
		return this.endpoint;
	}

	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public HttpMethodName getHttpMethod() {
		return this.httpMethod;
	}

	public BaiduBCSRequest getOriginalRequest() {
		return this.originalRequest;
	}

	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public String getResourcePath() {
		return this.resourcePath;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setContent(InputStream paramInputStream) {
		this.content = paramInputStream;
	}

	public void setEndpoint(String paramString) {
		this.endpoint = paramString;
	}

	public void setHttpMethod(HttpMethodName paramHttpMethodName) {
		this.httpMethod = paramHttpMethodName;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(getHttpMethod().toString() + " ");
		builder.append(getEndpoint().toString() + " ");

		builder.append("/");
		builder.append(((getResourcePath() != null) ? getResourcePath() : "") + " ");
		Map<String, String> param = getParameters();
		if (!param.isEmpty()) {
			builder.append("Parameters: (");
			for (String key : param.keySet()) {
				builder.append(key).append("=").append(param.get(key)).append(",");
			}
			builder.append(") ");
		}
		Map<String, String> header = getHeaders();
		if (!header.isEmpty()) {
			builder.append("Headers: (");
			for (String key : header.keySet()) {
				builder.append(key).append("=").append(param.get(key)).append(",");
			}
			builder.append(") ");
		}
		return builder.toString();
	}

	public BCSHttpRequest withParameter(String paramString1, String paramString2) {
		addParameter(paramString1, paramString2);
		return this;
	}
}
