/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.handler;

import com.baidu.inf.iis.bcs.http.BCSHttpResponse;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class PolicyResponseHandler extends HttpResponseHandler<Policy> {
	public BaiduBCSResponse<Policy> handle(BCSHttpResponse paramBCSHttpResponse) {
		String str = getResponseContentByStr(paramBCSHttpResponse);
		Policy localPolicy = new Policy().buildJsonStr(str);
		BaiduBCSResponse localBaiduBCSResponse = parseResponseMetadata(paramBCSHttpResponse);
		localBaiduBCSResponse.setResult(localPolicy);
		return localBaiduBCSResponse;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.handler.PolicyResponseHandler
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */