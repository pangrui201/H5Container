package com.haier.uhome.h5container.view;



import static com.haier.uhome.h5container.utils.PetrelLog.logger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haier.uhome.h5container.R;
import com.haier.uhome.h5container.utils.H5StatusBarUtils;

public class CustomizeTitleView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "CustomizeTitleView";
    private Context mContext;
    private TitleLayoutClick mTitleLayoutClick;
    private View mTitleBarLayout;

    private String title;
    private String customTitle;

    //标题栏基本控件
    private TextView mTitleView;
    private View mBackButton;
    private View mCloseButton;
    private View statusBarAdjustView;

    public CustomizeTitleView(Context context) {
        super(context);
        mContext = context;
        loadTitleViewLayout(mContext);
    }

    public CustomizeTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        loadTitleViewLayout(mContext);
    }

    private void loadTitleViewLayout(Context context) {
        mTitleBarLayout = View.inflate(context, R.layout.customize_navigation_bar, this);
        mTitleView = mTitleBarLayout.findViewById(R.id.title_bar_title_tv);
        mBackButton = mTitleBarLayout.findViewById(R.id.title_bar_nav_back);
        mCloseButton = mTitleBarLayout.findViewById(R.id.title_bar_nav_close);
        mTitleView.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mCloseButton.setOnClickListener(this);
        statusBarAdjustView = mTitleBarLayout.findViewById(R.id.status_bar_adjust_view);
    }

    @Override
    public void onClick(View view) {
        mTitleLayoutClick.onTitleClick(view);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setTitleLayoutClick(TitleLayoutClick titleLayoutClick) {
        mTitleLayoutClick = titleLayoutClick;
    }

    //定义TitleLayout中点击事件的接口
    public interface TitleLayoutClick {
        void onTitleClick(View view);
    }

    //设置标题栏隐藏显示
    public void setTitleBarVisible(boolean status) {
        mTitleBarLayout.setVisibility(status ? View.VISIBLE : View.GONE);
    }

    //设置标题隐藏显示
    public void setTitleViewVisible(boolean status) {
        mTitleView.setVisibility(status ? View.VISIBLE : View.GONE);
    }

    //设置返回键隐藏显示
    public void setBackButtonVisible(boolean status) {
        mBackButton.setVisibility(status ? View.VISIBLE : View.GONE);
    }

    //设置关闭键隐藏显示
    public void setCloseButtonVisible(boolean status) {
        mCloseButton.setVisibility(status ? View.VISIBLE : View.GONE);
    }

    //设置标题内容颜色
    public void setTitleTextColor(int titleTxtColor) {
        mTitleView.setTextColor(titleTxtColor);
    }

    //设置标题内容
    public void setTitleText(String title) {
        this.customTitle = title;
        this.title = title;
        mTitleView.setText(title);
    }

    //设置标题内容
    public void setTitle(String title) {
        if (title != null && customTitle != null && !title.equals(customTitle)) {
            return;
        }

        if (!TextUtils.isEmpty(title) && (
                title.toLowerCase().startsWith("http://") ||
                        title.toLowerCase().startsWith("https://") ||
                        title.toLowerCase().startsWith("file://"))) {
            logger().info(TAG + ",title is url:" + title);
            return;
        }
        logger().info(TAG + ",title:" + title);
        this.title = title;
        mTitleView.setText(title);
    }

    //获取标题内容
    public String getTitle() {
        return title;
    }

    //获取标题栏View
    public View getTitleBar() {
        return mTitleBarLayout;
    }

    //获取标题TextView
    public TextView getTitleView() {
        return mTitleView;
    }

    //获取返回键View
    public View getBackButton() {
        return mBackButton;
    }

    //获取关闭键View
    public View getCloseButton() {
        return mCloseButton;
    }

    //设置沉浸式状态栏
    public void setTranslucentStatusBar(boolean underneathStatusBar, int color) {
        if (H5StatusBarUtils.isSupport()) {
            int statusBarHeight = H5StatusBarUtils.getStatusBarHeight(mContext);
            if (statusBarHeight == 0) {
                return;
            }
            if (mTitleBarLayout.getVisibility() == View.VISIBLE) {
                //native title bar显示的情况下，状态栏占位view需显示，防止native title布局遮挡
                LayoutParams layoutParams =
                        (LayoutParams) statusBarAdjustView.getLayoutParams();
                layoutParams.height = statusBarHeight;
                statusBarAdjustView.setLayoutParams(layoutParams);
                statusBarAdjustView.setVisibility(View.VISIBLE);
                try {
                    H5StatusBarUtils.setTransparentColor((Activity) mContext, color);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            } else {
                // //native title bar不显示的情况下
                if (!underneathStatusBar) {
                    //不使用沉浸式状态栏，则直接返回；
                    return;
                }
                //使用沉浸式状态栏，则需设置window属性，开启沉浸式状态显示。
                try {
                    H5StatusBarUtils.setTransparentColor((Activity) mContext, Color.TRANSPARENT);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    }
}
