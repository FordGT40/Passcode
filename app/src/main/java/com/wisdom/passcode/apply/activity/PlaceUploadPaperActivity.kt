package com.wisdom.passcode.apply.activity

import android.content.Intent
import android.net.Uri
import android.text.Html
import com.bumptech.glide.Glide
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.apply.model.UpLoadPaperModel
import com.wisdom.passcode.base.ActivityManager
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.helper.PopWindowHelper
import com.wisdom.passcode.util.*
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import kotlinx.android.synthetic.main.activity_upload_paper_licence.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File

class PlaceUploadPaperActivity : BaseActivity() {

    private var picPath = ""//公函的本地路径
    override fun initViews() {
        setTitle(R.string.title_upload_paper)

        text_paper_tample.text = Html.fromHtml(ConstantString.LICENCEPAPER)
        //接到上级页面传过来的参数
        val model = intent.extras.getSerializable("data") as UpLoadPaperModel
        btn_submit.setOnClickListener {
            if (checkPage()) {
                //走上传接口
                uploadInfo(model)
            }
        }
        //上传公函按钮点击事件
        btn_upload.setOnClickListener {
            PopWindowHelper(this).showUploadPop(this)
        }

    }

    /**
     *  @describe 上传数据到接口
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/26 0026  09:09
     */
    private fun uploadInfo(model: UpLoadPaperModel) {
        val idCardNum = EncrypAndDecrypUtil.encrypt(model.legalIdCard)
        val params = HttpParams()
        params.put("type", model.type)
        params.put("name", model.name)
        params.put("uscc", model.uscc)
        params.put("province", model.province)
        params.put("city", model.city)
        params.put("prefecture", model.prefecture)
        params.put("detailedAddress", model.detailedAddress)
        params.put("legalName", model.legal)
        params.put("legalIdCard", idCardNum)
        params.put("accountSliceType", model.accountSliceType)
        params.put("licenseImgFile", File(model.licenseImgFile))
        params.put("applyLetterFile", File(picPath))
        val paramList = listOf(
            "type${model.type}"
            , "name${model.name}"
            , "uscc${model.uscc}"
            , "province${model.province}"
            , "city${model.city}"
            , "prefecture${model.prefecture}"
            , "detailedAddress${model.detailedAddress}"
            , "legalName${model.legal}"
            , "legalIdCard$idCardNum"
            , "accountSliceType${model.accountSliceType}"
        ).toMutableList()


        LogUtil.getInstance().e("type:${model.type}")
        LogUtil.getInstance().e("name:${model.name}")
        LogUtil.getInstance().e("uscc:${model.uscc}")
        LogUtil.getInstance().e("province:${model.province}")
        LogUtil.getInstance().e("city:${model.city}")
        LogUtil.getInstance().e("prefecture:${model.prefecture}")
        LogUtil.getInstance().e("detailedAddress:${model.detailedAddress}")
        LogUtil.getInstance().e("legalName:${model.legal}")
        LogUtil.getInstance().e("legalIdCard:$idCardNum")
        LogUtil.getInstance().e("licenseImgFile:${model.licenseImgFile}")
        LogUtil.getInstance().e("applyLetterFile:$picPath")
        LogUtil.getInstance().e("accountSliceType:${model.accountSliceType}")



        Tools.showLoadingDialog(this)
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.APPLY_PLACE_CODE_URL,
            params,
            paramList,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    uploadInfo(model)
                }

                override fun onRefreshFail(msg: String?) {

                }
            }) {
                override fun onInterfaceSuccess(
                    jsonObject: JSONObject?,
                    call: Call?,
                    response: Response?
                ) {
                    Tools.closeDialog()
                    val code = jsonObject!!.getInt("code")
                    if (code == 0) {
                        AlertUtil.showMsgAlert(
                            this@PlaceUploadPaperActivity,
                            R.string.hint36
                        ) { _, _ ->
                            //点击确定
                            ActivityManager.getActivityManagerInstance().clearAllActivity()
                            this@PlaceUploadPaperActivity.finish()
                        }
                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                    }
                }

                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                    Tools.closeDialog()
                    toast(R.string.error_msg)
                }

            }

        )

    }


    /**
     *  @describe 检查页面空值
     *  @return
     *  @author HanXueFeng
     *  @time 2020/5/25 0025  17:33
     */
    private fun checkPage(): Boolean {
        var isCheck = true
        if (picPath == "") {
            isCheck = false
            toast(R.string.upload_paper_hint)
        } else if (!cb_licences.isChecked) {
            isCheck = false
            toast(R.string.upload_paper_cb_hint)
        } else {
            //验证通过

        }
        return isCheck
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_upload_paper_licence)
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
            Glide.with(this@PlaceUploadPaperActivity).load(picPath).into(iv_pic)
        }
    }
}
