package com.wisdom.passcode.homepage.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisdom.passcode.R
import com.wisdom.passcode.homepage.model.CodeListModel
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
    private val mListener: OnItemClickListener
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

var isOutOffDate=""
        val item = mList[position]
        //计算过期时间
        //即将过期衡量标准
        val nearlyOutOfDate = item.advanceTime.toLong() * 24 * 3600 * 1000
        //过期时间与当前时间戳的差值
        val temp = item.expireTime.toLong() - System.currentTimeMillis()
        if (temp > 0 && temp > nearlyOutOfDate) {
            //没过期
            isOutOffDate="0"
            holder.ll_parent.backgroundDrawable = mContext.resources.getDrawable(R.drawable.kz_a)
            holder.tv_out_off_date.visibility = View.GONE
            holder.tv_out_off_date_already.visibility = View.GONE

        } else if (temp in 1 until nearlyOutOfDate || temp == nearlyOutOfDate) {
            //即将过期
            isOutOffDate="1"
            holder.ll_parent.backgroundDrawable =
                mContext.resources.getDrawable(R.drawable.kz_a)
            holder.tv_out_off_date.visibility = View.VISIBLE
            holder.tv_out_off_date_already.visibility = View.GONE
        } else {
            //彻底过期了
            isOutOffDate="2"
            holder.ll_parent.backgroundDrawable =
                mContext.resources.getDrawable(R.drawable.kz_a_grey)
            holder.tv_dep.setTextColor(Color.parseColor("#333333"))
            holder.tv_name.setTextColor(Color.parseColor("#666666"))
            holder.tv_out_off_date.visibility = View.GONE
            holder.tv_date_useful.visibility = View.GONE
            holder.tv_out_off_date_already.visibility = View.VISIBLE
        }

        //设置卡面上的相关数据
        with(item) {
            holder.tv_dep.text = "${placeName}"
            holder.tv_name.text="【${carNumber}】车辆出入证"
            if(logoApp.isNullOrEmpty()){
                holder.iv_logo.visibility=View.GONE
            }else{
                holder.iv_logo.visibility=View.VISIBLE
                Glide.with(mContext).load(logoApp).into(holder.iv_logo)
            }
        }


        holder.itemView.setOnClickListener { mListener.onItemClick(item,isOutOffDate) }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: CodeListModel?,isOutOffDate:String)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ll_parent: RelativeLayout
        val tv_name: TextView
        val tv_dep: TextView
        val iv_logo: ImageView
        val tv_date_useful: TextView
        val tv_out_off_date: TextView
        val tv_out_off_date_already: TextView


        init {
            iv_logo = itemView.findViewById(R.id.iv_logo)
            ll_parent = itemView.findViewById(R.id.ll_parent)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_out_off_date = itemView.findViewById(R.id.tv_out_off_date)
            tv_out_off_date_already = itemView.findViewById(R.id.tv_out_off_date_already)
            tv_date_useful = itemView.findViewById(R.id.tv_date_useful)
            tv_dep = itemView.findViewById(R.id.tv_dep)
        }
    }

    companion object {
        private val TAG = CarCardListAdapter::class.java.simpleName
    }

}