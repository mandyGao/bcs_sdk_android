/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.Pair;

public class GetObjectRequest extends BaiduBCSRequest {
	private String versionKey;
	private Pair<Long> range;
	private ObjectMetadata objectMetadata;

	public GetObjectRequest(String paramString1, String paramString2) {
		super(paramString1, paramString2, HttpMethodName.GET);
	}

	public GetObjectRequest(String paramString1, String paramString2,
			String paramString3) {
		super(paramString1, paramString2, HttpMethodName.GET);
		this.versionKey = paramString3;
	}

	public ObjectMetadata getObjectMetadata() {
		return this.objectMetadata;
	}

	public Pair<Long> getRange() {
		return this.range;
	}

	public String getVersionKey() {
		return this.versionKey;
	}

	public void setObjectMetadata(ObjectMetadata paramObjectMetadata) {
		this.objectMetadata = paramObjectMetadata;
	}

	public void setRange(Pair<Long> paramPair) {
		this.range = paramPair;
	}

	public void setVersionKey(String paramString) {
		this.versionKey = paramString;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.request.GetObjectRequest
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */