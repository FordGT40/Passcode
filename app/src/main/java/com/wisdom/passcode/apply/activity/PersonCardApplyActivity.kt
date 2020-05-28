package com.wisdom.passcode.apply.activity

import android.content.Intent
import android.view.View
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.StrUtils
import kotlinx.android.synthetic.main.activity_person_card_apply.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class PersonCardApplyActivity : BaseActivity(), View.OnClickListener {

    var placeCode = ""
    var typeCode = ""

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
                if (placeCode != "") {
                    startActivityForResult<CardTypeChooseActivity>(
                        ConstantString.REQUEST_CODE,
                        "type" to ConstantString.CARD_TYPE_PERSON,
                        "placeCode" to placeCode
                    )
                } else {
                    toast(R.string.hint39)
                }
            }
            R.id.btn_submit -> {
                //提交按钮点击事件(暂时跳转成功页面)
                if (checkPageData()) {
                    startActivity<CardApplySuccessActivity>("title" to R.string.title_person_card_apply)
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ConstantString.RESULT_CODE_CHOOSE_PLACE) {
            //选择场所后返回的
            if (data != null) {
                placeCode = data.getStringExtra("code")
                tv_place_name.text = data.getStringExtra("name")
            }
        } else if (resultCode == ConstantString.RESULT_CODE_CHOOSE_CARD_TYPE) {
            if (data!=null) {
//                typeCode = data.getStringExtra("ids")
                val name = data.getStringExtra("name")
                val label = data.getStringExtra("label")
                tv_card_type.text = "$name($label)"
            }
        }
    }

    /**
     *  @describe 检查界面是否有空值
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/28 0028  16:29
     */
    fun checkPageData(): Boolean {
        var isCheck = true
        when {
            placeCode=="" -> {
                isCheck = false
                toast(R.string.hint39)
            }
            typeCode=="" -> {
                isCheck = false
                toast(R.string.hint40)
            }
            StrUtils.isEdtTxtEmpty(et_reason) -> {
                isCheck = false
                toast(R.string.hint41)
            }
            StrUtils.isEdtTxtEmpty(et_person_id_name) -> {
                isCheck = false
                toast(R.string.hint42)
            }
            StrUtils.isEdtTxtEmpty(et_person_phone) -> {
                isCheck = false
                toast(R.string.hint43)
            }
            StrUtils.isEdtTxtEmpty(et_person_dep) -> {
                isCheck = false
                toast(R.string.hint44)
            }
            !cb_licences.isChecked->{
                isCheck = false
                toast(R.string.upload_paper_cb_hint)
            }
        }
        return isCheck
    }
}
