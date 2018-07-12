package cc.seedland.inf.passport.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cc.seedland.inf.passport.R;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/12 14:52
 * 描述 ：
 **/

public class PasswordEditText extends PassportEditText {

    private Drawable mShowDrawable;
    private Drawable mHideDrawable;

    private boolean showFlag;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        if(a.hasValue(R.styleable.PasswordEditText_showDrawable)) {
            mShowDrawable = a.getDrawable(R.styleable.PasswordEditText_showDrawable);
        }
        if(a.hasValue(R.styleable.PasswordEditText_hideDrawable)) {
            mHideDrawable = a.getDrawable(R.styleable.PasswordEditText_hideDrawable);
        }
        a.recycle();

        mShowDrawable.setBounds(0, 0, mShowDrawable.getIntrinsicWidth(), mShowDrawable.getIntrinsicHeight());
        mHideDrawable.setBounds(0, 0, mHideDrawable.getIntrinsicWidth(), mHideDrawable.getIntrinsicHeight());
        changeDrawable(false);
    }

    /**
     * 我们无法直接给EditText设置点击事件，只能通过按下的位置来模拟clear点击事件
     * 当我们按下的位置在图标包括图标到控件右边的间距范围内均算有效
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int start = getWidth() - getTotalPaddingRight() - getPaddingRight(); // 起始位置
                int end = getWidth(); // 结束位置
                boolean available = (event.getX() > start) && (event.getX() < end);
                if (available) {
                    if(showFlag) {
                        setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }else {
                        setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    showFlag = !showFlag;
                    changeDrawable(showFlag);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    protected void changeDrawable(boolean onoff) {
        Drawable right = onoff ? mShowDrawable : mHideDrawable;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

}