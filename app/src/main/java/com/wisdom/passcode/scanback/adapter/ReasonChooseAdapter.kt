package com.wisdom.passcode.scanback.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.wisdom.passcode.R
import com.wisdom.passcode.mine.model.PersonalInfoModel
import com.wisdom.passcode.scanback.model.ReasonModel

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.homepage.adapter
 * @class describe：管理员场所选择适配器
 * @author HanXueFeng
 * @time 2020/6/12 0012 16:40
 * @change
 */
class ReasonChooseAdapter(
    val context: Context,
    private val listData: List<ReasonModel>
) : BaseAdapter() {
val colorBgColor= listOf("#F7EBC7","#C7E1F7","#FFEBDD")
    val textColor= listOf("#c08403","#1986db","#da641b")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?{
        var viewHolder: ViewHolder
        var itemView = convertView
        if (itemView == null) {
            viewHolder = ViewHolder()
            itemView = LayoutInflater.from(context).inflate(R.layout.item_reason_choose, parent, false)
            viewHolder.tv_content = itemView.findViewById(R.id.tv_content)
            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }
        viewHolder.tv_content.text = listData[position].text
        if(position<3){
            viewHolder.tv_content.setTextColor(Color.parseColor(textColor[position]))
            viewHolder.tv_content.setBackgroundColor(Color.parseColor(colorBgColor[position]))
        }else{
            viewHolder.tv_content.setTextColor(Color.parseColor(textColor[position%3]))
            viewHolder.tv_content.setBackgroundColor(Color.parseColor(colorBgColor[position%3]))
        }
        return itemView
    }

    override fun getItem(position: Int): Any {
        return listData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listData.size
    }

    inner class ViewHolder {
        lateinit var tv_content: TextView
    }
}