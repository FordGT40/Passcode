package com.wisdom.passcode.faceId

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.faceId.faceAli.FaceDetectionActivity
import com.wisdom.passcode.helper.Helper
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import kotlinx.android.synthetic.main.activity_face_id_verify_code.*
import kotlinx.android.synthetic.main.activity_face_id_verify_code.iv_back
import kotlinx.android.synthetic.main.activity_login.tv_send_code
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*

class FaceIdVerifyCodeActivity : BaseActivity() {


    @SuppressLint("SetTextI18n")
    override fun initViews() {
        val name = intent.getStringExtra("name")
        val idCard = intent.getStringExtra("idCard")
        val phone = SharedPreferenceUtil.getConfig(this).sharedPreferences.getString("phone", "")
        tv_phone_hint.visibility = View.GONE
        tv_phone_hint.text = "${getString(R.string.code_data)}$phone"
        //发送验证码点击事件
        tv_send_code.setOnClickListener {
            if (phone.isNotEmpty()) {
//            发送验证码
                sendSms(phone)
            } else {
                toast(R.string.get_user_phone_fail)
            }
        }
        //返回
        iv_back.setOnClickListener { finish() }
        //验证码输入框内容监听
        et_code.addTextChangedListener(Helper.getCodeWatcher(this, btn_next))
        //下一步点击事件（验证验证码准确性）
        btn_next.setOnClickListener {
            if (et_code.text.toString().isNotEmpty()) {
                checkSmsCode(et_code.text.toString(), idCard, name)
            } else {
                toast(R.string.code_check_empty)
            }
        }
        btn_next.isClickable = false
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_face_id_verify_code)
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
        params.put("type", ConstantString.FACE_TYPE)
        val paramsList = listOf(
            "type${ConstantString.FACE_TYPE}"
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
                        tv_phone_hint.visibility = View.VISIBLE
                        //验证码发送成功，进入等待输入验证码页面
                        toast(R.string.sms_send_success)
                        startTimer()
                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }
                }

            })
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
        tv_send_code.text=getString(R.string.send_code)
    }


    /**
     *  @describe 访问接口验证验证码的有效性
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  09:50
     */
    private fun checkSmsCode(inputContent: String, idCard: String, name: String) {
        val paramas = HttpParams()
        paramas.put("phone", EncrypAndDecrypUtil.encrypt(ConstantString.userPhone))
        paramas.put("type", ConstantString.FACE_TYPE)
        paramas.put("code", EncrypAndDecrypUtil.encrypt(inputContent))
        val paramArray = listOf(
            "phone${EncrypAndDecrypUtil.encrypt(ConstantString.userPhone)}"
            , "type${ConstantString.FACE_TYPE}",
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
                        // 打开扫脸界面
                        val intent =
                            Intent(this@FaceIdVerifyCodeActivity, FaceDetectionActivity::class.java)
                        intent.putExtra("code", et_code.text.toString())
                        intent.putExtra("idCard", idCard)
                        intent.putExtra("realName", name)
                        startActivity(intent)

                    } else {
                        //验证失败
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }

                }
            })

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
        tv_send_code.setBackgroundResource(R.drawable.shape_yellow_btn)
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
