package com.baidu.inf.iis.bcs.handler;

import org.json.JSONObject;

import com.baidu.inf.iis.bcs.http.BCSHttpResponse;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class ErrorResponseHandler extends HttpResponseHandler<BCSServiceException> {
	private static final Logger log = LoggerFactory.getLogger(ErrorResponseHandler.class);

	public BaiduBCSResponse<BCSServiceException> handle(BCSHttpResponse paramBCSHttpResponse) {
		BaiduBCSResponse<BCSServiceException> localBaiduBCSResponse = new BaiduBCSResponse<BCSServiceException>();

		String str1 = getResponseContentByStr(paramBCSHttpResponse);
		int i = -1;
		String str2 = "";

		if (0 != str1.length()) {
			try {
				JSONObject json = new JSONObject(str1);
				JSONObject errorJSON = json.getJSONObject("Error");
				i = errorJSON.getInt("code");
				str2 = errorJSON.getString("Message");
			} catch (Exception localException) {
				log.warn("analyze bcs error response json failed.", localException);
			}
		}
		BCSServiceException localBCSServiceException = new BCSServiceException("[StatusCode:" + paramBCSHttpResponse.getStatusCode() + "] [ErrorMsg:" + str1 + "]");
		localBCSServiceException.setHttpErrorCode(paramBCSHttpResponse.getStatusCode());
		localBCSServiceException.setRequestId(paramBCSHttpResponse.getHeaders().get("x-bs-request-id"));
		localBCSServiceException.setBcsErrorCode(i);
		localBCSServiceException.setBcsErrorMessage(str2);
		localBaiduBCSResponse.setResult(localBCSServiceException);
		return localBaiduBCSResponse;
	}
}
