package com.baidu.inf.iis.bcs.policy;

public enum PolicyAction {
	all, put_bucket_policy, get_bucket_policy, list_object, delete_bucket, get_object, put_object, delete_object, put_object_policy, get_object_policy;

	public static PolicyAction toPolicyAction(String paramString) {
		if (paramString.equals("*")) {
			return all;
		}
		return valueOf(paramString);
	}

}
