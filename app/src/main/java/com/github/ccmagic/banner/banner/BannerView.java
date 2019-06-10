package com.github.ccmagic.banner.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


public class BannerView extends FrameLayout {

    /**
     * 是否touch了这个banner，当用户touch的时候应该取消自动轮播，跟随用户手势滑动
     * <p>
     * true 触碰了-跟随用户手势
     * false 没有触碰-自动轮播
     */
    private boolean touched = false;

    private BannerAdapter bannerAdapter;
    private BannerViewPager viewPager;

    private RadioGroup radioGroupIndicator;

    public BannerView(@NonNull Context context) {
        super(context);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 控制自动轮播
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (touched) {//不自动轮播
                return;
            }
            getHandler().removeCallbacks(runnable);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            postDelayed(runnable, 2000);
        }
    };

    /**
     *
     */
    public void setAdapter(@NonNull BannerAdapter bannerAdapter) {
        this.bannerAdapter = bannerAdapter;
        viewPager = new BannerViewPager(getContext());
        viewPager.setBannerView(this);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(layoutParams);
        //
        viewPager.setAdapter(bannerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    postDelayed(() -> viewPager.setCurrentItem(bannerAdapter.pageCount(), false), 200);
                    radioGroupIndicator.check(bannerAdapter.pageCount() - 1);
                } else if (position == bannerAdapter.pageCount() + 1) {
                    postDelayed(() -> viewPager.setCurrentItem(1, false), 200);
                    radioGroupIndicator.check(0);
                } else {
                    radioGroupIndicator.check(position - 1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addView(viewPager);

        initIndicator();
        //
        if (bannerAdapter.pageCount() != 1) {
            viewPager.setCurrentItem(1);
            postDelayed(runnable, 2000);
        }
    }

    /**
     * 初始化指示器
     */
    private void initIndicator() {
        radioGroupIndicator = new RadioGroup(getContext());
        radioGroupIndicator.setOrientation(RadioGroup.HORIZONTAL);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        radioGroupIndicator.setGravity(Gravity.CENTER);
        radioGroupIndicator.setLayoutParams(layoutParams);
        for (int i = 0; i < bannerAdapter.pageCount(); i++) {
            RadioButton radioButton = bannerAdapter.indicator(getContext(), i);
            if (radioButton == null) {
                radioButton = new RadioButton(getContext());
            }
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioButton.setId(i);
            radioGroupIndicator.addView(radioButton);
        }
        addView(radioGroupIndicator);
    }

    /**
     * 设置item点击事件
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.bannerAdapter.setOnItemClickListener(listener);
    }

    /**
     * 是否touch了这个banner，当用户touch的时候应该取消自动轮播，跟随用户手势滑动
     * <p>
     * true 触碰了-跟随用户手势
     * false 没有触碰-自动轮播
     */
    void setTouched(boolean touched) {
        this.touched = touched;
        if (touched) {
            getHandler().removeCallbacks(runnable);
        } else {
            postDelayed(runnable, 2000);
        }
    }

    public interface OnItemClickListener {
        void itemClick(int position);
    }
}
