package com.wisdom.passcode.apply.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.model.CardTypeListModel

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.adapter
 * @class describe：
 * @time 2020/5/8 0008 11:47
 * @change
 */
class CardTypeListAdapter(
    private val mContext: Context,
    private var mList: List<CardTypeListModel>,
    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<CardTypeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_card_type_choose, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = mList[position]
        holder.tv_name.text = "${item.name}(${item.lable})"
        holder.tv_time.text = item.timeRange
        holder.itemView.setOnClickListener {
            mListener.onItemClick(item)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: CardTypeListModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name: TextView
        val tv_time: TextView

        init {
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_time = itemView.findViewById(R.id.tv_time)
        }
    }

    companion object {
        private val TAG = CardTypeListAdapter::class.java.simpleName
    }

    //        上拉加载更多时候使用的方法
//    fun loadMoreData(moreList: List<PlaceSearchListModel>) {
//        moreList.forEach { item ->
//            this.mList.toMutableList().add(item)
//        }
//        notifyDataSetChanged()
//    }
//
    //    刷新数据源使用的方法
    fun refreshData(refreshList: List<CardTypeListModel>) {
        this.mList = refreshList
        notifyDataSetChanged()
    }
}