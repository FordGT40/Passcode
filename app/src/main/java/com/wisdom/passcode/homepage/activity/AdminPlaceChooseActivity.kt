package com.wisdom.passcode.homepage.activity

import android.app.Instrumentation
import android.transition.Fade
import android.view.KeyEvent
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.homepage.adapter.AdminPlaceChooseAdapter
import com.wisdom.passcode.mine.model.PersonalInfoModel
import kotlinx.android.synthetic.main.activity_admin_place_choose.*

class AdminPlaceChooseActivity : BaseActivity() {


    override fun initViews() {
        val placeName = intent.getStringExtra("placeName")
        if (!placeName.isNullOrEmpty()) {
            tv_place.text = placeName
        }
        val dataSource = SharedPreferenceUtil.getPersonalInfoModel(this).placeList
        //遍历个人信息数据源，拿到可以供管理员切换的地点信息
        var listData=ArrayList<PersonalInfoModel.PlaceCodeBean>().toMutableList()
        for(item in dataSource){
            if(!item.placeCode.isNullOrEmpty()){
                for(it in item.placeCode){
                    listData.add(it)
                }
            }
        }
        //设置适配器展示数据
        val adapter = AdminPlaceChooseAdapter(this, listData)
        listView.setOnItemClickListener { _, _, position, _ ->
            //将选中的场所信息本地化
            SharedPreferenceUtil.setAdminPlaceInfo(this, listData[position])
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
