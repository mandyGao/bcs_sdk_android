package com.baidu.inf.iis.bcs.handler;

import com.baidu.inf.iis.bcs.http.BCSHttpResponse;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class ObjectMetadataResponseHandler extends HttpResponseHandler<ObjectMetadata> {
	
	public BaiduBCSResponse<ObjectMetadata> handle(BCSHttpResponse paramBCSHttpResponse) {
		ObjectMetadata localObjectMetadata = new ObjectMetadata();
		populateObjectMetadata(paramBCSHttpResponse, localObjectMetadata);

		BaiduBCSResponse<ObjectMetadata> localBaiduBCSResponse = parseResponseMetadata(paramBCSHttpResponse);
		localBaiduBCSResponse.setResult(localObjectMetadata);
		return localBaiduBCSResponse;
	}
}
