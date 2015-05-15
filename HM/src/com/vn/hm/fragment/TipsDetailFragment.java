package com.vn.hm.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TipsDetailFragment extends BaseFragment {
    private String TAG = "TipsDetailFragment";
    private ListView listview;
    private int idCate;
    private List<CateTipsObject> listData;
    private String titleName;
    public TipsDetailFragment(int cateId,String name) {
	this.idCate = cateId;
	this.titleName = name;
    }

    @Override
    public int getlayout() {
	// TODO Auto-generated method stub
	return R.layout.detail_tips_layout;
    }

    @Override
    public void initView(View view) {
	// TODO Auto-generated method stub
	listview = (ListView) view.findViewById(R.id.detail_tips_listview_id);
	MainActivity.updateTitleHeader(titleName);
	getDetailTips(idCate);
    }

    private void getDetailTips(int cateId) {
	HashMap<String, String> params = new HashMap<String, String>();
	String API = D3Utils.API.API_LIST_CATE_TIPS_DETAIL + idCate + ".json";
	D3Utils.execute(getActivity(), RequestMethod.GET, API, params,
		new ApiServiceCallback() {

		    @Override
		    public void onError(String msgError) {
			Log.i(TAG, "Error : " + msgError);
			super.onError(msgError);
		    }

		    @Override
		    public void onSucces(String responeData) {
			super.onSucces(responeData);
			Log.i(TAG, "Data = " + responeData.toString());
			try {
			    JSONObject jsonRes = new JSONObject(responeData);
			    JSONObject js = jsonRes
				    .getJSONObject("responsse_data");
			    JSONArray jsonArray = js.getJSONArray("data");
			    listData = new ArrayList<CateTipsObject>();
			    for (int i = 0; i < jsonArray.length(); i++) {
				CateTipsObject item = new CateTipsObject();
				JSONObject jobj1 = jsonArray.getJSONObject(i);
				JSONObject jobj = jobj1.getJSONObject("Tip");
				item.setId(Integer.valueOf(jobj.getString("id")));
				item.setName(jobj.getString("content"));
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
	    holder.txtName.setText(Html.fromHtml(obj.getName()));
	    return view;
	}

    }

    private class Holder {
	private TextView txtName;
    }
}
