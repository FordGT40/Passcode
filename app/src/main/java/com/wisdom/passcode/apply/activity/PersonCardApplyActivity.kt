package com.wisdom.passcode.apply.activity

import android.content.Intent
import android.view.View
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.StrUtils
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_car_card_apply.*
import kotlinx.android.synthetic.main.activity_person_card_apply.*
import kotlinx.android.synthetic.main.activity_person_card_apply.btn_submit
import kotlinx.android.synthetic.main.activity_person_card_apply.cb_licences
import kotlinx.android.synthetic.main.activity_person_card_apply.et_person_dep
import kotlinx.android.synthetic.main.activity_person_card_apply.et_person_id_name
import kotlinx.android.synthetic.main.activity_person_card_apply.et_person_job
import kotlinx.android.synthetic.main.activity_person_card_apply.et_person_phone
import kotlinx.android.synthetic.main.activity_person_card_apply.et_reason
import kotlinx.android.synthetic.main.activity_person_card_apply.tv_card_type
import kotlinx.android.synthetic.main.activity_person_card_apply.tv_place_name
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.json.JSONObject

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
                //提交按钮点击事件
                if (checkPageData()) {
                    //提交数据到接口
                    submitData()
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
            if (data != null) {
                typeCode = data.getStringExtra("id")
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
    private fun checkPageData(): Boolean {
        var isCheck = true
        when {
            placeCode == "" -> {
                isCheck = false
                toast(R.string.hint39)
            }
            typeCode == "" -> {
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
            !cb_licences.isChecked -> {
                isCheck = false
                toast(R.string.upload_paper_cb_hint)
            }
        }
        return isCheck
    }

    /**
     *  @describe 提交数据到接口
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/28 0028  17:33
     */
    private fun submitData() {
        val params = HttpParams()
        val phoneNum = EncrypAndDecrypUtil.encrypt(StrUtils.getEdtTxtContent(et_person_phone))
        params.put("placeIdEncryption", placeCode)
        params.put("applyType", ConstantString.CARD_TYPE_PERSON)
        params.put("guaranteeName", StrUtils.getEdtTxtContent(et_person_id_name))
        params.put("guaranteeJob", StrUtils.getEdtTxtContent(et_person_job))
        params.put("guaranteeDept", StrUtils.getEdtTxtContent(et_person_dep))
        params.put("reason", StrUtils.getEdtTxtContent(et_reason))
        params.put("passCodeTypeId", typeCode)
        params.put("guaranteePhone", phoneNum)
        params.put("carNumber", "")
        val paramsList = listOf(
            "placeIdEncryption$placeCode"
            , "applyType${ConstantString.CARD_TYPE_PERSON}"
            , "guaranteeName${StrUtils.getEdtTxtContent(et_person_id_name)}"
            , "guaranteeJob${StrUtils.getEdtTxtContent(et_person_job)}"
            , "guaranteeDept${StrUtils.getEdtTxtContent(et_person_dep)}"
            , "reason${StrUtils.getEdtTxtContent(et_reason)}"
            , "passCodeTypeId$typeCode"
            , "guaranteePhone$phoneNum"
            , "carNumber"
        ).toMutableList()
        Tools.showLoadingDialog(this@PersonCardApplyActivity)
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.PASSCODE_APPLY_URL,
            params,
            paramsList,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    Tools.closeDialog()
                }

                override fun onRefreshFail(msg: String?) {
                    Tools.closeDialog()
                }
            }) {
                override fun onInterfaceSuccess(
                    jsonObject: JSONObject?,
                    call: Call?,
                    response: Response?
                ) {
                    Tools.closeDialog()
                    val code = jsonObject!!.optInt("code")
                    val msg = jsonObject!!.optString("msg")
                    if (code == 0) {
                        //成功
                        startActivity<CardApplySuccessActivity>("title" to R.string.title_person_card_apply)
                    } else {
                        //失败
                        toast(msg)
                    }
                }
            })
    }

}
