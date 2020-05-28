package com.wisdom.passcode.util

import android.content.Context
import android.widget.Toast

/**
 * Created by daixun on 15/8/29.
 */
class ToastUtil {
    companion object{
    private var mContext: Context? = null

    fun init(context: Context?) {
        mContext = context
    }

    fun showToast(msg: String) {
        Toast.makeText(mContext, msg + "", Toast.LENGTH_SHORT).show()
    }

    fun showToast(msgRes: Int) {
        showToast(mContext!!.getString(msgRes))
    }}
}