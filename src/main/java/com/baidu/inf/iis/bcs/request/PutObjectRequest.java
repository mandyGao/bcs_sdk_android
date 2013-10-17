/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import java.io.File;
import java.io.InputStream;

public class PutObjectRequest extends BaiduBCSRequest {
	private File file = null;
	private InputStream objectContent = null;
	private ObjectMetadata metadata = null;
	private X_BS_ACL acl = null;

	public PutObjectRequest(String paramString1, String paramString2,
			File paramFile) {
		super(paramString1, paramString2, HttpMethodName.PUT);
		this.file = paramFile;
	}

	public PutObjectRequest(String paramString1, String paramString2,
			InputStream paramInputStream, ObjectMetadata paramObjectMetadata) {
		super(paramString1, paramString2, HttpMethodName.PUT);
		this.objectContent = paramInputStream;
		this.metadata = paramObjectMetadata;
	}

	public X_BS_ACL getAcl() {
		return this.acl;
	}

	public File getFile() {
		return this.file;
	}

	public ObjectMetadata getMetadata() {
		return this.metadata;
	}

	public InputStream getObjectContent() {
		return this.objectContent;
	}

	public void setAcl(X_BS_ACL paramX_BS_ACL) {
		this.acl = paramX_BS_ACL;
	}

	public void setFile(File paramFile) {
		this.file = paramFile;
	}

	public void setMetadata(ObjectMetadata paramObjectMetadata) {
		this.metadata = paramObjectMetadata;
	}

	public void setObjectContent(InputStream paramInputStream) {
		this.objectContent = paramInputStream;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.request.PutObjectRequest
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */