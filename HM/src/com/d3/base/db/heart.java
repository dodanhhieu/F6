package com.d3.base.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="heart")
public class heart extends Model{

	@Column(name = "name")
	public String name;
	
	@Column(name = "index")
	public int index;
	
	@Column(name = "date")
	public String date;
	
	public heart(){
		super();
	}
	
	public heart(String name, int index, String date){
		super();
		this.name = name;
		this.index = index;
		this.date = date;
	}
	
	
}
