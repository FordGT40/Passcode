package com.wisdom.passcode.faceId.faceAli

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.alibaba.android.mnnkit.actor.FaceDetector
import com.alibaba.android.mnnkit.actor.FaceDetector.FaceDetectorCreateConfig
import com.alibaba.android.mnnkit.entity.FaceDetectConfig
import com.alibaba.android.mnnkit.entity.MNNCVImageFormat
import com.alibaba.android.mnnkit.entity.MNNFlipType
import com.alibaba.android.mnnkit.intf.InstanceCreatedListener
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantString.Companion.userPhone
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.R
import com.wisdom.passcode.base.ActivityManager
import com.wisdom.passcode.base.BroadCastManager
import com.wisdom.passcode.faceId.IdentifyingActivity
import com.wisdom.passcode.faceId.faceAli.FaceDetectionActivity
import com.wisdom.passcode.helper.Helper
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.ImageUtil
import com.wisdom.passcode.util.Tools
import com.wisdom.passcode.util.httpUtil.HttpUtil
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback
import com.wisdom.passcode.util.speak.SpeakUtil
import kotlinx.android.synthetic.main.activity_face_detection.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*

class FaceDetectionActivity : VideoBaseActivity() {
    private var mDrawSurfaceHolder: SurfaceHolder? = null
    protected var mCameraView: CameraView? = null
    private var isSpeak1 = true
    private var isSpeak2 = false
    private var isDetection = false
    private var isDetection2 = false
    private var isFaceSuccess = false

    //    private Switch mOrderSwitch;
    //    private TextView mTimeCost;
    //    private TextView mFaceAction;
    //    private TextView mYPR;
    // 当前渲染画布的尺寸
    protected var mActualPreviewWidth = 0
    protected var mActualPreviewHeight = 0
    private var mFaceDetector: FaceDetector? = null
    private val KeyPointsPaint = Paint()
    private val PointOrderPaint = Paint()
    private val ScorePaint = Paint()
    private val scores =
        FloatArray(MAX_RESULT) // 置信度
    private val rects =
        FloatArray(MAX_RESULT * 4) // 矩形区域
    private val keypts =
        FloatArray(MAX_RESULT * 2 * 106) // 脸106关键点
    private val listAction = ArrayList<String>()
    var action1: String? = ""
    var action2 = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化动作数组
//        listAction.add("眉毛挑动");
//        listAction.add("眉毛挑动");
        listAction.toMutableList()
        listAction.add("摇头")
        listAction.add("摇头")
        listAction.add("点头")
        listAction.add("点头")
        listAction.add("点头")
        listAction.add("点头")
        listAction.add("眨眼")
        listAction.add("眨眼")
        listAction.add("眨眼")
        //        listAction.add("嘴巴大张");
//        listAction.add("嘴巴大张");
        //随机选中两个动作
        action1 = listAction[Random().nextInt(8)]
        action2 = ""
        object : Thread() {
            override fun run() {
                super.run()
                while (true) {
                    action2 = listAction[Random().nextInt(8)]!!
                    if (action1 != action2) {
                        return
                    }
                }
            }
        }.start()
        //
        KeyPointsPaint.color = Color.WHITE
        KeyPointsPaint.style = Paint.Style.FILL
        KeyPointsPaint.strokeWidth = 2f
        PointOrderPaint.color = Color.GREEN
        PointOrderPaint.style = Paint.Style.STROKE
        PointOrderPaint.strokeWidth = 2f
        PointOrderPaint.textSize = 18f
        ScorePaint.color = Color.WHITE
        ScorePaint.strokeWidth = 2f
        ScorePaint.textSize = 40f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_switch_camera -> mCameraView!!.switchCamera()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun createKitInstance() {
        val createConfig = FaceDetectorCreateConfig()
        createConfig.mode =
            FaceDetector.FaceDetectMode.MOBILE_DETECT_MODE_VIDEO
        FaceDetector.createInstanceAsync(
            this,
            createConfig,
            object : InstanceCreatedListener<FaceDetector?> {
                override fun onSucceeded(faceDetector: FaceDetector?) {
                    mFaceDetector = faceDetector
                }

                override fun onFailed(i: Int, error: Error) {
                    Log.e(Common.TAG, "create face detetector failed: $error")
                }
            })
    }

    public override fun actionBarTitle(): String {
        return "人脸检测"
    }

    public override fun doRun() {
        val stub = findViewById<ViewStub>(R.id.viewStub)
        stub.layoutResource = R.layout.activity_face_detection
        stub.inflate()

        // point view
        val drawView = findViewById<SurfaceView>(R.id.points_view)
        drawView.setZOrderOnTop(true)
        drawView.holder.setFormat(PixelFormat.TRANSPARENT)
        mDrawSurfaceHolder = drawView.holder

        // 点序
//        mOrderSwitch = findViewById(R.id.swPointOrder);
//        mTimeCost = findViewById(R.id.costTime);
//        mFaceAction = findViewById(R.id.faceAction);
//        mYPR = findViewById(R.id.ypr);

        camera_view!!.setPreviewCallback(object : CameraView.PreviewCallback {
            override fun onGetPreviewOptimalSize(
                optimalWidth: Int,
                optimalHeight: Int,
                cameraOrientation: Int,
                deviecAutoRotateAngle: Int
            ) {

                // w为图像短边，h为长边
                var w = optimalWidth
                var h = optimalHeight
                if (cameraOrientation == 90 || cameraOrientation == 270) {
                    w = optimalHeight
                    h = optimalWidth
                }

                // 屏幕长宽
                val metric = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(metric)
                val screenW = metric.widthPixels
                val screenH = metric.heightPixels
                val contentTop =
                    window.findViewById<View>(Window.ID_ANDROID_CONTENT)
                        .top
                val frame = Rect()
                window.decorView.getWindowVisibleDisplayFrame(frame)
                val statusBarHeight = frame.top
                val layoutVideo = findViewById<RelativeLayout>(R.id.videoLayout)
                val frameLayout =
                    layoutVideo.findViewById<FrameLayout>(R.id.videoContentLayout)
                if (deviecAutoRotateAngle == 0 || deviecAutoRotateAngle == 180) {
                    val fixedScreenH = screenW * h / w // 宽度不变，等比缩放的高度
                    val params = frameLayout.layoutParams
                    params.height = fixedScreenH
                    frameLayout.layoutParams = params
                    mActualPreviewWidth = screenW
                    mActualPreviewHeight = fixedScreenH
                } else {
                    val previewHeight = screenH - contentTop - statusBarHeight
                    val fixedScreenW = previewHeight * h / w // 高度不变，等比缩放的宽
                    val params = frameLayout.layoutParams
                    params.width = fixedScreenW
                    frameLayout.layoutParams = params
                    mActualPreviewWidth = fixedScreenW
                    mActualPreviewHeight = previewHeight

                    // re layout
                    val componentLayoutParams =
                        RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    componentLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.videoContentLayout)

//                    RelativeLayout componentLayout  = findViewById(R.id.componentLayout);
//                    componentLayout.setLayoutParams(componentLayoutParams);

//                    RelativeLayout.LayoutParams yprLayoutParams = news RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    yprLayoutParams.addRule(RelativeLayout.BELOW, R.id.costTime);
//                    mYPR.setPadding(24,0,0,0);
//                    mYPR.setLayoutParams(yprLayoutParams);

//                    RelativeLayout.LayoutParams faceActionLayoutParams = news RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    faceActionLayoutParams.addRule(RelativeLayout.BELOW, R.id.ypr);
//                    mFaceAction.setPadding(24,0,0,0);
//                    mFaceAction.setLayoutParams(faceActionLayoutParams);
                }
            }

            override fun onPreviewFrame(
                data: ByteArray,
                width: Int,
                height: Int,
                cameraOrientation: Int
            ) {
                if (mFaceDetector == null) {
                    return
                }

                // 输入角度
                val inAngle =
                    if (camera_view.isFrontCamera) (cameraOrientation + 360 - rotateDegree) % 360 else (cameraOrientation + rotateDegree) % 360
                // 输出角度
                var outAngle = 0
                if (!screenAutoRotate()) {
                    outAngle =
                        if (camera_view.isFrontCamera) (360 - rotateDegree) % 360 else rotateDegree % 360
                }
                val start = System.currentTimeMillis()
                val detectConfig =
                    FaceDetectConfig.ACTIONTYPE_EYE_BLINK or FaceDetectConfig.ACTIONTYPE_MOUTH_AH or FaceDetectConfig.ACTIONTYPE_HEAD_YAW or FaceDetectConfig.ACTIONTYPE_HEAD_PITCH or FaceDetectConfig.ACTIONTYPE_BROW_JUMP
                val results = mFaceDetector!!.inference(
                    data,
                    width,
                    height,
                    MNNCVImageFormat.YUV_NV21,
                    detectConfig,
                    inAngle,
                    outAngle,
                    if (camera_view.isFrontCamera) MNNFlipType.FLIP_Y else MNNFlipType.FLIP_NONE
                )
                var timeCostText = "0 ms"
                var yprText = ""
                val faceActionText = ""
                var faceCount = 0
                if (results != null && results.size > 0) {
                    faceCount = results.size

                    // time cost
                    timeCostText = (System.currentTimeMillis() - start).toString() + "ms"
                    // ypr
                    val firstReport = results[0]
                    yprText =
                        """
                        yaw: ${firstReport.yaw}
                        pitch: ${firstReport.pitch}
                        roll: ${firstReport.roll}

                        """.trimIndent()
                    //
                    var i = 0
                    while (i < results.size && i < MAX_RESULT) {

                        // key points 绘制关键点
//                        System.arraycopy(results[i].keyPoints, 0, keypts, i*106*2, 106*2);
                        // face rect
                        rects[i * 4] = results[i].rect.left.toFloat()
                        rects[i * 4 + 1] = results[i].rect.top.toFloat()
                        rects[i * 4 + 2] = results[i].rect.right.toFloat()
                        rects[i * 4 + 3] = results[i].rect.bottom.toFloat()
                        i++
                    }
                    //第一个动作播报，并开始监测
                    if (isSpeak1) {
                        isSpeak1 = false
                        SpeakUtil(this@FaceDetectionActivity).speak("请$action1")
                        isDetection = true
                        tv_info?.text = "请$action1"
                    }

                    //监测第一个动作
                    if (isDetection) {
                        if (faceActionDesc(firstReport.faceActionMap).contains(action1!!)) {
                            isDetection = false
                            isSpeak2 = true
                        }
                    }
                    //播报第二个动作
                    if (isSpeak2) {
                        isSpeak2 = false
                        SpeakUtil(this@FaceDetectionActivity).speak("请$action2")
                        isDetection2 = true
                        tv_info?.text = "请$action2"
                    }
                    //监听第二个动作
                    if (isDetection2) {
                        if (faceActionDesc(firstReport.faceActionMap).contains(action2)) {
                            isDetection2 = false
                            SpeakUtil(this@FaceDetectionActivity).speak("正在识别，请稍后……")
                            tv_info?.text = "正在识别，请稍后……"
                            isFaceSuccess = true
                            //走人脸识别前后台对比的操作
                            //识别成功访问思权接口,到服务器端验证人脸是否和身份证信息一致
                            val idCard = intent.getStringExtra("idCard")
                            var bitmap =
                                ImageUtil.getBitmapView(camera_view, data)
                            bitmap = ImageUtil.rotateBitmap(bitmap, 270f)
                            try {
                                //保存扫脸拍照到本地存储卡
//                                ImageUtil.saveMyBitmap(bitmap,"face.jpg");
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            startActivity<IdentifyingActivity>("data" to idCard)
                            try {
                                checkBitmap(bitmap)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }

//                mTimeCost.setText(timeCostText);
//                mYPR.setText(yprText);
//                mFaceAction.setText(faceActionText);
                DrawResult(scores, rects, keypts, faceCount, cameraOrientation, rotateDegree)
            }
        })
    }

    private fun faceActionDesc(faceActionMap: Map<String, Boolean>): String {
        var desc = ""
        if (faceActionMap.isEmpty()) {
            return desc
        }
        val actions: MutableList<String> =
            ArrayList()
        for ((key, bActing) in faceActionMap) {
            println("Key = $key, Value = $bActing")
            if (!bActing) continue
            if (key == "HeadYaw") {
                actions.add("摇头")
            }
            if (key == "BrowJump") {
                actions.add("眉毛挑动")
            }
            if (key == "EyeBlink") {
                actions.add("眨眼")
            }
            if (key == "MouthAh") {
                actions.add("嘴巴大张")
            }
            if (key == "HeadPitch") {
                actions.add("点头")
            }
        }
        for (i in actions.indices) {
            val action = actions[i]
            if (i > 0) {
                desc += "、$action"
                continue
            }
            desc = action
        }
        return desc
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mFaceDetector != null) {
            mFaceDetector!!.release()
        }
    }

    private fun DrawResult(
        scores: FloatArray, rects: FloatArray, facePoints: FloatArray,
        faceCount: Int, cameraOrientation: Int, rotateDegree: Int
    ) {
        var canvas: Canvas? = null
        try {
            canvas = mDrawSurfaceHolder!!.lockCanvas()
            if (canvas == null) {
                return
            }
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            var kx = 0.0f
            var ky = 0.0f

            // 这里只写了摄像头正向为90/270度的一般情况，如果有其他情况，自行枚举
            if (90 == cameraOrientation || 270 == cameraOrientation) {
                if (!screenAutoRotate()) {
                    kx = mActualPreviewWidth.toFloat() / camera_view.previewSize.height
                    ky = mActualPreviewHeight.toFloat() / camera_view.previewSize.width
                } else {
                    if (0 == rotateDegree || 180 == rotateDegree) { // 屏幕竖直方向翻转
                        kx =
                            mActualPreviewWidth.toFloat() / camera_view.previewSize.height
                        ky =
                            mActualPreviewHeight.toFloat() / camera_view.previewSize.width
                    } else if (90 == rotateDegree || 270 == rotateDegree) { // 屏幕水平方向翻转
                        kx =
                            mActualPreviewWidth.toFloat() / camera_view.previewSize.width
                        ky =
                            mActualPreviewHeight.toFloat() / camera_view.previewSize.height
                    }
                }
            }

            // 绘制人脸关键点
            for (i in 0 until faceCount) {
                for (j in 0..105) {
                    val keyX = facePoints[i * 106 * 2 + j * 2]
                    val keyY = facePoints[i * 106 * 2 + j * 2 + 1]
                    canvas.drawCircle(keyX * kx, keyY * ky, 4.0f, KeyPointsPaint)
                    //                    if (mOrderSwitch.isChecked()) {
//                        canvas.drawText(j+"", keyX * kx, keyY * ky, PointOrderPaint); //标注106点的索引位置
//                    }
                }
                val left = rects[0]
                val top = rects[1]
                val right = rects[2]
                val bottom = rects[3]
                canvas.drawLine(
                    left * kx, top * ky,
                    right * kx, top * ky, KeyPointsPaint
                )
                canvas.drawLine(
                    right * kx, top * ky,
                    right * kx, bottom * ky, KeyPointsPaint
                )
                canvas.drawLine(
                    right * kx, bottom * ky,
                    left * kx, bottom * ky, KeyPointsPaint
                )
                canvas.drawLine(
                    left * kx, bottom * ky,
                    left * kx, top * ky, KeyPointsPaint
                )

//TODO 注释掉绘制人脸识别置信度、位置等信息的代码
//                canvas.drawText(scores[i] + "", left * kx, top * ky - 10, ScorePaint);
            }
        } catch (t: Throwable) {
            Log.e(Common.TAG, "Draw result error: %s", t)
        } finally {
            if (canvas != null) {
                mDrawSurfaceHolder!!.unlockCanvasAndPost(canvas)
            }
        }
    }

    /**
     * 与后台进行人脸比对
     *
     * @param bitmap
     */
    @Throws(Exception::class)
    private fun checkBitmap(bitmap: Bitmap) {
        //关闭等待页面的广播
        val broadcastIntent = Intent()
        broadcastIntent.action = ConstantString.BROADCAST_FINISH_TAG
        val code = intent.getStringExtra("code")
        val idCard = intent.getStringExtra("idCard")
        val realName = intent.getStringExtra("realName")
        val base64 = ImageUtil.bitmapToBase64(bitmap)
        //加密
        val codeData = EncrypAndDecrypUtil.encrypt(code)
        val phoneData = EncrypAndDecrypUtil.encrypt(userPhone)
        val idCardData = EncrypAndDecrypUtil.encrypt(idCard)

        Log.i("*****code:", code)
        Log.i("*****idCard:", idCard)
        Log.i("*****realName:", realName)
        Log.i("*****phone:", userPhone)
        //构建参数
        val params = HttpParams()
        params.put("code", codeData)
        params.put("idCard", idCardData)
        params.put("realName", realName)
        params.put("phone", phoneData)
        params.put("imgBase64", base64)
        //构建签名数组(图片的base64太过冗长，不参与签名算法，否则一直签名错误)
        val listSign = listOf(
            "code$codeData",
            "idCard$idCardData",
            "realName$realName",
            "phone$phoneData"
//            , "imgBase64$base64"
        ).toMutableList()
        //访问网络，人脸，进行人脸对比操作
        Tools.showLoadingDialog(this)
        HttpUtil.httpPostWithStampAndSignToken(
            ConstantUrl.AUTHENTICATION_URL,
            params,
            listSign,
            object : StringsCallback(object : OnTokenRefreshSuccessListener {
                override fun onRefreshSuccess() {
                    checkBitmap(bitmap)
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
                    val code = jsonObject!!.optInt("code")
                    if (code == 0) {
//                        实名认证成功后，去接口请求用户信息，并刷新本地sp文件中的用户信息
                        Helper.syncPersonalInfo(this@FaceDetectionActivity,object :Helper.OnPersonalInfoCompletedListener{
                            override fun onPersonalInfoCompleted() {
                                BroadCastManager.getInstance()
                                    .sendBroadCast(this@FaceDetectionActivity, broadcastIntent)
                                ActivityManager.getActivityManagerInstance().clearAllActivity()
                                finish()
                                toast(R.string.face_success)
                            }
                        })

                    } else {
                        toast(HttpUtil.getErrorMsgByCode("$code"))
                        //发送广播，关闭遮罩页面
                        BroadCastManager.getInstance()
                            .sendBroadCast(this@FaceDetectionActivity, broadcastIntent)
                        finish()
                    }
                }

                override fun onError(call: Call?, response: Response?, e: java.lang.Exception?) {
                    super.onError(call, response, e)
                    Tools.closeDialog()
                }


            })

    }

    companion object {
        val TAG = FaceDetectionActivity::class.java.simpleName
        private const val MAX_RESULT = 10
    }
}