package com.wisdom.passcode.mine.activity

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.mine.fragment.CarCodeRecordFragment
import com.wisdom.passcode.mine.fragment.PersonCodeRecordFragment
import com.wisdom.passcode.mine.fragment.PlaceCodeRecordFragment
import kotlinx.android.synthetic.main.activity_my_cards.*

class ApplyRecordListActivity : BaseActivity() {
    //Fragment 数组
    private val TAB_FRAGMENTS = arrayOf(
        PlaceCodeRecordFragment(), PersonCodeRecordFragment(), CarCodeRecordFragment()
    )

    val tabString = arrayOf(
        R.string.tab_record_1, R.string.tab_record_2, R.string.tab_record_3
    )

    override fun initViews() {
        setTitle(R.string.title_apply_record)
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

    override fun setlayoutIds() {
        setContentView(R.layout.activity_apply_record_list)
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
