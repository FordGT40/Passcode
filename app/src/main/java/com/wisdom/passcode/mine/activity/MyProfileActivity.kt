package com.wisdom.passcode.mine.activity

import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.helper.Helper
import com.wisdom.passcode.helper.PopWindowHelper
import com.wisdom.passcode.mine.model.RealNameInfoDetailModel
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.FileUtils
import com.wisdom.passcode.util.PrivacyUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_my_profile.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File

class MyProfileActivity : BaseActivity() {
    private var carPhoto = ""

    override fun initViews() {
        setTitle(R.string.title_my_profile)
        getUserInfo()
        //修改手机号点击事件
        tv_alter_phone.setOnClickListener {
            startActivity<AlterPhoneActivity>()
        }
        //修改头像
        tv_alter_head_image.setOnClickListener {
            PopWindowHelper(this).showUploadPop(this)
        }
        //设置用户头像
        val headImgeUrl = SharedPreferenceUtil.getPersonalInfoModel(this).avatar
        Glide.with(this).load(headImgeUrl).into(iv_head)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_my_profile)

    }

    /**
     *  @describe 获取实名认证后的信息
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/29 0029  16:18
     */
    private fun getUserInfo() {
        val params = HttpParams()
        val paramsList = ArrayList<String>().toMutableList()
        Tools.showLoadingDialog(this@MyProfileActivity)
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.USER_AUTHINFO_URL,
            params,
            paramsList,
            object : StringsCallback(
                object : OnTokenRefreshSuccessListener {
                    override fun onRefreshSuccess() {
                        Tools.closeDialog()
                        getUserInfo()
                    }

                    override fun onRefreshFail(msg: String?) {
                        Tools.closeDialog()
                    }
                }
            ) {
                override fun onInterfaceSuccess(
                    jsonObject: JSONObject?,
                    call: Call?,
                    response: Response?
                ) {
                    Tools.closeDialog()
                    val code = jsonObject!!.optInt("code")
                    val msg = jsonObject.optString("msg")
                    if (code == 0) {
                        //获得个人信息，显示个人信息并脱敏
                        val model = Gson().fromJson(
                            jsonObject.optString("data"),
                            RealNameInfoDetailModel::class.java
                        )
                        tv_name.text = PrivacyUtil.nameDesensitization(model.realName)
                        tv_sex.text = if (model.sex == "0") {
                            "女"
                        } else {
                            "男"
                        }
                        tv_phone.text = PrivacyUtil.phoneDesensitization(
                            EncrypAndDecrypUtil.decrypt(
                                SharedPreferenceUtil.getPersonalInfoModel(this@MyProfileActivity).phonenumber
                            )
                        )
                        tv_idcard.text =
                            PrivacyUtil.idCardDesensitization(EncrypAndDecrypUtil.decrypt(model.idCard))
                        tv_addr.text = model.address
                    } else {
                        //失败
                        toast(msg)
                    }
                }
            })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ConstantString.ALBUM_SELECT_CODE -> {
                //相册选择
                if (resultCode == BaseActivity.RESULT_OK) {
                    if (data != null) {
                        // Get the Uri of the selected file
                        val uri = data.data
                        val uri2 =
                            Uri.parse(Uri.encode(uri.toString()))
                        carPhoto = FileUtils.getPath(context, uri)
                        if (carPhoto.isNotEmpty()) {
                            uploadHeadFile(carPhoto)
                        }
                    }
                }
            }
            ConstantString.FILE_SELECT_CODE -> {
                //文件选择器选择
                if (resultCode == BaseActivity.RESULT_OK) {
                    if (data != null) {
                        // Get the Uri of the selected file
                        val uri = data.data
                        carPhoto = FileUtils.getPathByUri4kitkat(context, uri)
                        if (carPhoto.isNotEmpty()) {
                            uploadHeadFile(carPhoto)
                        }
                    }
                }
            }
            ConstantString.REQUEST_CAMERA -> {
            }
            else -> {
                //相机拍照选择
                carPhoto = ConstantString.PIC_LOCATE
                if (carPhoto.isNotEmpty() && data != null) {
                    uploadHeadFile(carPhoto)
                }
            }
        }
    }

    /**
     *  @describe 修改头像
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/15 0015  11:30
     */
    private fun uploadHeadFile(picPath: String) {
        val params = HttpParams()
        params.put("avatarFile", File(picPath))
        val listParams = ArrayList<String>().toMutableList()
        Tools.showLoadingDialog(this)
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.UPDATE_AVATAR_URL,
            params,
            listParams,
            object : StringsCallback(
                object : OnTokenRefreshSuccessListener {
                    override fun onRefreshSuccess() {
                        uploadHeadFile(picPath)
                    }

                    override fun onRefreshFail(msg: String?) {

                    }
                }
            ) {
                override fun onInterfaceSuccess(
                    jsonObject: JSONObject?,
                    call: Call?,
                    response: Response?
                ) {
                    Glide.with(this@MyProfileActivity).load(picPath).into(iv_head)
                    Helper.syncPersonalInfo(this@MyProfileActivity,
                        object : Helper.OnPersonalInfoCompletedListener {
                            override fun onPersonalInfoCompleted() {
                                Tools.closeDialog()
                            }
                        })
                }
            })
    }
}
