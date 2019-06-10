package com.github.ccmagic.banner.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


/**
 * 轮播图adapter
 */
public abstract class BannerAdapter extends PagerAdapter {

    private Context context;
    private BannerView.OnItemClickListener onItemClickListener;

    public BannerAdapter(Context context) {
        this.context = context;
    }

    public abstract int pageCount();

    public void setOnItemClickListener(BannerView.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public int getCount() {
        if (pageCount() == 0) {
            return 0;
        }
        if (pageCount() == 1) {
            return 1;
        }
        return pageCount() + 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position == pageCount() + 1) {
            position = 1;
        } else if (position == 0) {
            position = pageCount();
        }
        //position增加-1是为了让显示的position从0开始计数
        final int position2 = position - 1;
        View view = instantiateItem(context, position2);
        if (onItemClickListener != null) {
            view.setOnClickListener(v -> onItemClickListener.itemClick(position2));
        }
        container.addView(view);
        return view;
    }

    /**
     * position从0开始计数
     */
    protected abstract View instantiateItem(Context context, int position);

    protected abstract View indicator();

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
