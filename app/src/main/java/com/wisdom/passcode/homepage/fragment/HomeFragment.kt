package com.wisdom.passcode.homepage.fragment

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.bertsir.zbar.QrManager
import com.google.android.material.appbar.AppBarLayout
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.activity.CarCardApplyActivity
import com.wisdom.passcode.apply.activity.PersonCardApplyActivity
import com.wisdom.passcode.apply.activity.PlaceCodePlaceTypeChooseActivity
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.helper.Helper
import com.wisdom.passcode.homepage.activity.AdminPlaceChooseActivity
import com.wisdom.passcode.homepage.activity.MyCardsActivity
import com.wisdom.passcode.homepage.activity.ShowCodeActivity
import com.wisdom.passcode.scanback.activity.ScanBackMainActivity
import com.wisdom.passcode.scanback.helper.ScanHelper
import com.wisdom.passcode.scanback.model.GetScanResultModel
import com.wisdom.passcode.util.Tools
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_open.*
import kotlinx.android.synthetic.main.include_toolbar_close.*
import kotlinx.android.synthetic.main.include_toolbar_open.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import kotlin.math.abs


class HomeFragment : Fragment(), View.OnClickListener, AppBarLayout.OnOffsetChangedListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app_bar.addOnOffsetChangedListener(this)
        ll_show_code.setOnClickListener(this)
        ll_apply_card.setOnClickListener(this)
        tv_head_scan.setOnClickListener(this)
        ll_admin_scan_person.setOnClickListener(this)
        tv_cards_more.setOnClickListener(this)
        ll_scan.setOnClickListener(this)
        tv_head_show_code.setOnClickListener(this)
        iv_ad.setOnClickListener(this)
        ll_apply_place.setOnClickListener(this)
        ll_apply_id.setOnClickListener(this)
        ll_show_card.setOnClickListener(this)
        tv_head_show_card.setOnClickListener(this)
        ll_top.setOnClickListener(this)


    }

    override fun onResume() {
        super.onResume()
        if (ConstantString.isAdmin.toBoolean()) {
            //是管理员
            rl_admin.visibility = View.VISIBLE
            //设置管理员场所信息
            if (SharedPreferenceUtil.getAdminPlaceInfo(context) != null) {
                //存在之前选的
                ConstantString.adminPlaceModel = SharedPreferenceUtil.getAdminPlaceInfo(context)
                tv_place_name.text = SharedPreferenceUtil.getAdminPlaceInfo(context).placeName
            } else {
                //不存在之前选好的地点信息
                ConstantString.adminPlaceModel =
                    SharedPreferenceUtil.getPersonalInfoModel(context).placeList[0]
                tv_place_name.text =
                    SharedPreferenceUtil.getPersonalInfoModel(context).placeList[0].placeName
            }

        } else {
            //非管理员
            rl_admin.visibility = View.GONE
        }
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_top -> {
                //管理员选择场所
                val intent=Intent(context,AdminPlaceChooseActivity::class.java)
                intent.putExtra("placeName",tv_place_name.text.toString())
                ll_place.visibility=View.INVISIBLE
                startActivityForResult(intent,ConstantString.REQUEST_CODE, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
            }
            R.id.iv_ad -> {
                startActivity<ScanBackMainActivity>()
            }
            R.id.ll_show_card, R.id.tv_head_show_card -> {
                //亮证
                startActivity<MyCardsActivity>()
            }
            R.id.ll_apply_id -> {
                //人员码申请
                startActivity<PersonCardApplyActivity>()
            }
            R.id.ll_admin_scan_person, R.id.ll_scan, R.id.tv_head_scan -> {
                //扫描人员码，二维码
                Tools.startScanActivity(context!!, activity!!,
                    QrManager.OnScanResultCallback { result ->
                        val content = result.content
                        if (content.contains(ConstantString.PLACE_QRCODE_HEAD_URL)) {
                            //扫描的是场所码
                            val placeCode =
                                content.replace(ConstantString.PLACE_QRCODE_HEAD_URL, "")
                            val data = GetScanResultModel()
                            data.type = ConstantString.SCAN_CODE_TYPE_WALL_CODE
                            data.carNumber = ""
                            data.passCodeId = ""
                            data.placeCodeEncryption = placeCode
                            data.userIdEncryption = ConstantString.userIdEncryption
                            ScanHelper.getScanPlaceName(context!!, data)

                        } else {
                            toast("扫的不是场所码")
                        }
                    })
            }
            R.id.ll_apply_card -> {
                //车辆出入码申请
                startActivity<CarCardApplyActivity>()
            }
            R.id.tv_cards_more -> {
                //更多卡证页面（我的卡证）
                startActivity<MyCardsActivity>()
            }
            R.id.ll_apply_place -> {
                //场所码申请
                startActivity<PlaceCodePlaceTypeChooseActivity>()
            }
            R.id.ll_show_code, R.id.tv_head_show_code -> {
                //亮码页面
                if (Helper.checkUserState(context)) {
                    startActivity<ShowCodeActivity>()
                }
            }

        }
    }


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        //垂直方向偏移量
        val offset = abs(verticalOffset)
        //最大偏移距离
        val scrollRange = appBarLayout!!.totalScrollRange
        if (offset <= scrollRange / 2) { //当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            include_toolbar_open.visibility = View.VISIBLE
            include_toolbar_close.visibility = View.GONE
            //根据偏移百分比 计算透明值
            val scale2 = offset.toFloat() / (scrollRange / 2)
            val alpha2 = (255 * scale2).toInt()
            bg_toolbar_open.setBackgroundColor(Color.argb(alpha2, 25, 131, 209))
        } else { //当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            include_toolbar_close.visibility = View.VISIBLE
            include_toolbar_open.visibility = View.GONE
            val scale3 = (scrollRange - offset).toFloat() / (scrollRange / 2)
            val alpha3 = (255 * scale3).toInt()
            bg_toolbar_close.setBackgroundColor(Color.argb(alpha3, 25, 131, 209))
        }

        //根据偏移百分比计算扫一扫布局的透明度值
        val scale = offset.toFloat() / scrollRange
        val alpha = (255 * scale).toInt()
        bg_content.setBackgroundColor(Color.argb(alpha, 25, 131, 209))

    }

    override fun onDestroy() {
        super.onDestroy()
        if (app_bar != null) {
            app_bar.removeOnOffsetChangedListener(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            ConstantString.CODE_PLACE_CHOOSE->{
//                选择完场所了
                ll_place.visibility=View.VISIBLE
            }
        }
    }

}
