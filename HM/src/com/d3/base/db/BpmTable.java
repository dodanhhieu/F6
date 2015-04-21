package com.d3.base.db;

import java.util.List;

import com.vn.hm.object.CategoryObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class BpmTable extends BaseTable{

	public static String name = "_name";
	public static String date_time = "_date";
	public static String bpm = "_bpm";
	
	public BpmTable(Context context) {
		super(context);
		addColumns(name);
		addColumns(date_time);
		addColumns(bpm);
	}

	@Override
	public int getIndex() {

		return 1;
	}
	
	public void updateData(Context context, String strName, String strDate, String strBpm){
		if (context != null) {
			ContentValues value = new ContentValues();
			value.put(name, strName);
			value.put(date_time, strDate);
			value.put(bpm, strBpm);
			context.getContentResolver().insert(getContentUri(), value);
		}
	}
	
	public List<CategoryObject> getAllData(Context context){
		List<CategoryObject> list = null;
		
		return list;
	}
}
