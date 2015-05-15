package com.vn.hm.fragment;

import android.view.View;

import com.d3.base.BaseFragment;
import com.vn.hm.R;
import com.vn.hm.object.CategoryObjectDetail;

public class VideoView extends BaseFragment{

	private android.widget.VideoView videoView;
	private CategoryObjectDetail object;
	
	public VideoView(CategoryObjectDetail object) {
		this.object = object;
	}
	
	@Override
	public int getlayout() {
		// TODO Auto-generated method stub
		return R.layout.video_view_layout;
	}

	@Override
	public void initView(View view) {
		videoView = (android.widget.VideoView)view.findViewById(R.id.videoview_id);
		
	}

}
