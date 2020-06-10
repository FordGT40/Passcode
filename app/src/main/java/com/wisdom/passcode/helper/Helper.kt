package com.wisdom.passcode.helper

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.model.LocationModel
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.faceId.FaceIdInputNameActivity
import com.wisdom.passcode.mine.model.PersonalInfoModel
import com.wisdom.passcode.util.*
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.backgroundDrawable
import org.json.JSONObject


/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.helper
 * @class describe：
 * @author HanXueFeng
 * @time 2020/5/12 0012 13:24
 * @change
 */
class Helper {
    companion object {

        /**
         *  @describe 登录页面控件输进入容的监听方法
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/12 0012  13:27
         */
        fun getPhoneWatcher(
            context: Context,
            et_phone: EditText,
            et_psw: EditText,
            et_code: EditText,
            btn_login: Button,
            type: Int
        ): TextWatcher {
            return object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    when (type) {
                        0 -> {
                            //电话框
                            if (RegularUtil.isPhoneNumCorrect(s.toString())) {
                                if (et_code.text.toString() != "" || et_psw.text.toString() != "") {
                                    btn_login.isClickable = true
                                    btn_login.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_blue_deep)
                                } else {
                                    btn_login.isClickable = false
                                    btn_login.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                                }
                            } else {
                                if (s.toString().length == 11) {
                                    ToastUtil.showToast(R.string.phone_wrong)
                                }
                                btn_login.isClickable = false
                                btn_login.backgroundDrawable =
                                    context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                            }
                        }
                        2 -> {
                            // 验证码框
                            if (et_code.text.toString() != "") {
                                if (s.toString().length in 4..6) {
                                    btn_login.isClickable = true
                                    btn_login.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_blue_deep)
                                } else {
                                    btn_login.isClickable = false
                                    btn_login.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                                }
                            } else {
                                btn_login.isClickable = false
                                btn_login.backgroundDrawable =
                                    context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                            }
                        }
                        1 -> {
                            //密码框
                            if (et_phone.text.toString() != "") {
                                if (s.toString().length in 6..16) {
                                    btn_login.isClickable = true
                                    btn_login.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_blue_deep)
                                } else {
                                    btn_login.isClickable = false
                                    btn_login.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                                }
                            } else {
                                btn_login.isClickable = false
                                btn_login.backgroundDrawable =
                                    context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                            }
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
        }

        /**
         *  @describe 控制实名制页面输入框
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/19 0019  14:15
         */
        fun getIdCardInputWatcher(
            context: Context,
            et_name: EditText,
            et_id_card: EditText,
            btn_next: Button,
            type: Int
        ): TextWatcher {
            return object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    when (type) {
                        0 -> {
                            //姓名框
                            if (s.toString().isNotEmpty()) {
                                if (RegularUtil.isValidate18Idcard(et_id_card.text.toString())) {
                                    btn_next.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_blue_deep)
                                    btn_next.isClickable = true
                                } else {
                                    btn_next.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                                    btn_next.isClickable = false
                                }
                            } else {
                                btn_next.backgroundDrawable =
                                    context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                                btn_next.isClickable = false
                            }
                        }
                        1 -> {
                            //身份证框
                            if (RegularUtil.isValidate18Idcard(s.toString())) {
                                if (et_name.text.isNotEmpty()) {
                                    btn_next.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_blue_deep)
                                    btn_next.isClickable = true
                                } else {
                                    btn_next.backgroundDrawable =
                                        context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                                    btn_next.isClickable = false
                                }
                            } else {
                                btn_next.backgroundDrawable =
                                    context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                                btn_next.isClickable = false
                            }
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            }

        }


        /**
         *  @describe 从SP文件本地化信息
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/19 0019  14:22
         */
        fun getInfoFromSpFile(context: Context) {
            SharedPreferenceUtil.getConfig(context).sharedPreferences.apply {
                ConstantString.accessToken = getString("accessToken", "")!!
                ConstantString.timeStamp = getLong("timeStamp", 0L)
                ConstantString.userPhone = getString("phone", "")!!
                ConstantString.refreshToken = getString("refreshToken", "")!!
                ConstantString.isAdmin = getString("isAdmin", "")!!
                ConstantString.userIdEncryption = getString("userIdEncryption", "")!!
            }
        }

        /**
         *  @describe 验证码框的内容监听
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/19 0019  15:07
         */
        fun getCodeWatcher(context: Context, btn_next: Button): TextWatcher {
            return object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().length in 4..8) {
                        btn_next.backgroundDrawable =
                            context.getDrawable(R.drawable.shape_circle_conner_blue_deep)
                        btn_next.isClickable = true
                    } else {
                        btn_next.backgroundDrawable =
                            context.getDrawable(R.drawable.shape_circle_conner_grey_btn)
                        btn_next.isClickable = false
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
        }

        /**
         *  @describe 从接口获得用户信息，并本地化
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/20 0020  14:36
         */
        fun syncPersonalInfo(context: Context, listener: OnPersonalInfoCompletedListener) {
            val params = HttpParams()
            val listSign = ArrayList<String>()
            Tools.showLoadingDialog(context)
            HttpUtil.httpPostWithStampAndSignToken(
                ConstantUrl.USER_INFO_URL,
                params,
                listSign.toMutableList(),
                object : StringsCallback(object : OnTokenRefreshSuccessListener {
                    override fun onRefreshSuccess() {
//                        token刷新成功，重新回调自己方法
                        Tools.closeDialog()
                        syncPersonalInfo(context, listener)
                    }

                    override fun onRefreshFail(msg: String?) {
                        Tools.closeDialog()
                        LogUtil.getInstance().e(msg)
                    }
                }) {
                    override fun onInterfaceSuccess(
                        jsonObject: JSONObject?,
                        call: Call?,
                        response: Response?
                    ) {
                        Tools.closeDialog()
                        val json = jsonObject
                        val code = json!!.optInt("code")
                        if (code == 0) {
                            //成功获得数据
                            val jsonString = json.optString("data")
//                        val listData = Gson().fromJson<Any>(jsonArray.toString(),
//                            object : TypeToken<List<GuessYourAttentionModel>>() {}.type) as List<GuessYourAttentionModel>
                            val model = Gson().fromJson(jsonString, PersonalInfoModel::class.java)
                            SharedPreferenceUtil.setPersonalInfoModel(context, model)
                            listener.onPersonalInfoCompleted()
                        } else {
                            ToastUtil.showToast(HttpUtil.getErrorMsgByCode("$code"))
                        }
                    }
                })
        }

        /**
         *  @describe 检查用户的授权状态
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/20 0020  16:13
         */
        fun checkUserState(context: Context?): Boolean {
            var isChecked = false
            val userState = SharedPreferenceUtil.getPersonalInfoModel(context).authState
            if (userState == ConstantString.USER_STATE_TRUE) {
                //用户已实名
                isChecked = true
            } else {
                //用户未实名
                isChecked = false
                AlertUtil.showCustomAlert(context
                    , context?.getString(R.string.go_auth_check)
                    , context?.getString(R.string.cancle)
                    , context?.getString(R.string.hint17)
                    , DialogInterface.OnClickListener { _, _ ->
                        val intent = Intent(context, FaceIdInputNameActivity::class.java)
                        context?.startActivity(intent)
                    }
                    , null)
            }
            return isChecked
        }


        /**
         *  @describe 获得地市信息
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/25 0025  11:21
         */
        fun getLocationData(id: String) {
            val paramas = HttpParams()
            paramas.put("pid", id)
            val listParams = listOf("pid$id").toMutableList()
            HttpUtil.httpPostWithStampAndSignToken(
                ConstantUrl.SELECT_LOCATION_URL,
                paramas,
                listParams,
                object : StringsCallback(
                    object : OnTokenRefreshSuccessListener {
                        override fun onRefreshSuccess() {
                            getLocationData(id)

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
                        val code = jsonObject?.optInt("code")
                        if (code == 0) {
                            val data = jsonObject.optString("data")
                            val locationModelList = Gson().fromJson<List<LocationModel>>(
                                data,
                                object : TypeToken<List<LocationModel>>() {}.type
                            )
                        } else {
                            HttpUtil.getErrorMsgByCode("$code")
                        }
                    }
                })

        }


        /**
         * 将channelId传递到后台。
         * @param channelId
         */
        fun setChannelId(channelId: String) {
            val params = HttpParams()
            params.put("channelId", channelId)
            params.put("channelType", "1")
            val paramsList = listOf("channelId$channelId", "channelType1").toMutableList()
            HttpUtil.httpPostWithStampAndSignToken(
                ConstantUrl.USER_SETPUSHINFO_URL,
                params,
                paramsList,
                object : StringsCallback(object : OnTokenRefreshSuccessListener {
                    override fun onRefreshSuccess() {
                        setChannelId(channelId)
                    }

                    override fun onRefreshFail(msg: String?) {
                        LogUtil.getInstance().e(msg)
                    }
                }) {
                    override fun onInterfaceSuccess(
                        jsonObject: JSONObject?,
                        call: Call?,
                        response: Response?
                    ) {
                        val code = jsonObject!!.optInt("code")
                        val msg = jsonObject!!.optString("msg")
                        LogUtil.getInstance().e("绑定channelId：$code")
                        LogUtil.getInstance().e("绑定channelId：$msg")
                    }
                })
        }

        fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
            if (v.layoutParams is MarginLayoutParams) {
                val p = v.layoutParams as MarginLayoutParams
                p.setMargins(l, t, r, b)
                v.requestLayout()
            }
        }

        /**
         *  @describe 监测页面的滚动情况
         *  @return
         *  @author HanXueFeng
         *  @time 2020/6/10 0010  09:50
         */
        fun detectPageScroll(context: Context, view: View, listener: OnScrollPageListener) {
            val gesDetector = GestureDetector(context, GestureSmoothDetector(listener))
            view.setOnTouchListener { _, event ->
                gesDetector.onTouchEvent(event)
                true
            }
        }

    }


    interface OnPersonalInfoCompletedListener {
        fun onPersonalInfoCompleted()
    }

    interface OnScrollPageListener {
        fun onPageUp()
        fun onPageDown()
        fun onOthers()
    }

}