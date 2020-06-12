package com.wisdom.passcode.scanback.activity

import android.graphics.Color
import android.view.View
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.scanback.model.ScanBackModel
import com.wisdom.passcode.util.Tools
import kotlinx.android.synthetic.main.activity_guard_result.*

class GuardResultActivity : BaseActivity() {


    override fun initViews() {
//沉浸式状态栏
        setNoStateBar()
        //返回键点击事件
        iv_back.setOnClickListener { finish() }
        //设置特殊字体
        Tools.setFont(this, tv_place_name)
        val model = intent.extras.getSerializable("data") as ScanBackModel.PushDataBean
        setPageData(model)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_guard_result)
    }

    /**
     *  @describe 设置页面数据
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/12 0012  10:30
     */
    private fun setPageData(model: ScanBackModel.PushDataBean) {
        val isAgree = intent.getBooleanExtra("isAgree", false)
        with(model) {
            tv_place_name.text = placeCodeName
            tv_name.text = visitorsUserName
            tv_name2.text = userName
            if (isAgree) {
                //同意了
                iv_img.setImageResource(R.drawable.pass)
                tv_hint.text = "同意了"
                tv_in.visibility = View.VISIBLE
                tv_name.setTextColor(Color.parseColor("#00C775"))
                tv_name2.setTextColor(Color.parseColor("#00C775"))
            } else {
                //不同意
                iv_img.setImageResource(R.drawable.stop)
                tv_hint.text = "拒绝了"
                tv_in.visibility = View.GONE
                tv_name.setTextColor(Color.parseColor("#F65252"))
                tv_name2.setTextColor(Color.parseColor("#F65252"))
            }
        }

    }
}
