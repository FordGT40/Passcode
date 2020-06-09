package com.wisdom.passcode.mine.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.mine.model.MyCardsApplyListModel
import com.wisdom.passcode.util.Tools

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.adapter
 * @class describe：
 * @time 2020/5/8 0008 11:47
 * @change
 */
class CardsApplyRecordListAdapter(
    private val mContext: Context,
    private var mList: List<MyCardsApplyListModel>,
    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<CardsApplyRecordListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_place_apply_record, parent, false)
        return ViewHolder(view)
    }

    //    上拉加载更多时候使用的方法
    fun loadMoreData(moreList: List<MyCardsApplyListModel>) {
        moreList.forEach { item ->
            this.mList.toMutableList().add(item)
        }
        notifyDataSetChanged()
    }

    //    刷新数据源使用的方法
    fun refreshData(refreshList: List<MyCardsApplyListModel>) {
        this.mList = refreshList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = mList[position]

        holder.tv_date.text = Tools.stampToDate(item.createDate)
        holder.tv_name.text = item.placeName
        when (item.auditStatus) {
            ConstantString.MY_APPLY_CARD_RECORD_USEING -> {
                //审核通过
                holder.tv_hint.visibility = View.GONE
                holder.tv_state.setText(R.string.card_record_use)
                holder.tv_state.setTextColor(Color.parseColor("#00974A"))
                holder.tv_state.background =
                    mContext.getDrawable(R.drawable.shape_circle_corner_green)
            }
            ConstantString.MY_APPLY_CARD_RECORD_UNDERCHECK -> {
//                待审核
                holder.tv_hint.visibility = View.GONE
                holder.tv_state.setText(R.string.code_check_under_check)
                holder.tv_state.setTextColor(Color.parseColor("#0059A1"))
                holder.tv_state.background =
                    mContext.getDrawable(R.drawable.shape_circle_corner_blue)
            }
            ConstantString.MY_APPLY_CARD_RECORD_REFUSED -> {
//                审核未通过
                holder.tv_hint.visibility = View.VISIBLE
                holder.tv_state.setText(R.string.code_check_unpass)
                holder.tv_state.setTextColor(Color.parseColor("#F41717"))
                holder.tv_state.background =
                    mContext.getDrawable(R.drawable.shape_circle_corner_red)
                holder.tv_hint.text = item.rejectReason
            }
            ConstantString.MY_APPLY_CARD_RECORD_OUT_OFF_DATE -> {
//                已过期
                holder.tv_hint.visibility = View.GONE
                holder.tv_state.setText(R.string.card_record_outoff_date)
                holder.tv_state.setTextColor(Color.parseColor("#0059A1"))
                holder.tv_state.background =
                    mContext.getDrawable(R.drawable.shape_circle_corner_blue)
            }
            ConstantString.MY_APPLY_CARD_RECORD_USELESS -> {
//                已失效
                holder.tv_hint.visibility = View.GONE
                holder.tv_state.setText(R.string.card_record_useless)
                holder.tv_state.setTextColor(Color.parseColor("#0059A1"))
                holder.tv_state.background =
                    mContext.getDrawable(R.drawable.shape_circle_corner_blue)
            }

        }

       holder.itemView.setOnClickListener { mListener.onItemClick(mList[position]) }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: MyCardsApplyListModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name: TextView
        val tv_date: TextView
        val tv_hint: TextView
        val tv_state: TextView

        init {
            tv_name = itemView.findViewById(R.id.tv_place)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_hint = itemView.findViewById(R.id.tv_hint)
            tv_state = itemView.findViewById(R.id.tv_state)
        }
    }

    companion object {
        private val TAG = CardsApplyRecordListAdapter::class.java.simpleName
    }

}