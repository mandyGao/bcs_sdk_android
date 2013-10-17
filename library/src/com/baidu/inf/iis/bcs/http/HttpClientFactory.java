/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.baidu.inf.iis.bcs.http;

import com.baidu.inf.iis.bcs.model.BCSClientException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HttpClientFactory {
	private class TrustAllSSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public TrustAllSSLSocketFactory(KeyStore paramKeyStore)
				throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(paramKeyStore);

			HttpClientFactory.TrustingX509TrustManager localTrustingX509TrustManager = new HttpClientFactory.TrustingX509TrustManager();

			this.sslContext.init(null, new TrustManager[] { localTrustingX509TrustManager }, null);
		}

		public Socket createSocket() throws IOException {
			return this.sslContext.getSocketFactory().createSocket();
		}

		public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean) 
				throws IOException, UnknownHostException {
			return this.sslContext.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
		}
	}

//	private static class TrustingSocketFactory implements SocketFactory, LayeredSocketFactory {
//		
//		private static SSLContext createSSLContext() throws IOException {
//			try {
//				SSLContext localSSLContext = SSLContext.getInstance("TLS");
//				localSSLContext.init(null, new TrustManager[] { new HttpClientFactory.TrustingX509TrustManager() }, null);
//				return localSSLContext;
//			} catch (Exception localException) {
//				throw new IOException(localException.getMessage());
//			}
//		}
//
//		private SSLContext sslcontext;
//
//		private TrustingSocketFactory() {
//			this.sslcontext = null;
//		}
//
//		public Socket connectSocket(Socket paramSocket,
//				InetSocketAddress paramInetSocketAddress1,
//				InetSocketAddress paramInetSocketAddress2,
//				HttpParams paramHttpParams) throws IOException,
//				UnknownHostException, ConnectTimeoutException {
//			int i = HttpConnectionParams.getConnectionTimeout(paramHttpParams);
//			int j = HttpConnectionParams.getSoTimeout(paramHttpParams);
//
//			SSLSocket localSSLSocket = (SSLSocket) ((paramSocket != null) ? paramSocket
//					: createSocket(paramHttpParams));
//			if (paramInetSocketAddress2 != null) {
//				localSSLSocket.bind(paramInetSocketAddress2);
//			}
//			localSSLSocket.connect(paramInetSocketAddress1, i);
//			localSSLSocket.setSoTimeout(j);
//			return localSSLSocket;
//		}
//
//		public Socket createLayeredSocket(Socket paramSocket,
//				String paramString, int paramInt, boolean paramBoolean)
//				throws IOException, UnknownHostException {
//			return getSSLContext().getSocketFactory().createSocket();
//		}
//
//		public Socket createSocket(HttpParams paramHttpParams)
//				throws IOException {
//			return getSSLContext().getSocketFactory().createSocket();
//		}
//
//		private SSLContext getSSLContext() throws IOException {
//			if (this.sslcontext == null)
//				this.sslcontext = createSSLContext();
//			return this.sslcontext;
//		}
//
//		public boolean isSecure(Socket paramSocket)
//				throws IllegalArgumentException {
//			return true;
//		}
//
//		@Override
//		public Socket createSocket(Socket arg0, String arg1, int arg2,
//				boolean arg3) throws IOException, UnknownHostException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public Socket connectSocket(Socket sock, String host, int port,
//				InetAddress localAddress, int localPort, HttpParams params)
//				throws IOException, UnknownHostException,
//				ConnectTimeoutException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public Socket createSocket() throws IOException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	}

	private static class TrustingX509TrustManager implements X509TrustManager {
		private static final X509Certificate[] X509_CERTIFICATES = new X509Certificate[0];

		public void checkClientTrusted(
				X509Certificate[] paramArrayOfX509Certificate,
				String paramString) throws CertificateException {
		}

		public void checkServerTrusted(
				X509Certificate[] paramArrayOfX509Certificate,
				String paramString) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return X509_CERTIFICATES;
		}
	}

	public HttpClient createHttpClient(
			ClientConfiguration paramClientConfiguration) {
		BasicHttpParams localBasicHttpParams = new BasicHttpParams();
		HttpProtocolParams.setUserAgent(localBasicHttpParams,
				paramClientConfiguration.getUserAgent());
		HttpProtocolParams.setVersion(localBasicHttpParams,
				HttpVersion.HTTP_1_1);
		HttpConnectionParams.setConnectionTimeout(localBasicHttpParams,
				paramClientConfiguration.getConnectionTimeout());
		HttpConnectionParams.setSoTimeout(localBasicHttpParams,
				paramClientConfiguration.getSocketTimeout());
		HttpConnectionParams
				.setStaleCheckingEnabled(localBasicHttpParams, true);
		HttpConnectionParams.setTcpNoDelay(localBasicHttpParams, true);

		int i = paramClientConfiguration.getSocketBufferSizeHints()[0];
		int j = paramClientConfiguration.getSocketBufferSizeHints()[1];
		if ((i > 0) || (j > 0)) {
			HttpConnectionParams.setSocketBufferSize(localBasicHttpParams,
					Math.max(i, j));
		}

		SchemeRegistry localSchemeRegistry = new SchemeRegistry();

		localSchemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		try {
			KeyStore localKeyStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			localKeyStore.load(null, null);
			localSchemeRegistry.register(new Scheme("https",
					new TrustAllSSLSocketFactory(localKeyStore), 443));
		} catch (Exception localException) {
			throw new BCSClientException("Can not enable ssl.", localException);
		}

		ConnManagerParams.setMaxTotalConnections(localBasicHttpParams,
				paramClientConfiguration.getMaxConnections());
		ConnManagerParams.setMaxConnectionsPerRoute(
				localBasicHttpParams,
				new ConnPerRouteBean(paramClientConfiguration
						.getMaxConnectionsPerRoute()));
		ThreadSafeClientConnManager localThreadSafeClientConnManager = new ThreadSafeClientConnManager(
				localBasicHttpParams, localSchemeRegistry);

		DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(
				localThreadSafeClientConnManager, localBasicHttpParams);

		String str1 = paramClientConfiguration.getProxyHost();
		int k = paramClientConfiguration.getProxyPort();
		if ((str1 != null) && (k > 0)) {
			HttpHost localHttpHost = new HttpHost(str1, k);
			localDefaultHttpClient.getParams().setParameter(
					"http.route.default-proxy", localHttpHost);

			String str2 = paramClientConfiguration.getProxyUsername();
			String str3 = paramClientConfiguration.getProxyPassword();
			String str4 = paramClientConfiguration.getProxyDomain();
			String str5 = paramClientConfiguration.getProxyWorkstation();

			if ((str2 != null) && (str3 != null)) {
				localDefaultHttpClient.getCredentialsProvider().setCredentials(
						new AuthScope(str1, k),
						new NTCredentials(str2, str3, str5, str4));
			}

		}

		return localDefaultHttpClient;
	}
}

