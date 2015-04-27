package com.vn.hm.fragment;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.R;

import d3.lib.base.callback.RestClient.RequestMethod;

public class BmiFragment extends BaseFragment {
	private String TAG = "BmiFragment";
	@Override
	public int getlayout() {
		// TODO Auto-generated method stub
		return R.layout.bmi_layout;
	}

	@Override
	public void initView(View view) {
//		updateBmi();
		updateProfile();
	}
	
	private void  updateBmi(){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(D3Utils.API.BASESERVER + D3Utils.API.API_UPDATE_BMI + "?token="+D3Utils.ACCOUNT.TOKEN);
		Log.i(TAG, "API : " + D3Utils.API.BASESERVER + D3Utils.API.API_UPDATE_BMI + "?token="+D3Utils.ACCOUNT.TOKEN);
		try {
		   
		    JSONObject jsonString = new JSONObject();
		    JSONObject jsonParams = new JSONObject();
		 // Create a local instance of cookie store
		    CookieStore cookieStore = new BasicCookieStore();

		    // Create local HTTP context
		    HttpContext localContext = new BasicHttpContext();
		    // Bind custom cookie store to the local context
		    Cookie cookie = new BasicClientCookie("name", "value-longsex");
		    cookieStore.addCookie(cookie);
		    localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		    
		    
		    try {
//				jsonString.put("email", D3Utils.ACCOUNT.EMAIL);
				jsonString.put("bmi", "25.5");
			    jsonParams.put("User", jsonString);
			    StringEntity strEntity = new StringEntity(jsonParams.toString());
			    strEntity.setContentType("application/json;charset=UTF-8");
			    strEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httppost.setEntity(strEntity);
			    Log.i(TAG, "Login 1- cucki" + cookieStore);
			    Log.i(TAG , "Params-send : " + EntityUtils.toString(strEntity));
			} catch (JSONException e) {
				e.printStackTrace();
			}

		    // Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost,localContext);
		    Log.i(TAG, "Login 2 : " + response.getStatusLine().getStatusCode());
		    if (response.getStatusLine().getStatusCode() == 200) {
		    	String responseText = EntityUtils.toString(response.getEntity(),"utf-8");
		        System.out.println("The response is" + responseText.toString());  
			}

		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		}
	}
	
	private void updateProfile(){
		HttpClient httpclient = new DefaultHttpClient();
		String API = D3Utils.API.BASESERVER + D3Utils.API.API_EDIT_PROFILE+ "?token="+D3Utils.ACCOUNT.TOKEN;
		HttpPost httppost = new HttpPost(API);
		Log.i(TAG, "API : " + API);
		try {
		   
		    JSONObject jsonString = new JSONObject();
		    JSONObject jsonParams = new JSONObject();
		 // Create a local instance of cookie store
		    CookieStore cookieStore = new BasicCookieStore();

		    // Create local HTTP context
		    HttpContext localContext = new BasicHttpContext();
		    // Bind custom cookie store to the local context
		    Cookie cookie = new BasicClientCookie("name", "value-longsex");
		    cookieStore.addCookie(cookie);
		    localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		    
		    
		    try {
//				jsonString.put("email", D3Utils.ACCOUNT.EMAIL);
				jsonString.put("bmi", "25.5");
				jsonString.put("height", "1.95");
				
			    jsonParams.put("User", jsonString);
			    StringEntity strEntity = new StringEntity(jsonParams.toString());
			    strEntity.setContentType("application/json;charset=UTF-8");
			    strEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httppost.setEntity(strEntity);
			    Log.i(TAG, "Login 1- cucki" + cookieStore);
			    Log.i(TAG , "Params-send : " + EntityUtils.toString(strEntity));
			} catch (JSONException e) {
				e.printStackTrace();
			}

		    // Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost,localContext);
		    Log.i(TAG, "Login 2 : " + response.getStatusLine().getStatusCode());
		    if (response.getStatusLine().getStatusCode() == 200) {
		    	String responseText = EntityUtils.toString(response.getEntity(),"utf-8");
		        System.out.println("The response is" + responseText.toString());  
			}

		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		}
	}
}
