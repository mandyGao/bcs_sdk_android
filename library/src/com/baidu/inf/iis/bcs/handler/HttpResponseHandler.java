package com.baidu.inf.iis.bcs.handler;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.baidu.inf.iis.bcs.http.BCSHttpResponse;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;
import com.baidu.inf.iis.bcs.utils.ServiceUtils;

public abstract class HttpResponseHandler<T> {
	
	private static final Logger log = LoggerFactory.getLogger(HttpResponseHandler.class);
	private static final Set<String> ignoredHeaders = new HashSet<String>();

	static {
		ignoredHeaders.add("Date");
		ignoredHeaders.add("Server");
		ignoredHeaders.add("x-bs-request-id");
	}

	protected String getResponseContentByStr(BCSHttpResponse paramBCSHttpResponse) {
		if (null != paramBCSHttpResponse.getContent()) {
			byte[] arrayOfByte = new byte[1024];
			StringBuilder localStringBuilder = new StringBuilder();
			try {
				int i;
				while ((i = paramBCSHttpResponse.getContent().read(arrayOfByte)) > 0) {
					localStringBuilder.append(new String(arrayOfByte, 0, i));
				}
			} catch (IOException localIOException) {
				throw new BCSClientException("Read http response body error.", localIOException);
			}
			return localStringBuilder.toString();
		}
		return "";
	}

	public abstract BaiduBCSResponse<T> handle(BCSHttpResponse paramBCSHttpResponse);

	public boolean isNeedsConnectionLeftOpen() {
		return false;
	}

	protected BaiduBCSResponse<T> parseResponseMetadata(BCSHttpResponse paramBCSHttpResponse) {
		BaiduBCSResponse<T> localBaiduBCSResponse = new BaiduBCSResponse<T>();
		String str = paramBCSHttpResponse.getHeaders().get("x-bs-request-id");
		localBaiduBCSResponse.setRequestId(str);
		log.info("Bcs requestId:" + str);
		return localBaiduBCSResponse;
	}

	protected void populateObjectMetadata(BCSHttpResponse paramBCSHttpResponse, ObjectMetadata paramObjectMetadata) {
		Map<String, String> header = paramBCSHttpResponse.getHeaders();
		for (String key : header.keySet()) {
			if (key.startsWith("x-bs-meta-")) {
				key = key.substring("x-bs-meta-".length());
				paramObjectMetadata.addUserMetadata(key, header.get(key));
			} else if (key.equals("Last-Modified")) {
				try {
					paramObjectMetadata.setHeader(key, ServiceUtils.parseRfc822Date(header.get(key)));
				} catch (ParseException localParseException) {
					log.warn("Unable to parse last modified date: "+header.get(key)+":"+localParseException);
				}
			} else if (!(ignoredHeaders.contains(key))) {
				paramObjectMetadata.setHeader(key, header.get(key));
			}
		}
	}
}
