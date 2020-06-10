package com.wisdom.passcode.homepage.activity

import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.homepage.adapter.ShowCodeRecordAdapter
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.homepage.model.ShowCodeRecordModel
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView
import kotlinx.android.synthetic.main.fragment_place_card.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject

class ShowCodeRecordActivity : BaseActivity() {
    private var page = 1
    private var pageSize = 10
    private lateinit var adapter: ShowCodeRecordAdapter
    private var dataList: List<ShowCodeRecordModel> = ArrayList()


    override fun initViews() {
       setTitle(R.string.title_show_code_record)
        adapter=ShowCodeRecordAdapter(this,dataList,object :ShowCodeRecordAdapter.OnItemClickListener{
            override fun onItemClick(item: ShowCodeRecordModel) {
                //TODO 子项点击事件
            }
        })
        recyclerView.setLinearLayout()
        recyclerView.setAdapter(adapter)
        recyclerView.setOnPullLoadMoreListener(object :
            PullLoadMoreRecyclerView.PullLoadMoreListener {
            override fun onLoadMore() {
                page += 1
                getRecordsData(ConstantString.RECYCLER_PULL_LOADMORE)
            }

            override fun onRefresh() {
                page = 1
                getRecordsData(ConstantString.RECYCLER_PULL_REFRESH)
            }
        })
        getRecordsData(ConstantString.RECYCLER_PULL_REFRESH)

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_show_code_record)
    }


    /**
     *  @describe 获取“亮码记录”数据
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/2 0002  11:33
     */
    fun getRecordsData(pullFlag: String) {
//        Tools.showLoadingDialog(context)
        val params = HttpParams()
        params.put("pageSize", pageSize)
        params.put("pageNum", page)
        params.put("type", ConstantString.SHOW_CARD_RECORD_PERSON)
        val paramsList = listOf(
            "pageSize$pageSize",
            "pageNum$page",
            "type${ConstantString.SHOW_CARD_RECORD_PERSON}"
        ).toMutableList()
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.RECORD_SHOW_URL,
            params,
            paramsList,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    Tools.closeDialog()
                    getRecordsData(pullFlag)
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
                        //访问成功，封装数据源
                        val jsonStr = jsonObject.optJSONObject("data").optString("list")
                        val data = Gson().fromJson<List<ShowCodeRecordModel>>(jsonStr, object :
                            TypeToken<List<ShowCodeRecordModel>>() {}.type)

                        if (!data.isNullOrEmpty()) {
                            tv_nodata.visibility = View.GONE
                            if (pullFlag == ConstantString.RECYCLER_PULL_REFRESH) {
                                //刷新
                                recyclerView.setPullLoadMoreCompleted()
                                adapter.refreshData(data)
                            } else {
                                //加载更多
                                recyclerView.setPullLoadMoreCompleted()
                                adapter.loadMoreData(data)
                            }
                        } else {
                            recyclerView.setPullLoadMoreCompleted()
                            if (pullFlag == ConstantString.RECYCLER_PULL_LOADMORE) {
                                toast(R.string.no_more_data)
                            } else {
                                tv_nodata.visibility = View.VISIBLE
                            }
                        }
                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }

                }
            })
    }
}
