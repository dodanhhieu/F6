package com.vn.hm.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Select;
import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.d3.base.DataSharePref;
import com.d3.base.GlobalFunction;
import com.d3.base.db.UserAccount;
import com.vn.hm.MainActivity;
import com.vn.hm.R;

public class LoginFragment extends BaseFragment {
	private EditText etEmail, etPassword;
	private Button btnLogin;
	private String TAG = "LoginFragment";
	private String email,password;
	@Override
	public int getlayout() {
		// TODO Auto-generated method stub
		return R.layout.login_layout;
	}

	@Override
	public void initView(View view) {
		MainActivity.updateTitleHeader(D3Utils.SCREEN.LOGIN);
		etEmail = (EditText) view.findViewById(R.id.etEmail);
		etPassword = (EditText) view.findViewById(R.id.etPass);
		btnLogin = (Button) view.findViewById(R.id.btnLogin);
		updateData();
		
		Log.i(TAG, "Email : " + email);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});
	}
	private void updateData(){
		Select select = new Select();
		ArrayList<UserAccount> listAccount = select.all().from(UserAccount.class).execute();
		if (listAccount.size() > 0) {
			Collections.reverse(listAccount);
			UserAccount user = listAccount.get(0);
			email = user.email;
			password = user.password;
		}
	}
	private void login() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(D3Utils.API.BASESERVER
				+ D3Utils.API.API_LOGIN);
		Log.i(TAG, "API : " + D3Utils.API.BASESERVER + D3Utils.API.API_LOGIN);
		try {

			JSONObject jsonString = new JSONObject();
			JSONObject jsonParams = new JSONObject();
			// Create a local instance of cookie store
			CookieStore cookieStore = new BasicCookieStore();

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			Cookie cookie = new BasicClientCookie("name", "value-long");
			cookieStore.addCookie(cookie);
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String strEmail = etEmail.getText().toString();
			if (GlobalFunction.isEmail(strEmail)) {
				try {
					jsonString.put("email", strEmail);
					jsonString.put("passwd", etPassword.getText().toString());

					jsonParams.put("User", jsonString);
					StringEntity strEntity = new StringEntity(
							jsonParams.toString());
					strEntity.setContentType("application/json;charset=UTF-8");
					strEntity
							.setContentEncoding(new BasicHeader(
									HTTP.CONTENT_TYPE,
									"application/json;charset=UTF-8"));
					httppost.setEntity(strEntity);
					// Log.i(TAG, "Login 1- cucki" + cookieStore);
					Log.i(TAG,
							"Params-send : " + EntityUtils.toString(strEntity));

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost,
						localContext);
				Log.i(TAG, "Login 2 : "
						+ response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200) {
					String responseText = EntityUtils.toString(
							response.getEntity(), "utf-8");
					System.out.println("The response is"
							+ responseText.toString());
					try {
						JSONObject jsonRes = new JSONObject(
								responseText.toString());
						JSONObject jsonData = jsonRes
								.getJSONObject("responsse_data");
						token = jsonData.getString("data");
						String status = jsonData.getString("status");
						
						//token = "asdasiefnjn12e324qczxcsfas4";
						//status = "1";
						if (status.equalsIgnoreCase("0")) {
							GlobalFunction.showDialog(getActivity(),
									"Login fail", "OK", null, null, null);
						} else {
							DataSharePref dataSharePref = new DataSharePref(
									getActivity());
							dataSharePref.saveString(D3Utils.TOKEN_KEY, token);
							// MenuFragment menu = new MenuFragment();
							dataSharePref.saveInt(D3Utils.LOGIN_KEY, 1);
							// menu.updateUI(token, getActivity());
							// menu.setUpStatusLogin(token, getActivity());
							switchFragment(new ExerciseCategory());
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				GlobalFunction.showDialog(getActivity(), "Email format error",
						"OK", null, null, null);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

	}

	String token;

	public void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment, "");
			// MenuFragment menu = new MenuFragment();
			// menu.setUpStatusLogin(token,getActivity());
		}

	}
}
