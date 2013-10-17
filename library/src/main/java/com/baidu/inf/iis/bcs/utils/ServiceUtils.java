package com.baidu.inf.iis.bcs.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.util.Base64;

import com.baidu.inf.iis.bcs.http.BCSHttpRequest;
import com.baidu.inf.iis.bcs.model.BCSClientException;

public class ServiceUtils {
	private static final Logger log = LoggerFactory.getLogger(ServiceUtils.class);

	protected static final DateUtils dateUtils = new DateUtils();

	public static byte[] computeMD5Hash(byte[] paramArrayOfByte)
			throws NoSuchAlgorithmException, IOException {
		return computeMD5Hash(new ByteArrayInputStream(paramArrayOfByte));
	}

	public static byte[] computeMD5Hash(InputStream paramInputStream)
			throws NoSuchAlgorithmException, IOException {
		BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream);
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			byte[] arrayOfByte1 = new byte[16384];
			int i = -1;
			while ((i = localBufferedInputStream.read(arrayOfByte1, 0, arrayOfByte1.length)) != -1) {
				localMessageDigest.update(arrayOfByte1, 0, i);
			}
			byte[] arrayOfByte2 = localMessageDigest.digest();
			byte[] arrayOfByte3 = arrayOfByte2;

			return arrayOfByte3;
		} finally {
			try {
				localBufferedInputStream.close();
			} catch (Exception localException2) {
				log.warn("Unable to close input stream of hash candidate: " + localException2);
			}
		}
	}

	public static URL convertRequestToUrl(BCSHttpRequest paramBCSHttpRequest) {
		String endpoint = paramBCSHttpRequest.getEndpoint() + "/" + paramBCSHttpRequest.getResourcePath();

		int i = 1;
		Map<String, String> param = paramBCSHttpRequest.getParameters();
		
		for (String str2 : paramBCSHttpRequest.getParameters().keySet()) {
			if (i != 0) {
				endpoint = endpoint + "?";
				i = 0;
			} else {
				endpoint = endpoint + "&";
			}

			String str3 = (String) paramBCSHttpRequest.getParameters().get(str2);
			endpoint = endpoint + str2 + "=" + urlEncode(str3);
		}
		try {
			return new URL(endpoint);
		} catch (MalformedURLException localMalformedURLException) {
			throw new BCSClientException("Unable to convert request to well formed URL: " + localMalformedURLException.getMessage(), localMalformedURLException);
		}
	}

	public static String formatIso8601Date(Date paramDate) {
		return dateUtils.formatIso8601Date(paramDate);
	}

	public static String formatRfc822Date(Date paramDate) {
		return dateUtils.formatRfc822Date(paramDate);
	}

	public static byte[] fromBase64(String paramString) {
		byte[] arrayOfByte;
		try {
			arrayOfByte = Base64.decode(paramString.getBytes(Constants.DEFAULT_ENCODING), Base64.DEFAULT);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			log.warn(
					"Tried to Base64-decode a String with the wrong encoding: ",
					localUnsupportedEncodingException);
			arrayOfByte = Base64.decode(paramString.getBytes(), Base64.DEFAULT);
		}
		return arrayOfByte;
	}

	public static byte[] fromHex(String paramString) {
		byte[] arrayOfByte = new byte[(paramString.length() + 1) / 2];
		String str = null;
		int i = 0;
		int j = 0;
		while (i < paramString.length()) {
			str = paramString.substring(i, i + 2);
			i += 2;
			arrayOfByte[(j++)] = (byte) Integer.parseInt(str, 16);
		}
		return arrayOfByte;
	}

	public static String join(List<String> paramList) {
		String str1 = "";

		int i = 1;
		for (String str2 : paramList) {
			if (i == 0) {
				str1 = str1 + ", ";
			}
			str1 = str1 + str2;
			i = 0;
		}

		return str1;
	}

	public static Date parseIso8601Date(String paramString)
			throws ParseException {
		return dateUtils.parseIso8601Date(paramString);
	}

	public static Date parseRfc822Date(String paramString)
			throws ParseException {
		return dateUtils.parseRfc822Date(paramString);
	}

	public static String removeQuotes(String paramString) {
		if (paramString == null) {
			return null;
		}

		paramString = paramString.trim();
		if (paramString.startsWith("\"")) {
			paramString = paramString.substring(1);
		}
		if (paramString.endsWith("\"")) {
			paramString = paramString.substring(0, paramString.length() - 1);
		}

		return paramString;
	}

	public static String toBase64(byte[] paramArrayOfByte) {
		byte[] arrayOfByte = Base64.encode(paramArrayOfByte, Base64.DEFAULT);
		return new String(arrayOfByte);
	}

	public static byte[] toByteArray(String paramString) {
		try {
			return paramString.getBytes(Constants.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			log.warn("Encoding " + Constants.DEFAULT_ENCODING + " is not supported", localUnsupportedEncodingException);
		}
		return paramString.getBytes();
	}

	public static String toHex(byte[] paramArrayOfByte) {
		StringBuilder localStringBuilder = new StringBuilder(paramArrayOfByte.length * 2);
		for (int i = 0; i < paramArrayOfByte.length; ++i) {
			String str = Integer.toHexString(paramArrayOfByte[i]);
			if (str.length() == 1)
				localStringBuilder.append("0");
			else if (str.length() == 8) {
				str = str.substring(6);
			}
			localStringBuilder.append(str);
		}
		return localStringBuilder.toString().toLowerCase(Locale.getDefault());
	}

	public static String urlEncode(String paramString) {
		if (paramString == null) {
			return null;
		}
		try {
			String str = URLEncoder.encode(paramString, Constants.DEFAULT_ENCODING);
			return str.replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			throw new BCSClientException("Unable to encode path: " + paramString, localUnsupportedEncodingException);
		}
	}
}
