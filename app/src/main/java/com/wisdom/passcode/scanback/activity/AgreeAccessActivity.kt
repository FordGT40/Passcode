package com.wisdom.passcode.scanback.activity

import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.scanback.model.ScanBackModel
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_agree_access.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject

class AgreeAccessActivity : BaseActivity() {


    override fun initViews() {
        setTitle(R.string.title_agree_success)
        val model=intent.extras.getSerializable("data") as ScanBackModel.PushDataBean
        btn_agree.setOnClickListener {
            setAgreeInfo(true,model)
        }
        btn_unagree.setOnClickListener {
            setAgreeInfo(false,model)
        }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_agree_access)
    }


    /**
     *  @describe 设置是否同意
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/10 0010  14:43
     */
    fun setAgreeInfo(isAgree: Boolean, model: ScanBackModel.PushDataBean) {
        val params = HttpParams()
        val reason = et_reason.text.toString()
        val listParams = listOf("rejectReason$reason").toMutableList()
        with(model) {
            params.put("rejectReason", reason)
            params.put("logId", logId)
            listParams.add("logId$logId")
        }
        if (isAgree) {
            //同意
            params.put("agree", "1")
            listParams.add("agree1")

        } else {
            //不同意
            params.put("agree", "2")
            listParams.add("agree2")
        }
        Tools.showLoadingDialog(this)
        //访问接口提交数据
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.SCAN_AGREE_URL,
            params,
            listParams,
            object : StringsCallback(
                object : OnTokenRefreshSuccessListener {
                    override fun onRefreshSuccess() {
                        setAgreeInfo(isAgree, model)
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
                        //返回请求成功
                        toast("审核成功！")
                        this@AgreeAccessActivity.finish()
                    } else {
                        //返回请求失败
                        toast(msg)

                    }
                }
            })
    }


}
