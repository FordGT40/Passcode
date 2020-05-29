package com.wisdom.passcode.apply.activity

import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.adapter.CardTypeListAdapter
import com.wisdom.passcode.apply.model.CardTypeListModel
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.LogUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView
import kotlinx.android.synthetic.main.activity_card_type_choose.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject

class CardTypeChooseActivity : BaseActivity() {
    var cardType = ""
    var placeCode = ""
    var dataList: List<CardTypeListModel> = ArrayList()
    lateinit var adapter: CardTypeListAdapter

    override fun initViews() {
        setTitle(R.string.title_card_type_choose)
        cardType = intent.getStringExtra("type")
        placeCode = intent.getStringExtra("placeCode")
        recyclerView.setLinearLayout()
        recyclerView.pushRefreshEnable = false
        adapter =
            CardTypeListAdapter(this, dataList, object : CardTypeListAdapter.OnItemClickListener {
                override fun onItemClick(item: CardTypeListModel) {
                    val intentBack = Intent()
                    intentBack.putExtra("lable", item.lable)
                    intentBack.putExtra("name", item.name)
                    intentBack.putExtra("ids", item.id)
                    setResult(ConstantString.RESULT_CODE_CHOOSE_CARD_TYPE,intentBack)
                    this@CardTypeChooseActivity.finish()
                }
            })
        recyclerView.setOnPullLoadMoreListener(object :
            PullLoadMoreRecyclerView.PullLoadMoreListener {
            override fun onLoadMore() {
            }

            override fun onRefresh() {
                getPlaceCardTypeList(cardType, placeCode)
            }
        })
        recyclerView.setAdapter(adapter)
        getPlaceCardTypeList(cardType, placeCode)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_card_type_choose)
    }


    /**
     *  @describe 取得某个场所下可申请的通行证类型
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/28 0028  14:52
     */
    fun getPlaceCardTypeList(cardType: String, placeCode: String) {
        Tools.showLoadingDialog(this)
        val params = HttpParams()
        params.put("placeIdEncryption", placeCode)
        params.put("type", cardType)
        val paramsList = listOf("placeIdEncryption$placeCode", "type$cardType").toMutableList()
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.CODETYPE_SEARCH_URL,
            params,
            paramsList,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    Tools.closeDialog()
                    recyclerView.setPullLoadMoreCompleted()
                    getPlaceCardTypeList(cardType, placeCode)
                }

                override fun onRefreshFail(msg: String?) {
                    Tools.closeDialog()
                    recyclerView.setPullLoadMoreCompleted()
                }
            }) {
                override fun onInterfaceSuccess(
                    jsonObject: JSONObject,
                    call: Call?,
                    response: Response?
                ) {
                    Tools.closeDialog()
                    recyclerView.setPullLoadMoreCompleted()
                    val code = jsonObject?.optInt("code")
                    if (code == 0) {
                        //访问获取数据成功
                        val listData = Gson().fromJson<Any>(
                            jsonObject.optString("data"),
                            object :
                                TypeToken<List<CardTypeListModel>>() {}.type
                        ) as List<CardTypeListModel>
                        if (listData.isNullOrEmpty()) {
                            //没有相关数据
                            ll_nodata.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        } else {
                            //找到相关数据
                            ll_nodata.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            dataList = listData
                            adapter.refreshData(listData)
                        }
                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }
                }
            })

    }


}
