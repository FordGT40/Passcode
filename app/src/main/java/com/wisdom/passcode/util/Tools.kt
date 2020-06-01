package com.wisdom.passcode.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.view.WindowManager
import android.widget.TextView
import cn.bertsir.zbar.QrConfig
import cn.bertsir.zbar.QrManager
import cn.bertsir.zbar.view.ScanLineView
import com.wisdom.passcode.R
import com.wisdom.passcode.widght.CustomProgressDialog
import com.wisdom.passcode.widght.NoLineClickSpan
import java.text.SimpleDateFormat
import java.util.*


/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.util
 * @class describe：
 * @author HanXueFeng
 * @time 2020/4/30 0030 15:41
 * @change
 */
class Tools {
      
    companion object {
        private  var progressDialog: CustomProgressDialog?=null
        //将像素转换为px
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        //将px转换为dp
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         *  @describe
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/9 0009  09:40
         */
        fun getClickableSpan(
            str: String,//待操作文字
            startIndex: Int,//开始变色的索引
            endIndex: Int,//结束变色的索引
            color: Int,//文字更改成的颜色
            ifUnderLine: Boolean,//文字是否添加下划线
            listener: OnColoredStringClickedListener?//高亮文字的点击事件（可传null）
        ): SpannableString? {
            val spannableString =
                SpannableString(str)
//设置点击事件，以及是否有下划线
            spannableString.setSpan(
                object : NoLineClickSpan() {
                    //高亮文字的点击事件
                    override fun onClick() {
                        listener?.onColoredStringClickedListener()
                    }

                    //是否存在下划线
                    override fun hasLineDown(ds: TextPaint) {
                        ds.isUnderlineText = ifUnderLine
                    }

                },
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            //设置文字的前景色
            spannableString.setSpan(
                ForegroundColorSpan(color),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannableString
        }


        /**
         *  @describe 开启扫一扫界面
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/11 0011  13:21
         */
        fun startScanActivity(
            context: Context,
            activity: Activity,
            resultCall: QrManager.OnScanResultCallback
        ) {
            //扫描人员码，二维码
            QrManager.getInstance().init(getQrConfig(context)).startScan(activity, resultCall)
        }


        /**
         *  @describe 获得扫一扫操作配置实例对象
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/7 0007  13:15
         */
        private fun getQrConfig(context: Context): QrConfig {
            return QrConfig.Builder()
                .setDesText(context.getString(R.string.scan_qr3)) //扫描框下文字
                .setShowDes(true) //是否显示扫描框下面文字
                .setShowLight(true) //显示手电筒按钮
                .setShowTitle(false) //显示Title
                .setShowAlbum(false) //显示从相册选择按钮
                .setCornerColor(Color.TRANSPARENT) //设置扫描框颜色
                .setLineColor(Color.parseColor("#008DFF")) //设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM) //设置扫描线速度
                .setScanType(QrConfig.TYPE_QRCODE) //设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE) //设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25) //此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true) //是否扫描成功后bi~的声音
                .setNeedCrop(true) //从相册选择二维码之后再次截取二维码
                .setIsOnlyCenter(false) //是否只识别框中内容(默认为全屏识别)
                .setTitleText(context.getString(R.string.scan_qr2)) //设置Tilte文字
                .setTitleBackgroudColor(Color.BLUE) //设置状态栏颜色
                .setTitleTextColor(Color.BLACK) //设置Title文字颜色
                .setShowZoom(false) //是否手动调整焦距
                .setAutoZoom(true) //是否自动调整焦距
                .setFingerZoom(false) //是否开始双指缩放
                .setScreenOrientation(QrConfig.SCREEN_PORTRAIT) //设置屏幕方向
                .setDoubleEngine(false) //是否开启双引擎识别(仅对识别二维码有效，并且开启后只识别框内功能将失效)
                .setOpenAlbumText(context.getString(R.string.get_qr_icon)) //打开相册的文字
                .setLooperScan(false) //是否连续扫描二维码
                .setLooperWaitTime(5 * 1000) //连续扫描间隔时间
                .setScanLineStyle(ScanLineView.style_radar) //扫描动画样式
                .setAutoLight(false) //自动灯光
                .setShowVibrator(false)//是否震动提醒
                .create()
        }

        interface OnColoredStringClickedListener {
            fun onColoredStringClickedListener()
        }


        /**
         * 显示加载对话框 加载数据的对话框
         */
        fun showLoadingDialog(context: Context?) {
            progressDialog = CustomProgressDialog(context, "正在加载.....")
            if (!progressDialog!!.isShowing) {
                progressDialog!!.setCanceledOnTouchOutside(false)
                progressDialog!!.show()
            }
        }
        fun showCustomDialog(
            context: Context?,
            content: String?
        ) {
            progressDialog = CustomProgressDialog(context, content)
            if (!progressDialog!!.isShowing) {
                progressDialog!!.show()
                progressDialog!!.setCanceledOnTouchOutside(false)
            }
        }

        // 关闭对话框
        fun closeDialog() {
            progressDialog?.dismiss()
        }

        /**
         *  @describe 禁止手机截屏操作
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/21 0021  09:52
         */
        fun forbiddenScreenShort(activity: Activity){
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }

        /**
         *  @describe 设置屏幕亮度的方法 （亮度取值范围：0-255）
         *  @return
         *  @author HanXueFeng
         *  @time 2019/6/21  15:21
         */
         fun setScreenLight(context: Activity, brightness: Int) {
            val lp = context.window.attributes
            lp.screenBrightness = ((brightness) * (1f / 255f)).toFloat()
            context.window.attributes = lp;
        }

        //将时间戳转换为时间
        fun stampToDate(s: Long): String? {
            return  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(s))
        }

        /**
         * 设置特殊字体
         * @param activity
         * @param textView
         */
        fun setFont(activity: Activity, textView: TextView) {
            val tf = Typeface.createFromAsset(activity.assets, "fonts/card_title_font.ttf")
            textView.typeface = tf
        }
    }


}