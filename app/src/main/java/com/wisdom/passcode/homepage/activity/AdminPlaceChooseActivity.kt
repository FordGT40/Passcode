package com.wisdom.passcode.homepage.activity

import android.app.Instrumentation
import android.transition.Fade
import android.view.KeyEvent
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.homepage.adapter.AdminPlaceChooseAdapter
import kotlinx.android.synthetic.main.activity_admin_place_choose.*

class AdminPlaceChooseActivity : BaseActivity() {


    override fun initViews() {
        val placeName = intent.getStringExtra("placeName")
        if (!placeName.isNullOrEmpty()) {
            tv_place.text = placeName
        }
        val dataList = SharedPreferenceUtil.getPersonalInfoModel(this).placeList
        val adapter = AdminPlaceChooseAdapter(this, dataList)
        listView.setOnItemClickListener { parent, view, position, id ->
            //将选中的场所信息本地化
            SharedPreferenceUtil.setAdminPlaceInfo(this, dataList[position])
            closePage()
        }
        listView.adapter = adapter
        ll_parent.setOnClickListener { closePage() }
    }

    /**
     *  @describe 关闭页面的方法
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/12 0012  17:30
     */
    private fun closePage() {
        //关闭当前页面
        setResult(ConstantString.CODE_PLACE_CHOOSE)
        object : Thread() {
            override fun run() {
                try {
                    val inst = Instrumentation()
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_admin_place_choose)
        window.enterTransition = Fade().setDuration(1000)
        window.exitTransition = Fade().setDuration(1000)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(ConstantString.CODE_PLACE_CHOOSE)
        }
        return super.onKeyDown(keyCode, event)
    }
}
