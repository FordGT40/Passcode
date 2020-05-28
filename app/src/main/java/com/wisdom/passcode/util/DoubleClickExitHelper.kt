package com.wisdom.passcode.util

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast

/**
 * Created by Mustang on 2015/9/14.
 */
class DoubleClickExitHelper(private val mActivity: Activity) {
    private var isOnKeyBacking = false
    private val mHandler: Handler
    private var mBackToast: Toast? = null

    /**
     * Activity onKeyDown事件
     */
    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false
        }
        return if (isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable)
            if (mBackToast != null) {
                mBackToast!!.cancel()
            }
            mActivity.finish()
            true
        } else {
            isOnKeyBacking = true
            if (mBackToast == null) {
                mBackToast = Toast.makeText(mActivity, "再按一次退出", Toast.LENGTH_SHORT)
            }
            mBackToast!!.show()
            //延迟两秒的时间，把Runable发出去
            mHandler.postDelayed(onBackTimeRunnable, 2000)
            true
        }
    }

    private val onBackTimeRunnable = Runnable {
        isOnKeyBacking = false
        if (mBackToast != null) {
            mBackToast!!.cancel()
        }
    }

    init {
        mHandler = Handler(Looper.getMainLooper())
    }
}