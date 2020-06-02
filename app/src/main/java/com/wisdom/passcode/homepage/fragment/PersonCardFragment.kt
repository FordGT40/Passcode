package com.wisdom.passcode.homepage.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.homepage.adapter.PersonCardListAdapter
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView
import kotlinx.android.synthetic.main.fragment_person_card.*

import kotlinx.android.synthetic.main.fragment_place_card.recyclerView
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class PersonCardFragment : Fragment() {
    private var page = 1
    private var pageSize = 10
    private lateinit var adapter: PersonCardListAdapter
    private var dataList: List<CodeListModel> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PersonCardListAdapter(
            context!!,
            dataList,
            object : PersonCardListAdapter.OnItemClickListener {
                override fun onItemClick(item: CodeListModel?) {
                    //TODO  子项点击事件
                }
            })
        recyclerView.setLinearLayout()
        recyclerView.setAdapter(adapter)
        recyclerView.setOnPullLoadMoreListener(object :
            PullLoadMoreRecyclerView.PullLoadMoreListener {
            override fun onLoadMore() {
                page += 1
                getCardsData(ConstantString.RECYCLER_PULL_LOADMORE)
            }

            override fun onRefresh() {
                page = 1
                getCardsData(ConstantString.RECYCLER_PULL_REFRESH)
            }
        })
        getCardsData(ConstantString.RECYCLER_PULL_REFRESH)

    }

    /**
     *  @describe 获取“我的卡证”数据
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/2 0002  11:33
     */
    fun getCardsData(pullFlag: String) {
//        Tools.showLoadingDialog(context)
        val params = HttpParams()
        params.put("pageSize", pageSize)
        params.put("pageNum", page)
        params.put("type", ConstantString.PASS_CODE_TYPE_PERSON)
        val paramsList = listOf(
            "pageSize$pageSize",
            "pageNum$page",
            "type${ConstantString.PASS_CODE_TYPE_PERSON}"
        ).toMutableList()
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.PASSCODE_MINE_URL,
            params,
            paramsList,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    Tools.closeDialog()
                    getCardsData(pullFlag)
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
                        val data = Gson().fromJson<List<CodeListModel>>(
                            jsonStr,
                            object : TypeToken<List<CodeListModel>>() {}.type
                        )

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
