package com.d3.base.db;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="heart")
public class Heart extends Model{

	private String TAG = "Heart";
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "indexHeart")
	public int indexHeart;
	
	@Column(name = "date")
	public String date;

	
	public Heart(){
		super();
	}
	
	public Heart(String name, int indexHeart, String date){
		super();
		this.name = name;
		this.indexHeart = indexHeart;
		this.date = date;
		Log.i(TAG, "dave done");
	}
	
	
}
