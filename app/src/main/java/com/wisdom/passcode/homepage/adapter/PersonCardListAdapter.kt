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
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.util.PrivacyUtil
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
class PersonCardListAdapter(
    private val mContext: Context,
    private var mList: List<CodeListModel>,
    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<PersonCardListAdapter.ViewHolder>() {
    private val sp: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_card_b, parent, false)
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
            holder.ll_parent.backgroundDrawable = mContext.resources.getDrawable(R.drawable.kz_b)
            holder.tv_out_off_date.visibility = View.INVISIBLE
            holder.tv_out_off_date_already.visibility = View.INVISIBLE
        } else if (temp in 1 until nearlyOutOfDate || temp == nearlyOutOfDate) {
            //即将过期
            holder.ll_parent.backgroundDrawable =
                mContext.resources.getDrawable(R.drawable.kz_b)
            holder.tv_out_off_date.visibility = View.VISIBLE
            holder.tv_out_off_date_already.visibility = View.INVISIBLE
        } else {
            //彻底过期了
            holder.ll_parent.backgroundDrawable =
                mContext.resources.getDrawable(R.drawable.kz_b_grey)
            holder.tv_dep.setTextColor(Color.parseColor("#333333"))
            holder.tv_name.setTextColor(Color.parseColor("#666666"))
            holder.tv_out_off_date.visibility = View.INVISIBLE
            holder.tv_date_useful.visibility = View.INVISIBLE
            holder.tv_out_off_date_already.visibility = View.VISIBLE
        }
        //设置卡面上的相关数据
        with(item) {
            holder.tv_dep.text = "$placeName"
            holder.tv_date_useful.text = "$codeTypeName($codeTypeLable)"
            if (logoApp.isNullOrEmpty()) {
                holder.iv_logo.visibility = View.GONE
            } else {
                holder.iv_logo.visibility = View.VISIBLE
                Glide.with(mContext).load(logoApp).into(holder.iv_logo)
            }
        }
        var userName = SharedPreferenceUtil.getPersonalInfoModel(mContext).nickName
        holder.tv_name.text = "【${PrivacyUtil.nameDesensitization(userName)}】人员出入证"
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
        val tv_name: TextView
        val tv_dep: TextView
        val iv_logo: ImageView

        val tv_date_useful: TextView
        val tv_out_off_date: TextView
        val tv_out_off_date_already: TextView


        init {
            ll_parent = itemView.findViewById(R.id.ll_parent)
            iv_logo = itemView.findViewById(R.id.iv_logo)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_out_off_date = itemView.findViewById(R.id.tv_out_off_date)
            tv_out_off_date_already = itemView.findViewById(R.id.tv_out_off_date_already)
            tv_date_useful = itemView.findViewById(R.id.tv_date_useful)
            tv_dep = itemView.findViewById(R.id.tv_dep)
        }
    }

    companion object {
        private val TAG = PersonCardListAdapter::class.java.simpleName
    }

}