package com.wisdom.passcode.apply.activity

import android.view.View
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import kotlinx.android.synthetic.main.activity_place_code_place_type_choose.*
import org.jetbrains.anko.startActivity

class PlaceCodePlaceTypeChooseActivity : BaseActivity(), View.OnClickListener {

    override fun initViews() {
        setTitle(R.string.title_place_code_place_type)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_place_code_place_type_choose)
        ll_company.setOnClickListener(this)
        ll_gov.setOnClickListener(this)
        ll_other.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_company -> {
                //企业
                startActivity<PlaceCodeApplyActivity>("data" to ConstantString.PLACE_TYPE_COMPANY)
            }
            R.id.ll_gov -> {
                //政府/事业单位
                startActivity<PlaceCodeApplyActivity>("data" to ConstantString.PLACE_TYPE_GOVERNMENT)
            }
            R.id.ll_other -> {
                //其他组织
                startActivity<PlaceCodeApplyActivity>("data" to ConstantString.PLACE_TYPE_OTHER)
            }
        }
    }

}
