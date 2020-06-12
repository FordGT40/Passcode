package com.wisdom.passcode.homepage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.wisdom.passcode.R
import com.wisdom.passcode.mine.model.PersonalInfoModel

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.homepage.adapter
 * @class describe：管理员场所选择适配器
 * @author HanXueFeng
 * @time 2020/6/12 0012 16:40
 * @change
 */
class AdminPlaceChooseAdapter(
    val context: Context,
    private val listData: List<PersonalInfoModel.PlaceListBean>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?{
        var viewHolder: ViewHolder
        var itemView = convertView
        if (itemView == null) {
            viewHolder = ViewHolder()
            itemView = LayoutInflater.from(context).inflate(R.layout.item_admin_place_choose, parent, false)
            viewHolder.tv_name = itemView.findViewById(R.id.tv_name)
            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }
        viewHolder.tv_name.text = listData[position].placeName
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
        lateinit var tv_name: TextView
    }
}