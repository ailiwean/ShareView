package com.ailiwean.lib.base;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/***
 * 每个PageView的持有,提供快捷操作
 */
public class BaseViewHolder {

    View pageView;

    private BaseViewHolder(View pageView) {
        this.pageView = pageView;
    }

    public static BaseViewHolder getInstance(View pageView) {
        return new BaseViewHolder(pageView);
    }

    public <T extends View> T getView(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public <T extends View> T getViewByType(Class<T> type, @IdRes int id) {
        return pageView.findViewById(id);
    }

    public TextView getTv(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public ImageView getIv(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public TableLayout getTl(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public ViewPager getVp(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public RecyclerView getRv(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public LinearLayout getLl(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public FrameLayout getFl(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public RelativeLayout getRl(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public BaseViewHolder setText(@IdRes int id, CharSequence charSequence) {
        this.<TextView>getView(id).setText(charSequence);
        return this;
    }


}
