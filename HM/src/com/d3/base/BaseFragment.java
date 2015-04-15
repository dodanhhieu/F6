package com.d3.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{


	public BaseFragment() {
		super();
	}
	
	
	public abstract int getlayout();
	
	public abstract void initView(View view);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(getlayout(), null);
		initView(view);
		return view;
	}
}
