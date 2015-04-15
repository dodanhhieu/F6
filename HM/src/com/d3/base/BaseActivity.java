package com.d3.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity{

	private int mTitle;
	
 
	public BaseActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// no implement
	}
	
	public void switchContent(int resLayoutId,Fragment newFragment){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction trs = fm.beginTransaction();
		trs.replace(resLayoutId, newFragment);
		trs.commit();
	}
}
