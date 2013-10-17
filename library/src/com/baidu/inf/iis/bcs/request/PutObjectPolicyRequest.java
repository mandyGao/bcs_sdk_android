/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.policy.Policy;

public class PutObjectPolicyRequest extends BaiduBCSRequest {
	private Policy policy;
	private X_BS_ACL acl;

	public PutObjectPolicyRequest(String paramString1, String paramString2,
			Policy paramPolicy) {
		super(paramString1, paramString2, HttpMethodName.PUT);
		this.policy = paramPolicy;
	}

	public PutObjectPolicyRequest(String paramString1, String paramString2,
			X_BS_ACL paramX_BS_ACL) {
		super(paramString1, paramString2, HttpMethodName.PUT);
		this.acl = paramX_BS_ACL;
	}

	public X_BS_ACL getAcl() {
		return this.acl;
	}

	public Policy getPolicy() {
		return this.policy;
	}

	public void setAcl(X_BS_ACL paramX_BS_ACL) {
		this.acl = paramX_BS_ACL;
	}

	public void setPolicy(Policy paramPolicy) {
		this.policy = paramPolicy;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.request.PutObjectPolicyRequest
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */