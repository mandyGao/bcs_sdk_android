package com.baidu.inf.iis.bcs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Mimetypes {
	
	private static Logger logger = LoggerFactory.getLogger(Mimetypes.class);
	
	public static final String MIMETYPE_XML = "application/xml";
	public static final String MIMETYPE_HTML = "text/html";
	public static final String MIMETYPE_OCTET_STREAM = "application/octet-stream";
	public static final String MIMETYPE_GZIP = "application/x-gzip";
	private static Mimetypes mimetypes = null;

	public static synchronized Mimetypes getInstance() {
		if (mimetypes != null) {
			return mimetypes;
		}

		mimetypes = new Mimetypes();
		InputStream localInputStream = mimetypes.getClass().getResourceAsStream("/mime.types");
		if (localInputStream != null) {
			logger.info("Loading mime types from file in the classpath: mime.types");
			try {
				mimetypes.loadAndReplaceMimetypes(localInputStream);
			} catch (IOException localIOException) {
				logger.error("Failed to load mime types from file in the classpath: mime.types", localIOException);
			}
		} else {
			logger.warn("Unable to find 'mime.types' file in classpath");
		}
		return mimetypes;
	}

	private HashMap<String, String> extensionToMimetypeMap;

	public Mimetypes() {
		this.extensionToMimetypeMap = new HashMap<String, String>();
	}

	public String getMimetype(File paramFile) {
		return getMimetype(paramFile.getName());
	}

	public String getMimetype(String paramString) {
		int i = paramString.lastIndexOf(".");
		if ((i > 0) && (i + 1 < paramString.length())) {
			String str1 = paramString.substring(i + 1);
			if (this.extensionToMimetypeMap.keySet().contains(str1)) {
				String str2 = (String) this.extensionToMimetypeMap.get(str1);
				logger.info("Recognised extension '"+str1+"', mimetype is: '"+str2+"'");
				return str2;
			}
			logger.info("Extension '" + str1 + "' is unrecognized in mime type listing" + ", using default mime type: '" + "application/octet-stream" + "'");

		} else {
			logger.info("File name has no extension, mime type cannot be recognised for: "+paramString);
		}

		return "application/octet-stream";
	}

	public void loadAndReplaceMimetypes(InputStream paramInputStream) throws IOException {
		BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
		String str1 = null;

		while ((str1 = localBufferedReader.readLine()) != null) {
			str1 = str1.trim();

			if (str1.startsWith("#"))
				continue;
			if (str1.length() == 0) {
				continue;
			}
			StringTokenizer localStringTokenizer = new StringTokenizer(str1, " \t");
			if (localStringTokenizer.countTokens() > 1) {
				String str2 = localStringTokenizer.nextToken();
				while (localStringTokenizer.hasMoreTokens()) {
					String str3 = localStringTokenizer.nextToken();
					this.extensionToMimetypeMap.put(str3, str2);
					logger.info("Setting mime type for extension '" + str3 + "' to '" + str2 + "'");
				}
			} else {
				logger.info("Ignoring mimetype with no associated file extensions: '" + str1 + "'");
			}
		}
	}
}
