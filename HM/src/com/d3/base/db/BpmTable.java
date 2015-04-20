package com.d3.base.db;

import android.content.ContentValues;
import android.content.Context;

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
}
