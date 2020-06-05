package com.wisdom.passcode.helper

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.max

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.helper
 * @class describe：
 * @author HanXueFeng
 * @time 2020/6/5 0005 09:49
 * @change
 */
class PagerTransformerCustom() :ViewPager.PageTransformer, Parcelable {
    //最小透明度
    private val MIN_SCALE = 0.8f
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PagerTransformerCustom> {
        override fun createFromParcel(parcel: Parcel): PagerTransformerCustom {
            return PagerTransformerCustom(parcel)
        }

        override fun newArray(size: Int): Array<PagerTransformerCustom?> {
            return arrayOfNulls(size)
        }
    }

    override fun transformPage(page: View, position: Float) {
        page.apply {
            when {
                position < -1 -> {
                    //向左滑动时，左边的page，由于看不见，所以设不设置都无所谓
                }
                position <= 1 -> {
                    //[-1,1]
                    //向左滑动时，代表中间的page和右边的page
                    //向右滑动时，代表中间的page和左边的page
                    val scaleValue = max(MIN_SCALE,1 - Math.abs(position))

                    scaleX = scaleValue
                    scaleY = scaleValue
                }
                else -> {
                    // position>1
                    // 向右滑动时，右边的page，还是看不见，所以可以不用设置
                }
            }
        }
    }
}