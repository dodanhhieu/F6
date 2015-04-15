package com.vn.hm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.d3.base.D3Utils;
import com.d3.base.GlobalFunction;
import com.vn.base.api.ApiServiceCallback;

import d3.lib.base.callback.RestClient;
import d3.lib.base.callback.RestClient.RequestMethod;

public class BaseApplication extends Application{

	private String TAG = "BaseApplication";
	
	public void execute(final RequestMethod method, final String api,
			final Map<String, String> params,
			final ApiServiceCallback apiServiceCallback) {
		Log.i(TAG, "execute 1");
		execute(method, api, params, new HashMap<String, String>(),apiServiceCallback);
		
	}
	
	public void execute(final RequestMethod method, final String api,
			final Map<String, String> params,
			final Map<String, String> headers,
			final ApiServiceCallback apiServiceCallback) {
		if (apiServiceCallback != null) {
			apiServiceCallback.onStart();
		}
		
		Log.e("API", "param-send: " + params.toString());
		new AsyncTask<String, String, String>() {
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (apiServiceCallback != null) {
					if (client.getResponseCode() == 200) {
						try {
							JSONObject jsonObject = new JSONObject(client.getResponse());
							String is_succes = GlobalFunction.getString(jsonObject, D3Utils.KEY.status);
							String err_msg = GlobalFunction.getString(jsonObject, D3Utils.KEY.err_msg);

							if (D3Utils.VALUE.STATUS_API_SUCCESS.equals(is_succes)) {
								apiServiceCallback.onSucces(client.getResponse());
							} else {
								apiServiceCallback.onError(GlobalFunction.convertStringErrF(err_msg));
							}

							return;
						} catch (Exception exception) {
						}
					}
					
				}
			}
			
			private RestClient client;

			@Override
			protected String doInBackground(String... paramStrs) {
				client = new RestClient(D3Utils.API.BASESERVER + api);
				client.addHeader("Content-Type","application/json; charset=utf-8");
				client.addHeader("Accept", "application/json; charset=utf-8");
				Set<String> keys = params.keySet();

				for (String key : keys) {
					client.addParam(key, params.get(key));
				}

				if (headers != null) {
					Set<String> keyHeaders = headers.keySet();
					for (String key : keyHeaders) {
						client.addParam(key, headers.get(key));
					}
				}
				client.execute(method);

				return null;
			}
		}.execute("");
	}
}
