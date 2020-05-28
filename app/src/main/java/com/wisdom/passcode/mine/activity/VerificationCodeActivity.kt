package com.wisdom.passcode.mine.activity

import android.content.Intent
import android.os.Handler
import android.os.Message
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.widght.SecurityCodeView
import kotlinx.android.synthetic.main.activity_verification_code.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*

class VerificationCodeActivity : BaseActivity(), SecurityCodeView.InputCompleteListener {
    private var phone = ""

    override fun initViews() {
        iv_back.setOnClickListener { finish() }
        tv_no_code.setOnClickListener {
//            收不到验证码
            startActivity<RegisterCannotReceiveCodeActivity>()
        }
        //重新发送验证码
        btn_sen_code.setOnClickListener {
            sendSms(phone)
        }
        edit_security_code.setInputCompleteListener(this)

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_verification_code)
        //取得电话号码
        phone = intent.getStringExtra("data")
        //设置页面提示文字
        tv_phone_hint.text = "${getString(R.string.code_data)}$phone"
        startTimer()
    }

    override fun inputComplete(inputContent: String) {
        //当验证码输入完毕之后的回调
        checkSmsCode(inputContent)

    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    /**
     *  @describe 访问接口验证验证码的有效性
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  09:50
     */
    private fun checkSmsCode(inputContent: String) {
        val paramas = HttpParams()
        paramas.put("phone", EncrypAndDecrypUtil.encrypt(phone))
        paramas.put("type", ConstantString.REGISTER_TYPE)
        paramas.put("code", EncrypAndDecrypUtil.encrypt(inputContent))
        val paramArray = listOf(
            "phone${EncrypAndDecrypUtil.encrypt(phone)}"
            , "type${ConstantString.REGISTER_TYPE}",
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
                        //验证成功
                        val intent = Intent(this@VerificationCodeActivity,
                            RegisterPswInputActivity::class.java
                        )
                        intent.putExtra("data",phone)
                        intent.putExtra("code",inputContent)
                        startActivity(intent)
                    } else {
                        //验证失败
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }

                }
            })

    }


    override fun deleteContent(isDelete: Boolean) {
//       当验证码内容被删除时候回调
    }

    //-------------------------------------------------计时器相关操作-------------------------------------------------
    private var timerTask: MyTimerTask? = null
    private var timer: Timer? = null
    private var MAX_TIME = 60
    private val handler: Handler = object : Handler() {
        override fun dispatchMessage(msg: Message) {
            super.dispatchMessage(msg)
            if (MAX_TIME > 0 && msg.what == 1) {
                val value = java.lang.String.format(
                    resources.getString(
                        R.string.btn_reRendValidate
                    ),
                    MAX_TIME--
                )
                btn_sen_code.setText(value)
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
        btn_sen_code.isClickable = false
        val value = java.lang.String.format(
            resources.getString(R.string.btn_reRendValidate),
            MAX_TIME
        )
        btn_sen_code.text = value
        if (timer == null) timer = Timer(true)
        if (timerTask != null) timerTask!!.cancel()
        timerTask = MyTimerTask()
        timer!!.schedule(timerTask, 1000, 1000)
    }

    /**
     * 停止获取验证码按钮倒计时
     */
    private fun stopTimer() {
        btn_sen_code.isClickable = true
        btn_sen_code.text = "获取验证码"
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
     *  @describe 发送验证码接口
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  10:56
     */
    private fun sendSms(phoneNum: String) {
        Tools.showLoadingDialog(this)
        val params = HttpParams()
        params.put("phone", EncrypAndDecrypUtil.encrypt(phoneNum))
        params.put("type", ConstantString.REGISTER_TYPE)
        val paramsList = listOf(
            "type${ConstantString.REGISTER_TYPE}"
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
                    if (code == 0) {
                        //验证码发送成功，进入等待输入验证码页面
                        toast(R.string.sms_send_success)
                        startTimer()
                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }
                }

            })
    }
}
