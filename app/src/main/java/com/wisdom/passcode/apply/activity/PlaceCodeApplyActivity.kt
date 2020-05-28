package com.wisdom.passcode.apply.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citypickerview.CityPickerView
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.model.UpLoadPaperModel
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.helper.PopWindowHelper
import com.wisdom.passcode.util.FileUtils
import com.wisdom.passcode.util.LogUtil
import com.wisdom.passcode.util.RegularUtil
import com.wisdom.passcode.util.StrUtils
import kotlinx.android.synthetic.main.activity_place_code_apply.*
import org.jetbrains.anko.toast


class PlaceCodeApplyActivity : BaseActivity() {
    var provinceId = ""
    var cityId = ""
    var districtId = ""
    var picPath = ""//证照路径
    var placeType = ""//场所类型，上一个页面传进来的
    var paperType = ""

    private val mPicker = CityPickerView()
    override fun initViews() {
        setTitle(R.string.title_apply_code_place)
        placeType = intent.getStringExtra("data")
        //初始化地市选择滚轮数据
        mPicker.init(this)
        //获得当前场所类型
        when (placeType) {
            ConstantString.PLACE_TYPE_GOVERNMENT -> {
                //政府
                tv_place_type.text = getString(R.string.place_type_gov)
                rl_person_id.visibility = View.GONE
                rl_person_name.visibility = View.GONE

            }
            ConstantString.PLACE_TYPE_COMPANY -> {
                //企业
                tv_place_type.text = getString(R.string.place_type_company)
                rl_person_id.visibility = View.VISIBLE
                rl_person_name.visibility = View.VISIBLE
            }
            ConstantString.PLACE_TYPE_OTHER -> {
                //其他类型
                tv_place_type.text = getString(R.string.place_type_other)
                rl_person_id.visibility = View.GONE
                rl_person_name.visibility = View.GONE
            }
        }
//        页面内单选按钮的点击事件
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_type_a -> {
//                    多证合一营业执照
                    iv_example.setImageResource(R.drawable.paper_c)
                    paperType = ConstantString.PAPER_TYPE_A
                }
                R.id.radio_type_b -> {
//                    普通营业执照
                    iv_example.setImageResource(R.drawable.paper_a)
                    paperType = ConstantString.PAPER_TYPE_B
                }
                R.id.radio_type_c -> {
//                    事业单位法人证书或统一社会信用代码证书
                    iv_example.setImageResource(R.drawable.paper_b)
                    paperType = ConstantString.PAPER_TYPE_C
                }
                R.id.radio_type_d -> {
//                    普通组织机构代码证
                    iv_example.setImageResource(R.drawable.paper_c)
                    paperType = ConstantString.PAPER_TYPE_D
                }
            }
        }
        //根据场所类型控制Radio的显隐性
        setRadioButton(placeType)
//        地市选择功能点击事件
        val cityConfig = CityConfig.Builder().build()
        cityConfig.defaultProvinceName = "黑龙江省"
        cityConfig.defaultCityName = "哈尔滨市"
        cityConfig.defaultDistrict = "道里区"
        mPicker.setConfig(cityConfig)
        mPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onCancel() {
                super.onCancel()
            }

            @SuppressLint("SetTextI18n")
            override fun onSelected(
                province: ProvinceBean?,
                city: CityBean?,
                district: DistrictBean?
            ) {
                super.onSelected(province, city, district)
                provinceId = province!!.id
                cityId = city!!.id
                districtId = district!!.id
                tv_card_type.text = "${province.name} ${city.name} ${district.name}"
            }
        })
        tv_card_type.setOnClickListener {
            mPicker.showCityPicker()
        }


        //选择上传文件的点击事件
        btn_upload.setOnClickListener {
            PopWindowHelper(this).showUploadPop(this)
        }
        //下一步按钮点击事件
        btn_next.setOnClickListener {
            if (checkPageData()) {
                //验证通过
                createDataStartActivity()
            }
        }

    }


    override fun setlayoutIds() {
        setContentView(R.layout.activity_place_code_apply)
    }

    /**
     *  @describe 检查页面信息是否填写完整
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/25 0025  13:56
     */
    private fun checkPageData(): Boolean {
        var isChecked = true
        if (StrUtils.isEdtTxtEmpty(tv_dep)) {
            isChecked = false
            toast(R.string.hint18)
        } else if (StrUtils.isEdtTxtEmpty(tv_place_paper_code)) {
            isChecked = false
            toast(R.string.hint19)
        } else if (provinceId == "") {
            isChecked = false
            toast(R.string.hint20)
        } else if (StrUtils.isEdtTxtEmpty(et_person_id_name) && placeType == ConstantString.PLACE_TYPE_COMPANY) {
            isChecked = false
            toast(R.string.hint22)
        } else if (StrUtils.isEdtTxtEmpty(et_person_phone) && placeType == ConstantString.PLACE_TYPE_COMPANY) {
            isChecked = false
            toast(R.string.hint23)
        } else if (!RegularUtil.isValidate18Idcard(et_person_phone.text.toString()) && placeType == ConstantString.PLACE_TYPE_COMPANY) {
            isChecked = false
            toast(R.string.hint33)
        } else if (picPath == "") {
            isChecked = false
            toast(R.string.hint34)
        }
        return isChecked
    }

    /**
     *  @describe 页面回调方法
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/25 0025  17:12
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ConstantString.ALBUM_SELECT_CODE -> {
                //相册选择
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the Uri of the selected file
                        val uri = data.data
                        val uri2 =
                            Uri.parse(Uri.encode(uri.toString()))
                        picPath = FileUtils.getPath(this, uri)

                    }
                }
            }
            ConstantString.FILE_SELECT_CODE -> {
                //文件选择器选择
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the Uri of the selected file
                        val uri = data.data
                        picPath = FileUtils.getPathByUri4kitkat(this, uri)
                    }
                }
            }
            ConstantString.REQUEST_CAMERA -> {

            }
            else -> {
                //相机拍照选择
                picPath = ConstantString.PIC_LOCATE
                LogUtil.getInstance().e("pic:$picPath")
            }
        }
        if (picPath != "") {
            iv_paper.visibility = View.VISIBLE
            Glide.with(this@PlaceCodeApplyActivity).load(picPath).into(iv_paper)
        }
    }

    /**
     *  @describe 通过页面验证，跳转下一页
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/25 0025  17:14
     */
    private fun createDataStartActivity() {
        val model = UpLoadPaperModel()
        model.type = placeType
        model.name = tv_dep.text.toString()
        model.uscc = tv_place_paper_code.text.toString()
        model.province = provinceId
        model.city = cityId
        model.prefecture = districtId
        model.detailedAddress = et_addr.text.toString()

        if (et_person_id_name.text.toString().isEmpty()) {
            model.legal = ""
            model.legalIdCard = ""
        } else {
            model.legal = et_person_id_name.text.toString()
            model.legalIdCard = et_person_phone.text.toString()
        }
        model.accountSliceType = paperType
        model.licenseImgFile = picPath
        val bundle = Bundle()
        bundle.putSerializable("data", model)
        val intent = Intent(this, PlaceUploadPaperActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    /**
     *  @describe 根据选择的场所类型，展示不同的radio
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/26 0026  14:58
     */
    private fun setRadioButton(placeType: String?) {
        when (placeType) {
            ConstantString.PLACE_TYPE_GOVERNMENT -> {
                //政府
                radio_type_a.visibility = View.GONE
                radio_type_b.visibility = View.GONE
                radio_type_c.visibility = View.VISIBLE
                radio_type_d.visibility = View.VISIBLE

                hint_1.visibility = View.GONE
                hint_2.visibility = View.GONE
                hint_3.visibility = View.VISIBLE
                hint_4.visibility = View.VISIBLE
                radio_type_c.isChecked = true
            }
            else -> {
                //企业,其他
                radio_type_a.visibility = View.VISIBLE
                radio_type_b.visibility = View.VISIBLE
                radio_type_c.visibility = View.GONE
                radio_type_d.visibility = View.GONE

                hint_1.visibility = View.VISIBLE
                hint_2.visibility = View.VISIBLE
                hint_3.visibility = View.GONE
                hint_4.visibility = View.GONE
                radio_type_a.isChecked = true
            }

        }
    }
}
