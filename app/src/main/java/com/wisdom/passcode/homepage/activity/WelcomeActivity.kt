package com.wisdom.passcode.homepage.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.mine.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivity

class WelcomeActivity : BaseActivity() {


    override fun initViews() {
        btn_next.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_welcome)
    }
}
