package com.vn.hm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.d3.base.DataSharePref;
import com.vn.hm.calendar.CalendarFragment;
import com.vn.hm.fragment.BmiFragment;
import com.vn.hm.fragment.CateHealthNutritionFragment;
import com.vn.hm.fragment.EditUserFragment;
import com.vn.hm.fragment.ExerciseCategory;
import com.vn.hm.fragment.HeartTrackFragment;
import com.vn.hm.fragment.LoginFragment;
import com.vn.hm.fragment.RegisterFragment;

public class MenuFragment extends BaseFragment implements OnClickListener {

	private TextView funcGym, funcBmi, funcTracker, funcLogin,
			 funcRegister,funcTips,funcEditProfile,funcCalendar;
	private Fragment mContentFragment;
	private LinearLayout llLogin,llEditProfile;
	private int flagLogin = 0;
	private SharedPreferences sharePref;
	private String token;
	private String TAG = "MenuFragment";
	private ImageView imgProfile;
	public MenuFragment() {

	}

	@Override
	public int getlayout() {
		return R.layout.menu_layout;
	}

	@Override
	public void initView(View view) {
		// get status login 
		sharePref = getActivity().getSharedPreferences(D3Utils.SHARE_PREFERENCE, Context.MODE_PRIVATE);
		try {
			flagLogin = sharePref.getInt("LOGIN", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		funcGym = (TextView) view.findViewById(R.id.menu_gym_id);
		funcGym.setOnClickListener(this);
		funcBmi = (TextView) view.findViewById(R.id.menu_bmi_id);
		funcBmi.setOnClickListener(this);
		funcTracker = (TextView) view.findViewById(R.id.menu_heart_tracker_id);
		funcTracker.setOnClickListener(this);
		funcTips = (TextView) view.findViewById(R.id.menu_health_nutrition_id);
		funcTips.setOnClickListener(this);
		//funcRegister = (TextView) view.findViewById(R.id.menu_profile_id);
		//funcRegister.setOnClickListener(this);
		funcLogin = (TextView) view.findViewById(R.id.menu_login_id);
		funcLogin.setOnClickListener(this);
		funcEditProfile = (TextView) view.findViewById(R.id.menu_editprofile_id);
		funcEditProfile.setOnClickListener(this);
		llLogin = (LinearLayout)view.findViewById(R.id.menu_ll_login_id);
		llLogin.setOnClickListener(this);
		llEditProfile = (LinearLayout)view.findViewById(R.id.ll_menu_editprofile_id);
		llEditProfile.setOnClickListener(this);
		imgProfile = (ImageView)view.findViewById(R.id.menu_img_profile_id);
		imgProfile.setOnClickListener(this);
		
		funcCalendar = (TextView) view.findViewById(R.id.menu_calendar);
		funcCalendar.setOnClickListener(this);
		DataSharePref sharePref = new DataSharePref(getActivity());
		token = sharePref.getString(D3Utils.TOKEN_KEY);
		setUpStatusLogin(token,getActivity());
		Log.i(TAG , "init View");
	}
	boolean flagSwithView = false;
	@Override
	public void onClick(View v) {
		int idView = v.getId();
		switch (idView) {
		case R.id.menu_gym_id:
			mContentFragment = new ExerciseCategory();
			flagSwithView = true;
			break;
		case R.id.menu_bmi_id:
			mContentFragment = new BmiFragment();
			flagSwithView = true;
			break;
		case R.id.menu_heart_tracker_id:
			mContentFragment = new HeartTrackFragment(); 
			flagSwithView = true;
			break;
		case R.id.menu_health_nutrition_id:
			mContentFragment = new CateHealthNutritionFragment();
			flagSwithView = true;
			break;
		case R.id.menu_editprofile_id:
			flagSwithView = true;
			mContentFragment = new EditUserFragment();
			break;
		case R.id.menu_calendar:
//			CalendarUtility.addEvent(getActivity(), "Alo",
//				    "go to trainning", System.currentTimeMillis() + 15000,
//				    System.currentTimeMillis() + 30000);
			
			flagSwithView = true;
		    mContentFragment = new CalendarFragment();
		    break;
		case R.id.menu_f6_id:
			mContentFragment = new HomeFragment();
			break;
		case R.id.menu_ll_login_id:
			mContentFragment = new LoginFragment();
			flagSwithView = true;
			break;
		case R.id.menu_img_profile_id:
			
			setUpStatusLogin(token, getActivity());
			
			flagSwithView = false;
			break;
		case R.id.menu_login_id:
			flagSwithView = true;
			if (token == null) {
				// goto function register
				mContentFragment = new RegisterFragment();
			}else{
//				String str = funcLogin.getText().toString();
				DataSharePref dataSharePref = new DataSharePref(getActivity());
				int status = dataSharePref.getInt(D3Utils.LOGIN_KEY);
				if (status == 0) {
					// goto login
					mContentFragment = new LoginFragment();
					llLogin.setVisibility(View.INVISIBLE);
				}else if (status == 1) {
					// get logout
					getLogout();
				}
				
			}
			break;
		default:
			break;
		}
		
		// goto layout
		if (mContentFragment != null && flagSwithView) {
			MainActivity.slideMenu.toggle();
			switchFragment(mContentFragment);
		}
	}
	
	private void getLogout() {
		// logout success then set text is login and invisible edit profile
		DataSharePref dataSharePref = new DataSharePref(getActivity());
		dataSharePref.saveInt(D3Utils.LOGIN_KEY, 0);
		MainActivity.slideMenu.toggle();
		setUpStatusLogin(token,getActivity());
		switchFragment(new ExerciseCategory());
	}

	public void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment,"");
		}

	}
	
	public void starActivity(Class<?> newClass){
		Intent intent = new Intent(getActivity(), newClass);
		getActivity().startActivity(intent);
	}
	
	public void setUpStatusLogin(String token,Context context){
		DataSharePref dataSharePref = new DataSharePref(context);
		int statusLogin = dataSharePref.getInt(D3Utils.LOGIN_KEY);
		Log.i(TAG, "Token = " + token);
		Log.i(TAG, "statusLogin = " + statusLogin);
		if (token == null) {
			// open register
			funcLogin.setText(getActivity().getResources().getString(R.string.regsiter));
			llEditProfile.setVisibility(View.INVISIBLE);
			llLogin.setVisibility(View.VISIBLE);
		}else{
			// open login
			llLogin.setVisibility(View.VISIBLE);
			if (statusLogin == 1) {
				funcLogin.setText(getActivity().getResources().getString(R.string.logout));
				llEditProfile.setVisibility(View.VISIBLE);
			}else{
				funcLogin.setText(getActivity().getResources().getString(R.string.login));
				llEditProfile.setVisibility(View.INVISIBLE);
			}

		}
		
	}
	
	public void updateUI(String token,Context context){
		LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inf.inflate(R.layout.menu_layout, null);
		initView(view);
		setUpStatusLogin(token, context);
		Log.i("XXXX = ", "TT = " +token);
		Log.i("XXXX = ", "CC = " +context);
	}
}
