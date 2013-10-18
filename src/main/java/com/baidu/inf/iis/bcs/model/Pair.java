/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.model;

import java.util.ArrayList;
import java.util.List;

public class Pair<T> {
	private T first;
	private T second;

	public Pair(T paramT1, T paramT2) {
		this.first = paramT1;
		this.second = paramT2;
	}

	public T getFirst() {
		return this.first;
	}

	public T getSecond() {
		return this.second;
	}

	public void setFirst(T paramT) {
		this.first = paramT;
	}

	public void setSecond(T paramT) {
		this.second = paramT;
	}

	public List<T> toArrayList() {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(this.first);
		localArrayList.add(this.second);
		return localArrayList;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.model.Pair
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */