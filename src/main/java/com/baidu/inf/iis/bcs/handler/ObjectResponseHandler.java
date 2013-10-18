/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.handler;

import com.baidu.inf.iis.bcs.http.BCSHttpRequest;
import com.baidu.inf.iis.bcs.http.BCSHttpResponse;
import com.baidu.inf.iis.bcs.model.DownloadObject;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.request.BaiduBCSRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class ObjectResponseHandler extends HttpResponseHandler<DownloadObject> {
	public BaiduBCSResponse<DownloadObject> handle(
			BCSHttpResponse paramBCSHttpResponse) {
		DownloadObject localDownloadObject = new DownloadObject();
		localDownloadObject.setBucket(paramBCSHttpResponse.getRequest()
				.getOriginalRequest().getBucket());
		localDownloadObject.setObject(paramBCSHttpResponse.getRequest()
				.getOriginalRequest().getObject());

		ObjectMetadata localObjectMetadata = new ObjectMetadata();
		populateObjectMetadata(paramBCSHttpResponse, localObjectMetadata);
		localDownloadObject.setObjectMetadata(localObjectMetadata);

		BaiduBCSResponse localBaiduBCSResponse = parseResponseMetadata(paramBCSHttpResponse);

		localDownloadObject.setContent(paramBCSHttpResponse.getContent());

		localBaiduBCSResponse.setResult(localDownloadObject);
		return localBaiduBCSResponse;
	}

	public boolean isNeedsConnectionLeftOpen() {
		return true;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.handler.ObjectResponseHandler
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */