package com.wisdom.passcode.homepage.activity

import android.annotation.SuppressLint
import android.view.View
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantString.Companion.MY_CODE_PASS_TYPE_NORMAL
import com.wisdom.passcode.ConstantString.Companion.MY_CODE_PASS_TYPE_WORKDAYS
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.activity.CarCardApplyActivity
import com.wisdom.passcode.apply.activity.PersonCardApplyActivity
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.PrivacyUtil
import com.wisdom.passcode.util.QrCodeUtils
import kotlinx.android.synthetic.main.activity_card_detail.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

class CardDetailActivity : BaseActivity(), View.OnClickListener {

    var tag = ""

    @SuppressLint("SimpleDateFormat")
    override fun initViews() {
        iv_close.setOnClickListener { finish() }
        val ifDateOf = intent.getStringExtra("outOffDate")
        tag = intent.getStringExtra("tag")
        val data = intent.extras.getSerializable("data") as CodeListModel
        //生成二维码
        //对placeId进行加密操作
        var placeId = EncrypAndDecrypUtil.base64Encoder(data.placeId)
        placeId = EncrypAndDecrypUtil.SHA1(placeId)
        placeId = EncrypAndDecrypUtil.base64Encoder(placeId)
        val bitMap = QrCodeUtils.createQRCode(placeId, 700, 700, null)
        iv_qr.setImageBitmap(bitMap)
        //设置场所名称
        tv_dep.text = data.placeName
        //如果不是工作证，那么设置过期时间
        if (tag != ConstantString.DETAIL_PLACE_CARD) {
            val sp = SimpleDateFormat("yyyy-MM-dd")
            tv_date.text = "有效期至:${sp.format(data.expireTime.toLong())}"
        }
        //根据不同卡证设置不同的背景
        setUiEffect(ifDateOf, tag, data)
//重新申请点击事件
        tv_reapplay.setOnClickListener(this)
        btn_reapply.setOnClickListener(this)
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_card_detail)
    }


    private fun setUiEffect(
        ifDateOf: String?,
        tag: String?,
        data: CodeListModel
    ) {
        when (tag) {
            ConstantString.DETAIL_CAR_CARD -> {
                //车辆通行证
                with(data) {
                    cb_eye.visibility = View.GONE
                    tv_line_1.text = "【${carNumber}】车辆出入证"
                    val limit = when (codeTypeDataType) {
                        MY_CODE_PASS_TYPE_WORKDAYS -> {
                            "工作日"
                        }
                        MY_CODE_PASS_TYPE_NORMAL -> {
                            "无限制"
                        }
                        else -> {
                            ""
                        }
                    }
                    tv_line_2.text = "$limit${codeTypeTimeRange}可用"
                    tv_date_useful.text = "${codeTypeName}(${codeTypeLable})"
                    tv_date_useful.setBackgroundResource(R.drawable.shape_date_blue)
                }
                when (ifDateOf) {
                    "2" -> {
                        //过期了
                        rl_code.setBackgroundResource(R.drawable.kz_a_big_grey)
                        rl_out_of_date.visibility = View.VISIBLE

                    }
                    "1" -> {
                        //即将过期
                        rl_code.setBackgroundResource(R.drawable.kz_a_big)
                        rl_out_of_date.visibility = View.GONE
                        btn_reapply.visibility = View.VISIBLE
                        tv_date_useful.visibility = View.VISIBLE
                        tv_out_off_date.visibility = View.VISIBLE

                    }
                    else -> {
                        //没过期
                        rl_code.setBackgroundResource(R.drawable.kz_a_big)
                        rl_out_of_date.visibility = View.GONE
                    }
                }


            }
            ConstantString.DETAIL_PERSON_CARD -> {
                //人员通行证
                with(data) {
                    //设置脱敏信息可以自由切换
                    val name =
                        SharedPreferenceUtil.getPersonalInfoModel(this@CardDetailActivity).nickName
                    tv_line_1.text = "【${PrivacyUtil.nameDesensitization(name)}】人员出入证"
                    cb_eye.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            tv_line_1.text = "【$name】人员出入证"
                        } else {
                            tv_line_1.text = "【${PrivacyUtil.nameDesensitization(name)}】人员出入证"
                        }
                    }
                    val limit = when (codeTypeDataType) {
                        MY_CODE_PASS_TYPE_WORKDAYS -> {
                            "工作日"
                        }
                        MY_CODE_PASS_TYPE_NORMAL -> {
                            "无限制"
                        }
                        else -> {
                            ""
                        }
                    }
                    tv_line_2.text = "$limit${codeTypeTimeRange}可用"
                    tv_date_useful.text = "${codeTypeName}(${codeTypeLable})"
                    tv_date_useful.setBackgroundResource(R.drawable.shape_date_blue)
                }
                when (ifDateOf) {
                    "2" -> {
                        //过期了
                        rl_code.setBackgroundResource(R.drawable.kz_b_big_grey)
                        rl_out_of_date.visibility = View.VISIBLE

                    }
                    "1" -> {
                        //即将过期
                        rl_code.setBackgroundResource(R.drawable.kz_b_big)
                        rl_out_of_date.visibility = View.GONE
                        btn_reapply.visibility = View.VISIBLE
                        tv_date_useful.visibility = View.VISIBLE
                        tv_out_off_date.visibility = View.VISIBLE
                    }
                    else -> {
                        //没过期
                        rl_code.setBackgroundResource(R.drawable.kz_b_big)
                        rl_out_of_date.visibility = View.GONE
                    }
                }
            }
            ConstantString.DETAIL_PLACE_CARD -> {
                //工作证
                //设置前两行信息
                //设置脱敏信息可以自由切换
                val name =
                    SharedPreferenceUtil.getPersonalInfoModel(this@CardDetailActivity).nickName
                tv_line_1.text = "【${PrivacyUtil.nameDesensitization(name)}】工作证"
                cb_eye.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        tv_line_1.text = "【$name】工作证"
                    } else {
                        tv_line_1.text = "【${PrivacyUtil.nameDesensitization(name)}】工作证"
                    }
                }
                if (data.postName.isNullOrEmpty()) {
                    tv_line_2.text = data.deptName
                } else {
                    tv_line_2.text = "${data.deptName}--${data.postName}"
                }
                //没过期(工作证，没有过期的概念)
                rl_code.setBackgroundResource(R.drawable.kz_c_big)
                rl_out_of_date.visibility = View.GONE


            }
        }
    }

    /**
     *  @describe 页面内点击事件
     *  @return
     *  @author HanXueFeng
     *  @time 2020/6/9 0009  17:08
     */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_reapplay, R.id.btn_reapply -> {
//               重新申请按钮点击事件
                when (tag) {
                    ConstantString.DETAIL_CAR_CARD -> {
                        //车辆通行证重新申请
                        startActivity<CarCardApplyActivity>()
                    }
                    ConstantString.DETAIL_PERSON_CARD -> {
                        //人员通行证重新申请
                        startActivity<PersonCardApplyActivity>()
                    }
                }
            }
        }
    }


}
