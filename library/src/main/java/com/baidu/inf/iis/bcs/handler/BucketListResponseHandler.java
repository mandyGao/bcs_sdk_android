package com.baidu.inf.iis.bcs.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.inf.iis.bcs.http.BCSHttpResponse;
import com.baidu.inf.iis.bcs.model.BucketSummary;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.baidu.inf.iis.bcs.utils.Logger;
import com.baidu.inf.iis.bcs.utils.LoggerFactory;

public class BucketListResponseHandler extends HttpResponseHandler<List<BucketSummary>> {
	
	private static Logger log = LoggerFactory.getLogger(BucketListResponseHandler.class);
	
	public BaiduBCSResponse<List<BucketSummary>> handle(BCSHttpResponse paramBCSHttpResponse) {
		List<BucketSummary> localArrayList = new ArrayList<BucketSummary>();

		String strJson = getResponseContentByStr(paramBCSHttpResponse);
		log.debug("Response:"+strJson);
		try {
			Object result = null;
			if (strJson.startsWith("{") || strJson.startsWith("[")) {
				result = new JSONTokener(strJson).nextValue();
			}
			if (result == null && !(result instanceof JSONArray)) {
				return null;
			}

			JSONArray jsonArray = (JSONArray) result;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				BucketSummary localBucketSummary = new BucketSummary(json.getString("bucket_name"));
				localBucketSummary.setCdatatime(json.getLong("cdatetime"));
				localBucketSummary.setTotalCapacity(json.getLong("total_capacity"));
				localBucketSummary.setUsedCapacity(json.getLong("used_capacity"));
				localArrayList.add(localBucketSummary);
			}
		} catch (JSONException e) {
			log.error("", e);
		}

		BaiduBCSResponse<List<BucketSummary>> baiduBCSResponse = parseResponseMetadata(paramBCSHttpResponse);
		baiduBCSResponse.setResult(localArrayList);
		return baiduBCSResponse;
	}
}
