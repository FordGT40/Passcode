package com.wisdom.passcode.helper

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs


/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.helper
 * @class describe：
 * @author HanXueFeng
 * @time 2020/6/10 0010 09:35
 * @change
 */
class GestureSmoothDetector(val listener: Helper.OnScrollPageListener) :
    GestureDetector.OnGestureListener {
    override fun onShowPress(e: MotionEvent?) {
        // 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
        // 注意和onDown()的区别，强调的是没有松开或者拖动的状态
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        //用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发Java代码
        return false
    }

    //用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        listener.onPageDown()
        if (e1!!.y - e2!!.y > 30
            && abs(velocityY) > 50
        ) { //速度和距离都超过一定数值时才算滑动
            // Fling up
//            "上滑"
            listener.onPageUp()
        } else if (e2!!.y - e1!!.y > 30
            && abs(velocityY) > 50
        ) {
            // Fling down
//            "下滑"
            listener.onPageDown()
        } else {
//         未定义
            listener.onOthers()
        }


        return false
    }

    // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {

        return false;
    }

    override fun onLongPress(e: MotionEvent?) {
        // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
    }

}