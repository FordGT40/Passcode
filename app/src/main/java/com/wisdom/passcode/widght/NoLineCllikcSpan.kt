package com.wisdom.passcode.widght

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.widght
 * @class describe：
 * @author HanXueFeng
 * @time 2020/5/9 0009 10:44
 * @change
 */
abstract class NoLineClickSpan() :ClickableSpan(){
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)

        hasLineDown(ds)
    }


    override fun onClick(widget: View) {
        onClick()
    }

abstract fun  onClick()
abstract fun  hasLineDown(ds: TextPaint)
}