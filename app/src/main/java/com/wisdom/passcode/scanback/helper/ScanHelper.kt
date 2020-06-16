package com.wisdom.passcode.scanback.helper

import android.content.Context
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.scanback.activity.ScanBackMainActivity
import com.wisdom.passcode.scanback.model.GetScanResultModel
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.scanback.helper
 * @class describe：
 * @author HanXueFeng
 * @time 2020/6/1 0001 14:13
 * @change
 */
class ScanHelper {
    companion object {
        /**
         *  @describe 扫码后解析扫码场所信息
         *  @return
         *  @author HanXueFeng
         *  @time 2020/6/1 0001  14:15
         */
        fun getScanPlaceName(context: Context, model: GetScanResultModel) {
            Tools.showLoadingDialog(context)
            val params = HttpParams()
            params.put("type", model.type)
            params.put("placeCodeEncryption", model.placeCodeEncryption)
            params.put("userIdEncryption", model.userIdEncryption)
            params.put("passCodeId", model.passCodeId)
            params.put("carNumber", model.carNumber)
            val listParams = listOf(
                "type${model.type}"
                , "placeCodeEncryption${model.placeCodeEncryption}"
                , "userIdEncryption${model.userIdEncryption}"
                , "passCodeId${model.passCodeId}"
                , "carNumber${model.carNumber}"
            ).toMutableList()
            HttpUtil.httpPostWithStampAndSignToken(
                ConstantUrl.SCAN_CODE_URL,
                params,
                listParams,
                object : StringsCallback(object : OnTokenRefreshSuccessListener {
                    override fun onRefreshSuccess() {
                        Tools.closeDialog()
                        getScanPlaceName(context, model)
                    }

                    override fun onRefreshFail(msg: String?) {
                        Tools.closeDialog()
                    }
                }) {
                    override fun onInterfaceSuccess(
                        jsonObject: JSONObject?,
                        call: Call?,
                        response: Response?
                    ) {
                        Tools.closeDialog()
                        val code = jsonObject!!.optInt("code")
                        val msg = jsonObject!!.optInt("msg")
                        if (code == 0) {
                            val data = jsonObject.optJSONObject("data")
                            val placeName = data.optString("placeName")
                            val placeIdEnc = data.optString("placeIdEnc")
                            //打开扫码详情页面
                            context.startActivity<ScanBackMainActivity>("data" to placeName,"placeIdEnc" to placeIdEnc,"placeCode" to model.placeCodeEncryption)
                        } else {
                            context.toast(msg)
                        }
                    }

                })
        }

    }
}