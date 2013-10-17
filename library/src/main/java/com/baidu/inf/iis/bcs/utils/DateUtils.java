package com.baidu.inf.iis.bcs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class DateUtils {
	protected final SimpleDateFormat iso8601DateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	protected final SimpleDateFormat alternateIo8601DateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	protected final SimpleDateFormat rfc822DateParser = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

	public DateUtils() {
		this.iso8601DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
		this.rfc822DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
		this.alternateIo8601DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
	}

	public String formatIso8601Date(Date paramDate) {
		synchronized (this.iso8601DateParser) {
			return this.iso8601DateParser.format(paramDate);
		}
	}

	public String formatRfc822Date(Date paramDate) {
		synchronized (this.rfc822DateParser) {
			return this.rfc822DateParser.format(paramDate);
		}
	}

	public Date parseIso8601Date(String paramString) throws ParseException {
		try {
			synchronized (this.iso8601DateParser) {
				return this.iso8601DateParser.parse(paramString);
			}
		} catch (ParseException localParseException) {
			synchronized (this.alternateIo8601DateParser) {
				return this.alternateIo8601DateParser.parse(paramString);
			}
		}
	}

	public Date parseRfc822Date(String paramString) throws ParseException {
		synchronized (this.rfc822DateParser) {
			return this.rfc822DateParser.parse(paramString);
		}
	}
}
