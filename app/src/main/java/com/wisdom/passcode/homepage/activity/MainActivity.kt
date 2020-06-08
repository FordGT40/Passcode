package com.wisdom.passcode.homepage.activity

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.bertsir.zbar.QrManager
import com.baidu.android.pushservice.PushConstants
import com.baidu.android.pushservice.PushManager
import com.baidu.android.pushservice.PushSettings
import com.google.android.material.tabs.TabLayout
import com.permissionx.guolindev.PermissionX
import com.wisdom.passcode.R
import com.wisdom.passcode.helper.PagerTransformerCustom
import com.wisdom.passcode.homepage.fragment.HomeFragment
import com.wisdom.passcode.mine.fragment.MineFragment
import com.wisdom.passcode.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_meun_bar.*
import org.jetbrains.anko.toast

class MainActivity : FragmentActivity(), View.OnClickListener {
    private var vpposition = 0 //存position

    //记录上一次滑动的positionOffsetPixels值
    private var lastValue = -1
    private var isLeft = true
    private lateinit var doubleClickHelper: DoubleClickExitHelper

    //Tab 文字
    private val TAB_TITLES = intArrayOf(
        R.string.tab_home, R.string.tab_mine
    )

    //Tab 图片
    private val TAB_IMGS = intArrayOf(
        R.drawable.ic_tab_homepage_selector,
        R.drawable.ic_tab_personalcenter_selector
    )

    //Fragment 数组
    private val TAB_FRAGMENTS: Array<Fragment> = arrayOf(
        HomeFragment(),
        MineFragment()
    )

    private val COUNT = TAB_TITLES.size

    /**
     *  @describe 初始化界面控件的方法
     *  @return
     *  @author HanXueFeng
     *  @time 2020/4/29 0029  11:29
     */
    private fun initViews() {




        PushSettings.enableDebugMode(true)
        PushManager.startWork(
            applicationContext,
            PushConstants.LOGIN_TYPE_API_KEY,
            "GEBD43bqb4Wh6ex4Dvt4k1xr"
        )
        applyPermission()
        doubleClickHelper = DoubleClickExitHelper(this)
        ll_bar_1.setOnClickListener(this)
        ll_bar_4.setOnClickListener(this)
        ll_cb_2.setOnClickListener(this)
        setTabs(tab_home as TabLayout, this.layoutInflater, TAB_TITLES, TAB_IMGS)
        vp_home.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (positionOffset != 0f) {
                    if (lastValue >= positionOffsetPixels) {
                        //右滑
                        isLeft = false
                    } else if (lastValue < positionOffsetPixels) {
                        //左滑
                        isLeft = true
                    }
                }
                lastValue = positionOffsetPixels
            }

            override fun onPageSelected(position: Int) {
                setTabCheck(position)
                vpposition = position
            }
        })
        vp_home.setPageTransformer(false, PagerTransformerCustom())
        vp_home.adapter = MyViewPagerAdapter(supportFragmentManager)
        ll_cb_1.performClick()
        overAnim(ll_cb_1)
        startAnim(ll_cb_1)
        //沉浸式状态栏
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }


    /**
     *  @describe 动态申请权限
     *  @return
     *  @author HanXueFeng
     *  @time 2020/4/29 0029  11:49
     */
    private fun applyPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.VIBRATE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .onExplainRequestReason { deniedList, beforeRequest ->
                showRequestReasonDialog(
                    deniedList
                    , getString(R.string.permission_hint)
                    , getString(R.string.comfirm)
                    , getString(R.string.cancle)
                )
            }.onForwardToSettings { deniedList ->
                showForwardToSettingsDialog(
                    deniedList
                    , getString(R.string.permission_hand)
                    , getString(R.string.comfirm)
                    , getString(R.string.cancle)
                )
            }.request { allGranted, grantedList, deniedList ->
//                if (allGranted) {
//                    Toast.makeText(this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show()
//                }
            }


    }

    /**
     * @description: 设置添加Tab
     */
    private fun setTabs(
        tabLayout: TabLayout, inflater: LayoutInflater, tabTitlees: IntArray, tabImgs: IntArray
    ) {
        for (i in tabImgs.indices) {
            val tab: TabLayout.Tab = tabLayout.newTab()
            val view: View = inflater.inflate(R.layout.tab_custom, null)
            tab.customView = view
            val tvTitle = view.findViewById<View>(R.id.tv_tab) as TextView
            tvTitle.setText(tabTitlees[i])
            val imgTab =
                view.findViewById<View>(R.id.img_tab) as ImageView
            imgTab.setImageResource(tabImgs[i])
            tabLayout.addTab(tab)
        }
    }

    /**
     * 设置首页下导航滑动后选中的事件
     *
     * @param position
     */
    private fun setTabCheck(position: Int) {
        when (position) {
            0 -> {
                ll_cb_1.isChecked = true
                ll_cb_4.isChecked = false
                overAnim(ll_cb_1)
                startAnim(ll_cb_1)
            }
            1 -> {
                ll_cb_1.isChecked = false
                ll_cb_4.isChecked = true
                overAnim(ll_cb_4)
                startAnim(ll_cb_4)
            }
        }
    }

    /**
     * @description: ViewPager 适配器
     */
    inner class MyViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return TAB_FRAGMENTS[position]
        }

        override fun getCount(): Int {
            return COUNT
        }
    }

    /**
     * 连续点击两次退出的方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            doubleClickHelper.onKeyDown(keyCode, event)
        } else super.onKeyDown(keyCode, event)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_bar_1 -> {
                ll_cb_1.isChecked = true
                ll_cb_4.isChecked = false
                vp_home.currentItem = 0
                overAnim(ll_cb_1)
                startAnim(ll_cb_1)
            }
            R.id.ll_bar_4 -> {
                ll_cb_1.isChecked = false
                ll_cb_4.isChecked = true
                vp_home.currentItem = 1
                overAnim(ll_cb_4)
                startAnim(ll_cb_4)
            }
            R.id.ll_cb_2 -> {
                //扫描人员码，二维码
                Tools.startScanActivity(this, this,
                    QrManager.OnScanResultCallback { result -> toast(result.content) })
            }
        }
    }


    private fun startAnim(view: View) {
        val scaleX =
            ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.1f)
        val scaleY =
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.1f)
        val translationY = ObjectAnimator.ofFloat(
            view,
            View.TRANSLATION_Y,
            0f,
            -15f
        )
        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY, translationY)
        set.duration = 200
        set.start()
    }

    private fun overAnim(view: View) {
        val scaleX =
            ObjectAnimator.ofFloat(view, View.SCALE_X, 1.1f, 1f)
        val scaleY =
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.1f, 1f)
        val translationY = ObjectAnimator.ofFloat(
            view,
            View.TRANSLATION_Y,
            -15f,
            0f
        )
        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY, translationY)
        set.duration = 200
        set.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Push：解绑
        PushManager.stopWork(applicationContext)
    }
}
