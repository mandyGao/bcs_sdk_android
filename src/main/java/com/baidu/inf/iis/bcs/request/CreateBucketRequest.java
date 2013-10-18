package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;

public class CreateBucketRequest extends BaiduBCSRequest {
	private X_BS_ACL acl = null;

	public CreateBucketRequest(String paramString) {
		super(paramString, HttpMethodName.PUT);
	}

	public CreateBucketRequest(String paramString, X_BS_ACL paramX_BS_ACL) {
		super(paramString, HttpMethodName.PUT);
		this.acl = paramX_BS_ACL;
	}

	public X_BS_ACL getAcl() {
		return this.acl;
	}

	public void setAcl(X_BS_ACL paramX_BS_ACL) {
		this.acl = paramX_BS_ACL;
	}
}
