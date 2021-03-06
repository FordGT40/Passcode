package com.wisdom.passcode.apply.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.model.PlaceSearchListModel

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.adapter
 * @class describe：
 * @time 2020/5/8 0008 11:47
 * @change
 */
class PlaceNameSearchListAdapter(
    private val mContext: Context,
    private var mList: List<PlaceSearchListModel>,
    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<PlaceNameSearchListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_place_search, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = mList[position]
        holder.tv_name.text = item.name
        holder.itemView.setOnClickListener {
            mListener.onItemClick(mList[position])
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: PlaceSearchListModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name: TextView

        init {
            tv_name = itemView.findViewById(R.id.tv_name)
        }
    }

    companion object {
        private val TAG = PlaceNameSearchListAdapter::class.java.simpleName
    }
    //    上拉加载更多时候使用的方法
//    fun loadMoreData(moreList: List<PlaceSearchListModel>) {
//        moreList.forEach { item ->
//            this.mList.toMutableList().add(item)
//        }
//        notifyDataSetChanged()
//    }
//
//    //    刷新数据源使用的方法
//    fun refreshData(refreshList: List<PlaceSearchListModel>) {
//        this.mList = refreshList
//        notifyDataSetChanged()
//    }
}