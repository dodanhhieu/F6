package com.vn.hm.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.vn.base.api.ApiServiceCallback;
import com.vn.hm.MainActivity;
import com.vn.hm.R;
import com.vn.hm.object.CateTipsObject;

import d3.lib.base.callback.RestClient.RequestMethod;

public class CateHealthNutritionFragment extends BaseFragment {
    private String TAG = "HealthNutritionFragment";
    private ListView listview;
    private List<CateTipsObject> listData;

    @Override
    public int getlayout() {
	// TODO Auto-generated method stub
	return R.layout.cate_tips_layout;
    }

    @Override
    public void initView(View view) {
    MainActivity.updateTitleHeader(D3Utils.SCREEN.HEALTH_NUTRITION);	
	listview = (ListView) view.findViewById(R.id.cate_tips_listview_id);
	getAllCateTips();
	
	listview.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1,
		    int position, long arg3) {
		int id = listData.get(position).getId();
		TipsDetailFragment detail = new TipsDetailFragment(id,listData.get(position).getName());
		switchFragment(detail);
	    }
	});
    }

    public void switchFragment(Fragment fragment) {
	if (getActivity() == null)
	    return;

	if (getActivity() instanceof MainActivity) {
	    MainActivity fca = (MainActivity) getActivity();
	    fca.switchContent(fragment, "");
	}

    }

    private void getAllCateTips() {
	HashMap<String, String> params = new HashMap<String, String>();
	D3Utils.execute(getActivity(), RequestMethod.GET,
		D3Utils.API.API_LIST_CATE_TIPS, params,
		new ApiServiceCallback() {

		    @Override
		    public void onError(String msgError) {
			Log.i(TAG, "ERROR : " + msgError);
			super.onError(msgError);
		    }

		    @Override
		    public void onSucces(String responeData) {
			super.onSucces(responeData);
			Log.i(TAG, "Data = " + responeData.toString());
			listData = new ArrayList<CateTipsObject>();
			try {
			    JSONObject jsonObj = new JSONObject(responeData);
			    JSONObject jsonRes = jsonObj
				    .getJSONObject("responsse_data");
			    JSONArray jsonArray = jsonRes.getJSONArray("data");
			    int length = jsonArray.length();
			    for (int i = 0; i < length; i++) {
				JSONObject jobj = jsonArray.getJSONObject(i);
				JSONObject jsonItem = jobj
					.getJSONObject("CategoryTip");
				CateTipsObject item = new CateTipsObject();
				item.setId(Integer.valueOf(jsonItem
					.getString("id")));
				item.setName(jsonItem.getString("name"));
				listData.add(item);
			    }
			    CateTipsAdapter adapter = new CateTipsAdapter(
				    getActivity(), listData);
			    listview.setAdapter(adapter);
			} catch (JSONException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }
		});
    }

    private class CateTipsAdapter extends BaseAdapter {

	private Context mContext;
	private List<CateTipsObject> data;

	public CateTipsAdapter(Context context, List<CateTipsObject> list) {
	    this.mContext = context;
	    this.data = list;
	}

	@Override
	public int getCount() {

	    return data.size();
	}

	@Override
	public Object getItem(int arg0) {

	    return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
	    // TODO Auto-generated method stub
	    return data.get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
	    String format = "%d. %s";
	    View view = convertView;
	    Holder holder;
	    if (view == null) {
		LayoutInflater inf = (LayoutInflater) mContext
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inf.inflate(R.layout.cate_tips_item, null);
		holder = new Holder();
		holder.txtName = (TextView) view
			.findViewById(R.id.cate_tips_item_txtName_id);
		view.setTag(holder);
	    } else {
		holder = (Holder) view.getTag();
	    }
	    CateTipsObject obj = (CateTipsObject) data.get(position);
	    holder.txtName.setText(String.format(format, position + 1,
		    obj.getName()));
	    return view;
	}

    }

    private class Holder {
	private TextView txtName;
    }
}
