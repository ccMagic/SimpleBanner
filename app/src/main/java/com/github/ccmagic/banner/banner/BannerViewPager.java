package com.github.ccmagic.banner.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

class BannerViewPager extends ViewPager {

    private static final String TAG = "BannerViewPager";

    private BannerView bannerView;

    BannerViewPager(@NonNull Context context) {
        super(context);
    }

    BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    void setBannerView(BannerView bannerView) {
        this.bannerView = bannerView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            bannerView.setTouched(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            bannerView.setTouched(false);
        }
        return super.onTouchEvent(event);
    }
}
