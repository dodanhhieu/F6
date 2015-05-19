package com.d3.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class GlobalFunction {

	public static String getString(JSONObject jsonObj, String key){
		
		if (jsonObj != null && jsonObj.has(key)) {
			try {
				String temp = jsonObj.getString(key);
				if("null".equals(temp))
					temp = "";
				Log.i("XXX", " == " + temp);
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
	
	public static boolean isEmail(String email){
		 boolean isValid = false;

		    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		    CharSequence inputStr = email;

		    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		    Matcher matcher = pattern.matcher(inputStr);
		    if (matcher.matches()) {
		        isValid = true;
		    }
		    return isValid;
	}
	
	public static void showDialog(Context context, String message,String btnOk,String btnCancel, OnClickListener onClickListener, OnClickListener onClickListener2) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setCancelable(false);
		if (btnOk != null) {
			builder.setPositiveButton("OK", onClickListener);
		}
		if (btnCancel != null) {
			builder.setNegativeButton("Cancel", onClickListener2);
		}
		builder.show();

	}
	
	/**
	 * check user regsiter
	 * @param context
	 * @return true : registed
	 */
	public static boolean isRegister(Context context){
		DataSharePref dataSharePref = new DataSharePref(context);
		Log.i("GLOBAL", "TOKEN : " + dataSharePref.getString(D3Utils.TOKEN_KEY));
		if (dataSharePref.getString(D3Utils.TOKEN_KEY) != null) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isLogin(Context context){
		DataSharePref dataSharePref = new DataSharePref(context);
		Log.i("GLOBAL", "LOGIN : " + dataSharePref.getInt(D3Utils.LOGIN_KEY));
		if (dataSharePref.getInt(D3Utils.LOGIN_KEY) == 1) {
			return true;
		}else{
			return false;
		}
	}
}
