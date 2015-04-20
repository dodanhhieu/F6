package com.vn.hm.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.d3.base.BaseFragment;
import com.d3.base.db.BpmTable;
import com.hm.heart_rate_monitor.HeartRateMonitor;
import com.vn.hm.R;

public class HeartTrackFragment extends BaseFragment implements OnClickListener{

	private RelativeLayout btnStart;
	@Override
	public int getlayout() {
		
		return R.layout.hearttrack_layout;
	}

	@Override
	public void initView(View view) {
		btnStart = (RelativeLayout)view.findViewById(R.id.rl_start_id);
		btnStart.setOnClickListener(this);
		new BpmTable(getActivity());
	}

	@Override
	public void onClick(View v) {
		int idView = v.getId();
		switch (idView) {
		case R.id.rl_start_id:
			Intent intent = new Intent(getActivity(),HeartRateMonitor.class);
			getActivity().startActivity(intent);
			break;

		default:
			break;
		}
	}

}
