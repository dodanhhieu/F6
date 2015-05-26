package com.vn.hm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

public class SlideActivity extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_layout);

    ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
    ImageAdapter adapter = new ImageAdapter(this);
    viewPager.setAdapter(adapter);
    
    findViewById(R.id.txt_begin_id).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent iii = new Intent(SlideActivity.this, MainActivity.class);
			startActivity(iii);
			
		}
	});
  }

  
}
