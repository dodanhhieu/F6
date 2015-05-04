package com.d3.base;

import android.content.Context;
import android.content.SharedPreferences;

public class DataSharePref {

	private Context mContext;
	private SharedPreferences sharePref;
	
	public DataSharePref(Context ctx) {
		this.mContext = ctx;
		sharePref = mContext.getSharedPreferences(D3Utils.SHARE_PREFERENCE, Context.MODE_PRIVATE); 
	}
	
	public void saveInt(String key,int value){
		sharePref.edit().putInt(key, value).commit();
	}
	
	public int getInt(String key){
		return sharePref.getInt(key, 0);
	}
	
	public void saveString(String key,String value){
		sharePref.edit().putString(key, value).commit();
	}
	
	public String getString(String key){
		return sharePref.getString(key, null);
	}
	
	public void saveBoolean(String key,boolean value){
		sharePref.edit().putBoolean(key, value);
	}
	
	public boolean getBoolean(String key){
		return sharePref.getBoolean(key, false);
	}
	
}
