package com.wisdom.passcode.faceId

import android.content.IntentFilter
import android.os.Bundle
import com.wisdom.passcode.ConstantString

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_identifying.*

class IdentifyingActivity : BaseActivity() {
    private val finishReceiver = FinishReceiver(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gif.setGifImage(R.drawable.identifying)
        val data = intent.getStringExtra("data")
        tv_hint.text = "正在对身份证号为:\n${data}\n的用户进行实名认证"
        registerReceiver(finishReceiver, IntentFilter(ConstantString.BROADCAST_FINISH_TAG))
    }

    override fun initViews() {

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_identifying)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(finishReceiver)
    }
}
