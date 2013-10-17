package com.baidu.inf.iis.bcs.model;

public enum X_BS_ACL {
	Private("private"), 
	PublicRead("public-read"), 
	PublicWrite("public-write"), 
	PublicReadWrite("public-read-write"), 
	PublicControl("public-control");

	@Override
	public String toString() {
		return original;
	}

	private String original = "";

	private X_BS_ACL(String original) {
		this.original = original;
	}

}
