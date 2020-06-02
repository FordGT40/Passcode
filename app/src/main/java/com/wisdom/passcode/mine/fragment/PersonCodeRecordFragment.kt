package com.wisdom.passcode.mine.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl

import com.wisdom.passcode.R
import com.wisdom.passcode.mine.adapter.CardsApplyRecordListAdapter
import com.wisdom.passcode.mine.model.MyCardsApplyListModel
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView
import kotlinx.android.synthetic.main.fragment_person_code_record.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Use the [PersonCodeRecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonCodeRecordFragment : Fragment() {
    private var page = 1
    private var pageSize = 10
    private lateinit var adapter: CardsApplyRecordListAdapter
    private var dataList: List<MyCardsApplyListModel> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_code_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CardsApplyRecordListAdapter(
            context!!,
            dataList,
            object : CardsApplyRecordListAdapter.OnItemClickListener {
                override fun onItemClick(item: MyCardsApplyListModel) {
//                    TODO 子项点击事件
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
        params.put("type", ConstantString.PASSCODE_RECORD_TYPE_PERSON)
        val paramsList = listOf(
            "pageSize$pageSize",
            "pageNum$page",
            "type${ConstantString.PASSCODE_RECORD_TYPE_PERSON}"
        ).toMutableList()
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.PASSCODE_APPLYLIST_URL,
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
                        val data = Gson().fromJson<List<MyCardsApplyListModel>>(jsonStr, object:
                            TypeToken<List<MyCardsApplyListModel>>(){}.type)

                        if (!data.isNullOrEmpty()) {
                            tv_nodata.visibility=View.GONE
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
