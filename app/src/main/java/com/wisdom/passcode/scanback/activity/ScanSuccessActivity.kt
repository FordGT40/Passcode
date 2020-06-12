package com.wisdom.passcode.scanback.activity

import android.os.Handler
import android.os.Message
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.scanback.model.ScanBackModel
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_scan_success.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ScanSuccessActivity : BaseActivity() {


    override fun initViews() {
        setTitle(R.string.title_scan_success)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_scan_success)
        val model = intent.extras.getSerializable("data") as ScanBackModel.PushDataBean
        btn_apply_again.setOnClickListener {
            
            applyAgain(model)
        }
    }

    /**
     *  @describe 再次发起申请
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/10 0010  15:09
     */
    private fun applyAgain(model: ScanBackModel.PushDataBean) {
        val params = HttpParams()
        val listParams = ArrayList<String>().toMutableList()
        with(model) {
            params.put("logId", logId)
            params.put("pushType", pushType)
            params.put("placeCodeId", placeCodeId)
            params.put("placeCodeName", placeCodeName)
            params.put("userName", userName)
            params.put("userId", userId)
            params.put("adminName", adminName)
            params.put("type", type)
            params.put("remarks", remarks)
            params.put("visitorsUserId", visitorsUserId)
            params.put("visitorsUserName", visitorsUserName)
            params.put("visitorsUserPhone", visitorsUserPhone)
            params.put("visitorsUserDept", visitorsUserDept)

            listParams.add("logId$logId")
            listParams.add("pushType$pushType")
            listParams.add("placeCodeId$placeCodeId")
            listParams.add("placeCodeName$placeCodeName")
            listParams.add("userName$userName")
            listParams.add("userId$userId")
            //TODO
            if (adminName!=null) {
                listParams.add("adminName$adminName")
            }
            listParams.add("type$type")
            listParams.add("remarks$remarks")
            listParams.add("visitorsUserId$visitorsUserId")
            listParams.add("visitorsUserName$visitorsUserName")
            listParams.add("visitorsUserPhone$visitorsUserPhone")
            listParams.add("visitorsUserDept$visitorsUserDept")
        }
        //访问接口重新发送数据
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.SCAN_PUSHAGAIN_URL,
            params,
            listParams,
            object : StringsCallback(
                object : OnTokenRefreshSuccessListener {
                    override fun onRefreshSuccess() {
                        applyAgain(model)
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
                    val code = jsonObject!!.optInt("code")
                    val msg = jsonObject!!.optString("msg")
                    if (code == 0) {
startTimer()
                        toast(R.string.apply_again_success)
                    } else {
                        toast(msg)
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
                        R.string.btn_reRendValidate_apply
                    ),
                    MAX_TIME--
                )
                btn_apply_again.setText(value)
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
        btn_apply_again.isClickable = false
        btn_apply_again.setBackgroundResource(R.drawable.shape_grey_btn)
        val value = java.lang.String.format(
            resources.getString(R.string.btn_reRendValidate_apply),
            MAX_TIME
        )
        btn_apply_again.text = value
        if (timer == null) timer = Timer(true)
        if (timerTask != null) timerTask!!.cancel()
        timerTask = MyTimerTask()
        timer!!.schedule(timerTask, 1000, 1000)
    }

    /**
     * 停止获取验证码按钮倒计时
     */
    private fun stopTimer() {
        btn_apply_again.isClickable = true
        btn_apply_again.setBackgroundResource(R.drawable.shape_circle_conner_blue_deep)
        btn_apply_again.text = getString(R.string.apply_again)
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
