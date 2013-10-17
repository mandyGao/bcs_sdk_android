/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.model;

public class BucketSummary {
	private String bucket;
	private Long cdatatime;
	private Long usedCapacity;
	private Long totalCapacity;

	public BucketSummary(String paramString) {
		this.bucket = paramString;
	}

	public String getBucket() {
		return this.bucket;
	}

	public Long getCdatatime() {
		return this.cdatatime;
	}

	public Long getTotalCapacity() {
		return this.totalCapacity;
	}

	public Long getUsedCapacity() {
		return this.usedCapacity;
	}

	public void setBucket(String paramString) {
		this.bucket = paramString;
	}

	public void setCdatatime(Long paramLong) {
		this.cdatatime = paramLong;
	}

	public void setTotalCapacity(Long paramLong) {
		this.totalCapacity = paramLong;
	}

	public void setUsedCapacity(Long paramLong) {
		this.usedCapacity = paramLong;
	}

	public String toString() {
		return "BCSBucket [bucket=" + this.bucket + ", cdatatime="
				+ this.cdatatime + ", usedCapacity=" + this.usedCapacity
				+ ", totalCapacity=" + this.totalCapacity + "]";
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.model.BucketSummary
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */