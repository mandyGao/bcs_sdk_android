package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.SuperfileSubObject;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import java.util.List;

public class PutSuperfileRequest extends BaiduBCSRequest {
	private List<SuperfileSubObject> subObjectList = null;
	private ObjectMetadata objectMetadata = null;
	private X_BS_ACL acl = null;

	public PutSuperfileRequest(String paramString1, String paramString2, List<SuperfileSubObject> paramList) {
		super(paramString1, paramString2, HttpMethodName.PUT);
		this.subObjectList = paramList;
	}

	public PutSuperfileRequest(String paramString1, String paramString2, ObjectMetadata paramObjectMetadata, List<SuperfileSubObject> paramList) {
		super(paramString1, paramString2, HttpMethodName.PUT);
		this.subObjectList = paramList;
		this.objectMetadata = paramObjectMetadata;
	}

	public X_BS_ACL getAcl() {
		return this.acl;
	}

	public ObjectMetadata getObjectMetadata() {
		return this.objectMetadata;
	}

	public List<SuperfileSubObject> getSubObjectList() {
		return this.subObjectList;
	}

	public void setAcl(X_BS_ACL paramX_BS_ACL) {
		this.acl = paramX_BS_ACL;
	}

	public void setObjectMetadata(ObjectMetadata paramObjectMetadata) {
		this.objectMetadata = paramObjectMetadata;
	}

	public void setSubObjectList(List<SuperfileSubObject> paramList) {
		this.subObjectList = paramList;
	}
}
