/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectListing {
	private List<ObjectSummary> objectSummaries;
	private String bucket;
	private int objectTotal;
	private String prefix;
	private int start;
	private int limit;

	public ObjectListing() {
		this.objectSummaries = new ArrayList();
	}

	public ObjectListing addObjectSummary(ObjectSummary paramObjectSummary) {
		this.objectSummaries.add(paramObjectSummary);
		return this;
	}

	public String getBucket() {
		return this.bucket;
	}

	public int getLimit() {
		return this.limit;
	}

	public List<ObjectSummary> getObjectSummaries() {
		return this.objectSummaries;
	}

	public int getObjectTotal() {
		return this.objectTotal;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public int getStart() {
		return this.start;
	}

	public void setBucket(String paramString) {
		this.bucket = paramString;
	}

	public void setLimit(int paramInt) {
		this.limit = paramInt;
	}

	public void setObjectSummaries(List<ObjectSummary> paramList) {
		this.objectSummaries = paramList;
	}

	public void setObjectTotal(int paramInt) {
		this.objectTotal = paramInt;
	}

	public void setPrefix(String paramString) {
		this.prefix = paramString;
	}

	public void setStart(int paramInt) {
		this.start = paramInt;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.model.ObjectListing
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */