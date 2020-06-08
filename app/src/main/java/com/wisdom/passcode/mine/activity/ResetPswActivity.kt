package com.wisdom.passcode.mine.activity

import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.util.*
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_reset_psw.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*

class ResetPswActivity : BaseActivity() {
    var phoneNum = ""
    var isPhoneEdited = false
    override fun initViews() {
        setTitle(R.string.title_reset_psw)
        //判断本地是否存在之前登录过的手机号
        if (SharedPreferenceUtil.getPersonalInfoModel(this) != null) {
            //取得手机号，填写到界面
            val phone = SharedPreferenceUtil.getPersonalInfoModel(this).phonenumber
            phoneNum = EncrypAndDecrypUtil.decrypt(phone)
            et_phone.setText(PrivacyUtil.phoneDesensitization(phoneNum))
            isPhoneEdited = false
        } else {
            //用户需要手动填写电话号码，那么提交接口时候需要取输入框中的内容
            isPhoneEdited = true
        }
        //监测初始化的电话输入框中的电话号是否被动过
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isPhoneEdited = true
            }
        })
        //发送验证码点击事件
        tv_send_code.setOnClickListener {
            if (isPhoneEdited) {
                if (RegularUtil.isPhoneNumCorrect(et_phone)) {
                    sendSms(et_phone.text.toString())
                } else {
                    toast(R.string.phone_wrong)
                }
            } else {
                sendSms(phoneNum)
            }
        }
        //点击提交按钮点击事件
        btn_submit.setOnClickListener {
            if (checkPageData()) {
                //检查验证码是否正确
                checkSmsCode(
                    if (isPhoneEdited) {
                        et_phone.text.toString()
                    } else {
                        phoneNum
                    }, et_code.text.toString()
                )

            }
        }

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_reset_psw)
    }

    /**
     *  @describe 发送验证码接口
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  10:56
     */
    private fun sendSms(phoneNum: String) {
        Tools.showLoadingDialog(this)
        val params = HttpParams()
        params.put("phone", EncrypAndDecrypUtil.encrypt(phoneNum))
        params.put("type", ConstantString.RESET_PSW_TYPE)
        val paramsList = listOf(
            "type${ConstantString.RESET_PSW_TYPE}"
            , "phone${EncrypAndDecrypUtil.encrypt(phoneNum)}"
        ).toMutableList()
        HttpUtil.httpPostWithStampAndSign(
            ConstantUrl.SMS_URL,
            params,
            paramsList,
            object : StringCallback() {
                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                    Tools.closeDialog()
                }

                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    Tools.closeDialog()
                    val json = JSONObject(t)
                    val code = json.optInt("code")
                    val msg = json.optString("msg")

                    if (code == 0) {
                        //发送成功
                        startTimer()
                        toast(R.string.sms_send_success)
                    } else {
                        toast(msg)
                    }
                }

            })
    }

    /**
     *  @describe 访问接口验证验证码的有效性
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  09:50
     */
    private fun checkSmsCode(phoneNum: String, code: String) {
        val paramas = HttpParams()
        paramas.put("phone", EncrypAndDecrypUtil.encrypt(phoneNum))
        paramas.put("type", ConstantString.RESET_PSW_TYPE)
        paramas.put("code", EncrypAndDecrypUtil.encrypt(code))
        val paramArray = listOf(
            "phone${EncrypAndDecrypUtil.encrypt(phoneNum)}"
            , "type${ConstantString.RESET_PSW_TYPE}",
            "code${EncrypAndDecrypUtil.encrypt(code)}"
        ).toMutableList()
        Tools.showLoadingDialog(this)
        HttpUtil.httpPostWithStampAndSign(
            ConstantUrl.VERIFY_CODE_URL,
            paramas,
            paramArray,
            object : StringCallback() {
                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                    Tools.closeDialog()
                }

                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    Tools.closeDialog()
                    val jsonObject = JSONObject(t)
                    val code = jsonObject.optInt("code")
                    val msg = jsonObject.optString("msg")
                    if (code == 0) {
                        // 验证成功,执行修改密码操作
                        resetPsw()
                    } else {
                        //验证失败
                        toast(msg)
                    }

                }
            })

    }


    private fun checkPageData(): Boolean {
        var isChecked = true
        if (StrUtils.isEdtTxtEmpty(et_phone)) {
            isChecked = false
            toast(R.string.phone_empty_hint)
        } else if (StrUtils.isEdtTxtEmpty(et_code)) {
            isChecked = false
            toast(R.string.code_check_empty)
        } else if (StrUtils.isEdtTxtEmpty(et_psw)) {
            isChecked = false
            toast(R.string.psw_empty_error)
        } else if (with(et_psw.text.toString().length) {
                this < 6 || this > 16
            }) {
            isChecked = false
            toast(R.string.psw_error)
        } else if (isPhoneEdited) {
            if (!RegularUtil.isPhoneNumCorrect(et_phone)) {
                isChecked = false
                toast(R.string.phone_wrong)
            }
        }
        return isChecked
    }

    /**
     *  @describe 重置密码
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/8 0008  14:01
     */
    fun resetPsw() {
        val params = HttpParams()
        var phoneEn = if (isPhoneEdited) {
            EncrypAndDecrypUtil.encrypt(et_phone)
        } else {
            EncrypAndDecrypUtil.encrypt(phoneNum)
        }
        params.put("phone", phoneEn)
        params.put("newPassword", EncrypAndDecrypUtil.encrypt(et_psw))
        params.put("code", EncrypAndDecrypUtil.encrypt(et_code))
        val paramsList = listOf(
            "phone$phoneEn"
            , "newPassword${EncrypAndDecrypUtil.encrypt(et_psw)}"
            , "code${EncrypAndDecrypUtil.encrypt(et_code)}"
        ).toMutableList()
        HttpUtil.httpPostWithStampAndSign(
            ConstantUrl.USER_UPDATEPASSWORD_URL,
            params,
            paramsList,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    resetPsw()
                }

                override fun onRefreshFail(msg: String?) {
                    toast(msg!!)
                }
            }) {
                override fun onInterfaceSuccess(
                    jsonObject: JSONObject?,
                    call: Call?,
                    response: Response?
                ) {
                    val code = jsonObject!!.optInt("code")
                    val msg = jsonObject!!.optString("msg")

                    if (code == 0) {
                        AlertUtil.showMsgAlert(
                            this@ResetPswActivity,
                            R.string.reset_success
                        ) { _, _ ->
                            this@ResetPswActivity.finish()
                        }
                    } else {
                        toast(msg)
                    }
                }
            })


    }


    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    //-----------------------------------------以下部分是获取验证码按钮的计时器------------------------------------------
    private var timerTask: MyTimerTask? = null
    private var timer: Timer? = null
    private var MAX_TIME = 60
    private val handler: Handler = object : Handler() {
        override fun dispatchMessage(msg: Message) {
            super.dispatchMessage(msg)
            if (MAX_TIME > 0 && msg.what == 1) {
                val value = java.lang.String.format(
                    resources.getString(
                        R.string.btn_reRendValidate2
                    ),
                    MAX_TIME--
                )
                tv_send_code.setText(value)
            } else {
                stopTimer()
            }
        }
    }

    /**
     * 获得验证码倒计时的任务
     */
    inner class MyTimerTask : TimerTask() {
        override fun run() {
            val message = Message()
            message.what = 1
            handler.sendMessage(message)
        }
    }

    /**
     * 获取验证码开始倒计时
     */
    private fun startTimer() {
        tv_send_code.isClickable = false
        tv_send_code.setBackgroundResource(R.drawable.shape_grey_btn)
        val value = java.lang.String.format(
            resources.getString(R.string.btn_reRendValidate2),
            MAX_TIME
        )
        tv_send_code.text = value
        if (timer == null) timer = Timer(true)
        if (timerTask != null) timerTask!!.cancel()
        timerTask = MyTimerTask()
        timer!!.schedule(timerTask, 1000, 1000)
    }

    /**
     * 停止获取验证码按钮倒计时
     */
    private fun stopTimer() {
        tv_send_code.isClickable = true
        tv_send_code.setBackgroundResource(R.drawable.shape_circle_conner_blue_light3)
        tv_send_code.text = "获取验证码"
        MAX_TIME = 60
        if (timerTask != null) {
            timerTask!!.cancel()
            timerTask = null
        }
        if (timerTask != null) {
            timerTask!!.cancel()
            timerTask = null
        }
    }


}
