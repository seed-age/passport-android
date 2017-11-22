package cc.seedland.inf.passport.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 可以控制分割线显示的LinearLayout
 *
 * Created by xuchunlei on 2017/11/20.
 */

public class DividerLinearLayout extends LinearLayoutCompat {

    public DividerLinearLayout(Context context) {
        this(context ,null);
    }

    public DividerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean hasDividerBeforeChildAt(int childIndex) {

        int showDividers = getShowDividers();

        if (childIndex == 0) {
            return (showDividers & SHOW_DIVIDER_BEGINNING) != 0;
        } else if (childIndex == getChildCount()) {
            return (showDividers & SHOW_DIVIDER_END) != 0;
        } else if ((showDividers & SHOW_DIVIDER_MIDDLE) != 0) {
            boolean hasVisibleViewBefore = false;

            View child = getChildAt(childIndex);
            if (child.getVisibility() != GONE && getDividerFlag(child.getTag())) {
                hasVisibleViewBefore = true;
            }
            return hasVisibleViewBefore;
        }
        return false;
    }

    private boolean getDividerFlag(Object tag) {
        if(tag != null && tag instanceof String) {
            String value = tag.toString();

            int flagIndex = value.indexOf("divider:") + 8;
            boolean f = Integer.valueOf(value.substring(flagIndex, flagIndex + 1)) != 0;
            Log.e("xuchunlei", "dividerFlag------>" + f);
            return Integer.valueOf(value.substring(flagIndex, flagIndex + 1)) != 0;

        }
        return true;
    }
}
