package com.vn.hm;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d3.base.BaseActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.vn.hm.fragment.ExerciseCategory;

public class MainActivity extends BaseActivity implements OnClickListener {

	public static SlidingMenu slideMenu;
	private android.support.v4.app.Fragment mContent;
	private ImageView btnMenu;
	private static TextView txtTitle;
	private String strTitle = "HOME";	
	private View headerView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
	    // add slide menu
	    if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");

		if (mContent == null)
			mContent = new HomeFragment();

		slideMenu = getSlidingMenu();
		slideMenu.setShadowWidthRes(R.dimen.shadow_width);
		slideMenu.setShadowDrawable(R.drawable.shadow);
		slideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slideMenu.setFadeDegree(0.35f);
		slideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
//		 set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent,"HOME").commit();
		switchContent(new ExerciseCategory());
//		
//		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();

		// init headerview
		headerView = (View) findViewById(R.id.header_id);
		btnMenu = (ImageView)headerView.findViewById(R.id.img_menu_header_id);
		btnMenu.setOnClickListener(this);
		txtTitle = (TextView)headerView.findViewById(R.id.txt_header_title_id);
	}
	
	public void switchContent(android.support.v4.app.Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
	
	
	public void switchContent(android.support.v4.app.Fragment fragment,String nameFragment) {
		mContent = fragment;
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
		//transaction.setCustomAnimations(R.anim.right_in, R.anim.right_in, R.anim.right_out, R.anim.right_out);
		transaction.replace(R.id.content_frame, fragment,nameFragment);
		transaction.addToBackStack(nameFragment);
		transaction.commit();
		getSlidingMenu().showContent();
	}

	@Override
	public void onClick(View v) {
		int idView = v.getId();
		switch (idView) {
		case R.id.img_menu_header_id:
			slideMenu.toggle();
			break;

		default:
			break;
		}
		
	}
	
	public static void updateTitleHeader(String title){
		txtTitle.setText(title);
	}
	
}
