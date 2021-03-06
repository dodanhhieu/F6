package com.vn.hm.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.d3.base.DataSharePref;
import com.d3.base.GlobalFunction;
import com.d3.base.db.UserAccount;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.MainActivity;
import com.vn.hm.MenuFragment;
import com.vn.hm.R;

import d3.lib.base.callback.RestClient.RequestMethod;

public class EditUserFragment extends BaseFragment {

	private String TAG = "RegsiterFragment";
	private EditText etEmail, etPassword, etWeight, etHeight, etName;
	private Spinner spSex;

	private Button btnRegister;
	private ArrayList<String> arrSex = new ArrayList<String>();

	@Override
	public int getlayout() {

		return R.layout.register_layout;

	}

	@Override
	public void initView(View view) {
		MainActivity.updateTitleHeader(D3Utils.SCREEN.EDIT_PROFILE);
		etEmail = (EditText) view.findViewById(R.id.etEmail);
		etEmail.setEnabled(false);
		etPassword = (EditText) view.findViewById(R.id.etPass);
		etWeight = (EditText) view.findViewById(R.id.etWeight);
		etHeight = (EditText) view.findViewById(R.id.etHeight);
		etName = (EditText) view.findViewById(R.id.etName);
		
		spSex = (Spinner) view.findViewById(R.id.spSex);
		updateData();
		btnRegister = (Button) view.findViewById(R.id.btnRegister);
		btnRegister.setText("Update");
		arrSex.add("Men");
		arrSex.add("Women");
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, arrSex);
		spSex.setAdapter(mAdapter);
		
		etPassword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				String email = etEmail.getText().toString();
				if (!GlobalFunction.isEmail(email)) {
					GlobalFunction.showDialog(getActivity(), "Email Error", "Ok", null, null, null);
					etEmail.requestFocus();
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				registerAccount();
			}
		});

	}
	
	private void updateData(){
		Select select = new Select();
		ArrayList<UserAccount> listAccount = select.all().from(UserAccount.class).execute();
		if (listAccount.size() > 0) {
			Collections.reverse(listAccount);
			UserAccount user = listAccount.get(0);
			etEmail.setText(user.email);
			etName.setText(user.name);
			etHeight.setText(String.valueOf(user.height));
			etWeight.setText(String.valueOf(user.weight));
			etPassword.setText(user.password);
		}
	}
	
	private void registerAccount() {

//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("email", etEmail.getText().toString());
//		params.put("password", etPassword.getText().toString());
//		params.put("weight", etWeight.getText().toString());
//		params.put("height", etHeight.getText().toString());
//		params.put("name", etName.getText().toString());
//		UserAccount user = new UserAccount();
//		String strName = etName.getText().toString(); 
//		if (strName != null) {
//			user.name = "Please regsiter name";
//		}else{
//			user.name = strName;
//		}
//		user.save();
		int sexId = 0;
		if (String.valueOf(spSex.getSelectedItem()).equalsIgnoreCase("Men")) {
			sexId = 1;
		}else{
			sexId = 0;
		}
//		params.put("sex", String.valueOf(sexId));
		//params.put("birthday", etBirthDay.getText().toString());
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(D3Utils.API.BASESERVER + D3Utils.API.API_REGISTER);
		Log.i(TAG, "API : " + D3Utils.API.BASESERVER + D3Utils.API.API_REGISTER);
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
		    	jsonString.put("email", etEmail.getText().toString());
		    	jsonString.put("passwd", etPassword.getText().toString());
		    	jsonString.put("weight", etWeight.getText().toString());
		    	jsonString.put("height", etHeight.getText().toString());
		    	jsonString.put("name", etName.getText().toString());
		    	jsonString.put("sex", String.valueOf(sexId));
		    	jsonString.put("born", "2010-12-12");
				
			    jsonParams.put("User", jsonString);
			    StringEntity strEntity = new StringEntity(jsonParams.toString());
			    strEntity.setContentType("application/json;charset=UTF-8");
			    strEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httppost.setEntity(strEntity);
			    //Log.i(TAG, "Login 1- cucki" + cookieStore);
			    Log.i(TAG , "Params-send : " + EntityUtils.toString(strEntity));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    // Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost,localContext);
		    Log.i(TAG, "Login 2 : " + response.getStatusLine().getStatusCode());
		    if (response.getStatusLine().getStatusCode() == 200) {
		    	String responseText = EntityUtils.toString(response.getEntity(),"utf-8");
		        System.out.println("The response is" + responseText.toString());  
		        try {
					JSONObject jsonRes = new JSONObject(responseText.toString());
					JSONObject jsonData = jsonRes.getJSONObject("responsse_data");
					String token = jsonData.getString("data");
					if (token.length() > 0) {
						DataSharePref dataSharePref = new DataSharePref(getActivity());
						dataSharePref.saveString(D3Utils.TOKEN_KEY, token);
						dataSharePref.saveInt(D3Utils.LOGIN_KEY, 1);
						
						UserAccount account = new UserAccount();
						account.name = etName.getText().toString();
						account.email = etEmail.getText().toString();
						account.email= etEmail.getText().toString();
				    	account.password = etPassword.getText().toString();
				    	account.weight =  Float.valueOf(etWeight.getText().toString());
				    	account.height =  Float.valueOf(etHeight.getText().toString());
				    	account.gioitinh =  String.valueOf(sexId);
						account.save();
						
						//MenuFragment menu = new MenuFragment();
						//menu.setUpStatusLogin(token,getActivity());
						switchFragment(new ExerciseCategory());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		}
	}

	private void getListCategories() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", "");

		D3Utils.execute(getActivity(), RequestMethod.GET,
				D3Utils.API.API_LIST_ARTICLE, params, new ApiServiceCallback() {

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

	public void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment,"");
		}

	}
}
