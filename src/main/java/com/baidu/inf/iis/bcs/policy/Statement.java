/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.policy;

import java.util.ArrayList;
import java.util.List;

public class Statement {
	private List<String> user;
	private List<String> resource;
	private List<PolicyAction> action;
	private PolicyEffect effect;
	private PolicyTime time;
	private PolicyIP ip;

	public Statement() {
		this.user = new ArrayList();
		this.resource = new ArrayList();
		this.action = new ArrayList();

		this.time = null;
		this.ip = null;
	}

	public Statement addAction(PolicyAction paramPolicyAction) {
		this.action.add(paramPolicyAction);
		return this;
	}

	public Statement addResource(String paramString) {
		this.resource.add(paramString);
		return this;
	}

	public Statement addUser(String paramString) {
		this.user.add(paramString);
		return this;
	}

	public List<PolicyAction> getAction() {
		return this.action;
	}

	public PolicyEffect getEffect() {
		return this.effect;
	}

	public PolicyIP getIp() {
		return this.ip;
	}

	public List<String> getResource() {
		return this.resource;
	}

	public PolicyTime getTime() {
		return this.time;
	}

	public List<String> getUser() {
		return this.user;
	}

	public void setAction(List<PolicyAction> paramList) {
		this.action = paramList;
	}

	public void setEffect(PolicyEffect paramPolicyEffect) {
		this.effect = paramPolicyEffect;
	}

	public void setIp(PolicyIP paramPolicyIP) {
		this.ip = paramPolicyIP;
	}

	public void setResource(List<String> paramList) {
		this.resource = paramList;
	}

	public void setTime(PolicyTime paramPolicyTime) {
		this.time = paramPolicyTime;
	}

	public void setUser(List<String> paramList) {
		this.user = paramList;
	}
}
