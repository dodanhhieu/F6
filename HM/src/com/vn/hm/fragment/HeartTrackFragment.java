package com.vn.hm.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.d3.base.db.BpmTable;
import com.d3.base.db.Heart;
import com.d3.base.db.UserAccount;
import com.hm.heart_rate_monitor.HeartRateMonitor;
import com.vn.hm.MainActivity;
import com.vn.hm.R;
import com.vn.hm.R.id;

public class HeartTrackFragment extends BaseFragment implements OnClickListener{

	private TextView txtStart,txtHistory;
	private ListView listView;
	private TextView txtName,txtBpm;
	private UserAccount user;
	private String TAG = "HeartTrackFragment";
	@Override
	public int getlayout() {
		
		return R.layout.hearttrack_layout;
	}

	@Override
	public void initView(View view) {
		MainActivity.updateTitleHeader(D3Utils.SCREEN.HEART_TRACK);
		txtStart = (TextView)view.findViewById(R.id.txt_taptostart_id);
		txtStart.setOnClickListener(this);
		txtHistory = (TextView)view.findViewById(R.id.txt_history_id);
		txtHistory.setOnClickListener(this);
		listView = (ListView)view.findViewById(R.id.history_heart_lv_id);
		txtName = (TextView)view.findViewById(R.id.heart_name_id);
		txtBpm = (TextView)view.findViewById(R.id.heart_bpm_id);
		
		Select select = new Select();
		ArrayList<UserAccount> list = select.all().from(UserAccount.class).execute();
		Collections.reverse(list);
		if (list.size() > 0) {
			String name = list.get(0).name;
			txtName.setText(name);
		}
		
	}

	@Override
	public void onClick(View v) {
		int idView = v.getId();
		switch (idView) {
		case R.id.txt_taptostart_id:
			Intent intent = new Intent(getActivity(),HeartRateMonitor.class);
			getActivity().startActivityForResult(intent, 1);
			break;
		case R.id.txt_history_id:
			
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "====> resume");
		getAllHistory();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Log.i(TAG, "Value = " + list.get(i).date + " ; " + list.get(i).comment + " ; " + list.get(i).indexHeart);
			}
			HistoryAdapter adatpter = new HistoryAdapter(getActivity(), list);
			listView.setAdapter(adatpter);
			txtBpm.setText(String.valueOf(list.get(0).indexHeart));
			
		}
	}
	
	private class HistoryAdapter extends BaseAdapter{
		
		private Context mContext;
		private List<Heart> listData;
		
		public HistoryAdapter(Context ctx,List<Heart> list) {
			this.mContext = ctx;
			this.listData = list;
			Collections.reverse(listData);
		}
		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int arg0) {
			
			return listData.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view = convertView;
			Holder holder;
			if (view == null) {
				holder = new Holder();
				LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inf.inflate(R.layout.bpm_item, null);
				holder.txtBpm = (TextView)view.findViewById(R.id.bpmitem_index_id);
				holder.txtDate = (TextView)view.findViewById(R.id.bpmitem_date_id);
				holder.txtComment = (TextView)view.findViewById(R.id.bpmitem_comment_id);
				view.setTag(holder);
			}else{
				holder = (Holder)view.getTag();
			}
			holder.txtBpm.setText(String.valueOf(listData.get(position).indexHeart));
			holder.txtDate.setText(listData.get(position).date);
			holder.txtComment.setText(listData.get(position).comment);
			return view;
		}
		
	}
	
	private class Holder{
		private TextView txtBpm;
		private TextView txtDate;
		private TextView txtComment;
	}
}
