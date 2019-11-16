package com.ailiwean.lib.base;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.interfaces.BaseHolderClick;

/***
 * 每个PageView的持有,提供快捷操作
 */
public class BaseViewHolder implements View.OnClickListener {

    View pageView;

    ShareView shareView;

    BaseHolderClick baseHolderClick;

    protected BaseViewHolder(View pageView, ShareView shareView) {
        this.pageView = pageView;
        this.shareView = shareView;
    }

    public static BaseViewHolder getInstance(View pageView, ShareView shareView) {
        return new BaseViewHolder(pageView, shareView);
    }

    public <T extends View> T getView(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public <T extends View> T getView(Class<T> type, @IdRes int id) {
        return pageView.findViewById(id);
    }

    public TextView getTv(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public ImageView getIv(@IdRes int id) {
        return pageView.findViewById(id);
    }

    public EditText getEv(@IdRes int id) {
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
        getTv(id).setText(charSequence);
        return this;
    }

    public BaseViewHolder addClick(@IdRes int id, View.OnClickListener clickListener) {
        getView(id).setOnClickListener(clickListener);
        return this;
    }

    public BaseViewHolder addClick(@IdRes int id) {
        getView(id).setOnClickListener(this);
        return this;
    }

    public String getText(@IdRes int id) {

        View tv = getView(id);

        if (tv == null)
            return "";

        if (tv instanceof TextView)
            return ((TextView) tv).getText().toString();

        return "";
    }

    /***
     * 通过 {@link #addClick(int)} 添加后可设定该方法统一点击事件
     * @param baseHolderClick
     */
    public void setOnChildClickListener(BaseHolderClick baseHolderClick) {
        this.baseHolderClick = baseHolderClick;
    }

    public View getPageView() {
        return pageView;
    }

    public ShareView getShareView() {
        return shareView;
    }

    @Override
    public final void onClick(View v) {
        if (baseHolderClick != null)
            baseHolderClick.onChildClick(this, v);
    }

    public Context getContext() {
        return getPageView().getContext();
    }

}
