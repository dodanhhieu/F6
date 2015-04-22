package com.vn.hm.fragment;

import java.io.IOException;

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
import com.vn.hm.R;

import d3.lib.base.callback.RestClient.RequestMethod;

public class LoginFragment extends BaseFragment{

	private String TAG = "LoginFragment";

	@Override
	public int getlayout() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
	}

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		getLogin();
	} 
	
	private void getLogin(){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(D3Utils.API.BASESERVER + D3Utils.API.API_LOGIN);
		Log.i(TAG, "API : " + D3Utils.API.BASESERVER + D3Utils.API.API_LOGIN);
		try {
		   
		    JSONObject jsonString = new JSONObject();
		    JSONObject jsonParams = new JSONObject();
		     
		    try {
				jsonString.put("email", D3Utils.ACCOUNT.EMAIL);
				jsonString.put("passwd", D3Utils.ACCOUNT.PASS);
			    jsonParams.put("User", jsonString);
			    StringEntity strEntity = new StringEntity(jsonParams.toString());
			    strEntity.setContentType("application/json;charset=UTF-8");
			    strEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httppost.setEntity(strEntity);
			    
			    Log.i(TAG , "Params-send : " + EntityUtils.toString(strEntity));
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
		}
	}
}
