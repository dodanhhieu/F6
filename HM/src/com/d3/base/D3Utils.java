package com.d3.base;

import java.util.Map;

import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.BaseApplication;

import android.content.Context;
import d3.lib.base.callback.RestClient.RequestMethod;

public class D3Utils {

	public static final class API{
		public static String BASESERVER = "native.canhovietnam.net";
		public static String API_REGISTER = "/users/add.json";
		public static String API_LOGIN = "/users/login.json";
		public static String API_LOGOUT = "/users/logout.json";
		public static String API_UPDATE_BMI = "/users/updateBmi.json";
		public static String API_LIST_CATEGORY = "/categories/listCat.json";
		public static String API_LIST_ARTICLE = "/categories/listArticle.json?id=1";
		
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
	
	public static void execute(Context context, final RequestMethod method,
			final String api, final Map<String, String> params,
			final ApiServiceCallback apiServiceCallback) {
		((BaseApplication) context.getApplicationContext()).execute(method,
				api, params, apiServiceCallback);

	}
}
