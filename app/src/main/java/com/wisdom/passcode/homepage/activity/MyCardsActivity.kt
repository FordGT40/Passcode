package com.wisdom.passcode.homepage.activity

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.wisdom.passcode.R
import com.wisdom.passcode.base.BaseActivity
import com.wisdom.passcode.homepage.fragment.CarCardFragment
import com.wisdom.passcode.homepage.fragment.PersonCardFragment
import com.wisdom.passcode.homepage.fragment.PlaceCardFragment
import kotlinx.android.synthetic.main.activity_my_cards.*


class MyCardsActivity : BaseActivity() {
    //Fragment 数组
    private val TAB_FRAGMENTS = arrayOf(
        PlaceCardFragment(), PersonCardFragment(), CarCardFragment()
    )

    val tabString = arrayOf(
        R.string.tab_text_3, R.string.tab_text_2, R.string.tab_text_1
    )

    override fun initViews() {
        setTitle(R.string.title_my_cards)

    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_my_cards)
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
        mViewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(mEnhanceTabLayout.tabLayout))
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
