package com.wisdom.passcode.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.widght
 * @class describe：
 * @time 2020/6/16 0016 10:42
 * @change
 */
public class GridViewNoScroll extends GridView {
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    public GridViewNoScroll(Context context) {
        super(context);
    }

    public GridViewNoScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewNoScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GridViewNoScroll(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
