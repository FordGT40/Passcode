package com.wisdom.passcode.homepage.activity

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.helper.Helper
import com.wisdom.passcode.mine.activity.LoginActivity
import com.wisdom.passcode.util.httpUtil.HttpUtil
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*

class SplashActivity : BaseActivity() {
    private var timeStampFinish = false
    val handler = Handler(Looper.getMainLooper())

    //用来判断是不是登陆过，首次下载 跳转到“立即体验”页面；非首次下载，根据登录状态进行跳转
    private val startMain = Runnable {
        kotlin.run {
            val isFirstLoad = SharedPreferenceUtil.getConfig(this).sharedPreferences.getString(
                ConstantString.IS_FIRST_LOAD,
                ""
            )
            if (isFirstLoad == null || isFirstLoad.isEmpty()) {
                //第一次打开app
                startActivity<WelcomeActivity>()
                finish()
            } else {
                if (timeStampFinish) {
                    if (ConstantString.loginState) {
                        Helper.syncPersonalInfo(this,object:Helper.OnPersonalInfoCompletedListener{
                            override fun onPersonalInfoCompleted() {
                                startActivity<MainActivity>()
                                finish()
                            }
                        })

                    } else {
                        startActivity<LoginActivity>()
                        finish()
                    }
                } else {
                    toast(R.string.net_slow)
                }
            }
        }
    }

    override fun initViews() {

        //隐藏下半部分的虚拟按键
        val window: Window = window
        val params = window.attributes
        //点击会出现
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        //点击不会出现
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
        //沉浸式标题栏
        setNoStateBar()
        //将sp文件中的信息进行本地化操作
        Helper.getInfoFromSpFile(this)
        //判断是否免登录
        ConstantString.loginState = ConstantString.accessToken != ""
        //如果本地时间戳是空的，那么获取时间戳，否则不获取
        if (ConstantString.timeStamp == 0L) {
            getServerTimeMillis()
        } else {
            timeStampFinish = true
            //开启计时线程，判断跳转页面
            startIndexPage()
        }

    }

    /**
     *  @describe 获取系统时间戳保存到本地
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/18 0018  08:52
     */
    private fun getServerTimeMillis() {
        val param = HttpParams()
        HttpUtil.httpPost(ConstantUrl.SERVER_TIMEMILLIS_URL, param, object : StringCallback() {
            override fun onSuccess(t: String?, call: Call?, response: Response?) {
                val jsonObject = JSONObject(t)
                val code = jsonObject.optInt("code")
                if (code == 0) {
                    //获得时间戳，与本地时间戳做差，然后将差值保存在本地
                    val serverTimeStamp = jsonObject.optLong("data")
                    val sysTimeStamp = Date().time
                    ConstantString.timeStamp = sysTimeStamp - serverTimeStamp
                    //时间戳差值持久化
                    SharedPreferenceUtil.getConfig(this@SplashActivity)
                        .sharedPreferences.edit().apply {
                            putLong("timeStamp", ConstantString.timeStamp)
                            apply()
                        }
                    timeStampFinish = true
                    //开启计时线程，判断跳转页面
                    startIndexPage()
                } else {
                    timeStampFinish = false
                    toast(HttpUtil.getErrorMsgByCode("$code"))
                }
            }
        })
    }


    override fun setlayoutIds() {
        setContentView(R.layout.activity_splash)
    }

    /**
     * 开始进行倒计时，打开主界面
     */
    private fun startIndexPage() {
        handler.postDelayed(startMain, ConstantString.SPLASH_WAITING_SECONDS)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
