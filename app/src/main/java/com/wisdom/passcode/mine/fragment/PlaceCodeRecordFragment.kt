package com.wisdom.passcode.mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.mine.adapter.PlaceCodeRecordListAdapter
import com.wisdom.passcode.mine.adapter.PlaceCodeRecordListAdapter.OnItemClickListener
import com.wisdom.passcode.mine.model.PlaceCodeRecordModel
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView

import kotlinx.android.synthetic.main.fragment_place_card.recyclerView
import kotlinx.android.synthetic.main.fragment_place_code_record.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject


class PlaceCodeRecordFragment : Fragment() {


    private var page = 1
    private var pageSize = 10
    private lateinit var adapter: PlaceCodeRecordListAdapter
    private var dataList: List<PlaceCodeRecordModel.ListBean> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_code_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlaceCodeRecordListAdapter(
            context!!,
            dataList,
           object :OnItemClickListener{
               override fun onItemClick(item: PlaceCodeRecordModel.ListBean) {
//               TODO  子项点击事件

               }
           })
        recyclerView.setLinearLayout()
        recyclerView.setAdapter(adapter)
        recyclerView.setOnPullLoadMoreListener(object :
            PullLoadMoreRecyclerView.PullLoadMoreListener {
            override fun onLoadMore() {
                page += 1
                getData(ConstantString.RECYCLER_PULL_LOADMORE)
            }

            override fun onRefresh() {
                page = 1
                getData(ConstantString.RECYCLER_PULL_REFRESH)
            }
        })
        getData(ConstantString.RECYCLER_PULL_REFRESH)
    }

    /**
     *  @describe 请求接口数据的方法
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/27 0027  11:44
     */
    fun getData(pullFlag: String) {

        val params = HttpParams()
        params.put("pageNum", page)
        params.put("pageSize", pageSize)
        val listParams = listOf("pageNum$page", "pageSize$pageSize").toMutableList()
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.APPLY_PLACE_MINE_URL,
            params,
            listParams,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    getData(ConstantString.RECYCLER_PULL_REFRESH)
                }

                override fun onRefreshFail(msg: String?) {

                }

            }) {
                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                }

                override fun onInterfaceSuccess(
                    jsonObject: JSONObject?,
                    call: Call?,
                    response: Response?
                ) {
                    //解析请求回来的数据源
                    val code = jsonObject!!.optInt("code")
                    if (code == 0) {
                        //访问成功，封装数据源
                        val jsonStr = jsonObject.optString("data")
                        val data = Gson().fromJson(jsonStr, PlaceCodeRecordModel::class.java)

                        if (!data.list.isNullOrEmpty()) {
                            tv_nodata.visibility=View.GONE
                            if (pullFlag == ConstantString.RECYCLER_PULL_REFRESH) {
                                //刷新
                                recyclerView.setPullLoadMoreCompleted()
                                adapter.refreshData(data.list)
                            } else {
                                //加载更多
                                recyclerView.setPullLoadMoreCompleted()
                                adapter.loadMoreData(data.list)
                            }
                        } else {
                            recyclerView.setPullLoadMoreCompleted()
                            if (pullFlag == ConstantString.RECYCLER_PULL_LOADMORE) {
                                toast(R.string.no_more_data)
                            }else{
                                tv_nodata.visibility=View.VISIBLE
                            }
                        }
                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }
                }
            })
    }


}
