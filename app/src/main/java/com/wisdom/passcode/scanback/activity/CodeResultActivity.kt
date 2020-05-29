package com.wisdom.passcode.scanback.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_code_result.*

class CodeResultActivity : BaseActivity() {


    override fun initViews() {
        //沉浸式状态栏
        setNoStateBar()
        iv_back.setOnClickListener { finish() }
        comm_head_title.text=getText(R.string.title_scan_result)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_code_result)
    }


}
