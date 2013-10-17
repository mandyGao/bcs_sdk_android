package com.baidu.inf.iis.bcs.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.Pair;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class Policy {
	private static final Logger log = LoggerFactory.getLogger(Policy.class);

	public static void main(String[] paramArrayOfString) {
		Policy localPolicy = new Policy();
		Statement localStatement1 = new Statement();
		localStatement1.addAction(PolicyAction.get_bucket_policy)
				.addAction(PolicyAction.put_bucket_policy)
				.addAction(PolicyAction.all);
		localStatement1.addResource("/1").addResource("/2").addResource("/3");
		localStatement1.addUser("user1").addUser("user2");
		localStatement1.setEffect(PolicyEffect.allow);
		PolicyTime localPolicyTime = new PolicyTime();
		localPolicyTime
				.addSingleTime("2012-01-04D12:12:1")
				.addSingleTime("2012-01-04D12:12:2")
				.addTimeRange(new Pair("2012-01-04D12:12:3", "2012-01-04D12:12:4"));

		localStatement1.setTime(localPolicyTime);
		PolicyIP localPolicyIP = new PolicyIP();
		localPolicyIP.addSingleIp("1.1.1.1").addCidrIp("2.2.2.2/16").addIpRange(new Pair("3.3.3.3", "4.4.4.4"));
		localStatement1.setIp(localPolicyIP);

		Statement localStatement2 = new Statement();
		localStatement2.addAction(PolicyAction.get_bucket_policy).addAction(
				PolicyAction.put_bucket_policy);
		localStatement2.addResource("/1").addResource("/2").addResource("/3");
		localStatement2.addUser("user1").addUser("user2");
		localStatement2.setEffect(PolicyEffect.deny);

		localPolicy.addStatements(localStatement1);
		localPolicy.addStatements(localStatement2);

		String str1 = localPolicy.toJson();
		log.info("Policy object to json str:\n" + str1);

		localPolicy = new Policy().buildJsonStr(str1);

		String str2 = localPolicy.toJson();
		log.info("Json str 2 policy object 2 json str:\n" + str2);

		if (str1.equals(str2))
			log.info("Correct");
		else
			log.info("Invalid");
	}

	List<Statement> statements;

	private String originalJsonStr;

	public Policy() {
		this.statements = new ArrayList<Statement>();
	}

	public Policy addStatements(Statement paramStatement) {
		this.statements.add(paramStatement);
		return this;
	}

	public Policy buildJsonStr(String paramString) {
		Policy policy = null;
		try {
			policy = buildJsonStrOrThrow(paramString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return policy;
	}
	
	public Policy buildJsonStrOrThrow(String paramString) throws JSONException  {
		this.originalJsonStr = paramString;
		
		paramString = paramString.trim();
		Object result = null;
		if(paramString.startsWith("{") || paramString.startsWith("[")) {
			result = new JSONTokener(paramString).nextValue();
		}
		if (result == null) {
			return null;
		}
		if (result instanceof JSONObject) {
			Statement localStatement = new Statement();
			JSONArray array = ((JSONObject) result).getJSONArray("statements");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				JSONArray action = obj.getJSONArray("action");
				int j = 0;
				
				for (j = 0; j < action.length(); j++) {
					localStatement.addAction(PolicyAction.toPolicyAction(action.getString(i)));
				}
				
				JSONArray user = obj.getJSONArray("user");
				for (j = 0; j < user.length(); j++) {
					localStatement.addUser(user.getString(i));
				}
				
				JSONArray resource = obj.getJSONArray("resource");
				for (j = 0; j < user.length(); j++) {
					localStatement.addResource(resource.getString(i));
				}
				
				localStatement.setEffect(PolicyEffect.valueOf(obj.getString("effect")));
				
				if (!obj.isNull("time")) {
					PolicyTime localObject1 = new PolicyTime();
					Object time = obj.get("time");
					if(time instanceof String){
						localObject1.addSingleTime((String)time);
					} else if(time instanceof JSONArray) {
						localObject1.addTimeRange(new Pair<String>(((JSONArray) time).getString(0), ((JSONArray) time).getString(1)));
					} else if(time instanceof JSONObject){
						localObject1.addSingleTime(time.toString());
					} else {
						throw new BCSClientException("Analyze policy time failed.");
					}
					localStatement.setTime(localObject1);
				}
				
				if (!obj.isNull("ip")) {
					JSONArray ipArrayJSON = obj.getJSONArray("ip");
					PolicyIP localObject1 = new PolicyIP();
					for (j = 0; j < ipArrayJSON.length(); j++) {
						Object ip = ipArrayJSON.get(j);
						if (ip instanceof String || ip instanceof JSONObject) {
							String ipStr = ip.toString();
							if (ipStr.indexOf("/") != -1) {
								localObject1.addCidrIp(ipStr);
							} else {
								localObject1.addSingleIp(ipStr);
							}
						} else if (ip instanceof JSONArray) {
							localObject1.addIpRange(new Pair<String>(((JSONArray) ip).getString(0), ((JSONArray) ip).getString(1)));
						} else {
							throw new BCSClientException("Analyze policy time failed.");
						}
					}
					localStatement.setIp(localObject1);
				}
				addStatements(localStatement);
			}
		}
		return this;
	}

	public String getOriginalJsonStr() {
		return this.originalJsonStr;
	}

	public List<Statement> getStatements() {
		return this.statements;
	}

	public void setStatements(List<Statement> paramList) {
		this.statements = paramList;
	}

	public String toJson() {
		if (statements == null || statements.size() <= 0) {
			return "";
		}
		HashMap<String, List<HashMap<String, Object>>> localHashMap1 = new HashMap<String, List<HashMap<String, Object>>>();
		List<HashMap<String, Object>> statements = new ArrayList<HashMap<String, Object>>();
		for (Statement localStatement : this.statements) {
			HashMap<String, Object> localHashMap2 = new HashMap<String, Object>();

			localHashMap2.put("user", localStatement.getUser());

			localHashMap2.put("resource", localStatement.getResource());

			List<String> localArrayList = new ArrayList<String>();
			for (PolicyAction policyAction :  localStatement.getAction()) {
				localArrayList.add(policyAction.toString());
			}
			localHashMap2.put("action", localArrayList);

			localHashMap2.put("effect", localStatement.getEffect().toString());
			
			if ((null != localStatement.getTime())
					&& (!(localStatement.getTime().isEmpty()))) {
				List<String> localObject1 = new ArrayList<String>();
				localObject1.addAll(localStatement.getTime().getSingleTimeList());
				
				for (Pair<String> pair : localStatement.getTime().getTimeRangeList()) {
					localObject1.addAll(pair.toArrayList());
				}
				localHashMap2.put("time", localObject1);
			}

			if ((null != localStatement.getIp())
					&& (!(localStatement.getIp().isEmpty()))) {
				List<String> localObject1 = new ArrayList<String>();
				localObject1.addAll(localStatement.getIp().getSingleIpList());
				localObject1.addAll(localStatement.getIp().getCidrIpList());
				
				for (Pair<String> pair : localStatement.getIp().getIpRangeList()) {
					localObject1.addAll(pair.toArrayList());
				}
				localHashMap2.put("ip", localObject1);
			}
			statements.add(localHashMap2);
			localHashMap1.put("statements", statements);
		}

		return new JSONObject(localHashMap1).toString();
	}
}
