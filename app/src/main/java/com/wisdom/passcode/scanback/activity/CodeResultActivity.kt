package com.wisdom.passcode.scanback.activity

import android.graphics.Color
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.util.PrivacyUtil
import com.wisdom.passcode.util.Tools
import kotlinx.android.synthetic.main.activity_code_result.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundResource
import java.text.SimpleDateFormat
import java.util.*

class CodeResultActivity : BaseActivity() {


    override fun initViews() {
        //禁止截屏
        Tools.forbiddenScreenShort(this)
        //设置特殊字体
        Tools.setFont(this, tv_title)
        iv_back.setOnClickListener { finish() }
        tv_date.text = SimpleDateFormat("MM月dd日").format(Date())
        comm_head_title.text = getText(R.string.title_scan_result)
        //拿到上级页面数据
        val black = intent.getStringExtra("black")
        val agree = intent.getStringExtra("agree")
        val auth = intent.getStringExtra("auth")
        val placeName = intent.getStringExtra("placeName")
        val type = intent.getStringExtra("type")
        //黑名单用户特殊显示
        if (black.toBoolean()) {
            title.text = getText(R.string.forbidden)
            rl_top.backgroundResource = R.drawable.shape_code_result_red
            tv_num.backgroundColor = Color.parseColor("#EC2323")
        }
        //设置页面显示
        tv_name.text =
            PrivacyUtil.nameDesensitization(SharedPreferenceUtil.getPersonalInfoModel(this).nickName)
        tv_place.text = placeName
        //获取当前时间
        val strDate = "yyyy年MM月dd日"
        val strTime = "HH:mm"
        val sdDate = SimpleDateFormat(strDate)
        val sdTime = SimpleDateFormat(strTime)
        when (type) {
            ConstantString.SCAN_CODE_TYPE_IN -> {
//                入内
                tv_time.text =
                    "${sdDate.format(Date())}  ${sdTime.format(Date())}${getString(R.string.in_out_state_in)}"
            }
            ConstantString.SCAN_CODE_TYPE_OUT -> {
//                外出
                tv_time.text =
                    "${sdDate.format(Date())}  ${sdTime.format(Date())}${getString(R.string.in_out_state_out)}"
            }
        }

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_code_result)
    }


}
