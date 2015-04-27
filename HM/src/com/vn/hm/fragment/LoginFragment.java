package com.vn.hm.fragment;

import java.util.HashMap;

import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.R;

import d3.lib.base.callback.RestClient.RequestMethod;

public class LoginFragment extends BaseFragment {
	private EditText etEmail, etPassword;
	private Button btnLogin;

	@Override
	public int getlayout() {
		// TODO Auto-generated method stub
		return R.layout.login_layout;
	}

	@Override
	public void initView(View view) {
		etEmail = (EditText) view.findViewById(R.id.etEmail);
		etPassword = (EditText) view.findViewById(R.id.etPass);
		btnLogin = (Button) view.findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});
	}

	private void login() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", etEmail.getText().toString());
		params.put("password", etPassword.getText().toString());
		D3Utils.execute(getActivity(), RequestMethod.POST,
				D3Utils.API.API_LOGIN, params, new ApiServiceCallback() {
					@Override
					public void onStart() {
						super.onStart();

					}

					@Override
					public void onError(String msgError) {
						super.onError(msgError);
					}

					@Override
					public void onSucces(JSONObject responeJson) {
						super.onSucces(responeJson);

					}

				});

	}
}
