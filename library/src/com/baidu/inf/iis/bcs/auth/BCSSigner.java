package com.baidu.inf.iis.bcs.auth;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.baidu.inf.iis.bcs.http.BCSHttpRequest;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.request.BaiduBCSRequest;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;
import com.baidu.inf.iis.bcs.utils.ServiceUtils;

public class BCSSigner {
	
	private static Logger log = LoggerFactory.getLogger(BCSSigner.class);

	public static void sign(BaiduBCSRequest paramBaiduBCSRequest, BCSHttpRequest paramBCSHttpRequest, BCSCredentials paramBCSCredentials) {
		sign(paramBaiduBCSRequest, paramBCSHttpRequest, paramBCSCredentials, null);
	}

	public static void sign(BaiduBCSRequest paramBaiduBCSRequest, BCSHttpRequest paramBCSHttpRequest, BCSCredentials paramBCSCredentials, BCSSignCondition paramBCSSignCondition) {
		StringBuilder flag = new StringBuilder();
		StringBuilder params = new StringBuilder();

		if (null == paramBaiduBCSRequest.getHttpMethod()) {
			throw new BCSClientException("Sign failed! Param: httpMethod, bucket, object can not be empty!");
		}
		if (null == paramBaiduBCSRequest.getBucket()) {
			throw new BCSClientException("Sign failed! Param: httpMethod, bucket, object can not be empty!");
		}
		if ((null == paramBaiduBCSRequest.getObject())
				|| (0 >= paramBaiduBCSRequest.getObject().length())) {
			throw new BCSClientException("Sign failed! Param: httpMethod, bucket, object can not be empty!");
		}
		flag.append("MBO");
		params.append("Method=").append(paramBaiduBCSRequest.getHttpMethod().toString()).append("\n");
		params.append("Bucket=").append(paramBaiduBCSRequest.getBucket()).append("\n");
		params.append("Object=").append(paramBaiduBCSRequest.getObject()).append("\n");

		if (paramBCSSignCondition != null) {
			if (0 != paramBCSSignCondition.getIp().length()) {
				flag.append("I");
				params.append("Ip=").append(paramBCSSignCondition.getIp()).append("\n");
			}
			if (paramBCSSignCondition.getTime().longValue() > 0L) {
				flag.append("T");
				params.append("Time=").append(paramBCSSignCondition.getTime()).append("\n");
				paramBCSHttpRequest.addParameter("time",String.valueOf(paramBCSSignCondition.getTime()));
			}
			if (paramBCSSignCondition.getSize().longValue() > 0L) {
				flag.append("S");
				params.append("Size=").append(paramBCSSignCondition.getSize()).append("\n");
				paramBCSHttpRequest.addParameter("size",String.valueOf(paramBCSSignCondition.getSize()));
			}
		}
		params.insert(0, "\n");
		params.insert(0, flag.toString());
		log.debug("sign for request, original param : " + params.toString());

		try {
			byte[] secretKey = ServiceUtils.toByteArray(paramBCSCredentials.getSecretKey());
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, SigningAlgorithm.HmacSHA1.toString());
			Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
			mac.init(secretKeySpec);
			byte[] arrayOfByte = mac.doFinal(ServiceUtils.toByteArray(params.toString()));
			String sign = ServiceUtils.urlEncode(ServiceUtils.toBase64(arrayOfByte)).replace("%0A", "");
			paramBCSHttpRequest.addParameter("sign", flag.toString() + ":" + paramBCSCredentials.getAccessKey() + ":" + sign);
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			throw new BCSClientException("NoSuchAlgorithmException. Sign bcs failed!", localNoSuchAlgorithmException);
		} catch (InvalidKeyException localInvalidKeyException) {
			throw new BCSClientException("InvalidKeyException. Sign bcs failed!", localInvalidKeyException);
		} catch (RuntimeException localRuntimeException) {
			throw new BCSClientException("Sign bcs failed!", localRuntimeException);
		}
	}
}
