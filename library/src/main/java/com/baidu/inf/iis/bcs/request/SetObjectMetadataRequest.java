/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;

public class SetObjectMetadataRequest extends BaiduBCSRequest {
	private ObjectMetadata metadata;

	public SetObjectMetadataRequest(String paramString1, String paramString2,
			ObjectMetadata paramObjectMetadata) {
		super(paramString1, paramString2, HttpMethodName.PUT);
		if (null == paramObjectMetadata) {
			throw new BCSServiceException("Metadata should not be null.");
		}
		this.metadata = paramObjectMetadata;
	}

	public ObjectMetadata getMetadata() {
		return this.metadata;
	}

	public void setMetadata(ObjectMetadata paramObjectMetadata) {
		this.metadata = paramObjectMetadata;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.request.SetObjectMetadataRequest
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */