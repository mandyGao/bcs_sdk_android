/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.handler;

import com.baidu.inf.iis.bcs.http.BCSHttpResponse;
import com.baidu.inf.iis.bcs.model.Empty;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class VoidResponseHandler extends HttpResponseHandler<Empty> {
	public BaiduBCSResponse<Empty> handle(BCSHttpResponse paramBCSHttpResponse) {
		BaiduBCSResponse localBaiduBCSResponse = parseResponseMetadata(paramBCSHttpResponse);
		localBaiduBCSResponse.setResult(new Empty());
		return localBaiduBCSResponse;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.handler.VoidResponseHandler
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */