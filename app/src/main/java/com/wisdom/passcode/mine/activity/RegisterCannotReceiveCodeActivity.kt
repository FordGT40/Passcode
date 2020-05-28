package com.wisdom.passcode.mine.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register_cannot_receive_code.*

class RegisterCannotReceiveCodeActivity : BaseActivity() {


    override fun initViews() {
        iv_close.setOnClickListener { finish() }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_register_cannot_receive_code)
    }
}
