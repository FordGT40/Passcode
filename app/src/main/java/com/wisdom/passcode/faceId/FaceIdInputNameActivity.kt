package com.wisdom.passcode.faceId

import android.content.Intent
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.helper.Helper
import kotlinx.android.synthetic.main.activity_face_id_input_name.*

class FaceIdInputNameActivity : BaseActivity() {


    override fun initViews() {

        //为输入框设置相关动态监听
        val watcher0 = Helper.getIdCardInputWatcher(this, et_name, et_id_card, btn_next, 0)
        val watcher1 = Helper.getIdCardInputWatcher(this, et_name, et_id_card, btn_next, 1)
        et_name.addTextChangedListener(watcher0)
        et_id_card.addTextChangedListener(watcher1)
        val intent = Intent(this, FaceIdVerifyCodeActivity::class.java)
        //下一步按钮点击事件
        btn_next.setOnClickListener {
            intent.putExtra("name", et_name.text.toString())
            intent.putExtra("idCard", et_id_card.text.toString())
            startActivity(intent)
        }
        btn_next.isClickable=false
        iv_back.setOnClickListener { finish() }

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_face_id_input_name)
    }

}
