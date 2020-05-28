package com.wisdom.passcode.apply.activity

import android.view.View
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_person_card_apply.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class PersonCardApplyActivity : BaseActivity(), View.OnClickListener {


    override fun initViews() {
        setTitle(R.string.title_person_card_apply)
        tv_place_name.setOnClickListener(this)
        tv_card_type.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_person_card_apply)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_place_name -> {
                //选择场所名
                startActivityForResult<PlaceNameSearchActivity>(ConstantString.REQUEST_CODE)
            }
            R.id.tv_card_type -> {
                //出入证类型选择
                startActivityForResult<CardTypeChooseActivity>(ConstantString.REQUEST_CODE)
            }
            R.id.btn_submit -> {
                //提交按钮点击事件(暂时跳转成功页面)
                startActivity<CardApplySuccessActivity>("title" to R.string.title_person_card_apply)
            }

        }
    }


}
