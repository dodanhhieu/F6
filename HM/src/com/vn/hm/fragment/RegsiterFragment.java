package com.vn.hm.fragment;

import java.util.HashMap;

import android.util.Log;
import android.view.View;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.R;

import d3.lib.base.callback.RestClient.RequestMethod;

public class RegsiterFragment extends BaseFragment{

	private String TAG = "RegsiterFragment";
	
	@Override
	public int getlayout() {
		
		return R.layout.register_layout;
	}

	@Override
	public void initView(View view) {
		registerAccount();
		getListCategories();
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
}
