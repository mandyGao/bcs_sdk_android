package com.baidu.inf.iis.bcs.utils;

public class LoggerFactory {
	public static Logger getLogger(Class<?> clazz) {
		return new Logger(clazz.getName());
	}
}
