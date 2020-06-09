package com.wisdom.passcode.homepage.activity

import cn.bertsir.zbar.QrManager
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.mine.activity.LoginActivity
import com.wisdom.passcode.util.*
import kotlinx.android.synthetic.main.activity_show_code.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ShowCodeActivity : BaseActivity() {

    override fun initViews() {
        iv_back.setOnClickListener { finish() }
        //将屏幕亮度调整到最大值
        Tools.setScreenLight(this, ConstantString.SCREEN_LIGHT_MAX)
        tv_scan.setOnClickListener {
            Tools.startScanActivity(this, this,
                QrManager.OnScanResultCallback { result -> toast(result.toString()) })
        }
        //生成个人码
        createMyCode()
    }

    /**
     *  @describe 生成个人码
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/26 0026  15:08
     */
    private fun createMyCode() {
        if (ConstantString.userIdEncryption == "") {
            //没有userId数据了
            AlertUtil.showMsgAlert(this, R.string.user_id_lost) { _, _ ->
                startActivity<LoginActivity>()
            }
        } else {
            //拿到用户id数据，生成二维码进行展示操作
            val bitmap = QrCodeUtils.createQRCode(ConstantString.userIdEncryption, 300, 300, null)
            iv_qr.setImageBitmap(bitmap)
            //用户名
            val name = SharedPreferenceUtil.getPersonalInfoModel(this).nickName
            tv_line_1.text = PrivacyUtil.nameDesensitization(name)
        }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_show_code)
    }


}
