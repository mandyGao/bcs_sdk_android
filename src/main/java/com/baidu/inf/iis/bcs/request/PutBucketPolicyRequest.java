/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.policy.Policy;

public class PutBucketPolicyRequest extends BaiduBCSRequest {
	private Policy policy;
	private X_BS_ACL acl;

	public PutBucketPolicyRequest(String paramString, Policy paramPolicy) {
		super(paramString, HttpMethodName.PUT);
		this.policy = paramPolicy;
	}

	public PutBucketPolicyRequest(String paramString, X_BS_ACL paramX_BS_ACL) {
		super(paramString, HttpMethodName.PUT);
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
