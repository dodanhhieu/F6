package com.d3.base;

import java.util.Map;

import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.BaseApplication;

import android.content.Context;
import d3.lib.base.callback.RestClient.RequestMethod;

public class D3Utils {

	public static final class API{
		public static String BASESERVER = "http://native.appcheap.net";
		public static String API_REGISTER = "/users/add.json";
		public static String API_LOGIN = "/users/login.json";
		public static String API_LOGOUT = "/users/logout.json";
		public static String API_UPDATE_BMI = "/users/updateBmi.json";
		public static String API_LIST_CATEGORY = "/categories/listCat.json";
		public static String API_LIST_ARTICLE = "/categories/listArticle.json?id=1";
		public static String API_LIST_ALL_EXERCISES = "/exercises/listAll.json";
		public static String API_LIST_ALL_CATE_EXERCISES = "/category_execises/listCat.json";
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
	}
	
	public static void execute(Context context, final RequestMethod method,
			final String api, final Map<String, String> params,
			final ApiServiceCallback apiServiceCallback) {
		((BaseApplication) context.getApplicationContext()).execute(method,
				api, params, apiServiceCallback);

	}
}
