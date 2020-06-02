package com.wisdom.passcode.mine.activity

import android.os.Handler
import android.os.Message
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.ActivityManager
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.RegularUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_alter_phone.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*

class AlterPhoneActivity : BaseActivity() {


    override fun initViews() {
        setTitle(R.string.title_find_psw)
        //发送验证码点击事件
        tv_get_code.setOnClickListener {
            if (et_phone.text.toString().length < 11 || !RegularUtil.isPhoneNumCorrect(et_phone)) {
                toast(R.string.phone_wrong)
            } else {
                sendSms(et_phone.text.toString(), ConstantString.ALTER_PHONE_TYPE)
            }
        }
        //修改手机号点击事件
        btn_submit.setOnClickListener {
            checkSmsCode(et_code_num.text.toString())
        }
    }


    override fun setlayoutIds() {
        setContentView(R.layout.activity_alter_phone)
    }

    /**
     *  @describe 发送验证码接口
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  10:56
     */
    private fun sendSms(phoneNum: String, type: String) {
        Tools.showLoadingDialog(this)
        val params = HttpParams()
        params.put("phone", EncrypAndDecrypUtil.encrypt(phoneNum))
        params.put("type", type)
        val paramsList = listOf(
            "type${type}"
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
                    when (code) {
                        0 -> {
                            //验证码发送成功，进入等待输入验证码页面
                            toast(R.string.sms_send_success)
                            startTimer()
                        }
                        else -> {
                            toast(HttpUtil.getErrorMsgByCode("$code"))
                        }
                    }
                }

            })
    }

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
                tv_get_code.setText(value)
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
        tv_get_code.isClickable = false
        tv_get_code.setBackgroundResource(R.drawable.shape_grey_btn)
        val value = java.lang.String.format(
            resources.getString(R.string.btn_reRendValidate2),
            MAX_TIME
        )
        tv_get_code.text = value
        if (timer == null) timer = Timer(true)
        if (timerTask != null) timerTask!!.cancel()
        timerTask = MyTimerTask()
        timer!!.schedule(timerTask, 1000, 1000)
    }

    /**
     * 停止获取验证码按钮倒计时
     */
    private fun stopTimer() {
        tv_get_code.isClickable = true
        tv_get_code.setBackgroundResource(R.drawable.shape_yellow_btn)
        tv_get_code.text = "获取验证码"
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


    /**
     *  @describe 访问接口验证验证码的有效性
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  09:50
     */
    private fun checkSmsCode(inputContent: String) {
        val paramas = HttpParams()
        paramas.put("phone", EncrypAndDecrypUtil.encrypt(et_phone.text.toString()))
        paramas.put("type", ConstantString.ALTER_PHONE_TYPE)
        paramas.put("code", EncrypAndDecrypUtil.encrypt(inputContent))
        val paramArray = listOf(
            "phone${EncrypAndDecrypUtil.encrypt(et_phone.text.toString())}"
            , "type${ConstantString.ALTER_PHONE_TYPE}",
            "code${EncrypAndDecrypUtil.encrypt(inputContent)}"
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
                    if (code == 0) {
//验证码验证通过，提交数据到接口
                        alterPhone()

                    } else {
                        //验证失败
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }

                }
            })

    }

    /**
     *  @describe 执行接口，更改手机号
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/2 0002  09:22
     */
    private fun alterPhone() {
        Tools.showLoadingDialog(this)
        val urlParams = HttpParams()
        urlParams.put("newPhone", EncrypAndDecrypUtil.encrypt(et_phone))
        urlParams.put("code", EncrypAndDecrypUtil.encrypt(et_code_num))
        val paramLists = listOf(
            "newPhone${EncrypAndDecrypUtil.encrypt(et_phone)}"
            , "code${EncrypAndDecrypUtil.encrypt(et_code_num)}"
        ).toMutableList()
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.UPDATE_PHONE_URL,
            urlParams,
            paramLists,
            object : StringsCallback(
                object : OnTokenRefreshSuccessListener {
                    override fun onRefreshSuccess() {
                        Tools.closeDialog()
                        alterPhone()
                    }

                    override fun onRefreshFail(msg: String?) {
                        Tools.closeDialog()
                        toast(msg!!)
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
                        //手机号修改成功，需要重新登录
                        toast(R.string.alter_success)
                        SharedPreferenceUtil.getConfig(context).clearSharePrefernence()
                        //本地关键信息置空
                        ConstantString.accessToken = ""
                        ConstantString.refreshToken = ""
                        ConstantString.timeStamp = 0L
                        ConstantString.userPhone = ""
                        ConstantString.loginState = false
                        ConstantString.isAdmin = ""
                        ConstantString.userIdEncryption = ""
                        startActivity<LoginActivity>()
                        ActivityManager.getActivityManagerInstance().clearAllActivity()
                    } else {
                        //手机号修改失败
                        toast(msg)

                    }
                }
            })
    }
}
