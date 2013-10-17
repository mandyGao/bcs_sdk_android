package com.baidu.inf.iis.bcs.auth;

public class BCSCredentials {
	private String accessKey;
	private String secretKey;

	public BCSCredentials(String paramString1, String paramString2) {
		this.accessKey = paramString1;
		this.secretKey = paramString2;
	}

	public String getAccessKey() {
		return this.accessKey;
	}

	public String getSecretKey() {
		return this.secretKey;
	}
}
