package com.wisdom.passcode.scanback.activity

import android.graphics.Color
import android.view.View
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.scanback.model.ScanBackModel
import com.wisdom.passcode.util.Tools
import kotlinx.android.synthetic.main.activity_visitor_result.*

class VisitorResultActivity : BaseActivity() {

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

    /**
     *  @describe 设置页面数据
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/12 0012  10:30
     */
    private fun setPageData(model: ScanBackModel.PushDataBean) {
        val isAgree = intent.getBooleanExtra("isAgree", false)
        with(model) {
            tv_place_name.text=placeCodeName
            tv_name.text=visitorsUserName
            if (isAgree) {
              //同意了
                iv_img.setImageResource(R.drawable.pass)
                tv_name.setTextColor(Color.parseColor("#00c775"))
                tv_hint.text="已经同意了您的约见申请"
                tv_in.visibility= View.VISIBLE
            } else {
                 //不同意
                iv_img.setImageResource(R.drawable.stop)
                tv_name.setTextColor(Color.parseColor("#F65252"))
                tv_hint.text="拒绝了您的约见申请"
                tv_in.visibility= View.GONE
            }
        }

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_visitor_result)
    }
}
