package com.wisdom.passcode.homepage.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wisdom.passcode.R
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.util.Tools
import org.jetbrains.anko.backgroundDrawable

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
    private val mList: List<CodeListModel>,
    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<CarCardListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_card_a, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = mList[position]
        when (item.isDateOf) {
            "0" -> {
                //没过期
                holder.ll_parent.backgroundDrawable = mContext.resources.getDrawable(R.drawable.kz_a)
                holder.tv_date.text = "有效期至：2020-06-01"
            }
            "1" -> {
                //即将过期
                holder.ll_parent.backgroundDrawable =
                    mContext.resources.getDrawable(R.drawable.kz_a)
                val str = Tools.getClickableSpan(
                    "有效期至：2020-06-01 (即将过期)"
                    ,
                    15, 22, Color.parseColor("#FE3237"), false, null
                )
                holder.tv_date.text = str
            }
            else -> {
                //彻底过期了
                holder.ll_parent.backgroundDrawable = mContext.resources.getDrawable(R.drawable.kz_a_grey)
                holder.tv_date.text = "有效期至：2020-06-01(已过期)"
                holder.tv_dep.setTextColor(Color.parseColor("#333333"))
                holder.tv_card_name.setTextColor(Color.parseColor("#333333"))
                holder.tv_num.setTextColor(Color.parseColor("#666666"))
                holder.tv_date.setTextColor(Color.parseColor("#666666"))

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