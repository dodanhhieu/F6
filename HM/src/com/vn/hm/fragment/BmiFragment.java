package com.vn.hm.fragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.d3.base.DataSharePref;
import com.d3.base.db.BmiTable;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.MainActivity;
import com.vn.hm.R;

import d3.lib.base.callback.RestClient.RequestMethod;

public class BmiFragment extends BaseFragment implements OnClickListener {
	private String TAG = "BmiFragment";
	private TextView value1,value2,value3,value4,value5,txtRecommend, valueWeightIdeal;
	private TextView txtResult,txtRecommendExercise,txtHistory;
	private EditText edtAge,edtHeight,edtWeight;
	private float weightValue,heightValue;
	private float bmiValue = 0.00f;
	private int ageValue;
	private float minWeight,maxWeight;
	
	@Override
	public int getlayout() {
		return R.layout.bmi_layout;
	}

	@Override
	public void initView(View view) {
		MainActivity.updateTitleHeader(D3Utils.SCREEN.BMI);
		value1 = (TextView)view.findViewById(R.id.vl1_id);
		value2 = (TextView)view.findViewById(R.id.vl2_id);
		value3 = (TextView)view.findViewById(R.id.vl3_id);
		value4 = (TextView)view.findViewById(R.id.vl4_id);
		value5 = (TextView)view.findViewById(R.id.vl5_id);
		txtRecommend = (TextView)view.findViewById(R.id.txt_recommend_id);
		
		edtHeight = (EditText)view.findViewById(R.id.edt_height_id);
		edtWeight = (EditText)view.findViewById(R.id.edt_weight_id);
		txtResult = (TextView)view.findViewById(R.id.txt_result_id);
		txtRecommendExercise = (TextView)view.findViewById(R.id.txt_recommend_exercise_id);
		txtRecommendExercise.setOnClickListener(this);
		txtHistory = (TextView)view.findViewById(R.id.txt_history_id);
		txtHistory.setOnClickListener(this);
		heightValue = 1;
		weightValue = 1;
		initUI();
		edtHeight.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//calculateBmi();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String strHeight = s.toString();
				if (strHeight.length() > 1) {
					heightValue = Float.valueOf(strHeight);
					Log.i(TAG, "H = " + heightValue);
					calculateBmi();
				}
				
			}
		});
		
		edtWeight.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//calculateBmi();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String strWeight = s.toString();
				if (strWeight.length() > 1) {
					weightValue = Float.valueOf(strWeight);
					Log.i(TAG, "W = " + weightValue);
					calculateBmi();
//					new UpdateBMI().execute("");
				}
				
			}
		});
//		updateBmi();
//		updateProfile();
	}
	
	private void initUI(){
		value1.setText("< 20");
		value2.setText("20 - 25");
		value3.setText("25 - 30");
		value4.setText("30 - 40");
		value5.setText("> 40");
	}
	
	private void calculateBmi(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		if (weightValue < 1 || heightValue < 1) {
			txtResult.setText(String.valueOf(0.0));
		}else if (weightValue > 1 && heightValue > 1){
			
			bmiValue = (float)(weightValue / (heightValue * heightValue));
			bmiValue = bmiValue*10000;
			bmiValue = Math.round(bmiValue);
//			updateBmi(String.valueOf(bmiValue));
			txtResult.setText(String.valueOf(bmiValue));
			//log value bmi to db
	    	final String currentDate = dateFormat.format(new Date());
	    	BmiTable bmiTable = new BmiTable();
	    	bmiTable.date = currentDate;
	    	bmiTable.bmiIndex = bmiValue;
	    	bmiTable.save();
	    	// save done
			minWeight = (20 * heightValue * heightValue)/10000;
			maxWeight = (25 * heightValue * heightValue)/10000;
			if (bmiValue <= 20.0) {
				value1.setTextColor(getResources().getColor(R.color.red_color));
				value2.setTextColor(getResources().getColor(R.color.green_dam));
				value3.setTextColor(getResources().getColor(R.color.green_dam));
				value4.setTextColor(getResources().getColor(R.color.green_dam));
				value5.setTextColor(getResources().getColor(R.color.green_dam));
				
			}else if(bmiValue > 20.0 && bmiValue <= 25.0){
				value1.setTextColor(getResources().getColor(R.color.green_dam));
				value2.setTextColor(getResources().getColor(R.color.red_color));
				value3.setTextColor(getResources().getColor(R.color.green_dam));
				value4.setTextColor(getResources().getColor(R.color.green_dam));
				value5.setTextColor(getResources().getColor(R.color.green_dam));
			}else if(bmiValue > 25.0 && bmiValue <= 30.0){
				value1.setTextColor(getResources().getColor(R.color.green_dam));
				value2.setTextColor(getResources().getColor(R.color.green_dam));
				value3.setTextColor(getResources().getColor(R.color.red_color));
				value4.setTextColor(getResources().getColor(R.color.green_dam));
				value5.setTextColor(getResources().getColor(R.color.green_dam));
			}else if(bmiValue > 30.0 && bmiValue <= 40.0){
				value1.setTextColor(getResources().getColor(R.color.green_dam));
				value2.setTextColor(getResources().getColor(R.color.green_dam));
				value3.setTextColor(getResources().getColor(R.color.green_dam));
				value4.setTextColor(getResources().getColor(R.color.red_color));
				value5.setTextColor(getResources().getColor(R.color.green_dam));
			}else if(bmiValue > 40.0){
				value1.setTextColor(getResources().getColor(R.color.green_dam));
				value2.setTextColor(getResources().getColor(R.color.green_dam));
				value3.setTextColor(getResources().getColor(R.color.green_dam));
				value4.setTextColor(getResources().getColor(R.color.green_dam));
				value5.setTextColor(getResources().getColor(R.color.red_color));
			}
		}
		int weightRecommend = (int)(maxWeight - weightValue);
		if (bmiValue < 20.0 || bmiValue > 25.0) {
			if (weightRecommend > 0) {
				// up
				txtRecommend.setText("YOU NEED TO UP WEIGHT AROUND " + String.valueOf(weightRecommend) + " KG");
			}else{
				// down
				txtRecommend.setText("YOU NEED TO LOSE WEIGHT AROUND " + String.valueOf(weightRecommend) + " KG");
			}
		}else if (bmiValue > 20.0 && bmiValue <= 25.0){
			txtRecommend.setText(" GOOD BODY! " );
		}
		
	}
	
	private void  updateBmi(String bmiValue){
		HttpClient httpclient = new DefaultHttpClient();
		DataSharePref dataSharePref = new DataSharePref(getActivity());
		String token = dataSharePref.getString(D3Utils.TOKEN_KEY);
		
		HttpPost httppost = new HttpPost(D3Utils.API.BASESERVER + D3Utils.API.API_UPDATE_BMI + "?token="+token);
		Log.i(TAG, "API : " + D3Utils.API.BASESERVER + D3Utils.API.API_UPDATE_BMI + "?token="+token);
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
				jsonString.put("bmi", bmiValue);
			    jsonParams.put("User", jsonString);
			    StringEntity strEntity = new StringEntity(jsonParams.toString());
			    strEntity.setContentType("application/json;charset=UTF-8");
			    strEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httppost.setEntity(strEntity);
			    //Log.i(TAG, "Login 1- cucki" + cookieStore);
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

	@Override
	public void onClick(View v) {
		int idView = v.getId();
		switch (idView) {
		case R.id.txt_recommend_exercise_id:
			Log.i(TAG, "Recommend exercise");
			switchFragment(new ExerciseCategory());
			break;
		case R.id.txt_history_id:
			// open history fragment
			switchFragment(new BmiHistoryFragment());
			break;
		default:
			break;
		}
	}
	
	public void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment,"");
		}

	}
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private class UpdateBMI extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			if (weightValue < 1 || heightValue < 1) {
//				txtResult.setText(String.valueOf(0.0));
			}else if (weightValue > 1 && heightValue > 1){
				
				bmiValue = (float)(weightValue / (heightValue * heightValue));
				bmiValue = bmiValue*10000;
				bmiValue = Math.round(bmiValue);
				//updateBmi(String.valueOf(bmiValue));
				txtResult.setText(String.valueOf(bmiValue));
				//log value bmi to db
		    	final String currentDate = dateFormat.format(new Date());
		    	BmiTable bmiTable = new BmiTable();
		    	bmiTable.date = currentDate;
		    	bmiTable.bmiIndex = bmiValue;
		    	bmiTable.save();
		    	// save done
				minWeight = (20 * heightValue * heightValue)/10000;
				maxWeight = (25 * heightValue * heightValue)/10000;
				if (bmiValue <= 20.0) {
					value1.setTextColor(getResources().getColor(R.color.red_color));
					value2.setTextColor(getResources().getColor(R.color.green_dam));
					value3.setTextColor(getResources().getColor(R.color.green_dam));
					value4.setTextColor(getResources().getColor(R.color.green_dam));
					value5.setTextColor(getResources().getColor(R.color.green_dam));
					
				}else if(bmiValue > 20.0 && bmiValue <= 25.0){
					value1.setTextColor(getResources().getColor(R.color.green_dam));
					value2.setTextColor(getResources().getColor(R.color.red_color));
					value3.setTextColor(getResources().getColor(R.color.green_dam));
					value4.setTextColor(getResources().getColor(R.color.green_dam));
					value5.setTextColor(getResources().getColor(R.color.green_dam));
				}else if(bmiValue > 25.0 && bmiValue <= 30.0){
					value1.setTextColor(getResources().getColor(R.color.green_dam));
					value2.setTextColor(getResources().getColor(R.color.green_dam));
					value3.setTextColor(getResources().getColor(R.color.red_color));
					value4.setTextColor(getResources().getColor(R.color.green_dam));
					value5.setTextColor(getResources().getColor(R.color.green_dam));
				}else if(bmiValue > 30.0 && bmiValue <= 40.0){
					value1.setTextColor(getResources().getColor(R.color.green_dam));
					value2.setTextColor(getResources().getColor(R.color.green_dam));
					value3.setTextColor(getResources().getColor(R.color.green_dam));
					value4.setTextColor(getResources().getColor(R.color.red_color));
					value5.setTextColor(getResources().getColor(R.color.green_dam));
				}else if(bmiValue > 40.0){
					value1.setTextColor(getResources().getColor(R.color.green_dam));
					value2.setTextColor(getResources().getColor(R.color.green_dam));
					value3.setTextColor(getResources().getColor(R.color.green_dam));
					value4.setTextColor(getResources().getColor(R.color.green_dam));
					value5.setTextColor(getResources().getColor(R.color.red_color));
				}
			}
			int weightRecommend = (int)(maxWeight - weightValue);
			if (bmiValue < 20.0 || bmiValue > 25.0) {
				if (weightRecommend > 0) {
					// up
					txtRecommend.setText("YOU NEED TO UP WEIGHT AROUND " + String.valueOf(weightRecommend) + " KG");
				}else{
					// down
					txtRecommend.setText("YOU NEED TO LOSE WEIGHT AROUND " + String.valueOf(weightRecommend) + " KG");
				}
			}else if (bmiValue > 20.0 && bmiValue <= 25.0){
				txtRecommend.setText(" GOOD BODY! " );
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (weightValue < 1 || heightValue < 1) {
				txtResult.setText(String.valueOf(0.0));
			}
			
		}
	}
	
}
