package cc.seedland.inf.passport.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cc.seedland.inf.passport.R;

public class RatioImageView extends AppCompatImageView {

    private static final String TAG = "RatioImageView";

    /**
     * Fit ratio by width, which means width has fixed size, height = width /ratio
     */
    public static final int FIX_BY_WIDTH = 0;
    /**
     * Fit ratio by height, which means height has fixed size, width = height * ratio
     */
    public static final int FIX_BY_HEIGHT = 1;

    @IntDef(flag = true, value = {FIX_BY_WIDTH, FIX_BY_HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FixBy {
    }

    public static final int PRIORITY_HIGH = 1 << 1;
    public static final int PRIORITY_LOW = 1;

    @IntDef(flag = true, value = {PRIORITY_HIGH, PRIORITY_LOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RatioPriority {
    }


    /**
     * ratio represents width / height
     */
    private float ratio = Float.NaN;

    @FixBy
    private int mFixBy = FIX_BY_WIDTH;

    @RatioPriority
    private int mPriority = PRIORITY_LOW;

    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        ratio = a.getFloat(R.styleable.RatioImageView_ratio, Float.NaN);
        a.recycle();
    }

    /**
     * Set ratio of this image
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        setRatio(ratio, PRIORITY_LOW);
    }

    public void setRatio(float ratio, @RatioPriority int priority) {
        this.ratio = ratio;
        this.mPriority = priority;
    }

    public float getRatio() {
        return ratio;
    }

    /**
     * Calculate rect by which dimension, {@link #FIX_BY_WIDTH} or {@link #FIX_BY_HEIGHT}
     *
     * @param byWhat
     */
    public void setFixBy(@FixBy int byWhat) {
        this.mFixBy = byWhat;
    }


    @Override
    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            if (drawableHeight > 0 && mPriority == PRIORITY_LOW) {
                setRatio((float) drawableWidth / drawableHeight);
            }
        }
        super.setImageDrawable(drawable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // if isNaN, skip
        if (!Float.isNaN(ratio)) {
            // calculate by width
            if (mFixBy == FIX_BY_WIDTH) {
                int heightMode = MeasureSpec.getMode(heightMeasureSpec);
                int height = MeasureSpec.getSize(heightMeasureSpec);
                // if mode is MeasureSpec.EXACTLY, just use the specified size
                if (heightMode != MeasureSpec.EXACTLY) {
                    int measuredWidth = getMeasuredWidth();
                    if (heightMode == MeasureSpec.AT_MOST)
                        // if mode is AT_MOST, pick the minimum one
                        height = Math.min(height, (int) (measuredWidth / ratio + 0.5f));
                    else
                        // if mode is UNSPECIFIED, use the calculated size
                        height = (int) (measuredWidth / ratio + 0.5f);

                    // set re-calculated size
                    setMeasuredDimension(measuredWidth, height);
                }
            } else if (mFixBy == FIX_BY_HEIGHT) {
                int widthMode = MeasureSpec.getMode(widthMeasureSpec);
                int width = MeasureSpec.getSize(widthMeasureSpec);
                if (widthMode != MeasureSpec.EXACTLY) {
                    int measureHeight = getMeasuredHeight();
                    if (widthMode == MeasureSpec.AT_MOST)
                        width = Math.min(width, (int) (measureHeight * ratio + 0.5f));
                    else
                        width = (int) (measureHeight * ratio + 0.5f);
                    setMeasuredDimension(width, measureHeight);
                }
            }
        }
    }

}