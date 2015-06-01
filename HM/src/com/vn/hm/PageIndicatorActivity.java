package com.vn.hm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
		int currentItemt = myPager.getCurrentItem();

		// if (currentItemt == 0) {
		// findViewById(R.id.txt_begin_id).setVisibility(View.INVISIBLE);
		// }else{
		// findViewById(R.id.txt_begin_id).setVisibility(View.VISIBLE);
		// }
		myPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
					findViewById(R.id.txt_begin_id).setVisibility(
							View.INVISIBLE);
				} else {
					findViewById(R.id.txt_begin_id).setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

				
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		findViewById(R.id.txt_begin_id).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent iii = new Intent(PageIndicatorActivity.this,
								MainActivity.class);
						startActivity(iii);
						finish();
					}
				});
	}

	private int imageArra[] = { R.drawable.intro, R.drawable.g_a,
			R.drawable.g_b, R.drawable.g_c, R.drawable.g_d, R.drawable.g_e };

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// return true;
	// }

}
