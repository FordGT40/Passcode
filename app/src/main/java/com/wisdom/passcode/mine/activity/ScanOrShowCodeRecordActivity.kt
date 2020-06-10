package com.wisdom.passcode.mine.activity

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.mine.fragment.ScanCodeRecordFragment
import com.wisdom.passcode.mine.fragment.ShowCodeRecordFragment
import kotlinx.android.synthetic.main.activity_apply_record_list.*

class ScanOrShowCodeRecordActivity : BaseActivity() {
    val tabString = arrayOf(
        R.string.tab_text_show, R.string.tab_text_scan
    )
    private val TAB_FRAGMENTS = arrayOf(
        ShowCodeRecordFragment(), ScanCodeRecordFragment()
    )

    override fun initViews() {
        setTitle(R.string.title_scan_or_show_record)

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_scan_or_show_code_record)
        mEnhanceTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })
        for (strId in tabString) {
            mEnhanceTabLayout.addTab(getString(strId))
        }
//        去掉点击tab的阴影
        mEnhanceTabLayout.tabLayout.tabRippleColor =
            ColorStateList.valueOf(Color.parseColor("#00ffffff"))
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        mViewPager.adapter = adapter
        mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mEnhanceTabLayout.tabLayout))
        mEnhanceTabLayout.setupWithViewPager(mViewPager)
    }

    /**
     * @description: ViewPager 适配器
     */
    inner class MyViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return TAB_FRAGMENTS[position]
        }

        override fun getCount(): Int {
            return tabString.size
        }
    }
}
