package com.vn.hm.fragment;

import android.view.View;
import android.widget.ListView;

import com.d3.base.BaseFragment;
import com.vn.hm.R;

public class ExerciseDayFragment extends BaseFragment{

	private ListView listview;
	
	@Override
	public int getlayout() {
		// TODO Auto-generated method stub
		return R.layout.exerciseday_layout;
	}

	@Override
	public void initView(View view) {
		listview = (ListView)view.findViewById(R.id.exday_listview_id);
	}

}
