/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;

public class GenerateUrlRequest extends BaiduBCSRequest {
	BCSSignCondition bcsSignCondition;

	public GenerateUrlRequest(HttpMethodName paramHttpMethodName,
			String paramString1, String paramString2) {
		super(paramString1, paramString2, paramHttpMethodName);
	}

	public BCSSignCondition getBcsSignCondition() {
		return this.bcsSignCondition;
	}

	public void setBcsSignCondition(BCSSignCondition paramBCSSignCondition) {
		this.bcsSignCondition = paramBCSSignCondition;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.request.GenerateUrlRequest
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */