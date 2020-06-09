package com.wisdom.passcode.homepage.adapter

import android.content.Context
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
import com.wisdom.passcode.helper.Helper
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.util.PrivacyUtil
import org.w3c.dom.Text
import java.text.SimpleDateFormat

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.adapter
 * @class describe：
 * @time 2020/5/8 0008 11:47
 * @change
 */
class PlaceCardListAdapter(
    private val mContext: Context,
    private var mList: List<CodeListModel>,
    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<PlaceCardListAdapter.ViewHolder>() {
    private val sp: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_card_c, parent, false)
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
        var userName = SharedPreferenceUtil.getPersonalInfoModel(mContext).nickName
//        userName = EncrypAndDecrypUtil.decrypt(userName)
        //设置卡面上的相关数据
        with(item) {
            holder.tv_dep.text = placeName
            holder.tv_name.text = "【${PrivacyUtil.nameDesensitization(userName)}】工作证"
            if (postName.isNullOrEmpty()) {
                holder.tv_job.text = "$deptName"
            } else {
                holder.tv_job.text = "$deptName--$postName"
            }
            //logo是否显示的判断逻辑
            if (logoApp.isNullOrEmpty()) {
                holder.iv_logo.visibility = View.GONE
            } else {
                holder.iv_logo.visibility = View.VISIBLE
                Glide.with(mContext).load(logoApp).into(holder.iv_logo)
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
        val tv_dep: TextView
        val tv_name: TextView
        val tv_job: TextView
        val iv_logo: ImageView


        init {
            ll_parent = itemView.findViewById(R.id.ll_parent)
            iv_logo = itemView.findViewById(R.id.iv_logo)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_job = itemView.findViewById(R.id.tv_job)
            tv_dep = itemView.findViewById(R.id.tv_dep)
        }
    }

    companion object {
        private val TAG = PlaceCardListAdapter::class.java.simpleName
    }

}