package com.vn.hm.fragment;


import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.MainActivity;
import com.vn.hm.R;
import com.vn.hm.object.CategoryObject;

import d3.lib.base.callback.RestClient.RequestMethod;

public class ExerciseCategory extends BaseFragment{

	private String TAG = "ExerciseCategory";
	private List<CategoryObject> listCate;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private ListView listview;
	@Override
	public int getlayout() {
		// TODO Auto-generated method stub
		return R.layout.exercise_category_layout;
	}

	@Override
	public void initView(View view) {

		// update title
		MainActivity.updateTitleHeader(D3Utils.SCREEN.CATEGORY_WORKOUT);
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.ic_launcher)
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory()
				.build();
		listview = (ListView)view.findViewById(R.id.exercise_cate_listview_id);
		getAllExerciseCategory();
		
	}

	private void getAllExerciseCategory(){
		HashMap<String, String> params = new HashMap<String, String>();
		D3Utils.execute(getActivity(), RequestMethod.GET,
				D3Utils.API.API_LIST_CATEGORY, params, new ApiServiceCallback(){
			
			@Override
			public void onError(String msgError) {
				Log.i(TAG, "error: " + msgError.toString());
				super.onError(msgError);
			}
			
			@Override
			public void onSucces(String responeData) {
				super.onSucces(responeData);
				try {
					JSONObject jsonRespone = new JSONObject(responeData);
					JSONObject jsonData = jsonRespone.getJSONObject("responsse_data");
					JSONArray jsonArrayData = jsonData.getJSONArray("data");
					for (int i = 0; i < jsonArrayData.length(); i++) {
						JSONObject object = jsonArrayData.getJSONObject(i);
						CategoryObject item = new CategoryObject();
						item.setId(object.getInt("id"));
						item.setName(object.getString("name"));
						item.setUrlImage(object.getString("image"));
						listCate.add(item);
					}
					CateAdapter cateAdapter = new CateAdapter(getActivity(), listCate);
					listview.setAdapter(cateAdapter);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public class CateAdapter extends BaseAdapter{

		private Context mContext;
		private List<CategoryObject> data;
		
		public CateAdapter(Context context,List<CategoryObject> listData) {
			this.mContext = context;
			this.data = listData;
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
			return data.get(arg0).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view = convertView;
			Holder holder;
			if (view == null) {
				LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inf.inflate(R.layout.exercise_cate_item, null);
				holder = new Holder();
				holder.imgThumb = (ImageView)view.findViewById(R.id.ex_cate_imgthumb_id);
				holder.txtName = (TextView)view.findViewById(R.id.ex_cate_title_id);
				view.setTag(holder);
			}else{
				holder = (Holder)view.getTag();
			}
			CategoryObject obj = (CategoryObject) getItem(position);
			imageLoader.displayImage(obj.getUrlImage(), holder.imgThumb, options);
			holder.txtName.setText(obj.getName());
			return view;
		}
		
	}
	
	private class Holder{
		private ImageView imgThumb;
		private TextView txtName;
	}

}
