package com.wisdom.passcode.mine.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_my_identification.*

class MyIdentificationActivity : BaseActivity() {

    override fun initViews() {
        setTitle(R.string.title_my_identifaction)
        tv_name.text = "张某"
        tv_sex.text = "男"
        tv_phone.text = "13100000000"
        tv_idcard.text = "230204199200000000"
        tv_addr.text = "哈尔滨是香坊区进乡街111号"
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_my_identification)
    }
}
