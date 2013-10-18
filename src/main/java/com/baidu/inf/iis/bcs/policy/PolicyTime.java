/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.policy;

import com.baidu.inf.iis.bcs.model.Pair;
import java.util.ArrayList;
import java.util.List;

public class PolicyTime {
	private List<String> singleTimeList;
	private List<Pair<String>> timeRangeList;

	public PolicyTime() {
		this.singleTimeList = new ArrayList();
		this.timeRangeList = new ArrayList();
	}

	public PolicyTime addSingleTime(String paramString) {
		this.singleTimeList.add(paramString);
		return this;
	}

	public PolicyTime addTimeRange(Pair<String> paramPair) {
		this.timeRangeList.add(paramPair);
		return this;
	}

	public List<String> getSingleTimeList() {
		return this.singleTimeList;
	}

	public List<Pair<String>> getTimeRangeList() {
		return this.timeRangeList;
	}

	public boolean isEmpty() {
		return ((this.singleTimeList.size() <= 0) && (this.timeRangeList.size() <= 0));
	}

	public void setSingleTimeList(List<String> paramList) {
		this.singleTimeList = paramList;
	}

	public void setTimeRangeList(List<Pair<String>> paramList) {
		this.timeRangeList = paramList;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.policy.PolicyTime
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */