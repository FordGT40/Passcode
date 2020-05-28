package com.wisdom.passcode.apply.activity

import android.app.Activity
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.util.KeyboardUtil
import kotlinx.android.synthetic.main.activity_car_card_apply.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast


class CarCardApplyActivity : BaseActivity(), View.OnClickListener {
    var placeCode = ""
    var typeCode = ""
    private var keyboardUtil: KeyboardUtil? = null
    override fun initViews() {
        setTitle(R.string.title_apply_car_card)
        btn_submit.setOnClickListener(this)
        tv_place_name.setOnClickListener(this)
        tv_card_type.setOnClickListener(this)
        //输入车牌号弹出特殊键盘
        et_plate_num.setOnTouchListener { _, _ ->
            hideSoftKeyboard(this@CarCardApplyActivity)
            if (keyboardUtil == null) {
                keyboardUtil = KeyboardUtil(this@CarCardApplyActivity, et_plate_num)
                keyboardUtil!!.hideSoftInputMethod()
                keyboardUtil!!.showKeyboard()
            } else {
                keyboardUtil!!.showKeyboard()
            }
            false
        }
        et_plate_num.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (keyboardUtil != null) {
                    if (keyboardUtil!!.isShow) {
                        keyboardUtil!!.hideKeyboard()
                    }
                }
            }
        }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_car_card_apply)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_place_name -> {
                //选择场所名
                startActivityForResult<PlaceNameSearchActivity>(ConstantString.REQUEST_CODE)
            }
            R.id.tv_card_type -> {
                //出入证类型选择
                if (placeCode!="") {
                    startActivityForResult<CardTypeChooseActivity>(
                        ConstantString.REQUEST_CODE,
                        "type" to ConstantString.CARD_TYPE_PERSON,
                        "placeCode" to placeCode
                    )
                } else {
                    toast(R.string.hint39)
                }
            }
            R.id.btn_submit -> {
                //提交按钮点击事件(暂时跳转成功页面)
                startActivity<CardApplySuccessActivity>("title" to R.string.title_apply_car_card)
            }

        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyboardUtil != null) {
                if (keyboardUtil!!.isShow) {
                    keyboardUtil!!.hideKeyboard()
                } else {
                    finish()
                }
            } else {
                finish()
            }
        }
        return false
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ConstantString.RESULT_CODE_CHOOSE_PLACE) {
            //选择场所后返回的
            if (data != null) {
                placeCode = data.getStringExtra("code")
                tv_place_name.text = data.getStringExtra("name")
            }
        }else if (resultCode == ConstantString.RESULT_CODE_CHOOSE_CARD_TYPE) {
            typeCode = data!!.getStringExtra("id")
            val name = data.getStringExtra("name")
            val label = data.getStringExtra("label")
            tv_card_type.text = "$name($label)"
        }
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    private fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}
