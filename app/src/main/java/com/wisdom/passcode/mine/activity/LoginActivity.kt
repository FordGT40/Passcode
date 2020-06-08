package com.wisdom.passcode.mine.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Message
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.ActivityManager
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.helper.Helper
import com.wisdom.passcode.homepage.activity.MainActivity
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.PhoneInfoUtil
import com.wisdom.passcode.util.RegularUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*


class LoginActivity : BaseActivity(), View.OnClickListener {
    private val tabString = arrayOf(
        R.string.tab_username_login, R.string.tab_code_login
    )
    var selection = "1"//标识当前选中的选项卡是哪个，默认是1，左侧选项卡
    var isNewUser = false//是否是新用户，根据这个值，控制点击登录按钮的跳转

    override fun initViews() {
        setTitle(R.string.title_login)
        //页面内点击事件
        tv_register.setOnClickListener(this)
        tv_find_psw.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        tv_send_code.setOnClickListener(this)
        //设置首非第一次启动标识
        SharedPreferenceUtil.getConfig(this).sharedPreferences.edit().apply {
            putString(ConstantString.IS_FIRST_LOAD, ConstantString.IS_FIRST_LOAD)
            apply()
        }


        //初始化上选项卡
        for (strId in tabString) {
            mEnhanceTabLayout.addTab(getString(strId))
        }
        //过滤掉上半部分选项卡点击后的背景效果
        mEnhanceTabLayout.tabLayout.tabRippleColor =
            ColorStateList.valueOf(Color.parseColor("#00ffffff"))

        //监听选项卡的选择事件
        mEnhanceTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {


            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        //密码登录
                        rl_code.visibility = View.GONE
                        rl_psw.visibility = View.VISIBLE
                        et_code.setText("")
                        selection = "1"
                    }
                    1 -> {
                        //免密登录
                        rl_code.visibility = View.VISIBLE
                        rl_psw.visibility = View.GONE
                        et_psw.setText("")
                        selection = "2"
                    }
                }
            }
        })
        //监听密码框设置登录按钮是否可以点击
        et_psw.addTextChangedListener(
            Helper.getPhoneWatcher(
                this,
                et_phone,
                et_psw,
                et_code,
                btn_login,
                1
            )
        )
        //监听验证码框设置登录按钮是否可以点击
        et_code.addTextChangedListener(
            Helper.getPhoneWatcher(
                this,
                et_phone,
                et_psw,
                et_code,
                btn_login,
                2
            )
        )
        //监听电话号框设置登录按钮是否可以点击
        et_phone.addTextChangedListener(
            Helper.getPhoneWatcher(
                this,
                et_phone,
                et_psw,
                et_code,
                btn_login,
                0
            )
        )
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
    }


    override fun setlayoutIds() {
        setContentView(R.layout.activity_login)
    }


    /**
     *  @describe 页面内的点击事件
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/12 0012  11:24
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
//                返回键
                finish()
            }
            R.id.btn_login -> {
                //登录按钮点击事件,核验二维码准确性
                if (selection == "1") {
                    loginSys()
                } else {
                    checkSmsCode(et_code.text.toString())
                }
            }
            R.id.tv_find_psw -> {
                //忘记密码页面
                startActivity<ResetPswActivity>()
            }
            R.id.tv_register -> {
                //注册页面
                startActivity<RegisterActivity>()
            }
            R.id.tv_send_code -> {
                //发送验证码
                if (RegularUtil.isPhoneNumCorrect(et_phone)) {
                    //电话号码正确
                    sendSms(et_phone.text.toString(), ConstantString.LOGIN_TYPE)
                } else {
                    toast(R.string.hint14)
                }
            }
        }
    }


    /**
     *  @describe 登录系统的操作
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  14:41
     */
    private fun loginSys() {
        Tools.showLoadingDialog(this)
        val phone = EncrypAndDecrypUtil.encrypt(et_phone.text.toString())
        val params = HttpParams()
        var listParams: MutableList<String>
        var password = ""
        var code = ""


        if (selection == "1") {
            //密码登录
            password = EncrypAndDecrypUtil.encrypt(et_psw.text.toString())
            listParams = listOf("phone$phone", "password$password").toMutableList()
            params.put("password", password)
            params.put("phone", phone)
        } else {
            //验证码登录
            code = EncrypAndDecrypUtil.encrypt(et_code.text.toString())
            listParams = listOf("phone$phone", "code$code").toMutableList()
            params.put("code", code)
            params.put("phone", phone)
        }
        params.put("diviceOs", "Android${PhoneInfoUtil.getSystemVersion()}")
        params.put("diviceName", PhoneInfoUtil.getDeviceManufacturer())
        params.put("diviceType", PhoneInfoUtil.getDeviceBrand())
        params.put("diviceId", PhoneInfoUtil.getFingerPrint())

        listParams.add("diviceOsAndroid${PhoneInfoUtil.getSystemVersion()}")
        listParams.add("diviceName${PhoneInfoUtil.getDeviceManufacturer()}")
        listParams.add("diviceType${PhoneInfoUtil.getDeviceBrand()}")
        listParams.add("diviceId${PhoneInfoUtil.getFingerPrint()}")




        //访问网络执行登录操作
        HttpUtil.httpPostWithStampAndSign(
            ConstantUrl.LOGIN_URL,
            params,
            listParams.toMutableList(),
            object : StringCallback() {
                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                    Tools.closeDialog()
                }


                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    Tools.closeDialog()
                    val jsonObject = JSONObject(t)
                    val codeBack = jsonObject.optInt("code")
                    if (codeBack == 0) {
                        toast(R.string.login_success)
                        //登录成功,获取token
                        val jsonData = jsonObject.optJSONObject("data")
                        val accessToken = jsonData.optString("accessToken")
                        val refreshToken = jsonData.optString("refreshToken")
                        val userIdEncryption = jsonData.optString("userIdEncryption")
                        val isAdmin = jsonData.optString("isAdmin")
                        ConstantString.loginState = true
                        //token持久化
                        SharedPreferenceUtil.getConfig(this@LoginActivity).sharedPreferences.edit()
                            .apply {
                                putString("accessToken", accessToken)
                                putString("refreshToken", refreshToken)
                                putString("phone", et_phone.text.toString())
                                putString("userIdEncryption", userIdEncryption)
                                putString("isAdmin", isAdmin)
                                apply()
                            }
                        //sp数据本地化
                        Helper.getInfoFromSpFile(this@LoginActivity)
                        //同步一下个人信息
                        Helper.syncPersonalInfo(this@LoginActivity,
                            object : Helper.OnPersonalInfoCompletedListener {
                                override fun onPersonalInfoCompleted() {
                                    //打开主页
                                    startActivity<MainActivity>()
                                    ActivityManager.getActivityManagerInstance().clearAllActivity()
                                }
                            })

                    } else {
                        //登录失败
                        toast(HttpUtil.getErrorMsgByCode("$codeBack"))
                    }
                }
            })

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
                        203 -> {
                            //新用户，发送注册验证码
                            sendSms(phoneNum, ConstantString.REGISTER_TYPE)
                            btn_login.text = getString(R.string.title_register)
                            isNewUser = true

                        }
                        else -> {
                            toast(HttpUtil.getErrorMsgByCode("$code"))
                        }
                    }
                }

            })
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
        var type = ConstantString.LOGIN_TYPE
        if (isNewUser) {
            type = ConstantString.REGISTER_TYPE
        }
        val paramas = HttpParams()
        paramas.put("phone", EncrypAndDecrypUtil.encrypt(et_phone.text.toString()))
        paramas.put("type", type)
        paramas.put("code", EncrypAndDecrypUtil.encrypt(inputContent))
        val paramArray = listOf(
            "phone${EncrypAndDecrypUtil.encrypt(et_phone.text.toString())}"
            , "type${type}",
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
                        //TODO 验证成功
                        if (isNewUser) {
                            startActivity<RegisterPswInputActivity>(
                                "code" to et_code.text.toString()
                                , "data" to et_phone.text.toString()
                            )
                        } else {
                            loginSys()
                        }
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
