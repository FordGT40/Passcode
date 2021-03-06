package com.wisdom.passcode.mine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wisdom.passcode.R
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.homepage.model.ShowCodeRecordModel
import com.wisdom.passcode.mine.model.ScanCodeRecordModel
import java.text.SimpleDateFormat

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.adapter
 * @class describe：
 * @time 2020/5/8 0008 11:47
 * @change
 */
class ScanCodeRecordAdapter(
    private val mContext: Context,
    private var mList: List<ScanCodeRecordModel>,
    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<ScanCodeRecordAdapter.ViewHolder>() {
    private val sp: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_scan_show_code, parent, false)
        return ViewHolder(view)
    }

    //    上拉加载更多时候使用的方法
    fun loadMoreData(moreList: List<ScanCodeRecordModel>) {
        moreList.forEach { item ->
            this.mList.toMutableList().add(item)
        }
        notifyDataSetChanged()
    }

    //    刷新数据源使用的方法
    fun refreshData(refreshList: List<ScanCodeRecordModel>) {
        this.mList = refreshList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        with(mList[position]) {
            holder.tv_place.text = placeName
            val typeStr = when (type) {
                "1" -> "进入"
                "2" -> "离开"
                else -> "经过"
            }
            holder.tv_date.text = "${sp.format(scanDate.toLong())} $typeStr"
        }
        holder.itemView.setOnClickListener {
            mListener.onItemClick(mList[position])
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: ScanCodeRecordModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_place: TextView
        val tv_date: TextView


        init {
            tv_place = itemView.findViewById(R.id.tv_place)
            tv_date = itemView.findViewById(R.id.tv_date)

        }
    }

    companion object {
        private val TAG = ScanCodeRecordAdapter::class.java.simpleName
    }

}