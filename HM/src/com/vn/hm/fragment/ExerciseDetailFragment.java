package com.vn.hm.fragment;

import java.util.HashMap;

import android.util.Log;
import android.view.View;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.R;

import d3.lib.base.callback.RestClient.RequestMethod;

public class ExerciseDetailFragment extends BaseFragment{

	private String TAG = "ExerciseDetailFragment";
	private int idCate;
	public ExerciseDetailFragment(int idCate) {
		this.idCate = idCate;
	}
	
	@Override
	public int getlayout() {
		
		return R.layout.exercise_detail_layout;
	}

	@Override
	public void initView(View view) {
		
	}
	
	}
