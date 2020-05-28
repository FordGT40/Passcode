package com.wisdom.passcode.mine.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.ActivityManager
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.faceId.FaceIdInputNameActivity
import com.wisdom.passcode.mine.activity.ApplyRecordListActivity
import com.wisdom.passcode.mine.activity.LoginActivity
import com.wisdom.passcode.mine.activity.MyIdentificationActivity
import com.wisdom.passcode.mine.activity.ScanShowCodeRecordActivity
import com.wisdom.passcode.util.AlertUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.head_title_bar.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MineFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        head_back_iv.visibility = View.GONE
        comm_head_title.text = getString(R.string.title_personal_center)
        tv_info.setOnClickListener(this)
        rl_scan_show_record.setOnClickListener(this)
        rl_apply_record.setOnClickListener(this)
        btn_search.setOnClickListener(this)
        rl_my_identifiction.setOnClickListener(this)
        if (SharedPreferenceUtil.getPersonalInfoModel(context).authState == ConstantString.AUTHENTICATION_TYPE_TRUE) {
            //已经实名了
            tv_attestation.visibility = View.VISIBLE
        } else {
            //没有实名了
            tv_attestation.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        //根据登录状态，改变登录按钮显示
        if (ConstantString.loginState) {
            //登录了
            tv_name.text=SharedPreferenceUtil.getPersonalInfoModel(context).nickName
            btn_search.setBackgroundResource(R.drawable.shape_circle_conner_blue_deep)
            btn_search.isClickable = true
        } else {
            //未登录
            btn_search.setBackgroundResource(R.drawable.shape_circle_conner_grey)
            btn_search.isClickable = false
            tv_name.text=getString(R.string.load_first)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_info -> {
                //个人资料
                startActivity<LoginActivity>()
            }
            R.id.rl_apply_record -> {
                //申请记录
                startActivity<ApplyRecordListActivity>()
            }
            R.id.rl_my_identifiction -> {
                //我的认证
                if (tv_attestation.visibility == View.VISIBLE) {
                    startActivity<MyIdentificationActivity>()
                } else {
                    startActivity<FaceIdInputNameActivity>()
                }
            }
            R.id.rl_scan_show_record -> {
                //扫码、亮码记录
                startActivity<ScanShowCodeRecordActivity>()
            }
            R.id.btn_search -> {
                //退出登录
                AlertUtil.showMsgAlert(
                    context
                    , getString(R.string.is_logout), null
                ) { _: DialogInterface, _: Int ->
                    SharedPreferenceUtil.getConfig(context).clearSharePrefernence()
                    //本地关键信息置空
                    ConstantString.accessToken = ""
                    ConstantString.refreshToken = ""
                    ConstantString.timeStamp = 0L
                    ConstantString.userPhone = ""
                    ConstantString.loginState = false
                    ConstantString.isAdmin=""
                    ConstantString.userIdEncryption=""

                    toast(R.string.logout_success)
                    startActivity<LoginActivity>()
                    ActivityManager.getActivityManagerInstance().clearAllActivity()
                }

            }


        }
    }

}
