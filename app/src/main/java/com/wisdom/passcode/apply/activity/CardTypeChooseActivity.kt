package com.wisdom.passcode.apply.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity

class CardTypeChooseActivity : BaseActivity() {

    override fun initViews() {
    setTitle(R.string.title_card_type_choose)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_card_type_choose)
    }
}
