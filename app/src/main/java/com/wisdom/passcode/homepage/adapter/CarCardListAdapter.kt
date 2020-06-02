package com.wisdom.passcode.homepage.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.util.Tools
import org.jetbrains.anko.backgroundDrawable
import java.text.SimpleDateFormat

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.adapter
 * @class describe：
 * @time 2020/5/8 0008 11:47
 * @change
 */
class CarCardListAdapter(
    private val mContext: Context,
    private var mList: List<CodeListModel>,
    private val mListener:OnItemClickListener
) : RecyclerView.Adapter<CarCardListAdapter.ViewHolder>() {
    private val sp: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_card_a, parent, false)
        return ViewHolder(view)
    }
    //    上拉加载更多时候使用的方法
    fun loadMoreData(moreList: List<CodeListModel>) {
        moreList.forEach { item ->
            this.mList.toMutableList().add(item)
        }
        notifyDataSetChanged()
    }

    //    刷新数据源使用的方法
    fun refreshData(refreshList: List<CodeListModel>) {
        this.mList = refreshList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        //子项点击事件
        holder.itemView.setOnClickListener {
            mListener?.onItemClick(mList[position])
        }

        val item = mList[position]
        //计算过期时间
        //即将过期衡量标准
        val nearlyOutOfDate = item.advanceTime.toLong() * 24 * 3600 * 1000
        //过期时间与当前时间戳的差值
        val temp = item.expireTime.toLong() - System.currentTimeMillis()
        if (temp > 0 && temp > nearlyOutOfDate) {
            //没过期
            holder.ll_parent.backgroundDrawable = mContext.resources.getDrawable(R.drawable.kz_a)
            holder.tv_date.text = "有效期至：${sp.format(item.expireTime.toLong())}"
        } else if (temp in 1 until nearlyOutOfDate || temp == nearlyOutOfDate) {
            //即将过期
            //即将过期
            holder.ll_parent.backgroundDrawable =
                mContext.resources.getDrawable(R.drawable.kz_a)
            val str = Tools.getClickableSpan(
                "有效期至：${sp.format(item.expireTime.toLong())} (即将过期)"
                ,
                15, 22, Color.parseColor("#FE3237"), false, null
            )
            holder.tv_date.text = str
        } else {
            //彻底过期了
            holder.ll_parent.backgroundDrawable =
                mContext.resources.getDrawable(R.drawable.kz_a_grey)
            holder.tv_date.text = "有效期至：${sp.format(item.expireTime.toLong())}(已过期)"
            holder.tv_dep.setTextColor(Color.parseColor("#333333"))
            holder.tv_card_name.setTextColor(Color.parseColor("#333333"))
            holder.tv_num.setTextColor(Color.parseColor("#666666"))
            holder.tv_date.setTextColor(Color.parseColor("#666666"))
        }
        //设置卡面上的相关数据
        holder.tv_dep.text = "【${item.codeTypeLable}】${item.placeName}"
        when (item.codeTypeDataType) {
            ConstantString.MY_CODE_PASS_TYPE_NORMAL -> {
                holder.tv_num.text = "无限制 ${item.codeTypeTimeRange}"
            }
            ConstantString.MY_CODE_PASS_TYPE_WORKDAYS -> {
                holder.tv_num.text = "工作日 ${item.codeTypeTimeRange}"
            }
            else -> {
                holder.tv_num.text = "${item.codeTypeTimeRange}"
            }
        }




        holder.itemView.setOnClickListener { mListener.onItemClick(item) }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: CodeListModel?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ll_parent: RelativeLayout
        val tv_date: TextView
        val tv_dep: TextView
        val tv_card_name: TextView
        val tv_num: TextView


        init {
            ll_parent = itemView.findViewById(R.id.ll_parent)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_num = itemView.findViewById(R.id.tv_num)
            tv_card_name = itemView.findViewById(R.id.tv_card_name)
            tv_dep = itemView.findViewById(R.id.tv_dep)
        }
    }

    companion object {
        private val TAG = CarCardListAdapter::class.java.simpleName
    }

}