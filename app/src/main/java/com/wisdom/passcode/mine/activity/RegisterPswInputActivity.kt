package com.wisdom.passcode.mine.activity

import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.ActivityManager
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.helper.Helper
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import kotlinx.android.synthetic.main.activity_register_psw_input.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject

class RegisterPswInputActivity : BaseActivity() {
    private var phone = ""
    private var verifyCode = ""

    override fun initViews() {
        iv_back.setOnClickListener { finish() }
        //眼睛点击事件的监听
        et_psw.typeface = Typeface.DEFAULT
        et_psw.transformationMethod = PasswordTransformationMethod()
        cb_eye.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                et_psw.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                et_psw.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            et_psw.setSelection(et_psw.length())
        }
        //关键字变色
        val content = Tools.getClickableSpan(
            getString(R.string.hint7)
            ,
            6,
            12,
            Color.parseColor("#0059A1"),
            false,
            object : Tools.Companion.OnColoredStringClickedListener {
                override fun onColoredStringClickedListener() {
                    toast("点击了协议")
                }

            }
        )
        tv_licence.text = content
        //输入框监听
        et_psw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length > 16) {
                    toast(R.string.hint12)
                }
                if (s.toString().length in 6..16 && cb_licence.isChecked) {
                    doBtnActive(true)
                } else {
                    doBtnActive(false)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        //复选框的点击事件
        cb_licence.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && et_psw.text.toString().length in 6..16) {
                doBtnActive(true)
            } else {
                doBtnActive(false)
            }
        }
        //获得电话号码,验证码
        phone = intent.getStringExtra("data")
        verifyCode = intent.getStringExtra("code")
        //提交按钮点击事件
        btn_submit.setOnClickListener {
            if (et_psw.text.toString().length < 6 || et_psw.text.toString().length > 16) {
                toast(R.string.psw_error)
            } else if (!cb_licence.isChecked) {
                toast(R.string.choose_licence)
            } else {
                registerAccount(phone, verifyCode)
            }
        }

    }


    override fun setlayoutIds() {
        setContentView(R.layout.activity_register_psw_input)
    }

    /**
     *  @describe 设置按钮是否为激活状态
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/12 0012  14:26
     */
    fun doBtnActive(isActive: Boolean) {
        if (isActive) {
            btn_submit.isClickable = true
            btn_submit.backgroundDrawable =
                context.getDrawable(R.drawable.shape_circle_conner_blue_deep)
        } else {
            btn_submit.isClickable = true
            btn_submit.backgroundDrawable =
                context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
        }
    }


    /**
     *  @describe 走接口注册账号的方法
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  13:29
     */
    private fun registerAccount(phone: String?, verifyCode: String) {
        Tools.showLoadingDialog(this)
        val phoneData = EncrypAndDecrypUtil.encrypt(phone)
        val codeData = EncrypAndDecrypUtil.encrypt(verifyCode)
        val pswData = EncrypAndDecrypUtil.encrypt(et_psw.text.toString())
        val params = HttpParams()
        params.put("phone", phoneData)
        params.put("password", pswData)
        params.put("code", codeData)
        val paramsList =
            listOf("phone$phoneData", "password$pswData", "code$codeData").toMutableList()

        HttpUtil.httpPostWithStampAndSign(
            ConstantUrl.REGISTER_URL,
            params,
            paramsList,
            object : StringCallback() {
                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                    Tools.closeDialog()
                }

                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    Tools.closeDialog()
                    val jsonObject = JSONObject(t)
                    val code = jsonObject.optInt("code")
                    if (code == 0) {
                        //注册成功,获取token
                        val jsonData = jsonObject.optJSONObject("data")
                        val accessToken = jsonData.optString("accessToken")
                        val refreshToken = jsonData.optString("refreshToken")
                        val userIdEncryption = jsonData.optString("userIdEncryption")
                        val isAdmin = jsonData.optString("isAdmin")
                        ConstantString.loginState = true
                        // token持久化
                        SharedPreferenceUtil.getConfig(this@RegisterPswInputActivity).sharedPreferences.edit()
                            .apply {
                                putString("accessToken", accessToken)
                                putString("refreshToken", refreshToken)
                                putString("phone", phone)
                                putString("userIdEncryption", userIdEncryption)
                                putString("isAdmin", isAdmin)
                                apply()
                            }
                        //本地化一下sp文件的数据
                        Helper.getInfoFromSpFile(this@RegisterPswInputActivity)
                        //同步一下个人信息
                        Helper.syncPersonalInfo(this@RegisterPswInputActivity,
                            object : Helper.OnPersonalInfoCompletedListener {
                                override fun onPersonalInfoCompleted() {
                                    //展示注册成功页面
                                    ActivityManager.getActivityManagerInstance().clearAllActivity()
                                    startActivity<RegisterSuccessActivity>()
                                }
                            })

                    } else {
                        //注册失败
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }
                }
            })
    }
}
