package com.vn.hm.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.d3.base.db.BmiTable;
import com.vn.hm.MainActivity;
import com.vn.hm.R;

public class BmiHistoryFragment extends BaseFragment {

	private String TAG = "BmiHistoryFragment";
	private TextView txtRecommend;
	private ListView listview;
	private String msgRecommend;
	private ArrayList<BmiTable> listData;
	private float bmiMin,bmiMax;
	@Override
	public int getlayout() {
		
		return R.layout.bmi_history_layout;
	}

	@Override
	public void initView(View view) {
		MainActivity.updateTitleHeader(D3Utils.SCREEN.BMI);
		txtRecommend = (TextView)view.findViewById(R.id.txt_msg_id);
		listview = (ListView)view.findViewById(R.id.history_id);
		// get all data in db 
		Select select = new Select();
		listData = select.all().from(BmiTable.class).execute();
		Collections.reverse(listData);
		Log.i(TAG, "Size data = " + listData.size());
		
		if (listData.size() > 0) {
			BmiHistoryAdapter adapter = new BmiHistoryAdapter(getActivity(), listData);
			listview.setAdapter(adapter);
			msgRecommend = getRecommendMsg();
			txtRecommend.setText(msgRecommend);
		}
		
	}
	
	private String getRecommendMsg(){
		bmiMin = 20.0f;
		bmiMax = 25.0f;
		int count = listData.size();
		float bmi1 = listData.get(1).bmiIndex;
		float bmi2 = listData.get(0).bmiIndex;
		for (int i = 0; i < count; i++) {
			Log.i(TAG, "BMI i = " + i+" :" + listData.get(i).bmiIndex);
		}
		Log.i(TAG, "BMI 1 = "+(count-2) +" :" + bmi1);
		Log.i(TAG, "BMI 2 = " +(count-1) +" :"+ bmi2);
		if (bmi1 > bmiMax && bmi2 > bmiMax) {
			// user need to lose weight
			if (bmi2 < bmi1) {
				// good
				msgRecommend = "GOOD! YOU NEED TO TRYUP TO GET GOOD BODY";
			}else{
				// bad
				msgRecommend = "BAD! YOU WERE NOT DO WORKOUT HARDWORK :(";
			}
		}else if(bmi1 < bmiMin && bmi2 < bmiMin){
			// user need to up weight
			if (bmi2 > bmi1) {
				// good
				msgRecommend = "GOOD! YOU NEED TO TRYUP TO GET GOOD BODY";
			}else{
				// bad
				msgRecommend = "BAD! YOU WERE NOT DO WORKOUT HARDWORK :(";
			}
		}else if(bmi2 >= bmiMin && bmi2 <= bmiMax){
			// good body
			msgRecommend = "GOOD! YOU HAVE GOOD BODY";
		}
		return msgRecommend;
	}
	private class BmiHistoryAdapter extends BaseAdapter{
		private Context mContext;
		private List<BmiTable> data;
		
		public BmiHistoryAdapter(Context context, List<BmiTable> list) {
			this.mContext = context;
			this.data = list;
		}
		@Override
		public int getCount() {
			
			return data.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return data.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View converView, ViewGroup arg2) {
			View view = converView;
			Holder holder;
			if (view == null) {
				holder = new Holder();
				LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inf.inflate(R.layout.bmi_item, null);
				holder.txtBmi = (TextView)view.findViewById(R.id.bmi_item_index_id);
				holder.txtDate = (TextView)view.findViewById(R.id.bmi_item_date_id);
				view.setTag(holder);
			}else{
				holder = (Holder)view.getTag();
			}
			holder.txtBmi.setText(String.valueOf(data.get(position).bmiIndex));
			holder.txtDate.setText(data.get(position).date);
			if (position == 0 ) {
				holder.txtBmi.setTextColor(getResources().getColor(R.color.red_color));
				holder.txtBmi.setTextSize(20);
			}
			return view;
		}
		
		
	}
	
	private class Holder{
		private TextView txtBmi;
		private TextView txtDate;
	}
	
	

}
