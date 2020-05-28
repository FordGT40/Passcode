package com.wisdom.passcode.apply.activity

import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.adapter.PlaceNameSearchListAdapter
import com.wisdom.passcode.apply.model.PlaceSearchListModel
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_car_access_certificate_search.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject

class PlaceNameSearchActivity : BaseActivity(), View.OnClickListener {


    override fun initViews() {
        setTitle(R.string.choose_place_name)
        btn_search.setOnClickListener(this)
        //取消框架的刷新功能
        recyclerView.setLinearLayout()
        recyclerView.pushRefreshEnable = false
        recyclerView.pullRefreshEnable = false
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_car_access_certificate_search)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_search -> {
                //搜索按钮点击事件
                if (et_content.text.toString().isNullOrEmpty()) {
                    //提示请输入要搜索的内容
                    toast(R.string.hint38)
                } else {
                    getPlaceData(et_content.text.toString())
                }
            }
        }
    }

    /**
     *  @describe 根据输入文字获取场所信息
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/28 0028  13:30
     */
    fun getPlaceData(placeName: String) {
        Tools.showLoadingDialog(this)
        val params = HttpParams()
        params.put("name", placeName)
        val paramsList = listOf("name$placeName").toMutableList()
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.PLACE_SEARCH_URL,
            params,
            paramsList,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    Tools.closeDialog()
                    getPlaceData(placeName)
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
                    if (code == 0) {
                        //得到成功数据
                        val list = Gson().fromJson<Any>(
                            jsonObject.optString("data"),
                            object : TypeToken<List<PlaceSearchListModel>>() {}.type
                        ) as List<PlaceSearchListModel>
                        if (list.isNullOrEmpty()) {
                            //没有数据,显示没有数据布局
                            ll_nodata.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE

                        } else {
                            //有数据l
                            ll_nodata.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            val adapter = PlaceNameSearchListAdapter(
                                this@PlaceNameSearchActivity,
                                list,
                                object : PlaceNameSearchListAdapter.OnItemClickListener {
                                    override fun onItemClick(item: PlaceSearchListModel) {
                                        //点击某个子项进行回显操作
                                        val intentBack = Intent()
                                        intentBack.putExtra("code", item.placeIdEncryption)
                                        intentBack.putExtra("name", item.name)
                                        setResult(ConstantString.RESULT_CODE_CHOOSE_PLACE, intentBack)
                                        this@PlaceNameSearchActivity.finish()
                                    }
                                })
                            recyclerView.setAdapter(adapter)
                        }
                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }
                }
            })

    }
}
