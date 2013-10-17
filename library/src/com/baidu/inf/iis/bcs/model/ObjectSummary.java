/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.model;

public class ObjectSummary {
	private String name;
	private Long size;
	private Long lastModifiedTime;
	private String versionKey;
	private boolean isSuperfile;
	private String parentDir;
	private boolean isDir;

	public Long getLastModifiedTime() {
		return this.lastModifiedTime;
	}

	public String getName() {
		return this.name;
	}

	public String getParentDir() {
		return this.parentDir;
	}

	public Long getSize() {
		return this.size;
	}

	public String getVersionKey() {
		return this.versionKey;
	}

	public boolean isDir() {
		return this.isDir;
	}

	public boolean isSuperfile() {
		return this.isSuperfile;
	}

	public void setIsDir(boolean paramBoolean) {
		this.isDir = paramBoolean;
	}

	public void setLastModifiedTime(Long paramLong) {
		this.lastModifiedTime = paramLong;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setParentDir(String paramString) {
		this.parentDir = paramString;
	}

	public void setSize(Long paramLong) {
		this.size = paramLong;
	}

	public void setSuperfile(boolean paramBoolean) {
		this.isSuperfile = paramBoolean;
	}

	public void setVersionKey(String paramString) {
		this.versionKey = paramString;
	}

	public String toString() {
		return "ObjectSummary [name=" + this.name + ", size=" + this.size
				+ ", lastModifiedTime=" + this.lastModifiedTime
				+ ", versionKey=" + this.versionKey + ", isSuperfile="
				+ this.isSuperfile + ", parentDir=" + this.parentDir
				+ ", isDir=" + this.isDir + "]";
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.model.ObjectSummary
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */