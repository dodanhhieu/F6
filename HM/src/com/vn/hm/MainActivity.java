package com.vn.hm;

import java.io.File;
import java.util.GregorianCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.d3.base.BaseActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
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
		// init config universal
		configUniversal();
		
		// init headerview
		headerView = (View) findViewById(R.id.header_id);
		btnMenu = (ImageView)headerView.findViewById(R.id.img_menu_header_id);
		btnMenu.setOnClickListener(this);
		txtTitle = (TextView)headerView.findViewById(R.id.txt_header_title_id);
		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		 StrictMode.setThreadPolicy(policy);
		 
		 settingCalendar();

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
	
	private void configUniversal(){
		File fileCache = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
											.memoryCacheExtraOptions(1024, 1024)
											.threadPoolSize(3)
											.memoryCacheSize(1024*1024*2)
											.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
											.build();
		
	}
	
	private void settingCalendar(){
		Intent calIntent = new Intent(Intent.ACTION_INSERT); 
		calIntent.setType("vnd.android.cursor.item/event");    
		calIntent.putExtra(Events.TITLE, "My House Party"); 
		calIntent.putExtra(Events.EVENT_LOCATION, "My Beach House"); 
		calIntent.putExtra(Events.DESCRIPTION, "A Pig Roast on the Beach"); 
		 
		GregorianCalendar calDate = new GregorianCalendar(2015, 4, 26);
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true); 
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,calDate.getTimeInMillis()); 
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,calDate.getTimeInMillis()); 
		 
		startActivity(calIntent);
	}
}
