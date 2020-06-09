package com.wisdom.passcode.scanback.activity

import android.view.View
import com.google.gson.Gson
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.scanback.model.ScanBackModel
import com.wisdom.passcode.scanback.model.UploadScanFormModel
import com.wisdom.passcode.util.*
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_scan_back_main.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject

class ScanBackMainActivity : BaseActivity() {

    var type = ConstantString.SCAN_CODE_TYPE_IN
    override fun initViews() {
        //设置键盘的监听,防止输入框呗软键盘遮挡
        KeyboardPatch(this, ll_parent).enable()
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
        setNoStateBar()
        //返回键点击事件
        iv_back.setOnClickListener { finish() }
        //设置特殊字体
        Tools.setFont(this, tv_place_name)
        //设置”进入“，”离开“ 点击切换事件
        rg_in_out.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_in -> {
                    //选中进入
                    type = ConstantString.SCAN_CODE_TYPE_IN
                    ll_in.setBackgroundResource(R.drawable.shape_sacn_reslt_in_out_green)
                    ll_out.setBackgroundResource(R.drawable.shape_sacn_reslt_in_or_out)

                    ll_in_info.visibility = View.VISIBLE

                    tv_in.setTextColor(resources.getColor(R.color.white))
                    tv_out.setTextColor(resources.getColor(R.color.textcolor))

                    tv_in.setCompoundDrawables(drawable_in_pre, null, null, null)
                    tv_out.setCompoundDrawables(drawable_out, null, null, null)
                }
                R.id.rb_out -> {
                    //选中离开
                    type = ConstantString.SCAN_CODE_TYPE_OUT
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
        //设置页面相关信息
        val placeName = intent.getStringExtra("data")
        val placeCode = intent.getStringExtra("placeCode")
        tv_place_name.text = placeName
        val name =
            SharedPreferenceUtil.getPersonalInfoModel(this).nickName
        val phoneNum =
            EncrypAndDecrypUtil.decrypt(SharedPreferenceUtil.getPersonalInfoModel(this).phonenumber)
        tv_name.text = PrivacyUtil.nameDesensitization(name)
        tv_phoneNum.text = PrivacyUtil.phoneDesensitization(phoneNum)
//提交按钮点击事件
        btn_submit.setOnClickListener {
            submitData(placeCode)
        }

    }


    override fun setlayoutIds() {
        setContentView(R.layout.activity_scan_back_main)
    }


    /**
     *  @describe 提交扫码后的信息到接口
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/1 0001  15:11
     */
    private fun scanLog(model: UploadScanFormModel) {
        val paramas = HttpParams()
        paramas.put("type", model.type)
        paramas.put("scanType", model.scanType)
        paramas.put("placeCodeEncryption", model.placeCodeEncryption)
        paramas.put("userIdEncryption", model.userIdEncryption)
        paramas.put("adminEncryption", model.adminEncryption)
        paramas.put("remarks", model.remarks)
        paramas.put("visitorsUserName", model.visitorsUserName)
        paramas.put("visitorsUserPhone", EncrypAndDecrypUtil.encrypt(model.visitorsUserPhone))
        paramas.put("visitorsUserDept", model.visitorsUserDept)
        paramas.put("carNumber", model.carNumber)
        val listParams = listOf(
            "type${model.type}",
            "scanType${model.scanType}"
            , "placeCodeEncryption${model.placeCodeEncryption}"
            , "userIdEncryption${model.userIdEncryption}"
            , "adminEncryption${model.adminEncryption}"
            , "visitorsUserName${model.visitorsUserName}"
            , "visitorsUserDept${model.visitorsUserDept}"
            , "visitorsUserPhone${EncrypAndDecrypUtil.encrypt(model.visitorsUserPhone)}"
            , "remarks${model.remarks}"
            , "carNumber${model.carNumber}"
        ).toMutableList()
        Tools.showLoadingDialog(this@ScanBackMainActivity)
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.SCAN_LOG_URL,
            paramas,
            listParams,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    Tools.closeDialog()

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
                    val msg = jsonObject!!.optString("msg")
                    if (code == 0) {
                        //获得数据、
                        val jsonData = jsonObject.optString("data")
                        val model = Gson().fromJson(jsonData, ScanBackModel::class.java)
                        with(model) {
                            startActivity<CodeResultActivity>(
                                "auth" to auth,
                                "black" to black,
                                "agree" to agree,
                                "placeName" to tv_place_name.text.toString(),
                                "type" to type
                            )
                        }
                        finish()
                    } else {
                        toast(msg)
                    }
                }
            })
    }

    /**
     *  @describe 提交数据到接口的方法
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/1 0001  15:41
     */
    private fun submitData(placeCode: String) {
        val dataModel = UploadScanFormModel()
        dataModel.type = type
        dataModel.scanType = ConstantString.SCAN_TYPE_SCAN
        dataModel.carNumber = ""
        dataModel.placeCodeEncryption = placeCode
        dataModel.adminEncryption = ""
        dataModel.userIdEncryption = ConstantString.userIdEncryption
        dataModel.remarks = StrUtils.getEdtTxtContent(et_reason)
        dataModel.visitorsUserDept = StrUtils.getEdtTxtContent(et_dep)
        dataModel.visitorsUserPhone = StrUtils.getEdtTxtContent(et_phone)
        dataModel.visitorsUserName = StrUtils.getEdtTxtContent(et_name)
        scanLog(dataModel)
    }
}
