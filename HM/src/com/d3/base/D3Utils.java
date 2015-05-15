package com.d3.base;

import java.util.Map;

import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;
import d3.lib.base.callback.RestClient.RequestMethod;

public class D3Utils {

	public static final class API{
		public static String BASESERVER = "http://native.appcheap.net";
		public static String API_REGISTER = "/users/add.json";
		public static String API_LOGIN = "/users/login.json";
		public static String API_LOGOUT = "/users/logout.json";
		public static String API_UPDATE_BMI = "/users/updateBmi.json";
		public static String API_EDIT_PROFILE = "/users/edit.json";
		public static String API_LIST_CATEGORY = "/categories/listCat.json";
		public static String API_LIST_ARTICLE = "/categories/listArticle.json?id=1";
		public static String API_LIST_CATE_TIPS = "/category_tips/listCat.json"; // tips health && nutrition
		public static String API_LIST_CATE_TIPS_DETAIL = "/category_tips/listArticle/"; // detail 1 tips
		public static String API_LIST_ALL_EXERCISES = "/exercises/listAll.json";
		public static String API_LIST_ALL_CATE_EXERCISES = "/category_exercises/listCat.json";// all cattegories
		public static String API_LIST_EXERCISE_OF_CATE = "/category_exercises/listArticle/"; // return all exercise of cate
	}
	public static final class KEY {
		public static final String is_success = "is_success";
		public static final String err_msg = "err_msg";
		public static final String status = "status";
	}
	
	public final static class VALUE {
		public static final String STATUS_API_SUCCESS = "1";
		public static final String STATUS_API_FAIL = "0";

	};
	
	public final static class SCREEN{
		public static final String CATEGORY_WORKOUT = "CATEGORY WORKOUT";
		public static final String DETAIL_CATEGORY_WORKOUT = "DETAIL CATEGORY WORKOUT";
		public static final String REGISTER = "REGISTER";
		public static final String LOGIN = "LOGIN";
		public static final String LOGOUT = "LOGOUT";
		public static final String EDIT_PROFILE = "EDIT PROFILE";
		public static final String CALENDAR = "CALENDAR";
		public static final String BMI = "BMI";
		public static final String BMI_HISTORY = "BMI HISTORY";
		public static final String HEALTH_NUTRITION = "HEALTH-NUTRITION";
		public static final String HEART_TRACK = "HEART TRACK";
	}
	
	public static void execute(Context context, final RequestMethod method,
			final String api, final Map<String, String> params,
			final ApiServiceCallback apiServiceCallback) {
		((BaseApplication) context.getApplicationContext()).execute(method,
				api, params, apiServiceCallback);

	}
	
	public final static class ACCOUNT{
		public static String EMAIL = "d5@gmail.com";
		public static String PASS = "xxxxxxxx";
		public static String TOKEN = "964ce6fe146e1f1e624c4e60dc0e5432037ea016";
	}
	
	public static String formatJson(String strJson){
		return strJson.replace("null", "none");
	}
	
	public static String SHARE_PREFERENCE = "com.vn.hm";
	public static String TOKEN_KEY = "token_key";
	public static String LOGIN_KEY = "login_key";
	public static String LOGOUT_KEY = "logout_key";
}
