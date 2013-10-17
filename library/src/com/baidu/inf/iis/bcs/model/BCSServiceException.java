/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.model;

public class BCSServiceException extends BCSClientException {
	private static final long serialVersionUID = -6120510420311024191L;
	private String requestId;
	private int httpErrorCode;
	private int bcsErrorCode;
	private String bcsErrorMessage;

	public BCSServiceException(String paramString) {
		super(paramString);
	}

	public BCSServiceException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public int getBcsErrorCode() {
		return this.bcsErrorCode;
	}

	public String getBcsErrorMessage() {
		return this.bcsErrorMessage;
	}

	public int getHttpErrorCode() {
		return this.httpErrorCode;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setBcsErrorCode(int paramInt) {
		this.bcsErrorCode = paramInt;
	}

	public void setBcsErrorMessage(String paramString) {
		this.bcsErrorMessage = paramString;
	}

	public void setHttpErrorCode(int paramInt) {
		this.httpErrorCode = paramInt;
	}

	public void setRequestId(String paramString) {
		this.requestId = paramString;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.model.BCSServiceException
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */