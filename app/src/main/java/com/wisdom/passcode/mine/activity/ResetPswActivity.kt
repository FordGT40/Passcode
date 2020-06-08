package com.wisdom.passcode.mine.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity

class ResetPswActivity : BaseActivity() {

    override fun initViews() {
       setTitle(R.string.title_reset_psw)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_reset_psw)
    }
}
