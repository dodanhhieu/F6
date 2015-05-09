package com.vn.hm.fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.activeandroid.query.Select;
import com.d3.base.BaseFragment;
import com.d3.base.db.BpmTable;
import com.d3.base.db.Heart;
import com.hm.heart_rate_monitor.HeartRateMonitor;
import com.vn.hm.R;

public class HeartTrackFragment extends BaseFragment implements OnClickListener{

	private RelativeLayout btnStart;
	private String TAG = "HeartTrackFragment";
	@Override
	public int getlayout() {
		
		return R.layout.hearttrack_layout;
	}

	@Override
	public void initView(View view) {
		btnStart = (RelativeLayout)view.findViewById(R.id.rl_start_id);
		btnStart.setOnClickListener(this);
//		new BpmTable(getActivity());
		getAllHistory();
	}

	@Override
	public void onClick(View v) {
		int idView = v.getId();
		switch (idView) {
		case R.id.rl_start_id:
			Intent intent = new Intent(getActivity(),HeartRateMonitor.class);
//			getActivity().startActivity(intent);
			getActivity().startActivityForResult(intent, 1);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "====> resume");
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(TAG, "onActivityResult");
		if (requestCode == 1) {
			Log.i(TAG , "data BPM = " + data.getIntExtra("BPM", 0));
			Log.i(TAG , "data Name = " + data.getIntExtra("Name", 0));
			Log.i(TAG , "data Date = " + data.getIntExtra("Date",0));
			
			int bmpIndex = data.getIntExtra("BPM", 0);
			String name = String.valueOf(data.getIntExtra("Name", 0));
			int bpmIndex = data.getIntExtra("BPM", 0);
			String date = String.valueOf(data.getIntExtra("Date",0));
			
			Heart h = new Heart(name, bpmIndex, date);
			h.save();
			//showDialogSaveData(bmpIndex);
		}
	}
	
	private void showDialogSaveData(int bpmIndex){
		LayoutInflater inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inf.inflate(R.layout.input_bpm_layout, null);
		EditText edtInput = (EditText)view.findViewById(R.id.input_name_id);
		Button btnSave = (Button)view.findViewById(R.id.input_btn_save_id);
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setCancelable(false);
		
		Dialog d = builder.create();
		d.show();
	}
	
	private void getAllHistory(){
		Select select = new Select();
		ArrayList<Heart> list = select.all().from(Heart.class).execute();
		for (int i = 0; i < list.size(); i++) {
			Log.i(TAG, "Value = " + list.get(i).date + " ; " + list.get(i).name + " ; " + list.get(i).indexHeart);
		}
	}
}
