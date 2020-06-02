package com.wisdom.passcode.mine.activity

import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_alter_phone.*

class AlterPhoneActivity : BaseActivity() {

    

    override fun initViews() {
        setTitle(R.string.title_find_psw)
        tv_get_code.setOnClickListener { 
//            if(checkedPhone()){
//
//            }
        }
    }

//    private fun checkedPhone(): Boolean {
//
//    }


    override fun setlayoutIds() {
        setContentView(R.layout.activity_alter_phone)
    }
    
    
}
