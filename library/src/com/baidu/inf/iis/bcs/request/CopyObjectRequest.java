/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.Resource;

public class CopyObjectRequest extends BaiduBCSRequest {
	private ObjectMetadata destMetadata;
	private Resource source;
	private Resource dest;
	private String sourceEtag;
	private String sourceDirective;

	public CopyObjectRequest(Resource paramResource1, Resource paramResource2) {
		super(paramResource2.getBucket(), paramResource2.getObject(),
				HttpMethodName.PUT);
		this.source = paramResource1;
		this.dest = paramResource2;
	}

	public CopyObjectRequest(Resource paramResource1, Resource paramResource2,
			ObjectMetadata paramObjectMetadata) {
		super(paramResource2.getBucket(), paramResource2.getObject(),
				HttpMethodName.PUT);
		this.destMetadata = paramObjectMetadata;
		this.source = paramResource1;
		this.dest = paramResource2;
	}

	public Resource getDest() {
		return this.dest;
	}

	public ObjectMetadata getDestMetadata() {
		return this.destMetadata;
	}

	public Resource getSource() {
		return this.source;
	}

	public String getSourceDirective() {
		return this.sourceDirective;
	}

	public String getSourceEtag() {
		return this.sourceEtag;
	}

	public void setDest(Resource paramResource) {
		this.dest = paramResource;
	}

	public void setDestMetadata(ObjectMetadata paramObjectMetadata) {
		this.destMetadata = paramObjectMetadata;
	}

	public void setSource(Resource paramResource) {
		this.source = paramResource;
	}

	public void setSourceDirective(String paramString) {
		this.sourceDirective = paramString;
	}

	public void setSourceEtag(String paramString) {
		this.sourceEtag = paramString;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.request.CopyObjectRequest
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */