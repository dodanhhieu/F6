package com.vn.hm.fragment;

import java.util.HashMap;

import android.util.Log;
import android.view.View;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.R;

import d3.lib.base.callback.RestClient.RequestMethod;

public class ExerciseCategory extends BaseFragment{
	private String TAG = "ExerciseCategory";

	@Override
	public int getlayout() {
		// TODO Auto-generated method stub
		return R.layout.exercise_category_layout;
	}

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		getAllExerciseCategory();
	}

	private void getAllExerciseCategory(){
		HashMap<String, String> params = new HashMap<String, String>();
		D3Utils.execute(getActivity(), RequestMethod.GET,
				D3Utils.API.API_LIST_ALL_CATE_EXERCISES, params, new ApiServiceCallback(){
			

			@Override
			public void onError(String msgError) {
				Log.i(TAG, "error: " + msgError.toString());
				super.onError(msgError);
			}
			
			@Override
			public void onSucces(String responeData) {
				super.onSucces(responeData);
				Log.i(TAG, "respone : " + responeData.toString());
			}
		});
	}
}
