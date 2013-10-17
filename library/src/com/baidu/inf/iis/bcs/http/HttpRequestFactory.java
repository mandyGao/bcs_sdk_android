package com.baidu.inf.iis.bcs.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.utils.Constants;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class HttpRequestFactory {
	private static final Logger log = LoggerFactory.getLogger(HttpRequestFactory.class);

	public String buildUri(ClientConfiguration paramClientConfiguration, BCSHttpRequest paramBCSHttpRequest) {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append(paramClientConfiguration.getProtocol().toString());
		localStringBuilder.append("://");
		localStringBuilder.append(paramBCSHttpRequest.getEndpoint());
		if ((paramBCSHttpRequest.getResourcePath() != null)
				&& (paramBCSHttpRequest.getResourcePath().length() > 0)) {
			if (!(paramBCSHttpRequest.getResourcePath().startsWith("/"))) {
				localStringBuilder.append("/");
			}
			localStringBuilder.append(paramBCSHttpRequest.getResourcePath());
		}
		String str = encodeParameters(paramBCSHttpRequest);
		localStringBuilder.append("?").append(str);
		return localStringBuilder.toString();
	}

	public HttpRequestBase createHttpRequestBase(ClientConfiguration paramClientConfiguration, BCSHttpRequest paramBCSHttpRequest) {
		String str = buildUri(paramClientConfiguration, paramBCSHttpRequest);
		log.debug(paramBCSHttpRequest.getHttpMethod().toString() + "  " + str);
		HttpRequestBase localObject1;
		HttpEntity localObject3;
		if (paramBCSHttpRequest.getHttpMethod() == HttpMethodName.GET) {
			localObject1 = new HttpGet(str);
		} else if (paramBCSHttpRequest.getHttpMethod() == HttpMethodName.PUT) {
			HttpPut localObject2 = new HttpPut(str);
			localObject1 = localObject2;
			if (null != paramBCSHttpRequest.getContent()) {
				localObject3 = new RepeatableInputStreamRequestEntity(paramBCSHttpRequest);
				localObject2.setEntity(localObject3);
			}
		} else if (paramBCSHttpRequest.getHttpMethod() == HttpMethodName.DELETE) {
			localObject1 = new HttpDelete(str);
		} else if (paramBCSHttpRequest.getHttpMethod() == HttpMethodName.HEAD) {
			localObject1 = new HttpHead(str);
		} else {
			throw new BCSClientException("Unknown HTTP method name:" + paramBCSHttpRequest.getHttpMethod().toString());
		}

		for (String key : paramBCSHttpRequest.getHeaders().keySet()) {
			if("Content-Length".equalsIgnoreCase(key)){
				continue;
			}
			localObject1.addHeader(key, paramBCSHttpRequest.getHeaders().get(key));
		}
		if(localObject1.getHeaders("Content-Type") == null || localObject1.getHeaders("Content-Type").length <= 0){
			localObject1.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + Constants.DEFAULT_ENCODING.toLowerCase());
		}
		
		return ((HttpRequestBase) (HttpRequestBase) (HttpRequestBase) localObject1);
	}

	private String encodeParameters(BCSHttpRequest paramBCSHttpRequest) {
		List<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
		Map<String, String> map = paramBCSHttpRequest.getParameters();
		if(map != null){
			for (String key : map.keySet()) {
				localArrayList.add(new BasicNameValuePair(key, map.get(key)));
			}
		}
		if (paramBCSHttpRequest.getParameters().size() > 0) {
			localArrayList = new ArrayList(paramBCSHttpRequest.getParameters().size());
			
			for (String key : paramBCSHttpRequest.getParameters().keySet()) {
				localArrayList.add(new BasicNameValuePair(key, paramBCSHttpRequest.getParameters().get(key)));
			}
		}
		StringBuilder builder = new StringBuilder();
		for (NameValuePair nameValuePair : localArrayList) {
			builder.append("&");
			builder.append(nameValuePair.getName());
			builder.append("=");
			builder.append(nameValuePair.getValue());
		}
		builder.deleteCharAt(0);
		return builder.toString();
	}
}
