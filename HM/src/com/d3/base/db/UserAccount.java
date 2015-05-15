package com.d3.base.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class UserAccount extends Model{
	
	@Column(name="name")
	public String name;
	
	@Column(name="bpmEverage")
	public int bpmEverage;
	
	@Column(name="email")
	public String email;
	
	@Column(name="password")
	public String password;
	
	@Column(name="weight")
	public float weight;
	
	@Column(name="height")
	public float height;
	
	@Column(name ="gioitinh")
	public String gioitinh;
	
	public UserAccount()
	{
		super();
	}
}
