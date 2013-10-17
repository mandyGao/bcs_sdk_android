package com.baidu.inf.iis.bcs.utils;

public class StringUtils {
	public static String trimSlash(String paramString) {
		String str = paramString.replaceAll("//", "/");
		if (!(paramString.equals(str))) {
			return trimSlash(str);
		}
		return str;
	}
}
