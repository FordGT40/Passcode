package com.wisdom.passcode.homepage.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wisdom.passcode.R
import com.wisdom.passcode.homepage.activity.CardDetailActivity
import com.wisdom.passcode.homepage.adapter.CarCardListAdapter
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView
import kotlinx.android.synthetic.main.activity_scan_show_code_record.*


class CarCardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_card, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //构造数据源
        val model1 = CodeListModel()
        model1.isDateOf = "0"
        val model2 = CodeListModel()
        model2.isDateOf = "2"
        val model3 = CodeListModel()
        model3.isDateOf = "1"
        val listData = listOf(model1, model2, model3)
        val adapter = CarCardListAdapter(
            context!!,
            listData,
            object : CarCardListAdapter.OnItemClickListener {
                override fun onItemClick(item: CodeListModel?) {

                    val intent = Intent(context, CardDetailActivity::class.java)
                    intent.putExtra("data", item?.isDateOf)
                    intent.putExtra("tag", "0")
                    startActivity(intent)
                }
            })
        recyclerView.setLinearLayout()
        recyclerView.setOnPullLoadMoreListener(object :
            PullLoadMoreRecyclerView.PullLoadMoreListener {
            override fun onLoadMore() {

            }

            override fun onRefresh() {

            }
        })
        recyclerView.setAdapter(adapter)


    }
}


