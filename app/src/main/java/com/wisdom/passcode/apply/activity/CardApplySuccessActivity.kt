package com.wisdom.passcode.apply.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.ActivityManager
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_car_access_certificate_success.*

class CardApplySuccessActivity : BaseActivity() {


    override fun initViews() {
      setTitle(intent.getIntExtra("title",R.string.title_apply_code_place))
        btn_back_to_main.setOnClickListener {
            ActivityManager.getActivityManagerInstance().clearAllActivity()
            this.finish()
        }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_car_access_certificate_success)
    }
}
