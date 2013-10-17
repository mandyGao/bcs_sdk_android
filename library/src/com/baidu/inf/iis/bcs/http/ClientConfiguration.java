/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.http;

public class ClientConfiguration {
	private String userAgent;
	private int maxErrorRetry;
	private Protocol protocol;
	private String proxyHost;
	private int proxyPort;
	private String proxyUsername;
	private String proxyPassword;
	private String proxyDomain;
	private String proxyWorkstation;
	private int maxConnections;
	private int maxConnectionsPerRoute;
	private int socketTimeout;
	private int connectionTimeout;
	private int socketSendBufferSizeHint;
	private int socketReceiveBufferSizeHint;

	public ClientConfiguration() {
		this.userAgent = "baidu-bcs-java-sdk/1";

		this.maxErrorRetry = 3;

		this.protocol = Protocol.HTTP;

		this.proxyHost = null;

		this.proxyPort = -1;

		this.proxyUsername = null;

		this.proxyPassword = null;

		this.proxyDomain = null;

		this.proxyWorkstation = null;

		this.maxConnections = 200;

		this.maxConnectionsPerRoute = 100;

		this.socketTimeout = 50000;

		this.connectionTimeout = 50000;

		this.socketSendBufferSizeHint = 0;

		this.socketReceiveBufferSizeHint = 0;
	}

	public int getConnectionTimeout() {
		return this.connectionTimeout;
	}

	public int getMaxConnections() {
		return this.maxConnections;
	}

	public int getMaxConnectionsPerRoute() {
		return this.maxConnectionsPerRoute;
	}

	public int getMaxErrorRetry() {
		return this.maxErrorRetry;
	}

	public Protocol getProtocol() {
		return this.protocol;
	}

	public String getProxyDomain() {
		return this.proxyDomain;
	}

	public String getProxyHost() {
		return this.proxyHost;
	}

	public String getProxyPassword() {
		return this.proxyPassword;
	}

	public int getProxyPort() {
		return this.proxyPort;
	}

	public String getProxyUsername() {
		return this.proxyUsername;
	}

	public String getProxyWorkstation() {
		return this.proxyWorkstation;
	}

	public int[] getSocketBufferSizeHints() {
		return new int[] { this.socketSendBufferSizeHint,
				this.socketReceiveBufferSizeHint };
	}

	public int getSocketTimeout() {
		return this.socketTimeout;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setConnectionTimeout(int paramInt) {
		this.connectionTimeout = paramInt;
	}

	public void setMaxConnections(int paramInt) {
		this.maxConnections = paramInt;
	}

	public void setMaxConnectionsPerRoute(int paramInt) {
		this.maxConnectionsPerRoute = paramInt;
	}

	public void setMaxErrorRetry(int paramInt) {
		this.maxErrorRetry = paramInt;
	}

	public void setProtocol(Protocol paramProtocol) {
		this.protocol = paramProtocol;
	}

	public void setProxyDomain(String paramString) {
		this.proxyDomain = paramString;
	}

	public void setProxyHost(String paramString) {
		this.proxyHost = paramString;
	}

	public void setProxyPassword(String paramString) {
		this.proxyPassword = paramString;
	}

	public void setProxyPort(int paramInt) {
		this.proxyPort = paramInt;
	}

	public void setProxyUsername(String paramString) {
		this.proxyUsername = paramString;
	}

	public void setProxyWorkstation(String paramString) {
		this.proxyWorkstation = paramString;
	}

	public void setSocketBufferSizeHints(int paramInt1, int paramInt2) {
		this.socketSendBufferSizeHint = paramInt1;
		this.socketReceiveBufferSizeHint = paramInt2;
	}

	public void setSocketTimeout(int paramInt) {
		this.socketTimeout = paramInt;
	}

	public void setUserAgent(String paramString) {
		this.userAgent = paramString;
	}

	public ClientConfiguration withConnectionTimeout(int paramInt) {
		setConnectionTimeout(paramInt);
		return this;
	}

	public ClientConfiguration withMaxConnections(int paramInt) {
		setMaxConnections(paramInt);
		return this;
	}

	public ClientConfiguration withMaxConnectionsPerRoute(int paramInt) {
		setMaxConnectionsPerRoute(paramInt);
		return this;
	}

	public ClientConfiguration withMaxErrorRetry(int paramInt) {
		setMaxErrorRetry(paramInt);
		return this;
	}

	public ClientConfiguration withProtocol(Protocol paramProtocol) {
		setProtocol(paramProtocol);
		return this;
	}

	public ClientConfiguration withProxyDomain(String paramString) {
		setProxyDomain(paramString);
		return this;
	}

	public ClientConfiguration withProxyHost(String paramString) {
		setProxyHost(paramString);
		return this;
	}

	public ClientConfiguration withProxyPassword(String paramString) {
		setProxyPassword(paramString);
		return this;
	}

	public ClientConfiguration withProxyPort(int paramInt) {
		setProxyPort(paramInt);
		return this;
	}

	public ClientConfiguration withProxyUsername(String paramString) {
		setProxyUsername(paramString);
		return this;
	}

	public ClientConfiguration withProxyWorkstation(String paramString) {
		setProxyWorkstation(paramString);
		return this;
	}

	public ClientConfiguration withSocketBufferSizeHints(int paramInt1,
			int paramInt2) {
		setSocketBufferSizeHints(paramInt1, paramInt2);
		return this;
	}

	public ClientConfiguration withSocketTimeout(int paramInt) {
		setSocketTimeout(paramInt);
		return this;
	}
}

/* Location:           E:\Downloads\Chrome\bcs-Baidu-BCS-SDK-Java-1.4.5\Baidu-BCS-SDK-Java-1.4.5\bcs-sdk-java_1.4.5.jar
 * Qualified Name:     com.baidu.inf.iis.bcs.http.ClientConfiguration
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.5.3
 */