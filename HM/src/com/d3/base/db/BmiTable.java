package com.d3.base.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "BmiTable")
public class BmiTable extends Model{
	
	@Column(name = "date")
	public String date;
	
	@Column(name= "bmiIndex")
	public float bmiIndex;
	
	public BmiTable() {
		super();
	}
	
	public BmiTable(String date,float value){
		super();
		this.date = date;
		this.bmiIndex = value;
	}
}
