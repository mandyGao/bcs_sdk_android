package com.baidu.inf.iis.bcs.handler;

import com.baidu.inf.iis.bcs.http.BCSHttpResponse;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ObjectListResponseHandler extends HttpResponseHandler<ObjectListing> {
	
	public BaiduBCSResponse<ObjectListing> handle(BCSHttpResponse paramBCSHttpResponse) {
		String str = getResponseContentByStr(paramBCSHttpResponse);

		ObjectListing localObjectListing = new ObjectListing();
		try{
			JSONObject json = new JSONObject(str);
			localObjectListing.setObjectTotal(json.getInt("object_total"));
			Object start = json.get("start");
			if (start instanceof String) {
				localObjectListing.setStart(Integer.valueOf(json.getString("start")));
			} else {
				localObjectListing.setStart(json.getInt("start"));
			}
			Object limit = json.get("limit");
			if (limit instanceof String) {
				localObjectListing.setLimit(Integer.valueOf(json.getString("limit")));
			} else {
				localObjectListing.setLimit(json.getInt("limit"));
			}
			localObjectListing.setBucket(json.getString("bucket"));
			if (!json.isNull("prefix")) {
				localObjectListing.setPrefix(json.getString("prefix"));
			}
			JSONArray jsonArray = json.getJSONArray("object_list");
			for (int i = 0; i < jsonArray.length(); i++) {
				ObjectSummary localObjectSummary = new ObjectSummary();
				JSONObject temp = jsonArray.getJSONObject(i);
				localObjectSummary.setName(temp.getString("object"));
				localObjectSummary.setVersionKey(temp.getString("version_key"));
				localObjectSummary.setIsDir((temp.getString("is_dir")).equals("1"));
				localObjectSummary.setSize(temp.getLong("size"));
				localObjectSummary.setLastModifiedTime(temp.getLong("mdatetime"));
				localObjectSummary.setParentDir(temp.getString("parent_dir"));
				localObjectListing.addObjectSummary(localObjectSummary);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		BaiduBCSResponse<ObjectListing> localObject = parseResponseMetadata(paramBCSHttpResponse);
		localObject.setResult(localObjectListing);
		return localObject;
	}
}
