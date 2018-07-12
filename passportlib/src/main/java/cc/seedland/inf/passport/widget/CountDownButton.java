package cc.seedland.inf.passport.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cc.seedland.inf.passport.R;

/**
 * 倒计时按钮
 * <p>
 * 显示倒计时文本的按钮，使用startCountDown()方法开启倒计时，stopCountDown()方法结束倒计时
 * </p>
 * <p>
 * @attr timeCount 计时总数，单位为秒，默认为60.如设置为60，则表示进行60秒的倒计时
 * @attr countStep 计时步进，单位与timeCount保持一致，默认为1.如设置为1，表示每次倒计时timeCount＝timeCount － 1
 * @attr formatText 按钮的格式化文本，文本中只提供一个整型参数，如"％d秒后重发"
 * @attr launchEvent 显示即计时标记，表示组件开启倒计时的触发事件，包括inflated（实例化后触发）和show（可见后触发）.默认为"attached",可以使用"attached | show"表示组合条件
 * </p>
 * 
 * @author    xuchunlei
 * create at: 2015年5月11日 下午5:18:37
 *
 */
public final class CountDownButton extends AppCompatTextView {

    //时间计数默认值
    private static final int DEFAULT_TIME_COUNT = 60;
    //消息－计数中
    private static final int MESSAGE_COUNTING = 1;
    //消息－计数后
    private static final int MESSAGE_COUNTED = MESSAGE_COUNTING + 1;
    
    //启动倒计时事件标记－表示在附加到窗口后启动
    private static final int ATTACHED = 0x00000000;
    //启动倒计时事件标记－表示在可见后启动
    private static final int SHOW = 0x00000002;
    
    //执行倒计时更新界面的定时任务
    private ScheduledExecutorService mCountDownTask;
    
    //倒计时计数
    private int mCount;
    //初始计数
    private int mInitCount;
    //计时步进
    private int mStep;
    //启动标记
    private int mLaunchFlag;
    // 静态文本颜色
    @ColorInt
    private int mEndTextColor;
    // 计时状态颜色
    @ColorInt
    private int mCountingColor;
    // 普通状态颜色
    @ColorInt
    private int mNormalColor;
    // 静止状态下的背景，倒计时结束时还原
    private Drawable mBackground;
    
    private Handler mHandler;
    
    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownButton);
        mInitCount = a.getInt(R.styleable.CountDownButton_timeCount, DEFAULT_TIME_COUNT);
        mCount = mInitCount;
        mStep = a.getInt(R.styleable.CountDownButton_countStep, 1);
        mLaunchFlag = a.getInt(R.styleable.CountDownButton_launchEvent, ATTACHED);
        final String countingText = a.getString(R.styleable.CountDownButton_countingText);
        final String endText = a.getString(R.styleable.CountDownButton_endText);
        mCountingColor = a.getColor(R.styleable.CountDownButton_countingColor, Color.GRAY);
        mNormalColor = a.getColor(R.styleable.CountDownButton_normalColor, Color.BLACK);
        a.recycle();

        mEndTextColor = getCurrentTextColor();
        mBackground = getBackground();
        
        setClickable(true);
        
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_COUNTING:
                        if(countingText != null) {
                            setText(String.format(countingText, mCount));
                        }else {
                            setText(String.valueOf(mCount));
                        }
                        break;
                    case MESSAGE_COUNTED:
                        enable(true);
                        setText(endText);
                        break;
                    default:
                        break;
                }
                
            }
        };
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownButton(Context context) {
        this(context, null);
    }
    
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if((mLaunchFlag & ATTACHED) == ATTACHED) {//设置实例化触发则开启倒计时
          //  startCountDown();
        }
        
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopCountDown();
    }
    
    /**
     * 开始倒计时
     *
     * @param clearFlag 清除标记，true清除静止状态下的样式
     * @author:     xuchunlei
     * created at: 2015年5月12日 上午9:40:31
     */
    public void startCountDown(boolean clearFlag) {
        if(!isCounting()) {
            mCountDownTask = generateCountDownTask();
        }
        
        enable(false);
        mCountDownTask.scheduleAtFixedRate(new Runnable() {
            
            @Override
            public void run() {
                mCount = mCount - mStep;
                if(mCount < mStep) {
                    mHandler.sendEmptyMessage(MESSAGE_COUNTED);
                    mCount = mInitCount;
                    mCountDownTask.shutdown();
                }else if(mCount >= mStep) {
                    mHandler.sendEmptyMessage(MESSAGE_COUNTING);
                    
                }
            }
        }, 0, mStep, TimeUnit.SECONDS);
    }

    public boolean isCounting() {
        return mCountDownTask != null && !mCountDownTask.isShutdown() && !mCountDownTask.isTerminated();
    }

    /**
     * 结束倒计时
     * 
     * @author:     xuchunlei
     * created at: 2015年5月12日 上午9:43:24
     */
    private void stopCountDown() {
        if(mCountDownTask!=null && !mCountDownTask.isTerminated() && !mCountDownTask.isShutdown()) {
            mCountDownTask.shutdownNow();
        }
    }
    
    /**
     * 生成一个拥有2个线程的定期任务
     * @return
     * @author:     xuchunlei
     * created at: 2015年5月12日 上午10:05:50
     */
    private ScheduledExecutorService generateCountDownTask() {
        return Executors.newScheduledThreadPool(2);
    }

    private void enable(boolean enabled){
        if(isEnabled() != enabled) {
            setEnabled(enabled);
            if(enabled) {
                mBackground.setColorFilter(mNormalColor, PorterDuff.Mode.SRC_ATOP);
                setTextColor(mNormalColor);
            }else {
                mBackground.setColorFilter(mCountingColor, PorterDuff.Mode.SRC_ATOP);
                setTextColor(mCountingColor);
            }
        }
    }
    
}
