package com.wisdom.passcode.mine.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.mine.adapter.ScanShowCodeRecordAdapter
import com.wisdom.passcode.mine.model.ScanShowCodeModel
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView
import kotlinx.android.synthetic.main.activity_scan_show_code_record.*
import org.jetbrains.anko.toast

class ScanShowCodeRecordActivity : BaseActivity() {


    override fun initViews() {
        setTitle(R.string.title_scan_show_record)
        var listData: MutableList<ScanShowCodeModel> = ArrayList()
        for (index in 1..15) {
            val scanShowCodeModel = ScanShowCodeModel()
            scanShowCodeModel.date = "2020-01-09"
            scanShowCodeModel.name = "测试卡"
            listData.add(scanShowCodeModel)
        }
        //设置adapter
        recyclerView.setLinearLayout()
        recyclerView.setColorSchemeResources(R.color.primary_blue, android.R.color.holo_blue_dark);
        val adapter = ScanShowCodeRecordAdapter(
            listData,
            this,
            object : ScanShowCodeRecordAdapter.OnDeteleClickedListener {
                override fun onDeleteClicked(data: ScanShowCodeModel) {
                    toast("点击了删除${data.name}")
                }

                override fun OnItemClicked(data: ScanShowCodeModel) {
//                TODO 子项点击事件
                }

            })
        recyclerView.setAdapter(adapter)
        recyclerView.setOnPullLoadMoreListener(object :
            PullLoadMoreRecyclerView.PullLoadMoreListener {
            override fun onLoadMore() {
                toast("加载")
//                recyclerView.setPullLoadMoreCompleted()
            }

            override fun onRefresh() {
                toast("刷新")
//                recyclerView.setPullLoadMoreCompleted()
            }
        })

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_scan_show_code_record)

    }


}
