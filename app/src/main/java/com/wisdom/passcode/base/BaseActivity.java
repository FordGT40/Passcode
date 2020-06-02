package com.wisdom.passcode.base;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.wisdom.passcode.R;
import com.wisdom.passcode.base.weakreferences.WeakReferenceActivity;
import com.wisdom.passcode.util.StatusBarUtil;


/**
 * 基类activity
 * 程序内各个activity的基础父类
 * */
public abstract class BaseActivity extends FragmentActivity {

    protected Context context;
    protected TextView title;
    protected ImageView backIv;
    protected TextView right;
    protected ImageView rightIv;
    private WeakReferenceActivity mActivity;




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //防止EditText被软键盘遮挡
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setlayoutIds();
        //沉浸式状态栏
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
//        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#0059A1"));
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
//        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
//            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
//            //这样半透明+白=灰, 状态栏的文字能看得清
//            StatusBarUtil.setStatusBarColor(this,0x55000000);
//        }



        context=this;
        mActivity = new WeakReferenceActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActivityManager.getActivityManagerInstance().addActivity(this);
        initHeadView();
        initViews();
    }



    public void initHeadView() {
        title = (TextView) findViewById(R.id.comm_head_title);
        backIv = (ImageView) findViewById(R.id.head_back_iv);
        right = (TextView) findViewById(R.id.head_right);
        rightIv = (ImageView) findViewById(R.id.head_right_iv);
        if (backIv != null)
            backIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
    }


    public void setTitle(int resId) {
        if (title != null)
            title.setText(resId);
    }

    public void setRight(int resId) {
        if (right != null) {
            right.setText(resId);
            right.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(String resId) {
        if (title != null)
            title.setText(resId);
    }

    public void setRightIcon(int resId) {
        if (null != rightIv) {
            rightIv.setImageResource(resId);
            rightIv.setVisibility(View.VISIBLE);
        }
    }

    public void showRight() {
        if (null != right)
            right.setVisibility(View.VISIBLE);
    }

    public void hideRight() {
        if (null != right)
            right.setVisibility(View.GONE);
    }

    public void hideBackIv() {
        if (null != backIv)
            backIv.setVisibility(View.GONE);
    }

    public abstract void setlayoutIds();
    public abstract void initViews();



    public  void startActivity(Class<?> cls){
        startActivity(cls,null);
    }
    public void startActivity(Class<?> cls, Bundle bundle){
        Intent intent=new Intent(this ,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void startActivityForResult(Class<?> cls,int requestCode){
        startActivityForResult(cls,null,requestCode);
    }
    public void startActivityForResult(Class<?> cls,Bundle bundle,int requestCode){
        Intent intent=new Intent(this,cls);
        intent.putExtras(bundle);
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setNoStateBar(){
        //沉浸式状态栏
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }
}
