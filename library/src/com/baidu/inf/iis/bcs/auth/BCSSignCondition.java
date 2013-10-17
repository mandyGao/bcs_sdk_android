package com.baidu.inf.iis.bcs.auth;

public class BCSSignCondition {
	private Long time;
	private String ip;
	private Long size;

	public BCSSignCondition() {
		this.time = 0L;
		this.ip = "";
		this.size = 0L;
	}

	public String getIp() {
		return this.ip;
	}

	public Long getSize() {
		return this.size;
	}

	public Long getTime() {
		return this.time;
	}

	public void setIp(String paramString) {
		this.ip = paramString;
	}

	public void setSize(Long paramLong) {
		this.size = paramLong;
	}

	public void setTime(Long paramLong) {
		this.time = paramLong;
	}
}
