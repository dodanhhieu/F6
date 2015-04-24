package com.vn.hm.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.R;
import com.vn.hm.object.CategoryObjectDetail;

import d3.lib.base.callback.RestClient.RequestMethod;

public class CategoryDetail extends BaseFragment {

	private String TAG = "CategoryDetail";
	private ListView listview;
	private List<CategoryObjectDetail> listData;

	private int idCate;

	public CategoryDetail(int cateId) {
		this.idCate = cateId;
	}

	@Override
	public int getlayout() {

		return R.layout.category_detail_layout;
	}

	@Override
	public void initView(View view) {
		getAllDataCate(idCate);
		listview = (ListView)view.findViewById(R.id.cate_detail_listview_id);

	}

	private void getAllDataCate(int idCate) {

		HashMap<String, String> params = new HashMap<String, String>();
		String API = D3Utils.API.API_LIST_EXERCISE_OF_CATE
				+ String.valueOf(idCate) + ".json";
		D3Utils.execute(getActivity(), RequestMethod.GET,
				D3Utils.API.API_LIST_ALL_EXERCISES, params,
				new ApiServiceCallback() {

					@Override
					public void onError(String msgError) {
						super.onError(msgError);
						Log.i(TAG, "ERROR: " + msgError);
					}

					@Override
					public void onSucces(String responeData) {
						super.onSucces(responeData);
						Log.i(TAG, "Res = " + responeData);
						try {
							JSONObject jsonRes = new JSONObject(responeData);
							JSONObject js = jsonRes.getJSONObject("responsse_data");
							JSONArray jsonArray = js.getJSONArray("data");
							listData = new ArrayList<CategoryObjectDetail>();
							for (int i = 0; i < jsonArray.length(); i++) {
								CategoryObjectDetail item = new CategoryObjectDetail();
								JSONObject jobj1 = jsonArray.getJSONObject(i);
								JSONObject jobj = jobj1.getJSONObject("Exercise");
								item.setId(Integer.valueOf(jobj.getString("id")));
								item.setDescription(jobj.getString("description"));
								item.setContent(jobj.getString("content"));
								// item.setImage(jobj.getString(""));
								item.setTitle(jobj.getString("title"));
								item.setVideo(jobj.getString("video"));
								listData.add(item);

							}
							CateDetailAdapter adapter = new CateDetailAdapter(getActivity(), listData);
							listview.setAdapter(adapter);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private class CateDetailAdapter extends BaseAdapter {
		private Context mContext;
		private List<CategoryObjectDetail> data;

		public CateDetailAdapter(Context context,
				List<CategoryObjectDetail> list) {
			this.mContext = context;
			this.data = list;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return data.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			Holder holder;
			if (view == null) {
				holder = new Holder();
				LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inf.inflate(R.layout.exercise_cate_detail_item, null);
				holder.txtTitle = (TextView)view.findViewById(R.id.ex_cate_title_id);
				holder.txtDes = (TextView)view.findViewById(R.id.ex_cate_des_id);
				holder.img = (ImageView)view.findViewById(R.id.ex_cate_imgthumb_id);
				view.setTag(holder);
			}else{
				holder = (Holder)view.getTag();
			}
			CategoryObjectDetail obj = (CategoryObjectDetail)data.get(position);
//			holder.txtTitle.setText(data.get(position).getTitle());
//			holder.txtDes.setText(data.get(position).getDescription());
			holder.txtTitle.setText(obj.getTitle());
			return view;
		}

	}
	
	private class Holder{
		private TextView txtTitle;
		private TextView txtDes;
		private ImageView img;
	}
}
