package com.d3.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GlobalFunction {

	public static String getString(JSONObject jsonObj, String key){
		if (jsonObj != null && jsonObj.has(key)) {
			try {
				String temp = jsonObj.getString(key);
				if("null".equals(temp))
					temp = "";
				return temp;
			} catch (JSONException e) {
			}
		}
		return "";
	}
	
	public static String convertStringErrF(String err){
		String message_alert = "";
		String input = err;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(input);
			for (int i = 0; i < jsonArray.length(); i++) {
				message_alert = jsonArray.getString(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return err;
		}
		if(!"".equals(message_alert)){
			return message_alert;
		}else{
			return err;
		}
		
	}
}
