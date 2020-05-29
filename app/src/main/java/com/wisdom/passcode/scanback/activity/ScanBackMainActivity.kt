package com.wisdom.passcode.scanback.activity

import android.view.View
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.StatusBarUtil
import com.wisdom.passcode.util.StrUtils
import kotlinx.android.synthetic.main.activity_scan_back_main.*

class ScanBackMainActivity : BaseActivity() {


    override fun initViews() {
        //四张图片的状态
        val drawable_in = resources.getDrawable(R.drawable.`in`)
        drawable_in.setBounds(0, 0, drawable_in.minimumWidth, drawable_in.minimumHeight)

        val drawable_in_pre = resources.getDrawable(R.drawable.in_pre)
        drawable_in_pre.setBounds(0, 0, drawable_in_pre.minimumWidth, drawable_in_pre.minimumHeight)

        val drawable_out = resources.getDrawable(R.drawable.out)
        drawable_out.setBounds(0, 0, drawable_out.minimumWidth, drawable_out.minimumHeight)

        val drawable_out_pre = resources.getDrawable(R.drawable.out_pre)
        drawable_out_pre.setBounds(
            0,
            0,
            drawable_out_pre.minimumWidth,
            drawable_out_pre.minimumHeight
        )
        tv_in.compoundDrawablePadding = 20
        tv_out.compoundDrawablePadding = 20
        //沉浸式状态栏
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)
        //返回键点击事件
        iv_back.setOnClickListener { finish() }
        //设置特殊字体
        tv_place_name.typeface = StrUtils.getTypefaceCardTitle(this)
        //设置”入内“，”外出“ 点击切换事件
        rg_in_out.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_in -> {
                    //选中进入
                    ll_in.setBackgroundResource(R.drawable.shape_sacn_reslt_in_out_green)
                    ll_out.setBackgroundResource(R.drawable.shape_sacn_reslt_in_or_out)

                    ll_in_info.visibility = View.VISIBLE

                    tv_in.setTextColor(resources.getColor(R.color.white))
                    tv_out.setTextColor(resources.getColor(R.color.textcolor))

                    tv_in.setCompoundDrawables(drawable_in_pre, null, null, null)
                    tv_out.setCompoundDrawables(drawable_out, null, null, null)
                }
                R.id.rb_out -> {
                    //选中外出
                    ll_out.setBackgroundResource(R.drawable.shape_sacn_reslt_in_out_green)
                    ll_in.setBackgroundResource(R.drawable.shape_sacn_reslt_in_or_out)

                    ll_in_info.visibility = View.GONE

                    tv_out.setTextColor(resources.getColor(R.color.white))
                    tv_in.setTextColor(resources.getColor(R.color.textcolor))

                    tv_in.setCompoundDrawables(drawable_in, null, null, null)
                    tv_out.setCompoundDrawables(drawable_out_pre, null, null, null)
                }
            }
        }
        //设置默认选中按钮
        rb_in.isChecked = true
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_scan_back_main)
    }
}
