package com.vn.hm.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.d3.base.GlobalFunction;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.MainActivity;
import com.vn.hm.R;
import com.vn.hm.calendar.CalendarUtility;
import com.vn.hm.object.CategoryObjectDetail;

import d3.lib.base.callback.RestClient.RequestMethod;

public class CategoryDetail extends BaseFragment {

	private String TAG = "CategoryDetail";
	private ListView listview;
	private List<CategoryObjectDetail> listData;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private int idCate;

	int mHour,nHour; 
	int mMinute,nMinute;
	
	private String title,strDes;
	/** This handles the message send from TimePickerDialogFragment on setting Time */
	int count = 0;
	Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message m){   
        	/** Creating a bundle object to pass currently set Time to the fragment */
        	Log.i(TAG, "COUNT = " + count++);
        	//if (count == 1) {
				
			
        	Bundle b = m.getData();
        	
        	/** Getting the Hour of day from bundle */
    		nHour = b.getInt("set_hour");
    		
    		/** Getting the Minute of the hour from bundle */
    		nMinute = b.getInt("set_minute");
    		
			Log.i(TAG, "Hour 1 == " + mHour);
//    		Toast.makeText(getActivity(), b.getString("today :"), Toast.LENGTH_SHORT).show();
    		
			CalendarUtility.addEvent(getActivity(), title,   strDes, System.currentTimeMillis()+ (nHour - mHour)*60*60*1000 + (nMinute - mMinute)*60*1000, System.currentTimeMillis() + 30000);
			Log.i(TAG, "curentime == " + System.currentTimeMillis());
			GlobalFunction.showDialog(getActivity(), "Add workout success", "OK", null, null, null);
        	//}
        }
	};
	private ProgressDialog pr;

	public CategoryDetail(int cateId) {
		this.idCate = cateId;
	}

	@Override
	public int getlayout() {

		return R.layout.category_detail_layout;
	}

	@Override
	public void initView(View view) {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.ic_launcher)
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory()
				.imageScaleType(ImageScaleType.NONE_SAFE)
				.build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		MainActivity.updateTitleHeader(D3Utils.SCREEN.DETAIL_CATEGORY_WORKOUT);
		getAllDataCate(idCate);
		listview = (ListView)view.findViewById(R.id.cate_detail_listview_id);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String urlVideo = listData.get(position).getVideo();
				Log.i(TAG, "videoUrl " + urlVideo);
				if (GlobalFunction.isRegister(getActivity())) {
					if (GlobalFunction.isLogin(getActivity())) {
						playVideoExercise(urlVideo);
					}else{
						GlobalFunction.showDialog(getActivity(), "Please LOGIN to play video", "Ok", null, null, null);
					}
				}else{
					GlobalFunction.showDialog(getActivity(), "Please register account to play video", "Ok", null, null, null);
				}
			}
		});
		
		
	}

	private void getAllDataCate(int idCate) {
		pr = ProgressDialog.show(getActivity(), "Fitness & Gym", "loading...");
		HashMap<String, String> params = new HashMap<String, String>();
		String API = D3Utils.API.API_LIST_EXERCISE_OF_CATE
				+ String.valueOf(idCate) + ".json";
		D3Utils.execute(getActivity(), RequestMethod.GET,API, params,
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
							for (int j = 0; j < jsonArray.length(); j++) {
								JSONArray jsojArray = jsonArray.getJSONObject(j).getJSONArray("Exercise");
								Log.i(TAG, "=====" + jsojArray.length());
								for (int i = 0; i < jsojArray.length(); i++) {
									CategoryObjectDetail item = new CategoryObjectDetail();
									JSONObject jobj = jsojArray.getJSONObject(i);
									item.setId(Integer.valueOf(jobj.getString("id")));
									item.setDescription(jobj.getString("description"));
									item.setContent(jobj.getString("content"));
									 item.setImage(jobj.getString("image"));
									item.setTitle(jobj.getString("title"));
									item.setVideo(jobj.getString("video"));
									listData.add(item);
								}
							}
							
							CateDetailAdapter adapter = new CateDetailAdapter(getActivity(), listData);
							listview.setAdapter(adapter);
							pr.dismiss();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			Holder holder;
			if (view == null) {
				holder = new Holder();
				LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inf.inflate(R.layout.exercise_cate_detail_item, null);
				holder.txtTitle = (TextView)view.findViewById(R.id.ex_cate_title_id);
				holder.txtDes = (TextView)view.findViewById(R.id.ex_cate_des_id);
				holder.img = (ImageView)view.findViewById(R.id.ex_cate_imgthumb_id);
				holder.videoView = (VideoView)view.findViewById(R.id.ex_cate_video_id);
				holder.txtAddWorkout = (TextView)view.findViewById(R.id.ex_cate_add_id);
				view.setTag(holder);
			}else{
				holder = (Holder)view.getTag();
			}
			CategoryObjectDetail obj = (CategoryObjectDetail)data.get(position);
			holder.txtDes.setText(data.get(position).getDescription());
			holder.txtTitle.setText(obj.getTitle());
			imageLoader.displayImage(obj.getImage(), holder.img, options);
			holder.txtAddWorkout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Log.i(TAG, "Add to workout");
					title = data.get(position).getTitle();
					strDes = data.get(position).getDescription();
					
//					GlobalFunction.showDialog(mContext, "Add workout success", "OK", null, null, null);
//					CalendarUtility.addEvent(getActivity(), title,
//						    strDes, System.currentTimeMillis() + 15000,
//						    System.currentTimeMillis() + 30000);
					
					
					/** Creating a bundle object to pass currently set time to the fragment */
					Bundle b = new Bundle();
					Calendar cal = Calendar.getInstance();
					cal.setTimeZone(TimeZone.getDefault());
					mHour = cal.get(Calendar.HOUR_OF_DAY);
					mMinute = cal.get(Calendar.MINUTE);
					Log.i(TAG, "Hour == " + mHour);
					/** Adding currently set hour to bundle object */
					b.putInt("set_hour", mHour);
					
					/** Adding currently set minute to bundle object */
					b.putInt("set_minute", mMinute);
//					b.putInt("set_am", cal.get(Calendar.AM_PM));
					
					/** Instantiating TimePickerDialogFragment */
					TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mHandler);
					count = 0;
					/** Setting the bundle object on timepicker fragment */
					timePicker.setArguments(b);			
					
					
					/** Getting fragment manger for this activity */
					FragmentManager fm = getActivity().getSupportFragmentManager();				
					
					/** Starting a fragment transaction */
					FragmentTransaction ft = fm.beginTransaction();
					
					/** Adding the fragment object to the fragment transaction */
					ft.add(timePicker, "time_picker");
					
					/** Opening the TimePicker fragment */
					ft.commit();
				}
			});
			
			return view;
		}

	}
	
	private class Holder{
		private TextView txtTitle;
		private TextView txtDes;
		private ImageView img;
		private VideoView videoView;
		private TextView txtAddWorkout;
	}
	
	public class LoadThumbnail extends AsyncTask<String, Void, Bitmap>{
		private ImageView imgView;
		
		public LoadThumbnail(ImageView img) {
			this.imgView = img;
		}
		
        @Override
        protected Bitmap doInBackground(String... objectURL) {
            //return ThumbnailUtils.createVideoThumbnail(objectURL[0], Thumbnails.MINI_KIND);
//            return ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(objectURL[0], Thumbnails.MINI_KIND), 100, 100);
        	return ThumbnailUtils.createVideoThumbnail(objectURL[0], MediaStore.Video.Thumbnails.MICRO_KIND);
        }

        @Override
        protected void onPostExecute(Bitmap result){
        	imgView.setImageBitmap(result);
        }
    }
	
	private void playVideoExercise(String urlVideo){
		String extension = MimeTypeMap.getFileExtensionFromUrl(urlVideo);
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
		mediaIntent.setDataAndType(Uri.parse(urlVideo), mimeType);
		getActivity().startActivity(mediaIntent);

	}
	
	 
}
