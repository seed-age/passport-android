package cc.seedland.inf.passport.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/04/02
 * 描述：
 * </pre>
 */

public class ImageCaptchaView extends View {

    // 尺寸
    private int width;          // 宽度
    private int height;         // 高度
    private int defaultWidth;   // 默认宽度
    private int defaultHeight;  // 默认高度

    // 绘制
    private Paint textPaint;
    private Paint linePaint;
    private List<Path> linePaths = new ArrayList<>();       // 干扰线坐标
    private Bitmap captchaBmp;   // 验证码图片

    private String captchaValue; // 验证码值
    private Random random;       // 用于生成一些随机数字

    public ImageCaptchaView(Context context) {
        this(context, null);
    }

    public ImageCaptchaView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageCaptchaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //获取宽和高的SpecMode和SpecSize
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        width = wMode == MeasureSpec.EXACTLY ? wSize : defaultWidth;
        height = hMode == MeasureSpec.EXACTLY ? hSize : defaultHeight;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        captchaBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        if(!TextUtils.isEmpty(captchaValue)) {
            updateCaptchaImage(this.captchaValue);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(captchaBmp, 0, 0, null);
    }

    public void setCaptchaValue(String captchaValue) {
        if(!TextUtils.isEmpty(captchaValue) && !captchaValue.equals(this.captchaValue)) {
            this.captchaValue = captchaValue;
            updateCaptchaImage(this.captchaValue);
            postInvalidate();
        }

    }

    private void initView() {

        random = new Random();

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(5);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(sp2px(30));
        textPaint.setShadowLayer(5, 3, 3, 0xff999999);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextScaleX(0.8f);
        textPaint.setColor(Color.GREEN);

        defaultWidth = (int)(textPaint.measureText("W") * 1.5 * 4 + dp2px(8));
        defaultHeight = (int)dp2px(40);
    }

    private void updateCaptchaImage(String value) {
        if(captchaBmp == null) {
            return;
        }
        Canvas canvas = new Canvas(captchaBmp);

        // 背景
        int r = random.nextInt(122) + 123;
        int g = random.nextInt(122) + 123;
        int b = random.nextInt(122) + 123;
        canvas.drawColor(Color.rgb(r, g, b));

        // 验证码


        // 干扰线
        linePaths.clear();
        for(int i = 0;i < 2;i++) {
            Path path = new Path();
            int startX = random.nextInt(width / 3) + 10;
            int startY = random.nextInt(height / 3) + 10;
            int endX = random.nextInt(width / 2) + width / 2 - 10;
            int endY = random.nextInt(height / 2) + height / 2 - 10;
            path.moveTo(startX, startY);
            path.quadTo(Math.abs(endX-startX) / 2, Math.abs(endY-startY) / 2,endX,endY);
            linePaths.add(path);
        }
        // 产生干扰效果2 -- 干扰线
        for(Path path : linePaths){
            linePaint.setARGB(255, random.nextInt(88), random.nextInt(88), random.nextInt(88));
            canvas.drawPath(path, linePaint);
        }

    }

    private float sp2px(final float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    private float dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
}
