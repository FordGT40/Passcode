package com.wisdom.passcode.mine.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.RegularUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.toast
import org.json.JSONObject

class RegisterActivity : BaseActivity(), View.OnClickListener {
    override fun initViews() {
        setTitle(R.string.title_register)
        iv_back.setOnClickListener { finish() }
        //监听文本框的输入
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (RegularUtil.isPhoneNumCorrect(s.toString())) {
                    doBtnActive(true)
                } else {
                    doBtnActive(false)
                }
                if (s.toString().length == 11 && !RegularUtil.isPhoneNumCorrect(s.toString())) {
                    toast(R.string.phone_wrong)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        //注册按钮点击事件
        btn_register.setOnClickListener(this)

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_register)
    }

    /**
     *  @describe 设置按钮是否为激活状态
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/12 0012  14:26
     */
    fun doBtnActive(isActive: Boolean) {
        if (isActive) {
            btn_register.isClickable = true
            btn_register.backgroundDrawable =
                context.getDrawable(R.drawable.shape_circle_conner_blue_deep)
        } else {
            btn_register.isClickable = true
            btn_register.backgroundDrawable =
                context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                //注册按钮
                if (et_phone.text.toString().length < 11 || !RegularUtil.isPhoneNumCorrect(et_phone)) {
                    toast(R.string.phone_wrong)
                } else {
                    sendSms("${et_phone.text}")
                }

            }
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
                        val intent =
                            Intent(this@RegisterActivity, VerificationCodeActivity::class.java)
                        intent.putExtra("data", phoneNum)
                        startActivity(intent)
                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }
                }

            })
    }
}
