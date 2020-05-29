package com.wisdom.passcode.scanback.activity

import android.graphics.Typeface
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.StatusBarUtil
import com.wisdom.passcode.util.StrUtils
import kotlinx.android.synthetic.main.activity_scan_back_main.*

class ScanBackMainActivity : BaseActivity() {


    override fun initViews() {
        //沉浸式状态栏
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)
        //返回键点击事件
        iv_back.setOnClickListener { finish() }
        //设置特殊字体
        tv_place_name.typeface = StrUtils.getTypefaceCardTitle(this)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_scan_back_main)
    }
}
