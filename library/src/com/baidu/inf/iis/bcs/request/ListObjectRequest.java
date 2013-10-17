/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.request;

import com.baidu.inf.iis.bcs.http.HttpMethodName;

public class ListObjectRequest extends BaiduBCSRequest {
	private String prefix = null;
	private int start = -1;
	private int limit = -1;
	private int listModel = 0;

	public ListObjectRequest(String paramString) {
		super(paramString, HttpMethodName.GET);
	}

	public int getLimit() {
		return this.limit;
	}

	public int getListModel() {
		return this.listModel;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public int getStart() {
		return this.start;
	}

	public void setLimit(int paramInt) {
		this.limit = paramInt;
	}

	public void setListModel(int paramInt) {
		this.listModel = paramInt;
	}

	public void setPrefix(String paramString) {
		this.prefix = paramString;
	}

	public void setStart(int paramInt) {
		this.start = paramInt;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.request.ListObjectRequest
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */