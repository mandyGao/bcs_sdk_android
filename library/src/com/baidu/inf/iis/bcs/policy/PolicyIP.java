/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.policy;

import com.baidu.inf.iis.bcs.model.Pair;
import java.util.ArrayList;
import java.util.List;

public class PolicyIP {
	private List<String> singleIpList;
	private List<String> cidrIpList;
	private List<Pair<String>> ipRangeList;

	public PolicyIP() {
		this.singleIpList = new ArrayList();
		this.cidrIpList = new ArrayList();
		this.ipRangeList = new ArrayList();
	}

	public PolicyIP addCidrIp(String paramString) {
		this.cidrIpList.add(paramString);
		return this;
	}

	public PolicyIP addIpRange(Pair<String> paramPair) {
		this.ipRangeList.add(paramPair);
		return this;
	}

	public PolicyIP addSingleIp(String paramString) {
		this.singleIpList.add(paramString);
		return this;
	}

	public List<String> getCidrIpList() {
		return this.cidrIpList;
	}

	public List<Pair<String>> getIpRangeList() {
		return this.ipRangeList;
	}

	public List<String> getSingleIpList() {
		return this.singleIpList;
	}

	public boolean isEmpty() {
		return ((this.singleIpList.size() <= 0)
				&& (this.cidrIpList.size() <= 0) && (this.ipRangeList.size() <= 0));
	}

	public void setCidrIpList(List<String> paramList) {
		this.cidrIpList = paramList;
	}

	public void setIpRangeList(List<Pair<String>> paramList) {
		this.ipRangeList = paramList;
	}

	public void setSingleIpList(List<String> paramList) {
		this.singleIpList = paramList;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.policy.PolicyIP
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */