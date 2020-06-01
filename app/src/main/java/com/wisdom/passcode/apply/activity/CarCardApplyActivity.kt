package com.wisdom.passcode.apply.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.helper.PopWindowHelper
import com.wisdom.passcode.util.*
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_car_card_apply.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File


class CarCardApplyActivity : BaseActivity(), View.OnClickListener {
    var placeCode = ""
    var typeCode = ""
    var carPhoto = ""
    var drivingLicencePhoto = ""
    var isCarPhoto = true//判断点击的是哪个按钮（上传车辆还是驾驶证）
    private var keyboardUtil: KeyboardUtil? = null
    override fun initViews() {
        setTitle(R.string.title_apply_car_card)
        btn_submit.setOnClickListener(this)
        tv_place_name.setOnClickListener(this)
        tv_card_type.setOnClickListener(this)
        //输入车牌号弹出特殊键盘
        et_plate_num.setOnTouchListener { _, _ ->
            hideSoftKeyboard(this@CarCardApplyActivity)
            if (keyboardUtil == null) {
                keyboardUtil = KeyboardUtil(this@CarCardApplyActivity, et_plate_num)
                keyboardUtil!!.hideSoftInputMethod()
                keyboardUtil!!.showKeyboard()
            } else {
                keyboardUtil!!.showKeyboard()
            }
            false
        }
        et_plate_num.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (keyboardUtil != null) {
                    if (keyboardUtil!!.isShow) {
                        keyboardUtil!!.hideKeyboard()
                    }
                }
            }
        }
        //上传车辆照片
        btn_car_photo.setOnClickListener {
            isCarPhoto = true
            PopWindowHelper(this).showUploadPop(this)
        }
        //上传驾驶证照片
        btn_driving_licence.setOnClickListener {
            isCarPhoto = false
            PopWindowHelper(this).showUploadPop(this)
        }

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_car_card_apply)
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
                        "type" to ConstantString.CARD_TYPE_CAR,
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


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyboardUtil != null) {
                if (keyboardUtil!!.isShow) {
                    keyboardUtil!!.hideKeyboard()
                } else {
                    finish()
                }
            } else {
                finish()
            }
        }
        return false
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            ConstantString.RESULT_CODE_CHOOSE_CARD_TYPE -> {
                typeCode = data!!.getStringExtra("id")
                val name = data.getStringExtra("name")
                val label = data.getStringExtra("label")
                tv_card_type.text = "$name($label)"
            }
            ConstantString.RESULT_CODE_CHOOSE_PLACE -> {
                //选择场所后返回的
                if (data != null) {
                    placeCode = data.getStringExtra("code")
                    tv_place_name.text = data.getStringExtra("name")
                }
            }
        }
        when (requestCode) {
            ConstantString.ALBUM_SELECT_CODE -> {
                //相册选择
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the Uri of the selected file
                        val uri = data.data
                        val uri2 =
                            Uri.parse(Uri.encode(uri.toString()))
                        carPhoto = FileUtils.getPath(this, uri)
                        if (isCarPhoto && carPhoto.isNotEmpty()) {
                            Glide.with(this@CarCardApplyActivity).load(carPhoto).into(iv_car_photo)
                        } else {
                            drivingLicencePhoto = FileUtils.getPath(this, uri)
                            if (drivingLicencePhoto.isNotEmpty()) {
                                Glide.with(this@CarCardApplyActivity).load(drivingLicencePhoto)
                                    .into(iv_driving_licence)
                            }
                        }


                    }
                }
            }
            ConstantString.FILE_SELECT_CODE -> {
                //文件选择器选择
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the Uri of the selected file
                        val uri = data.data
                        carPhoto = FileUtils.getPathByUri4kitkat(this, uri)
                        if (isCarPhoto && carPhoto.isNotEmpty()) {
                            Glide.with(this@CarCardApplyActivity).load(carPhoto).into(iv_car_photo)
                        } else {
                            drivingLicencePhoto = FileUtils.getPathByUri4kitkat(this, uri)
                            if (drivingLicencePhoto.isNotEmpty()) {
                                Glide.with(this@CarCardApplyActivity).load(drivingLicencePhoto)
                                    .into(iv_driving_licence)
                            }
                        }
                    }
                }
            }
            ConstantString.REQUEST_CAMERA -> {

            }
            else -> {
                //相机拍照选择
                carPhoto = ConstantString.PIC_LOCATE
                if (isCarPhoto && carPhoto.isNotEmpty()) {
                    Glide.with(this@CarCardApplyActivity).load(carPhoto).into(iv_car_photo)
                } else {
                    drivingLicencePhoto = ConstantString.PIC_LOCATE
                    if (drivingLicencePhoto.isNotEmpty()) {
                        Glide.with(this@CarCardApplyActivity).load(drivingLicencePhoto)
                            .into(iv_driving_licence)
                    }
                }

            }
        }
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    private fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
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
            StrUtils.isEdtTxtEmpty(et_plate_num) -> {
                isCheck = false
                toast(R.string.hint47)
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

            carPhoto.isNullOrEmpty() -> {
                isCheck = false
                toast(R.string.upload_car_photo)
            }
            drivingLicencePhoto.isNullOrEmpty() -> {
                isCheck = false
                toast(R.string.upload_driving_licence)
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
        params.put("applyType", ConstantString.CARD_TYPE_CAR)
        params.put("guaranteeName", StrUtils.getEdtTxtContent(et_person_id_name))
        params.put("guaranteeJob", StrUtils.getEdtTxtContent(et_person_job))
        params.put("guaranteeDept", StrUtils.getEdtTxtContent(et_person_dep))
        params.put("reason", StrUtils.getEdtTxtContent(et_reason))
        params.put("passCodeTypeId", typeCode)
        params.put("guaranteePhone", phoneNum)
        params.put("carNumber", StrUtils.getEdtTxtContent(et_plate_num))
        params.put("carPositiveImg", File(carPhoto))
        params.put("drivingLicenseImg", File(drivingLicencePhoto))
        val paramsList = listOf(
            "placeIdEncryption$placeCode"
            , "applyType${ConstantString.CARD_TYPE_CAR}"
            , "guaranteeName${StrUtils.getEdtTxtContent(et_person_id_name)}"
            , "guaranteeJob${StrUtils.getEdtTxtContent(et_person_job)}"
            , "guaranteeDept${StrUtils.getEdtTxtContent(et_person_dep)}"
            , "reason${StrUtils.getEdtTxtContent(et_reason)}"
            , "passCodeTypeId$typeCode"
            , "guaranteePhone$phoneNum"
            , "carNumber${StrUtils.getEdtTxtContent(et_plate_num)}"
        ).toMutableList()
        Tools.showLoadingDialog(this@CarCardApplyActivity)
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
