package com.wisdom.passcode.mine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wisdom.passcode.R
import com.wisdom.passcode.mine.model.ScanShowCodeModel

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.adapter
 * @class describe：
 * @author HanXueFeng
 * @time 2020/5/7 0007 17:20
 * @change
 */
class ScanShowCodeRecordAdapter(
    val listData: List<ScanShowCodeModel>,
    val context: Context,
    val onDeteleClickedListener: OnDeteleClickedListener
) :
    RecyclerView.Adapter<ScanShowCodeRecordAdapter.MyHolder>() {


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.findViewById<TextView>(R.id.tv_place)
        var tvDate = itemView.findViewById<TextView>(R.id.tv_date)
        var llDelete = itemView.findViewById<LinearLayout>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.item_scan_show_code, null, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvName.text = listData[position].name
        holder.tvDate.text = listData[position].date
        holder.llDelete.setOnClickListener {
            onDeteleClickedListener.onDeleteClicked(listData[position])
        }
       holder.itemView.setOnClickListener {
           onDeteleClickedListener.OnItemClicked(listData[position])
       }
    }

    interface OnDeteleClickedListener {
        fun onDeleteClicked(data: ScanShowCodeModel)
        fun OnItemClicked(data: ScanShowCodeModel)
    }
}