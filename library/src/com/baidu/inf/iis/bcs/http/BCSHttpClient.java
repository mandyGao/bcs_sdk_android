package com.baidu.inf.iis.bcs.http;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

import com.baidu.inf.iis.bcs.handler.ErrorResponseHandler;
import com.baidu.inf.iis.bcs.handler.HttpResponseHandler;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class BCSHttpClient {
	private static final int MAX_BACKOFF_IN_MILLISECONDS = 20000;
	private static final Logger log = LoggerFactory.getLogger(BCSHttpClient.class);

	private HttpRequestFactory httpRequestFactory = new HttpRequestFactory();
	private HttpClientFactory httpClientFactory = new HttpClientFactory();
	private final ClientConfiguration config;
	private HttpClient httpClient;
	private ErrorResponseHandler errorResponseHandler = new ErrorResponseHandler();

	public BCSHttpClient(ClientConfiguration paramClientConfiguration) {
		this.config = paramClientConfiguration;
		this.httpClient = this.httpClientFactory.createHttpClient(this.config);
	}

	private BCSHttpResponse createBCSHttpResponse(HttpResponse paramHttpResponse) 
			throws IllegalStateException, IOException {
		BCSHttpResponse localBCSHttpResponse = new BCSHttpResponse();
		if (null != paramHttpResponse.getEntity()) {
			localBCSHttpResponse.setContent(paramHttpResponse.getEntity().getContent());
		}
		localBCSHttpResponse.setStatusCode(paramHttpResponse.getStatusLine().getStatusCode());
		localBCSHttpResponse.setStatusText(paramHttpResponse.getStatusLine().getReasonPhrase());

		for (Header localHeader : paramHttpResponse.getAllHeaders()) {
			localBCSHttpResponse.addHeader(localHeader.getName(), localHeader.getValue());
		}
		return localBCSHttpResponse;
	}

	public <T> BaiduBCSResponse<T> execute(BCSHttpRequest paramBCSHttpRequest, HttpResponseHandler<T> paramHttpResponseHandler) {
		HttpRequestBase localHttpRequestBase = this.httpRequestFactory.createHttpRequestBase(this.config, paramBCSHttpRequest);
		boolean bool = false;
		this.httpClient.getConnectionManager().closeIdleConnections(30L, TimeUnit.SECONDS);

		int i = 0;
		while (true) {
			HttpResponse localHttpResponse = null;
			try {
				pauseExponentially(i);
				++i;
				localHttpResponse = this.httpClient.execute(localHttpRequestBase);
				log.info("Send Request Finish: Status:"+localHttpResponse.getStatusLine()+",URI:"+ localHttpRequestBase.getURI());
				if (isRequestSuccessful(localHttpResponse)) {
					bool = paramHttpResponseHandler.isNeedsConnectionLeftOpen();
					return handleHttpResponse(paramBCSHttpRequest, localHttpResponse, paramHttpResponseHandler);
				}
				BCSServiceException localObject1 = handleErrorHttpResponse(paramBCSHttpRequest, localHttpResponse, this.errorResponseHandler).getResult();
				if (!(shouldRetry((Exception) localObject1, i)))
					throw localObject1;
			} catch (ClientProtocolException localThrowable2) {
				log.warn("Unable to execute HTTP request: "+localThrowable2.getMessage());
				if (!(shouldRetry(localThrowable2, i))){
					throw new BCSClientException("Send to server failed: " + localThrowable2.getMessage(), localThrowable2);
				}
			} catch (IOException localThrowable3) {
				log.warn("Unable to execute HTTP request: "+ localThrowable3.getMessage());
				if (!(shouldRetry(localThrowable3, i))) {
					throw new BCSClientException("Send to server failed: " + localThrowable3.getMessage(), localThrowable3);
				}
			} finally {
				if (!(bool))
					try {
						localHttpResponse.getEntity().getContent().close();
					} catch (Throwable localThrowable5) {
					}
			}
		}
	}

	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}

	public ClientConfiguration getConfig() {
		return this.config;
	}

	public HttpRequestFactory getHttpRequestFactory() {
		return this.httpRequestFactory;
	}

	private BaiduBCSResponse<BCSServiceException> handleErrorHttpResponse(
			BCSHttpRequest paramBCSHttpRequest, HttpResponse paramHttpResponse,
			HttpResponseHandler<BCSServiceException> paramHttpResponseHandler)
			throws IllegalStateException, IOException {
		BCSHttpResponse localBCSHttpResponse = createBCSHttpResponse(paramHttpResponse);
		localBCSHttpResponse.setRequest(paramBCSHttpRequest);
		return paramHttpResponseHandler.handle(localBCSHttpResponse);
	}

	private <T> BaiduBCSResponse<T> handleHttpResponse(
			BCSHttpRequest paramBCSHttpRequest, HttpResponse paramHttpResponse,
			HttpResponseHandler<T> paramHttpResponseHandler)
			throws IllegalStateException, IOException {
		BCSHttpResponse localBCSHttpResponse = createBCSHttpResponse(paramHttpResponse);
		localBCSHttpResponse.setRequest(paramBCSHttpRequest);
		return paramHttpResponseHandler.handle(localBCSHttpResponse);
	}

	private boolean isRequestSuccessful(HttpResponse paramHttpResponse) {
		int i = paramHttpResponse.getStatusLine().getStatusCode();
		return (i / 100 == 2);
	}

	private void pauseExponentially(int paramInt) {
		if (0 == paramInt) {
			return;
		}
		long l1 = 300L;
		long l2 = (long) Math.pow(2.0D, paramInt) * l1;

		l2 = Math.min(l2, 20000L);
		log.debug("Retriable error detected, will retry in "+l2+"ms, attempt number: "+ paramInt);
		try {
			Thread.sleep(l2);
		} catch (InterruptedException localInterruptedException) {
			throw new BCSClientException(
					localInterruptedException.getMessage(),
					localInterruptedException);
		}
	}

	public void setHttpRequestFactory(HttpRequestFactory paramHttpRequestFactory) {
		this.httpRequestFactory = paramHttpRequestFactory;
	}

	public boolean shouldRetry(Exception paramException, int paramInt) {
		if (paramInt > this.config.getMaxErrorRetry()) {
			log.warn("Max error retry is["+this.config.getMaxErrorRetry()+"]. Stop retry.");
			return false;
		}

		if ((paramException instanceof NoHttpResponseException)
				|| (paramException instanceof SocketException)
				|| (paramException instanceof SocketTimeoutException)) {
			log.debug("Retrying on "+paramException.getClass().getName()+":"+paramException.getMessage());
			return true;
		}

		if (paramException instanceof BCSServiceException) {
			BCSServiceException localBCSServiceException = (BCSServiceException) paramException;
			if ((localBCSServiceException.getBcsErrorCode() == 500)
					|| (localBCSServiceException.getBcsErrorCode() == 503)) {
				log.debug("Retrying on server response[" + localBCSServiceException.getBcsErrorCode() + "]");
				return true;
			}
		}
		log.warn("Should not retry.");
		return false;
	}

	public void shutdown() {
		this.httpClient.getConnectionManager().shutdown();
	}
}
