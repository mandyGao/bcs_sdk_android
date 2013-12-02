package com.baidu.inf.iis.bcs.utils;

import android.util.Log;

public class Logger {

	public static int LEVEL_TRACE = 0;
	public static int LEVEL_DEBUG = 1;
	public static int LEVEL_INFO = 2;
	public static int LEVEL_WARN = 3;
	public static int LEVEL_ERROR = 4;

	private String tag;
	private int level = LEVEL_ERROR;

	public Logger(String tag) {
		super();
		this.tag = tag;
	}

	public void trace(String string) {
		if (level <= LEVEL_TRACE) {
			Log.v(tag, string);
		}
	}

	public void debug(String string) {
		if (level <= LEVEL_DEBUG) {
			Log.d(tag, string);
		}
	}

	public void info(String string) {
		if (level <= LEVEL_INFO) {
			Log.i(tag, string);
		}
	}

	public void warn(String string) {
		if (level <= LEVEL_WARN) {
			Log.w(tag, string);
		}
	}
	
	public void warn(String string, Throwable throwable) {
		if (level <= LEVEL_WARN) {
			Log.w(tag, string, throwable);
		}
	}

	public void error(String string) {
		if (level <= LEVEL_ERROR) {
			Log.e(tag, string);
		}
	}

	public void error(String string, Throwable throwable) {
		if (level <= LEVEL_ERROR) {
			Log.e(tag, string, throwable);
		}
	}

}
