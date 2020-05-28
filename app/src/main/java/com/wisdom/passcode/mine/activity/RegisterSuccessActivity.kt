package com.wisdom.passcode.mine.activity

import android.content.Intent
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.homepage.activity.MainActivity
import kotlinx.android.synthetic.main.activity_register_success.*

class RegisterSuccessActivity : BaseActivity() {


    override fun initViews() {
        btn_next.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this@RegisterSuccessActivity.finish()
        }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_register_success)
    }
}
