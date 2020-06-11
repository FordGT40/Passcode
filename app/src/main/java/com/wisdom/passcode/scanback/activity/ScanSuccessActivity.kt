package com.wisdom.passcode.scanback.activity

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
                        toast(R.string.apply_again_success)
                    } else {
                        toast(msg)
                    }
                }
            })

    }
}
