package com.vn.hm;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {
	Context context;
    private int[] GalImages = new int[] {
        R.drawable.img_i,
        R.drawable.img_a,
        R.drawable.img_b,
        R.drawable.img_c,
        R.drawable.img_d,
        R.drawable.img_e,
        R.drawable.img_f,
        R.drawable.img_g,
        R.drawable.img_h
    };
    ImageAdapter(Context context){
    	this.context=context;
    }
    @Override
    public int getCount() {
      return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      ImageView imageView = new ImageView(context);
      int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
      imageView.setPadding(padding, padding, padding, padding);
      imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      imageView.setImageResource(GalImages[position]);
      ((ViewPager) container).addView(imageView);
      return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      ((ViewPager) container).removeView((ImageView) object);
    }
  }
