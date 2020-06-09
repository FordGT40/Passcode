package com.wisdom.passcode.homepage.activity

import android.graphics.Color
import android.view.View
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.homepage.model.CodeListModel
import com.wisdom.passcode.util.Tools
import kotlinx.android.synthetic.main.activity_card_detail.*

class CardDetailActivity : BaseActivity() {


    override fun initViews() {
        iv_close.setOnClickListener { finish() }
        val ifDateOf = intent.getStringExtra("outOffDate")
        val tag = intent.getStringExtra("tag")
        val data=intent.extras.getSerializable("data") as CodeListModel
        //根据不同卡证设置不同的背景
        setUiEffect(ifDateOf, tag,data)

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
                when (ifDateOf) {
                    "2" -> {
                        //过期了
                        rl_code.setBackgroundResource(R.drawable.kz_a_big_grey)
                        rl_out_of_date.visibility = View.VISIBLE

                        tv_dep.setTextColor(Color.parseColor("#333333"))
                        tv_user_name.setTextColor(Color.parseColor("#666666"))
                        tv_user_dep.setTextColor(Color.parseColor("#666666"))
                        tv_user_num.setTextColor(Color.parseColor("#666666"))
                        tv_date.setTextColor(Color.parseColor("#666666"))
                    }
                    "1" -> {
                        //即将过期
                        rl_code.setBackgroundResource(R.drawable.kz_a_big)
                        rl_out_of_date.visibility = View.GONE
                        val str = Tools.getClickableSpan(
                            "有效期至：2020-06-01 (即将过期)"
                            ,
                            15, 22, Color.parseColor("#FE3237"), false, null
                        )
                        tv_date.text = str

                        tv_dep.setTextColor(Color.parseColor("#00447B"))
                        tv_user_name.setTextColor(Color.parseColor("#00447B"))
                        tv_user_dep.setTextColor(Color.parseColor("#00447B"))
                        tv_user_num.setTextColor(Color.parseColor("#00447B"))
                        tv_date.setTextColor(Color.parseColor("#A1C4E4"))
                    }
                    else -> {
                        //                    没过期
                        rl_code.setBackgroundResource(R.drawable.kz_a_big)
                        rl_out_of_date.visibility = View.GONE

                        tv_dep.setTextColor(Color.parseColor("#00447B"))
                        tv_user_name.setTextColor(Color.parseColor("#00447B"))
                        tv_user_dep.setTextColor(Color.parseColor("#00447B"))
                        tv_user_num.setTextColor(Color.parseColor("#00447B"))
                        tv_date.setTextColor(Color.parseColor("#A1C4E4"))
                    }
                }


            }
            ConstantString.DETAIL_PERSON_CARD -> {
                when (ifDateOf) {
                    "2" -> {
                        //过期了
                        rl_code.setBackgroundResource(R.drawable.kz_b_big_grey)
                        rl_out_of_date.visibility = View.VISIBLE
                        tv_dep.setTextColor(Color.parseColor("#333333"))
                        tv_user_name.setTextColor(Color.parseColor("#666666"))
                        tv_user_dep.setTextColor(Color.parseColor("#666666"))
                        tv_user_num.setTextColor(Color.parseColor("#666666"))
                        tv_date.setTextColor(Color.parseColor("#666666"))
                    }
                    "1" -> {
                        //即将过期
                        rl_code.setBackgroundResource(R.drawable.kz_b_big)
                        rl_out_of_date.visibility = View.GONE
                        val str = Tools.getClickableSpan(
                            "有效期至：2020-06-01 (即将过期)"
                            ,
                            15, 22, Color.parseColor("#FE3237"), false, null
                        )
                        tv_date.text = str

                        tv_dep.setTextColor(Color.parseColor("#793600"))
                        tv_user_name.setTextColor(Color.parseColor("#793600"))
                        tv_user_dep.setTextColor(Color.parseColor("#793600"))
                        tv_user_num.setTextColor(Color.parseColor("#793600"))
                        tv_date.setTextColor(Color.parseColor("#DDBB83"))
                    }
                    else -> {
                        //                    没过期
                        rl_code.setBackgroundResource(R.drawable.kz_b_big)
                        rl_out_of_date.visibility = View.GONE

                        tv_dep.setTextColor(Color.parseColor("#793600"))
                        tv_user_name.setTextColor(Color.parseColor("#793600"))
                        tv_user_dep.setTextColor(Color.parseColor("#793600"))
                        tv_user_num.setTextColor(Color.parseColor("#793600"))
                        tv_date.setTextColor(Color.parseColor("#DDBB83"))
                    }
                }

            }
            ConstantString.DETAIL_PLACE_CARD -> {
                when (ifDateOf) {
                    "2" -> {
                        //过期了
                        rl_code.setBackgroundResource(R.drawable.kz_c_big_grey)
                        rl_out_of_date.visibility = View.VISIBLE

                        tv_dep.setTextColor(Color.parseColor("#333333"))
                        tv_user_name.setTextColor(Color.parseColor("#666666"))
                        tv_user_dep.setTextColor(Color.parseColor("#666666"))
                        tv_user_num.setTextColor(Color.parseColor("#666666"))
                        tv_date.setTextColor(Color.parseColor("#666666"))
                    }
                    "1" -> {
                        //即将过期
                        rl_code.setBackgroundResource(R.drawable.kz_c_big)
                        rl_out_of_date.visibility = View.GONE
                        val str = Tools.getClickableSpan(
                            "有效期至：2020-06-01 (即将过期)"
                            ,
                            15, 22, Color.parseColor("#FE3237"), false, null
                        )
                        tv_date.text = str

                        tv_dep.setTextColor(Color.parseColor("#1E017B"))
                        tv_user_name.setTextColor(Color.parseColor("#1E017B"))
                        tv_user_dep.setTextColor(Color.parseColor("#1E017B"))
                        tv_user_num.setTextColor(Color.parseColor("#1E017B"))
                        tv_date.setTextColor(Color.parseColor("#A391DB"))
                    }
                    else -> {
                        //                    没过期
                        rl_code.setBackgroundResource(R.drawable.kz_c_big)
                        rl_out_of_date.visibility = View.GONE

                        tv_dep.setTextColor(Color.parseColor("#1E017B"))
                        tv_user_name.setTextColor(Color.parseColor("#1E017B"))
                        tv_user_dep.setTextColor(Color.parseColor("#1E017B"))
                        tv_user_num.setTextColor(Color.parseColor("#1E017B"))
                        tv_date.setTextColor(Color.parseColor("#A391DB"))
                    }
                }


            }
        }
    }


}
