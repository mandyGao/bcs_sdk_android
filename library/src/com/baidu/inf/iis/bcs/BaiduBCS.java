package com.baidu.inf.iis.bcs;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSigner;
import com.baidu.inf.iis.bcs.handler.BucketListResponseHandler;
import com.baidu.inf.iis.bcs.handler.ObjectListResponseHandler;
import com.baidu.inf.iis.bcs.handler.ObjectMetadataResponseHandler;
import com.baidu.inf.iis.bcs.handler.ObjectResponseHandler;
import com.baidu.inf.iis.bcs.handler.PolicyResponseHandler;
import com.baidu.inf.iis.bcs.handler.VoidResponseHandler;
import com.baidu.inf.iis.bcs.http.BCSHttpClient;
import com.baidu.inf.iis.bcs.http.BCSHttpRequest;
import com.baidu.inf.iis.bcs.http.ClientConfiguration;
import com.baidu.inf.iis.bcs.http.DefaultBCSHttpRequest;
import com.baidu.inf.iis.bcs.http.MD5DigestCalculatingInputStream;
import com.baidu.inf.iis.bcs.http.RepeatableFileInputStream;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.BucketSummary;
import com.baidu.inf.iis.bcs.model.DownloadObject;
import com.baidu.inf.iis.bcs.model.Empty;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.Pair;
import com.baidu.inf.iis.bcs.model.Resource;
import com.baidu.inf.iis.bcs.model.SuperfileSubObject;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.baidu.inf.iis.bcs.request.BaiduBCSRequest;
import com.baidu.inf.iis.bcs.request.CopyObjectRequest;
import com.baidu.inf.iis.bcs.request.CreateBucketRequest;
import com.baidu.inf.iis.bcs.request.DeleteBucketRequest;
import com.baidu.inf.iis.bcs.request.DeleteObjectRequest;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetBucketPolicyRequest;
import com.baidu.inf.iis.bcs.request.GetObjectMetadataRequest;
import com.baidu.inf.iis.bcs.request.GetObjectPolicyRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListBucketRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutBucketPolicyRequest;
import com.baidu.inf.iis.bcs.request.PutObjectPolicyRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.request.PutSuperfileRequest;
import com.baidu.inf.iis.bcs.request.SetObjectMetadataRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.baidu.inf.iis.bcs.utils.Constants;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;
import com.baidu.inf.iis.bcs.utils.Mimetypes;
import com.baidu.inf.iis.bcs.utils.ServiceUtils;

public class BaiduBCS {
	private static final Logger log = LoggerFactory.getLogger(BaiduBCS.class);
	
	private BCSHttpClient bcsHttpClient = null;
	private BCSCredentials credentials = null;
	private String endpoint = null;

	public BaiduBCS(BCSCredentials paramBCSCredentials, String paramString) {
		this.credentials = paramBCSCredentials;
		setEndpoint(paramString);
		this.bcsHttpClient = new BCSHttpClient(new ClientConfiguration());
	}

	public BaiduBCS(BCSCredentials paramBCSCredentials, String paramString, ClientConfiguration paramClientConfiguration) {
		this.credentials = paramBCSCredentials;
		setEndpoint(paramString);
		this.bcsHttpClient = new BCSHttpClient(paramClientConfiguration);
	}

	private void assertParameterNotNull(Object paramObject, String paramString) {
		if (null == paramObject)
			throw new IllegalArgumentException(paramString);
	}

	private String buildResourcePath(String bucket, String object) {
		if (object == null || !(object.startsWith("/"))) {
			throw new BCSClientException("BCS object must start with a slash.");
		}
		StringBuilder builder = new StringBuilder();
		builder.append(bucket);
		if (!(object.equals("/"))) {
			builder.append(ServiceUtils.urlEncode(object));
		}
		return builder.toString();
	}

	public BaiduBCSResponse<Empty> copyObject(CopyObjectRequest paramCopyObjectRequest) 
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramCopyObjectRequest, "The request parameter can be null.");
		assertParameterNotNull(paramCopyObjectRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramCopyObjectRequest.getSource().getBucket(), "The bucket parameter of source must be specified when copy an object.");

		assertParameterNotNull(paramCopyObjectRequest.getSource().getObject(), "The object parameter of source must be specified when copy an object.");

		assertParameterNotNull(paramCopyObjectRequest.getDest().getBucket(), "The bucket parameter of dest must be specified when copy an object.");
		assertParameterNotNull(paramCopyObjectRequest.getDest().getObject(), "The object parameter of dest must be specified when copy an object.");
		log.debug("copy object, src[Bucket:" + paramCopyObjectRequest.getSource().getBucket() + "][Object:" + paramCopyObjectRequest.getSource().getObject() + "] to dest[Bucket" + paramCopyObjectRequest.getDest().getBucket() + "][Object" + paramCopyObjectRequest.getDest().getObject() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramCopyObjectRequest);

		localBCSHttpRequest.addHeader("x-bs-copy-source", "bs://" + paramCopyObjectRequest.getSource().getBucket() + paramCopyObjectRequest.getSource().getObject());

		if (null != paramCopyObjectRequest.getSourceEtag()) {
			localBCSHttpRequest.addHeader("x-bs-copy-source-tag", paramCopyObjectRequest.getSourceEtag());
		}

		if (null != paramCopyObjectRequest.getSourceDirective()) {
			localBCSHttpRequest.addHeader("x-bs-copy-source-directive", paramCopyObjectRequest.getSourceDirective());
		}

		populateRequestMetadata(localBCSHttpRequest, paramCopyObjectRequest.getDestMetadata());

		return this.bcsHttpClient.execute(localBCSHttpRequest, new VoidResponseHandler());
	}

	public BaiduBCSResponse<Empty> copyObject(Resource paramResource1, Resource paramResource2) 
			throws BCSClientException, BCSServiceException {
		return copyObject(new CopyObjectRequest(paramResource1, paramResource2));
	}

	public BaiduBCSResponse<Empty> copyObject(Resource paramResource1, Resource paramResource2, ObjectMetadata paramObjectMetadata)
			throws BCSClientException, BCSServiceException {
		return copyObject(new CopyObjectRequest(paramResource1, paramResource2, paramObjectMetadata));
	}

	public BaiduBCSResponse<Empty> createBucket(CreateBucketRequest paramCreateBucketRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramCreateBucketRequest, "The request parameter can be null.");
		assertParameterNotNull(paramCreateBucketRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramCreateBucketRequest.getBucket(), "The bucket parameter must be specified when creating a bucket");
		log.debug("create bucket, bucket_name [" + paramCreateBucketRequest.getBucket() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramCreateBucketRequest);

		if (null != paramCreateBucketRequest.getAcl()) {
			localBCSHttpRequest.addHeader("x-bs-acl", paramCreateBucketRequest.getAcl().toString());
		}

		return this.bcsHttpClient.execute(localBCSHttpRequest, new VoidResponseHandler());
	}

	public BaiduBCSResponse<Empty> createBucket(String paramString)
			throws BCSClientException, BCSServiceException {
		return createBucket(new CreateBucketRequest(paramString));
	}

	private BCSHttpRequest createHttpRequest(BaiduBCSRequest paramBaiduBCSRequest) {
		DefaultBCSHttpRequest localDefaultBCSHttpRequest = new DefaultBCSHttpRequest(paramBaiduBCSRequest);

		localDefaultBCSHttpRequest.setEndpoint(this.endpoint);

		localDefaultBCSHttpRequest.setResourcePath(buildResourcePath(paramBaiduBCSRequest.getBucket(), paramBaiduBCSRequest.getObject()));

		localDefaultBCSHttpRequest.setHttpMethod(paramBaiduBCSRequest.getHttpMethod());

		BCSSigner.sign(paramBaiduBCSRequest, localDefaultBCSHttpRequest, this.credentials);
		return localDefaultBCSHttpRequest;
	}

	public BaiduBCSResponse<Empty> deleteBucket(DeleteBucketRequest paramDeleteBucketRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramDeleteBucketRequest, "The request parameter can be null.");
		assertParameterNotNull(paramDeleteBucketRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramDeleteBucketRequest.getBucket(), "The bucket parameter must be specified when deleting a bucket.");
		log.debug("delete bucket begin, bucket[" + paramDeleteBucketRequest.getBucket() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramDeleteBucketRequest);

		return this.bcsHttpClient.execute(localBCSHttpRequest, new VoidResponseHandler());
	}

	public BaiduBCSResponse<Empty> deleteBucket(String paramString)
			throws BCSClientException, BCSServiceException {
		return deleteBucket(new DeleteBucketRequest(paramString));
	}

	public BaiduBCSResponse<Empty> deleteObject(DeleteObjectRequest paramDeleteObjectRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramDeleteObjectRequest, "The request parameter can be null.");
		assertParameterNotNull(paramDeleteObjectRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramDeleteObjectRequest.getBucket(), "The bucket parameter must be specified when deleting an object.");
		assertParameterNotNull(paramDeleteObjectRequest.getObject(), "The object parameter must be specified when deleting an object.");
		log.debug("delete object, bucket[" + paramDeleteObjectRequest.getBucket() + "], object[" + paramDeleteObjectRequest.getObject() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramDeleteObjectRequest);

		return this.bcsHttpClient.execute(localBCSHttpRequest, new VoidResponseHandler());
	}

	public BaiduBCSResponse<Empty> deleteObject(String paramString1, String paramString2) 
			throws BCSClientException, BCSServiceException {
		return deleteObject(new DeleteObjectRequest(paramString1, paramString2));
	}

	public boolean doesObjectExist(String paramString1, String paramString2)
			throws BCSClientException, BCSServiceException {
		try {
			getObjectMetadata(paramString1, paramString2);
		} catch (BCSServiceException localBCSServiceException) {
			if (404 == localBCSServiceException.getHttpErrorCode()) {
				return false;
			}
			throw localBCSServiceException;
		} catch (BCSClientException localBCSClientException) {
			throw localBCSClientException;
		}
		return true;
	}

	public String generateUrl(GenerateUrlRequest paramGenerateUrlRequest) {
		assertParameterNotNull(paramGenerateUrlRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramGenerateUrlRequest.getBucket(), "The bucket parameter must be specified.");
		assertParameterNotNull(paramGenerateUrlRequest.getObject(), "The object parameter must be specified.");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramGenerateUrlRequest);
		BCSSigner.sign(paramGenerateUrlRequest, localBCSHttpRequest, this.credentials, paramGenerateUrlRequest.getBcsSignCondition());

		return this.bcsHttpClient.getHttpRequestFactory().buildUri(this.bcsHttpClient.getConfig(), localBCSHttpRequest);
	}

	public BaiduBCSResponse<Policy> getBucketPolicy(GetBucketPolicyRequest paramGetBucketPolicyRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramGetBucketPolicyRequest, "The request parameter can be null.");
		assertParameterNotNull(paramGetBucketPolicyRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramGetBucketPolicyRequest.getBucket(), "The bucket parameter must be specified when get policy of bucket.");
		log.debug("get bucket policy begin, bucket[" + paramGetBucketPolicyRequest.getBucket() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramGetBucketPolicyRequest);

		localBCSHttpRequest.addParameter("acl", String.valueOf(1));

		return this.bcsHttpClient.execute(localBCSHttpRequest, new PolicyResponseHandler());
	}

	public BaiduBCSResponse<Policy> getBucketPolicy(String paramString)
			throws BCSClientException, BCSServiceException {
		return getBucketPolicy(new GetBucketPolicyRequest(paramString));
	}

	public BCSCredentials getCredentials() {
		return this.credentials;
	}

	public String getDefaultEncoding() {
		return Constants.DEFAULT_ENCODING;
	}

	public String getEndpoint() {
		return this.endpoint;
	}

	public BaiduBCSResponse<DownloadObject> getObject(GetObjectRequest paramGetObjectRequest) 
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramGetObjectRequest, "The request parameter can be null.");
		assertParameterNotNull(paramGetObjectRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramGetObjectRequest.getBucket(), "The bucket parameter must be specified when getting an object.");
		assertParameterNotNull(paramGetObjectRequest.getObject(), "The object parameter must be specified when getting an object.");
		log.debug("get object begin, bucket[" + paramGetObjectRequest.getBucket() + "], object[" + paramGetObjectRequest.getObject() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramGetObjectRequest);

		if (null != paramGetObjectRequest.getRange()) {
			Pair<Long> localPair = paramGetObjectRequest.getRange();
			assertParameterNotNull(localPair.getFirst(), "The range first parameter must be specified when getting an object by range.");
			assertParameterNotNull(localPair.getSecond(), "The range second parameter must be specified when getting an object by range.");
			localBCSHttpRequest.addHeader("Range", "bytes=" + localPair.getFirst()+ "-" + localPair.getSecond());
		}

		return this.bcsHttpClient.execute(localBCSHttpRequest, new ObjectResponseHandler());
	}

	public BaiduBCSResponse<DownloadObject> getObject(GetObjectRequest paramGetObjectRequest, File paramFile)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramFile, "The destination file parameter must be specified when downloading an object directly to a file.");
		BaiduBCSResponse<DownloadObject> localBaiduBCSResponse = getObject(paramGetObjectRequest);
		DownloadObject localDownloadObject = localBaiduBCSResponse.getResult();
		if (null == localDownloadObject) {
			throw new BCSClientException("Get object response is empty.");
		}
		BufferedOutputStream localBufferedOutputStream = null;
		try {
			localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile));
			byte[] arrayOfByte = new byte[10240];

			int i;
			while ((i = localDownloadObject.getContent().read(arrayOfByte)) > -1) {
				localBufferedOutputStream.write(arrayOfByte, 0, i);
			}
		} catch (IOException localIOException) {
			
		} finally {
			try {
				localBufferedOutputStream.close();
			} catch (Exception localException3) {
			}
			try {
				localDownloadObject.getContent().close();
			} catch (Exception localException4) {
			}
		}
		if (localDownloadObject.getObjectMetadata().getContentLength() != paramFile.length()) {
			BCSServiceException localBCSServiceException = new BCSServiceException("Maybe download incompletely. http Content-Length=" + localDownloadObject.getObjectMetadata().getContentLength() + " ,download size=" + paramFile.length());

			localBCSServiceException.setBcsErrorCode(0);
			localBCSServiceException.setBcsErrorMessage("");
			localBCSServiceException.setHttpErrorCode(200);
			localBCSServiceException.setRequestId(localBaiduBCSResponse.getRequestId());

			throw localBCSServiceException;
		}
		return localBaiduBCSResponse;
	}

	public BaiduBCSResponse<DownloadObject> getObject(String paramString1, String paramString2) 
			throws BCSClientException, BCSServiceException {
		return getObject(new GetObjectRequest(paramString1, paramString2));
	}

	public BaiduBCSResponse<ObjectMetadata> getObjectMetadata(GetObjectMetadataRequest paramGetObjectMetadataRequest)
			throws BCSClientException, BCSServiceException {
		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramGetObjectMetadataRequest);
		return this.bcsHttpClient.execute(localBCSHttpRequest, new ObjectMetadataResponseHandler());
	}

	public BaiduBCSResponse<ObjectMetadata> getObjectMetadata(String paramString1, String paramString2)
			throws BCSClientException, BCSServiceException {
		return getObjectMetadata(new GetObjectMetadataRequest(paramString1, paramString2));
	}

	public BaiduBCSResponse<Policy> getObjectPolicy(GetObjectPolicyRequest paramGetObjectPolicyRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramGetObjectPolicyRequest, "The request parameter can be null.");
		assertParameterNotNull(paramGetObjectPolicyRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramGetObjectPolicyRequest.getBucket(), "The bucket parameter must be specified when getting policy of an object.");
		assertParameterNotNull(paramGetObjectPolicyRequest.getObject(), "The object parameter must be specified when getting policy of an object.");
		log.debug("get object policy, bucket[" + paramGetObjectPolicyRequest.getBucket() + "]" + ", object[" + paramGetObjectPolicyRequest.getObject() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramGetObjectPolicyRequest);

		localBCSHttpRequest.addParameter("acl", String.valueOf(1));

		return this.bcsHttpClient.execute(localBCSHttpRequest, new PolicyResponseHandler());
	}

	public BaiduBCSResponse<Policy> getObjectPolicy(String paramString1, String paramString2) 
			throws BCSClientException, BCSServiceException {
		return getObjectPolicy(new GetObjectPolicyRequest(paramString1, paramString2));
	}

	public BaiduBCSResponse<List<BucketSummary>> listBucket(ListBucketRequest paramListBucketRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramListBucketRequest, "The request parameter can be null.");
		assertParameterNotNull(paramListBucketRequest.getHttpMethod(), "The http method parameter in Request must be specified.");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramListBucketRequest);

		return this.bcsHttpClient.execute(localBCSHttpRequest, new BucketListResponseHandler());
	}

	public BaiduBCSResponse<ObjectListing> listObject(ListObjectRequest paramListObjectRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramListObjectRequest, "The request parameter can be null.");
		assertParameterNotNull(paramListObjectRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramListObjectRequest.getBucket(), "The bucket parameter must be specified when listing an bucket.");
		log.debug("list object begin, bucket[" + paramListObjectRequest.getBucket() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramListObjectRequest);

		if ((null != paramListObjectRequest.getPrefix()) && (0 != paramListObjectRequest.getPrefix().length())) {
			localBCSHttpRequest.addParameter("prefix", paramListObjectRequest.getPrefix());
		}

		if (paramListObjectRequest.getStart() >= 0) {
			localBCSHttpRequest.addParameter("start", String.valueOf(paramListObjectRequest.getStart()));
		}

		if (paramListObjectRequest.getLimit() >= 0) {
			localBCSHttpRequest.addParameter("limit", String.valueOf(paramListObjectRequest.getLimit()));
		}

		if (paramListObjectRequest.getListModel() != 0) {
			localBCSHttpRequest.addParameter("dir", String.valueOf(paramListObjectRequest.getListModel()));
		}

		return this.bcsHttpClient.execute(localBCSHttpRequest, new ObjectListResponseHandler());
	}

	private void populateRequestMetadata(BCSHttpRequest paramBCSHttpRequest, ObjectMetadata paramObjectMetadata) {
		if (null == paramObjectMetadata) {
			log.debug("populateRequestMetadata, metadata is null");
			return;
		}
		Map<String, Object> localMap = paramObjectMetadata.getRawMetadata();
		if (localMap != null) {
			for (String key : localMap.keySet()) {
				paramBCSHttpRequest.addHeader(key, (String) localMap.get(key));
			}
		}
		Map<String, String> localObject1 = paramObjectMetadata.getUserMetadata();
		if (localObject1 != null) {
			for (String key : localObject1.keySet()) {
				paramBCSHttpRequest.addHeader("x-bs-meta-" + key, localObject1.get(key));
			}
		}
	}

	public BaiduBCSResponse<Empty> putBucketPolicy(PutBucketPolicyRequest paramPutBucketPolicyRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramPutBucketPolicyRequest, "The request parameter can be null.");
		assertParameterNotNull(paramPutBucketPolicyRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramPutBucketPolicyRequest.getBucket(), "The bucket parameter must be specified when setting policy or acl to a bucket.");

		log.debug("put bucket policy begin, bucket[" + paramPutBucketPolicyRequest.getBucket() + "]");
		if ((null != paramPutBucketPolicyRequest.getPolicy()) && (null != paramPutBucketPolicyRequest.getAcl())) {
			throw new BCSClientException("Can set policy or acl to bucket at the same time.");
		}

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramPutBucketPolicyRequest);

		localBCSHttpRequest.addParameter("acl", String.valueOf(1));

		if (null != paramPutBucketPolicyRequest.getPolicy()) {
			String str = paramPutBucketPolicyRequest.getPolicy().toJson();
			byte[] arrayOfByte = ServiceUtils.toByteArray(str);
			localBCSHttpRequest.setContent(new ByteArrayInputStream(arrayOfByte));
			localBCSHttpRequest.addHeader("Content-Length", String.valueOf(arrayOfByte.length));
		} else if (null != paramPutBucketPolicyRequest.getAcl()) {
			localBCSHttpRequest.addHeader("x-bs-acl", paramPutBucketPolicyRequest.getAcl().toString());
		}

		return this.bcsHttpClient.execute(localBCSHttpRequest, new VoidResponseHandler());
	}

	public BaiduBCSResponse<Empty> putBucketPolicy(String paramString, Policy paramPolicy) 
			throws BCSClientException, BCSServiceException {
		return putBucketPolicy(new PutBucketPolicyRequest(paramString, paramPolicy));
	}

	public BaiduBCSResponse<Empty> putBucketPolicy(String paramString, X_BS_ACL paramX_BS_ACL) 
			throws BCSClientException, BCSServiceException {
		return putBucketPolicy(new PutBucketPolicyRequest(paramString, paramX_BS_ACL));
	}

	public BaiduBCSResponse<ObjectMetadata> putObject(PutObjectRequest paramPutObjectRequest) 
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramPutObjectRequest, "The request parameter can be null.");
		assertParameterNotNull(paramPutObjectRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramPutObjectRequest.getBucket(), "The bucket parameter must be specified when uploading an object.");
		assertParameterNotNull(paramPutObjectRequest.getObject(), "The object parameter must be specified when uploading an object.");
		log.debug("put object begin,bucket[" + paramPutObjectRequest.getBucket() + "], object[" + paramPutObjectRequest.getObject() + "]");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramPutObjectRequest);

		ObjectMetadata putObjectMetadata = paramPutObjectRequest.getMetadata();
		if (putObjectMetadata == null) {
			paramPutObjectRequest.setMetadata(new ObjectMetadata());
			putObjectMetadata = paramPutObjectRequest.getMetadata();
		}
		InputStream uploadInoutStream;
		if (paramPutObjectRequest.getFile() != null) {
			File uploadFile = paramPutObjectRequest.getFile();
			putObjectMetadata.setContentLength(uploadFile.length());
			if (putObjectMetadata.getContentType() == null) {
				putObjectMetadata.setContentType(Mimetypes.getInstance().getMimetype(uploadFile));
			}
			FileInputStream uploadFileInputStream = null;
			try {
				uploadFileInputStream = new FileInputStream(uploadFile);
				byte[] arrayOfByte = ServiceUtils.computeMD5Hash(uploadFileInputStream);
				putObjectMetadata.setContentMD5(ServiceUtils.toHex(arrayOfByte));
			} catch (Exception e) {
				log.error("get upload file inputstream and file's md5 fail.", e);
			} finally {
				try {
					uploadFileInputStream.close();
				} catch (IOException e) {
					log.error("close upload file inputstream fail.", e);
				}
			}
			try {
				uploadInoutStream = new RepeatableFileInputStream(uploadFile);
			} catch (FileNotFoundException localFileNotFoundException) {
				throw new BCSClientException("Unable to find file to upload", localFileNotFoundException);
			}
		} else {
			uploadInoutStream = paramPutObjectRequest.getObjectContent();
			if (null == putObjectMetadata) {
				throw new BCSClientException("Put object by Inputstream. Must have Content-Length in objectMetadata.");
			}
		}
		if (putObjectMetadata.getContentLength() < 0L) {
			throw new BCSClientException("Content-Length could not be empty.");
		}

		MD5DigestCalculatingInputStream md5CalculatingInputStream = null;
		if (putObjectMetadata.getContentMD5() == null) {
			try {
				md5CalculatingInputStream = new MD5DigestCalculatingInputStream(uploadInoutStream);
				uploadInoutStream = md5CalculatingInputStream;
			} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
				log.warn("No MD5 digest algorithm available.  Unable to calculate checksum and verify data integrity.", localNoSuchAlgorithmException);
			}
		}

		localBCSHttpRequest.setContent(uploadInoutStream);

		if (null != paramPutObjectRequest.getAcl()) {
			localBCSHttpRequest.addHeader("x-bs-acl", paramPutObjectRequest.getAcl().toString());
		}

		if (putObjectMetadata.getContentType() == null) {
			putObjectMetadata.setContentType("application/octet-stream");
		}

		populateRequestMetadata(localBCSHttpRequest, putObjectMetadata);

		BaiduBCSResponse<ObjectMetadata> localBaiduBCSResponse = this.bcsHttpClient.execute(localBCSHttpRequest, new ObjectMetadataResponseHandler());
		try {
			uploadInoutStream.close();
		} catch (Exception localException3) {
			log.warn("Unable to cleanly close input stream: " + localException3.getMessage(), localException3);
		}

		String str = putObjectMetadata.getContentMD5();
		if (null != md5CalculatingInputStream) {
			str = ServiceUtils.toHex(md5CalculatingInputStream.getMd5Digest());
		}
		if (!str.equalsIgnoreCase(localBaiduBCSResponse.getResult().getContentMD5())) {
			throw new BCSClientException("Client calculated content md5 didn't match md5 calculated by Baidu BCS. ");
		}

		return localBaiduBCSResponse;
	}

	public BaiduBCSResponse<ObjectMetadata> putObject(String paramString1, String paramString2, File paramFile)
			throws BCSClientException, BCSServiceException {
		return putObject(new PutObjectRequest(paramString1, paramString2, paramFile));
	}

	public BaiduBCSResponse<ObjectMetadata> putObject(String paramString1, String paramString2, InputStream paramInputStream, ObjectMetadata paramObjectMetadata) 
			throws BCSClientException, BCSServiceException {
		return putObject(new PutObjectRequest(paramString1, paramString2, paramInputStream, paramObjectMetadata));
	}

	public BaiduBCSResponse<Empty> putObjectPolicy(PutObjectPolicyRequest paramPutObjectPolicyRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramPutObjectPolicyRequest, "The request parameter can be null.");
		assertParameterNotNull(paramPutObjectPolicyRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramPutObjectPolicyRequest.getBucket(), "The bucket parameter must be specified when setting policy or acl to an object.");

		assertParameterNotNull(paramPutObjectPolicyRequest.getObject(), "The object parameter must be specified when setting policy or acl to an object.");

		log.debug("put object policy begin, bucket[" + paramPutObjectPolicyRequest.getBucket() + "]");
		if ((null != paramPutObjectPolicyRequest.getPolicy()) && (null != paramPutObjectPolicyRequest.getAcl())) {
			throw new BCSClientException("Can set policy or acl to object at the same time.");
		}

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramPutObjectPolicyRequest);

		localBCSHttpRequest.addParameter("acl", String.valueOf(1));

		if (null != paramPutObjectPolicyRequest.getPolicy()) {
			String str = paramPutObjectPolicyRequest.getPolicy().toJson();
			byte[] arrayOfByte = ServiceUtils.toByteArray(str);
			ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
			localBCSHttpRequest.setContent(localByteArrayInputStream);
			localBCSHttpRequest.addHeader("Content-Length", String.valueOf(arrayOfByte.length));
		} else if (null != paramPutObjectPolicyRequest.getAcl()) {
			localBCSHttpRequest.addHeader("x-bs-acl", paramPutObjectPolicyRequest.getAcl().toString());
		}

		return this.bcsHttpClient.execute(localBCSHttpRequest, new VoidResponseHandler());
	}

	public BaiduBCSResponse<Empty> putObjectPolicy(String paramString1, String paramString2, Policy paramPolicy) 
			throws BCSClientException, BCSServiceException {
		return putObjectPolicy(new PutObjectPolicyRequest(paramString1, paramString2, paramPolicy));
	}

	public BaiduBCSResponse<Empty> putObjectPolicy(String paramString1, String paramString2, X_BS_ACL paramX_BS_ACL)
			throws BCSClientException, BCSServiceException {
		return putObjectPolicy(new PutObjectPolicyRequest(paramString1, paramString2, paramX_BS_ACL));
	}

	public BaiduBCSResponse<ObjectMetadata> putSuperfile(PutSuperfileRequest paramPutSuperfileRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramPutSuperfileRequest, "The request parameter can be null.");
		assertParameterNotNull(paramPutSuperfileRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramPutSuperfileRequest.getSubObjectList(), "The sub-object list parameter in Request must be specified.");
		assertParameterNotNull(paramPutSuperfileRequest.getBucket(), "The bucket parameter must be specified when creating a superfile.");
		assertParameterNotNull(paramPutSuperfileRequest.getObject(), "The object parameter must be specified when creating a superfile.");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramPutSuperfileRequest);

		Map<String, Map<String, Map<String, String>>> localHashMap = new HashMap<String, Map<String, Map<String, String>>>();
		localHashMap.put("object_list", new LinkedHashMap<String, Map<String, String>>());
		for (int i = 0; i < paramPutSuperfileRequest.getSubObjectList().size(); ++i) {
			Map<String, Map<String, String>> objectList = localHashMap.get("object_list");
			
			SuperfileSubObject localObject = paramPutSuperfileRequest.getSubObjectList().get(i);
			Map<String, String> part = new HashMap<String, String>();
			part.put("url", "bs://" + localObject.getBucket() + localObject.getObject());
			part.put("etag", localObject.getEtag());
			
			objectList.put("part_" + i, part);
		}
		
		JSONObject json = new JSONObject(localHashMap);
		byte[] arrayOfByte = ServiceUtils.toByteArray(json.toString());
		localBCSHttpRequest.setContent(new ByteArrayInputStream(arrayOfByte));
		localBCSHttpRequest.addHeader("Content-Length", String.valueOf(arrayOfByte.length));

		localBCSHttpRequest.addParameter("superfile", String.valueOf(1));

		if (null != paramPutSuperfileRequest.getObjectMetadata()) {
			populateRequestMetadata(localBCSHttpRequest, paramPutSuperfileRequest.getObjectMetadata());
		}
		return this.bcsHttpClient.execute(localBCSHttpRequest, new ObjectMetadataResponseHandler());
	}

	public BaiduBCSResponse<ObjectMetadata> putSuperfile(String paramString1, String paramString2, List<SuperfileSubObject> paramList)
			throws BCSClientException, BCSServiceException {
		return putSuperfile(new PutSuperfileRequest(paramString1, paramString2, paramList));
	}

	public BaiduBCSResponse<ObjectMetadata> putSuperfile(String paramString1, String paramString2, ObjectMetadata paramObjectMetadata, List<SuperfileSubObject> paramList) 
			throws BCSClientException, BCSServiceException {
		return putSuperfile(new PutSuperfileRequest(paramString1, paramString2, paramObjectMetadata, paramList));
	}

	public void setCredentials(BCSCredentials paramBCSCredentials) {
		this.credentials = paramBCSCredentials;
	}

	public void setDefaultEncoding(String paramString) {
		Constants.DEFAULT_ENCODING = paramString;
	}

	public void setEndpoint(String paramString) {
		if (paramString.contains("://")) {
			throw new IllegalArgumentException("Endpoint should not contains '://'.");
		}
		this.endpoint = paramString;
	}

	public BaiduBCSResponse<Empty> setObjectMetadata(SetObjectMetadataRequest paramSetObjectMetadataRequest)
			throws BCSClientException, BCSServiceException {
		assertParameterNotNull(paramSetObjectMetadataRequest, "The request parameter can be null.");
		assertParameterNotNull(paramSetObjectMetadataRequest.getHttpMethod(), "The http method parameter in Request must be specified.");
		assertParameterNotNull(paramSetObjectMetadataRequest.getBucket(), "The bucket parameter must be specified when setting object meta.");
		assertParameterNotNull(paramSetObjectMetadataRequest.getObject(), "The object parameter must be specified when setting object meta.");

		BCSHttpRequest localBCSHttpRequest = createHttpRequest(paramSetObjectMetadataRequest);

		if (null != paramSetObjectMetadataRequest.getMetadata()) {
			paramSetObjectMetadataRequest.getMetadata().setContentMD5("");
			paramSetObjectMetadataRequest.getMetadata().setContentLength(0L);
		}

		populateRequestMetadata(localBCSHttpRequest, paramSetObjectMetadataRequest.getMetadata());

		localBCSHttpRequest.addHeader("x-bs-copy-source", "bs://" + paramSetObjectMetadataRequest.getBucket() + paramSetObjectMetadataRequest.getObject());

		return this.bcsHttpClient.execute(localBCSHttpRequest, new VoidResponseHandler());
	}

	public BaiduBCSResponse<Empty> setObjectMetadata(String paramString1, String paramString2, ObjectMetadata paramObjectMetadata)
			throws BCSClientException, BCSServiceException {
		return setObjectMetadata(new SetObjectMetadataRequest(paramString1, paramString2, paramObjectMetadata));
	}
}
