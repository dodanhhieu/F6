package com.vn.hm.fragment;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
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

public class Regsiter1Fragment extends BaseFragment{

	private String TAG = "RegsiterFragment";
	
	@Override
	public int getlayout() {
		
		return R.layout.register_layout;
	}

	@Override
	public void initView(View view) {
		//registerAccount();
		//getListCategories();
		postData();
	}
	
	private void registerAccount(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", "d3@gmail.com");
		params.put("password", "xxxxxxxx");
		params.put("weight", "68");
		params.put("height", "1.65");
		params.put("name", "Minh");
		params.put("sex", "men");
		params.put("birthday", "10/10/2010");
		
		D3Utils.execute(getActivity(), RequestMethod.POST, D3Utils.API.API_REGISTER, params, new ApiServiceCallback(){
			

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}
			
			@Override
			public void onError(String msgError) {
				Log.i(TAG, " error : " + msgError.toString());
				super.onError(msgError);
			}
			
			@Override
			public void onSucces(String responeData) {
				super.onSucces(responeData);
				Log.i(TAG, " success : " + responeData.toString());
			}
			
		});
	}

	private void getListCategories(){
		HashMap<String, String> params = new HashMap<String, String>();
		
		
		D3Utils.execute(getActivity(), RequestMethod.GET, D3Utils.API.API_LIST_ARTICLE, params, new ApiServiceCallback(){
			

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}
			
			@Override
			public void onError(String msgError) {
				Log.i(TAG, " error : " + msgError.toString());
				super.onError(msgError);
			}
			
			@Override
			public void onSucces(String responeData) {
				super.onSucces(responeData);
				Log.i(TAG, " success : " + responeData.toString());
			}
			
		});
	}
	
public void postData() {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(D3Utils.API.BASESERVER + D3Utils.API.API_REGISTER);
		Log.i(TAG, "API : " + D3Utils.API.BASESERVER + D3Utils.API.API_REGISTER);
		try {
		   
		    JSONObject jsonString = new JSONObject();
		    JSONObject jsonParams = new JSONObject();
		     
		    try {
				jsonString.put("email", D3Utils.ACCOUNT.EMAIL);
				jsonString.put("passwd", D3Utils.ACCOUNT.PASS);
			    jsonString.put("weight", "68");
			    jsonString.put("height", "1.65");
			    jsonString.put("name", "Minh");
			    jsonString.put("sex", "1");
			    jsonString.put("born", "2010-01-01");
			    jsonParams.put("User", jsonString);
			    StringEntity strEntity = new StringEntity(jsonParams.toString());
			    strEntity.setContentType("application/json;charset=UTF-8");
			    strEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httppost.setEntity(strEntity);
			    
			    Log.i(TAG, "Params-send : " + EntityUtils.toString(strEntity));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    // Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost);
		    if (response.getStatusLine().getStatusCode() == 200) {
		    	String responseText = EntityUtils.toString(response.getEntity(),"utf-8");
		        System.out.println("The response is" + responseText.toString());  
			}

		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		}}

	private void login(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", "");
		
		D3Utils.execute(getActivity(), RequestMethod.POST, D3Utils.API.API_LOGIN, params, new ApiServiceCallback(){
			@Override
			public void onStart() {
				// TODO bat dau goi api
				super.onStart();
			}
			
			@Override
			public void onError(String msgError) {
				// TODO loi api thi vao day
				super.onError(msgError);
			}
			
			@Override
			public void onSucces(String responeData) {
				// TODO goi api thanh cong thi vao day
				super.onSucces(responeData);
				try {
					JSONObject resJson = new JSONObject(responeData);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
	}
}
