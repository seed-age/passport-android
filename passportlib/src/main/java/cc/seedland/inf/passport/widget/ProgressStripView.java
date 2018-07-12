package cc.seedland.inf.passport.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cc.seedland.inf.passport.R;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/03/17
 * 描述：进度条，用于webview
 * </pre>
 */

public class ProgressStripView extends View {

    private static final int DEFAULT_WIDTH = 200;    // 单位dp
    private static final int DEFAULT_HEIGHT = 3;     // 单位dp

    private @ColorInt int frontColor;      // 前置颜色，显示进度
    private @ColorInt int backColor;       // 后置颜色，显示背景

    private Paint paint;
    private int width, height;
    private int progress;

    public ProgressStripView(Context context) {
        this(context, null);
    }

    public ProgressStripView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressStripView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化自定义属性
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressStripView);
        frontColor = array.getColor(R.styleable.ProgressStripView_frontColor, Color.rgb(60, 179,133));
        backColor = array.getColor(R.styleable.ProgressStripView_backColor, Color.TRANSPARENT);
        array.recycle();

        paint = new Paint();
        paint.setColor(frontColor);

        setBackgroundColor(backColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.AT_MOST:         // wrap_content
                width = dp2px(DEFAULT_WIDTH);
                break;
            default:
                width = widthSize;
        }

        switch (heightMode){
            case MeasureSpec.AT_MOST:
                height = dp2px(DEFAULT_HEIGHT);
                break;
            default:
                height = heightSize;
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float result = width * ((float) progress / (float) 100);
        canvas.drawRect(0, 0, result, height, paint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void hideGently() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(GONE);
            }
        }, 100);
    }

    /**
     * dp 转换为 px
     * @param dpValue
     * @return
     */
    private int dp2px(int dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int)((dpValue * scale) + 0.5f);
    }
}
