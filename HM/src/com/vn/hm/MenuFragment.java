package com.vn.hm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d3.base.BaseFragment;
import com.d3.base.D3Utils;
import com.d3.base.DataSharePref;
import com.vn.hm.fragment.BmiFragment;
import com.vn.hm.fragment.CateHealthNutritionFragment;
import com.vn.hm.fragment.ExerciseCategory;
import com.vn.hm.fragment.HeartTrackFragment;
import com.vn.hm.fragment.LoginFragment;
import com.vn.hm.fragment.RegisterFragment;

public class MenuFragment extends BaseFragment implements OnClickListener {

	private TextView funcGym, funcBmi, funcTracker, funcLogin,
			funcLogout, funcRegister,funcTips,funcEditProfile;
	private Fragment mContentFragment;
	private LinearLayout llLogin,llEditProfile;
	private int flagLogin = 0;
	private SharedPreferences sharePref;
	private String token;
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
		funcRegister = (TextView) view.findViewById(R.id.menu_profile_id);
		funcRegister.setOnClickListener(this);
		funcLogin = (TextView) view.findViewById(R.id.menu_login_id);
		funcLogin.setOnClickListener(this);
		funcEditProfile = (TextView) view.findViewById(R.id.menu_editprofile_id);
		funcEditProfile.setOnClickListener(this);
		llLogin = (LinearLayout)view.findViewById(R.id.menu_ll_login_id);
		llLogin.setOnClickListener(this);
		llEditProfile = (LinearLayout)view.findViewById(R.id.ll_menu_editprofile_id);
		DataSharePref sharePref = new DataSharePref(getActivity());
		token = sharePref.getString(D3Utils.TOKEN_KEY);
		setUpStatusLogin(token);
	}

	@Override
	public void onClick(View v) {
		int idView = v.getId();
		switch (idView) {
		case R.id.menu_gym_id:
			mContentFragment = new ExerciseCategory();
			break;
		case R.id.menu_bmi_id:
			mContentFragment = new BmiFragment();
			break;
		case R.id.menu_heart_tracker_id:
			mContentFragment = new HeartTrackFragment(); 
			break;
		case R.id.menu_health_nutrition_id:
			mContentFragment = new CateHealthNutritionFragment();
			break;
		case R.id.menu_profile_id:
//			mContentFragment = new RegisterFragment();
			break;
		case R.id.menu_f6_id:
			mContentFragment = new HomeFragment();
			break;
		case R.id.menu_ll_login_id:
			mContentFragment = new LoginFragment();
			break;
		case R.id.menu_login_id:
			if (token == null) {
				// goto function register
				mContentFragment = new RegisterFragment();
			}else{
				String str = funcLogin.getText().toString();
				if (str.equalsIgnoreCase(getResources().getString(R.string.login))) {
					// goto login
					mContentFragment = new LoginFragment();
				}else if (str.equalsIgnoreCase(getResources().getString(R.string.logout))) {
					// get logout
					getLogout();
				}
				
			}
			break;
		default:
			break;
		}
		
		// goto layout
		if (mContentFragment != null) {
			MainActivity.slideMenu.toggle();
			switchFragment(mContentFragment);
		}
	}
	
	private void getLogout() {
		// logout success then set text is login and invisible edit profile
		
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
	
	private void setUpStatusLogin(String token){
		if (token == null) {
			// open register
			funcLogin.setText(getActivity().getResources().getString(R.string.regsiter));
			llEditProfile.setVisibility(View.INVISIBLE);
		}else{
			// open login
			funcLogin.setText(getActivity().getResources().getString(R.string.logout));
			llEditProfile.setVisibility(View.VISIBLE);
		}
		
	}
}
