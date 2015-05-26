package com.vn.hm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

public class PageIndicatorActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageArra);
		ViewPager myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
		myPager.setAdapter(adapter);
		myPager.setCurrentItem(0);
		
		findViewById(R.id.txt_begin_id).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent iii = new Intent(PageIndicatorActivity.this,MainActivity.class);
				startActivity(iii);
				finish();
			}
		});
	}

	private int imageArra[] = { R.drawable.g_a, R.drawable.g_b,
			R.drawable.g_c, R.drawable.g_d,
			R.drawable.g_e };

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}

}
