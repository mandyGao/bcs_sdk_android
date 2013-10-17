<<<<<<< HEAD
package com.baidu.inf.iis.bcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.BucketSummary;
import com.baidu.inf.iis.bcs.model.Empty;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.model.Resource;
import com.baidu.inf.iis.bcs.model.SuperfileSubObject;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.baidu.inf.iis.bcs.policy.PolicyAction;
import com.baidu.inf.iis.bcs.policy.PolicyEffect;
import com.baidu.inf.iis.bcs.policy.Statement;
import com.baidu.inf.iis.bcs.request.CreateBucketRequest;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListBucketRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.request.PutSuperfileRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class HelloAndroidActivity extends Activity {
	
	private static final Logger log = LoggerFactory.getLogger(HelloAndroidActivity.class);
	
	// ----------------------------------------
		static String host = "bcs.duapp.com";
		static String accessKey = "xxx";
		static String secretKey = "xx";
		static String bucket = "xx";
		// ----------------------------------------
		static String object = "/log/2013-10-17/aaaaa/aaaaaaa.log";
		static File destFile = new File("test");
		
		
		BCSCredentials credentials;
		BaiduBCS baiduBCS;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		credentials = new BCSCredentials(accessKey, secretKey);
		baiduBCS = new BaiduBCS(credentials, host);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		ExecutorService  e = Executors.newCachedThreadPool();
			e.submit(new Runnable() {
				
				@Override
				public void run() {
					try {
//					listBucket(baiduBCS);
					getBucketPolicy(baiduBCS);
//					createBucket(baiduBCS);
//					deleteBucket(baiduBCS);
//					listObject(baiduBCS);
//					putObjectByFile(baiduBCS);
					} catch (BCSServiceException e) {
						
					} catch (BCSClientException e) {
						e.printStackTrace();
					}
				}
			});
			// -------------bucket-------------
			// getBucketPolicy(baiduBCS);
			// putBucketPolicyByPolicy(baiduBCS);
			// putBucketPolicyByX_BS_ACL(baiduBCS, X_BS_ACL.PublicControl);
			// listObject(baiduBCS);
			// ------------object-------------
//			putObjectByFile(baiduBCS);
			// putObjectByInputStream(baiduBCS);
			// getObjectWithDestFile(baiduBCS);
			// putSuperfile(baiduBCS);
			// deleteObject(baiduBCS);
			// getObjectMetadata(baiduBCS);
			// setObjectMetadata(baiduBCS);
			// copyObject(baiduBCS, bucket, object + "_copy" +
			// (System.currentTimeMillis()));
			// getObjectPolicy(baiduBCS);
			// putObjectPolicyByPolicy(baiduBCS);
			// putObjectPolicyByX_BS_ACL(baiduBCS, X_BS_ACL.PublicControl);

			// ------------common------------------
			// generateUrl(BaiduBCS baiduBCS);
		
	}
	public static void generateUrl(BaiduBCS baiduBCS) {
		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(HttpMethodName.GET, bucket, object);
		generateUrlRequest.setBcsSignCondition(new BCSSignCondition());
		generateUrlRequest.getBcsSignCondition().setIp("1.1.1.1");
		generateUrlRequest.getBcsSignCondition().setTime(123455L);
		generateUrlRequest.getBcsSignCondition().setSize(123455L);
		System.out.println(baiduBCS.generateUrl(generateUrlRequest));
	}

	public static void copyObject(BaiduBCS baiduBCS, String destBucket, String destObject) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("image/jpeg");
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject), objectMetadata);
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject), null);
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject));
	}

	private static void createBucket(BaiduBCS baiduBCS) {
		baiduBCS.createBucket(new CreateBucketRequest(bucket, X_BS_ACL.Private));
	}

	private static void deleteBucket(BaiduBCS baiduBCS) {
		baiduBCS.deleteBucket(bucket);
	}

	public static void deleteObject(BaiduBCS baiduBCS) {
		Empty result = baiduBCS.deleteObject(bucket, object).getResult();
		log.info(result.toString());
	}

	private static void getBucketPolicy(BaiduBCS baiduBCS) {
		BaiduBCSResponse<Policy> response = baiduBCS.getBucketPolicy(bucket);

		log.info("After analyze: " + response.getResult().toJson());
		log.info("Origianal str: " + response.getResult().getOriginalJsonStr());
	}

	public static void getObjectMetadata(BaiduBCS baiduBCS) {
		ObjectMetadata objectMetadata = baiduBCS.getObjectMetadata(bucket, object).getResult();
		log.info(objectMetadata.toString());
	}

	private static void getObjectPolicy(BaiduBCS baiduBCS) {
		BaiduBCSResponse<Policy> response = baiduBCS.getObjectPolicy(bucket, object);
		log.info("After analyze: " + response.getResult().toJson());
		log.info("Origianal str: " + response.getResult().getOriginalJsonStr());
	}

	private static void getObjectWithDestFile(BaiduBCS baiduBCS) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, object);
		baiduBCS.getObject(getObjectRequest, destFile);
	}

	private static void listBucket(BaiduBCS baiduBCS) {
		ListBucketRequest listBucketRequest = new ListBucketRequest();
		BaiduBCSResponse<List<BucketSummary>> response = baiduBCS.listBucket(listBucketRequest);
		for (BucketSummary bucket : response.getResult()) {
			log.info("bucket:"+bucket);
		}
	}

	private static void listObject(BaiduBCS baiduBCS) {
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		listObjectRequest.setLimit(20);
		// ------------------by dir
		{
			// prefix must start with '/' and end with '/'
			 listObjectRequest.setPrefix("/log/");
			 listObjectRequest.setListModel(2);
		}
		// ------------------only object
		{
			// prefix must start with '/'
			// listObjectRequest.setPrefix("/1/");
		}
		BaiduBCSResponse<ObjectListing> response = baiduBCS.listObject(listObjectRequest);
		log.info("we get [" + response.getResult().getObjectSummaries().size() + "] object record.");
		for (ObjectSummary os : response.getResult().getObjectSummaries()) {
			log.info(os.toString());
		}
	}

	private static void putBucketPolicyByPolicy(BaiduBCS baiduBCS) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.get_object);
		st1.addUser("zhengkan").addUser("zhangyong01");
		st1.addResource(bucket + "/111").addResource(bucket + "/111");
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putBucketPolicy(bucket, policy);
	}

	private static void putBucketPolicyByX_BS_ACL(BaiduBCS baiduBCS, X_BS_ACL acl) {
		baiduBCS.putBucketPolicy(bucket, acl);
	}

	public static void putObjectByFile(BaiduBCS baiduBCS) {
		PutObjectRequest request = new PutObjectRequest(bucket, object, createSampleFile());
		ObjectMetadata metadata = new ObjectMetadata();
//		 metadata.setContentType("text/html");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata.toString());
	}

	public static void putObjectByInputStream(BaiduBCS baiduBCS) throws FileNotFoundException {
		File file = createSampleFile();
		InputStream fileContent = new FileInputStream(file);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("text/html");
		objectMetadata.setContentLength(file.length());
		PutObjectRequest request = new PutObjectRequest(bucket, object, fileContent, objectMetadata);
		ObjectMetadata result = baiduBCS.putObject(request).getResult();
		log.info(result.toString());
	}

	private static void putObjectPolicyByPolicy(BaiduBCS baiduBCS) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.get_object);
		st1.addUser("zhengkan").addUser("zhangyong01");
		st1.addResource(bucket + object).addResource(bucket + object);
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putObjectPolicy(bucket, object, policy);
	}

	private static void putObjectPolicyByX_BS_ACL(BaiduBCS baiduBCS, X_BS_ACL acl) {
		baiduBCS.putObjectPolicy(bucket, object, acl);
	}

	public static void putSuperfile(BaiduBCS baiduBCS) {
		List<SuperfileSubObject> subObjectList = new ArrayList<SuperfileSubObject>();
		// 0
		BaiduBCSResponse<ObjectMetadata> response1 = baiduBCS.putObject(bucket, object + "_part0", createSampleFile());
		subObjectList.add(new SuperfileSubObject(bucket, object + "_part0", response1.getResult().getETag()));
		// 1
		BaiduBCSResponse<ObjectMetadata> response2 = baiduBCS.putObject(bucket, object + "_part1", createSampleFile());
		subObjectList.add(new SuperfileSubObject(bucket, object + "_part1", response2.getResult().getETag()));
		// put superfile
		PutSuperfileRequest request = new PutSuperfileRequest(bucket, object + "_superfile", subObjectList);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putSuperfile(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata.toString());
	}

	public static void setObjectMetadata(BaiduBCS baiduBCS) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("text/html12");
		baiduBCS.setObjectMetadata(bucket, object, objectMetadata);
	}

	private static File createSampleFile() {
		try {
			File file = File.createTempFile("java-sdk-", ".txt");
			file.deleteOnExit();

			Writer writer = new OutputStreamWriter(new FileOutputStream(file));
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.close();

			return file;
		} catch (IOException e) {
			log.error("tmp file create failed.");
			return null;
		}
	}

=======
package com.baidu.inf.iis.bcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.BucketSummary;
import com.baidu.inf.iis.bcs.model.Empty;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.model.Resource;
import com.baidu.inf.iis.bcs.model.SuperfileSubObject;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.baidu.inf.iis.bcs.policy.PolicyAction;
import com.baidu.inf.iis.bcs.policy.PolicyEffect;
import com.baidu.inf.iis.bcs.policy.Statement;
import com.baidu.inf.iis.bcs.request.CreateBucketRequest;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListBucketRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.request.PutSuperfileRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class HelloAndroidActivity extends Activity {
	
	private static final Logger log = LoggerFactory.getLogger(HelloAndroidActivity.class);
	
	// ----------------------------------------
		static String host = "bcs.duapp.com";
		static String accessKey = "B93140bc2ee59972fe94a18221266421";
		static String secretKey = "EAe9c7410a2d3ca82cf08f1138ada98e";
		static String bucket = "weplay";
		// ----------------------------------------
		static String object = "/log/2013-10-17/aaaaa/aaaaaaa.log";
		static File destFile = new File("test");
		
		
		BCSCredentials credentials;
		BaiduBCS baiduBCS;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		credentials = new BCSCredentials(accessKey, secretKey);
		baiduBCS = new BaiduBCS(credentials, host);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		ExecutorService  e = Executors.newCachedThreadPool();
			e.submit(new Runnable() {
				
				@Override
				public void run() {
					try {
//					listBucket(baiduBCS);
					getBucketPolicy(baiduBCS);
//					createBucket(baiduBCS);
//					deleteBucket(baiduBCS);
//					listObject(baiduBCS);
//					putObjectByFile(baiduBCS);
					} catch (BCSServiceException e) {
						
					} catch (BCSClientException e) {
						e.printStackTrace();
					}
				}
			});
			// -------------bucket-------------
			// getBucketPolicy(baiduBCS);
			// putBucketPolicyByPolicy(baiduBCS);
			// putBucketPolicyByX_BS_ACL(baiduBCS, X_BS_ACL.PublicControl);
			// listObject(baiduBCS);
			// ------------object-------------
//			putObjectByFile(baiduBCS);
			// putObjectByInputStream(baiduBCS);
			// getObjectWithDestFile(baiduBCS);
			// putSuperfile(baiduBCS);
			// deleteObject(baiduBCS);
			// getObjectMetadata(baiduBCS);
			// setObjectMetadata(baiduBCS);
			// copyObject(baiduBCS, bucket, object + "_copy" +
			// (System.currentTimeMillis()));
			// getObjectPolicy(baiduBCS);
			// putObjectPolicyByPolicy(baiduBCS);
			// putObjectPolicyByX_BS_ACL(baiduBCS, X_BS_ACL.PublicControl);

			// ------------common------------------
			// generateUrl(BaiduBCS baiduBCS);
		
	}
	public static void generateUrl(BaiduBCS baiduBCS) {
		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(HttpMethodName.GET, bucket, object);
		generateUrlRequest.setBcsSignCondition(new BCSSignCondition());
		generateUrlRequest.getBcsSignCondition().setIp("1.1.1.1");
		generateUrlRequest.getBcsSignCondition().setTime(123455L);
		generateUrlRequest.getBcsSignCondition().setSize(123455L);
		System.out.println(baiduBCS.generateUrl(generateUrlRequest));
	}

	public static void copyObject(BaiduBCS baiduBCS, String destBucket, String destObject) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("image/jpeg");
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject), objectMetadata);
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject), null);
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject));
	}

	private static void createBucket(BaiduBCS baiduBCS) {
		baiduBCS.createBucket(new CreateBucketRequest(bucket, X_BS_ACL.Private));
	}

	private static void deleteBucket(BaiduBCS baiduBCS) {
		baiduBCS.deleteBucket(bucket);
	}

	public static void deleteObject(BaiduBCS baiduBCS) {
		Empty result = baiduBCS.deleteObject(bucket, object).getResult();
		log.info(result.toString());
	}

	private static void getBucketPolicy(BaiduBCS baiduBCS) {
		BaiduBCSResponse<Policy> response = baiduBCS.getBucketPolicy(bucket);

		log.info("After analyze: " + response.getResult().toJson());
		log.info("Origianal str: " + response.getResult().getOriginalJsonStr());
	}

	public static void getObjectMetadata(BaiduBCS baiduBCS) {
		ObjectMetadata objectMetadata = baiduBCS.getObjectMetadata(bucket, object).getResult();
		log.info(objectMetadata.toString());
	}

	private static void getObjectPolicy(BaiduBCS baiduBCS) {
		BaiduBCSResponse<Policy> response = baiduBCS.getObjectPolicy(bucket, object);
		log.info("After analyze: " + response.getResult().toJson());
		log.info("Origianal str: " + response.getResult().getOriginalJsonStr());
	}

	private static void getObjectWithDestFile(BaiduBCS baiduBCS) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, object);
		baiduBCS.getObject(getObjectRequest, destFile);
	}

	private static void listBucket(BaiduBCS baiduBCS) {
		ListBucketRequest listBucketRequest = new ListBucketRequest();
		BaiduBCSResponse<List<BucketSummary>> response = baiduBCS.listBucket(listBucketRequest);
		for (BucketSummary bucket : response.getResult()) {
			log.info("bucket:"+bucket);
		}
	}

	private static void listObject(BaiduBCS baiduBCS) {
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		listObjectRequest.setLimit(20);
		// ------------------by dir
		{
			// prefix must start with '/' and end with '/'
			 listObjectRequest.setPrefix("/log/");
			 listObjectRequest.setListModel(2);
		}
		// ------------------only object
		{
			// prefix must start with '/'
			// listObjectRequest.setPrefix("/1/");
		}
		BaiduBCSResponse<ObjectListing> response = baiduBCS.listObject(listObjectRequest);
		log.info("we get [" + response.getResult().getObjectSummaries().size() + "] object record.");
		for (ObjectSummary os : response.getResult().getObjectSummaries()) {
			log.info(os.toString());
		}
	}

	private static void putBucketPolicyByPolicy(BaiduBCS baiduBCS) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.get_object);
		st1.addUser("zhengkan").addUser("zhangyong01");
		st1.addResource(bucket + "/111").addResource(bucket + "/111");
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putBucketPolicy(bucket, policy);
	}

	private static void putBucketPolicyByX_BS_ACL(BaiduBCS baiduBCS, X_BS_ACL acl) {
		baiduBCS.putBucketPolicy(bucket, acl);
	}

	public static void putObjectByFile(BaiduBCS baiduBCS) {
		PutObjectRequest request = new PutObjectRequest(bucket, object, createSampleFile());
		ObjectMetadata metadata = new ObjectMetadata();
//		 metadata.setContentType("text/html");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata.toString());
	}

	public static void putObjectByInputStream(BaiduBCS baiduBCS) throws FileNotFoundException {
		File file = createSampleFile();
		InputStream fileContent = new FileInputStream(file);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("text/html");
		objectMetadata.setContentLength(file.length());
		PutObjectRequest request = new PutObjectRequest(bucket, object, fileContent, objectMetadata);
		ObjectMetadata result = baiduBCS.putObject(request).getResult();
		log.info(result.toString());
	}

	private static void putObjectPolicyByPolicy(BaiduBCS baiduBCS) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.get_object);
		st1.addUser("zhengkan").addUser("zhangyong01");
		st1.addResource(bucket + object).addResource(bucket + object);
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putObjectPolicy(bucket, object, policy);
	}

	private static void putObjectPolicyByX_BS_ACL(BaiduBCS baiduBCS, X_BS_ACL acl) {
		baiduBCS.putObjectPolicy(bucket, object, acl);
	}

	public static void putSuperfile(BaiduBCS baiduBCS) {
		List<SuperfileSubObject> subObjectList = new ArrayList<SuperfileSubObject>();
		// 0
		BaiduBCSResponse<ObjectMetadata> response1 = baiduBCS.putObject(bucket, object + "_part0", createSampleFile());
		subObjectList.add(new SuperfileSubObject(bucket, object + "_part0", response1.getResult().getETag()));
		// 1
		BaiduBCSResponse<ObjectMetadata> response2 = baiduBCS.putObject(bucket, object + "_part1", createSampleFile());
		subObjectList.add(new SuperfileSubObject(bucket, object + "_part1", response2.getResult().getETag()));
		// put superfile
		PutSuperfileRequest request = new PutSuperfileRequest(bucket, object + "_superfile", subObjectList);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putSuperfile(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata.toString());
	}

	public static void setObjectMetadata(BaiduBCS baiduBCS) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("text/html12");
		baiduBCS.setObjectMetadata(bucket, object, objectMetadata);
	}

	private static File createSampleFile() {
		try {
			File file = File.createTempFile("java-sdk-", ".txt");
			file.deleteOnExit();

			Writer writer = new OutputStreamWriter(new FileOutputStream(file));
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.close();

			return file;
		} catch (IOException e) {
			log.error("tmp file create failed.");
			return null;
		}
	}

>>>>>>> 924427ee05ef98bc62b83ea7bb88d2d59dfc1630
}