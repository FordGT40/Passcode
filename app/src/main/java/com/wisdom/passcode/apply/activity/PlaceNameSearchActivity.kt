package com.wisdom.passcode.apply.activity

import android.view.View
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_car_access_certificate_search.*
import org.jetbrains.anko.startActivity

class PlaceNameSearchActivity : BaseActivity(), View.OnClickListener {


    override fun initViews() {
        setTitle(R.string.choose_place_name)
        btn_search.setOnClickListener(this)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_car_access_certificate_search)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_search -> {
                //搜索按钮点击事件
                startActivity<CardApplySuccessActivity>()
            }
        }
    }
}
