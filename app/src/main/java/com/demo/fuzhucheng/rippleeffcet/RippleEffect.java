package com.demo.fuzhucheng.rippleeffcet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Button;

import com.demo.fuzhucheng.R;

/**
 * Created by ${符柱成} on 2016/8/26.
 */
public class RippleEffect extends Button {

    private static final int INVALIDATE_DURATION = 20; //每次刷新的时间间隔
    private static int DIFFUSE_GAP = 10;                  //扩散半径增量，就是扩散的速度啦
    private static int TAP_TIMEOUT;                   //判断点击和长按的时间

    private int mViewWidth;                            //控件宽度和高度
    private int mViewHeight;
    private int pointX;                               //控件原点坐标（左上角）
    private int pointY;
    private int mMaxRadio;                             //扩散的最大半径
    private int mShaderRadio;                       //不断扩散的圆的半径，

    private Paint mBackgroundPaint;                        //画底部背景的画笔
    private Paint mWaterRipplePaint;                          //画圆的画笔
    private int mBackgroundColor;           //背景颜色
    private int mWaterRippleColor;              //水波纹颜色

    private boolean isPushButton;                     //记录是否按钮被按下

    public RippleEffect(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleEffectProperty);//将获取的属性转化为我们最先设好的属性
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.RippleEffectProperty_RippleEffectSpreadSpeed :
                    DIFFUSE_GAP= (int) typedArray.getInteger(attr,0);       //默认黄色
                    break;
                case R.styleable.RippleEffectProperty_RippleEffectBackgroundColor :
                    mBackgroundColor=typedArray.getColor(attr,0);          //默认绿色
                    break;
                case R.styleable.RippleEffectProperty_RippleEffectWaterRippleColor :
                    mWaterRippleColor=typedArray.getColor(attr,0);          //默认黑色
                    break;
            }
        }
        typedArray.recycle();           //回收属性对象

        initPaint();
        TAP_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    }

    /**
     * 初始化画笔资源
     */
    private void initPaint() {
        mWaterRipplePaint = new Paint();
        mBackgroundPaint = new Paint();
        mWaterRipplePaint.setColor(mBackgroundColor);
        mBackgroundPaint.setColor(mWaterRippleColor);
    }

    //得到view的宽对和高度
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mViewWidth = w;
        this.mViewHeight = h;
    }

    //得到点击view中的位置的x、y值以及一个点击下去的时间
    private int eventX;
    private int eventY;
    private long downTime = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //只需要取一次时间。为了得到这个点击的持续时间，在down事件开始一个系统时钟计时
                if(downTime == 0){
                    downTime = SystemClock.elapsedRealtime();
                }
                eventX = (int)event.getX();
                eventY = (int)event.getY();
                //计算最大半径
                countMaxRadio();
                isPushButton = true;
                postInvalidateDelayed(INVALIDATE_DURATION);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //
                if(SystemClock.elapsedRealtime() - downTime < TAP_TIMEOUT){
                    DIFFUSE_GAP = 30;
                    postInvalidate();
                }else{
                    clearData();
                }
                break;
        }
        //这里要return这个，如果returntrue的话会影响其他的点击事件
        return super.onTouchEvent(event);
    }

    /**
     * 计算此时的最大半径
     */
    private void countMaxRadio() {
        if(mViewWidth > mViewHeight){
            if(eventX < mViewWidth / 2){
                mMaxRadio = mViewWidth - eventX;
            }else{
                mMaxRadio = mViewWidth / 2 + eventX;
            }
        }else{
            if(eventY < mViewHeight / 2){
                mMaxRadio = mViewHeight - eventY;
            }else{
                mMaxRadio = mViewHeight / 2 + eventY;
            }
        }
    }

    /**
     * 清理改变的数据（初始化数据）。把画布的数据全清，还原。进而画更外一圈的圆才形成波纹嘛
     *
     */
    private void clearData(){
        downTime = 0;
        DIFFUSE_GAP = 10;
        isPushButton = false;
        mShaderRadio = 0;
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(!isPushButton) return; //如果按钮没有被按下则返回

        //绘制按下后的整个背景,然后保存canvas状态！！！，可以调用canvas的平移、放缩、错切、裁剪等。
        canvas.drawRect(pointX, pointY, pointX + mViewWidth, pointY + mViewHeight, mBackgroundPaint);
        canvas.save();
        //绘制扩散圆形背景，其实就是绘制那个点击的水波纹，然后restore用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
        canvas.clipRect(pointX, pointY, pointX + mViewWidth, pointY + mViewHeight);           //这个方法就是在手机屏幕上裁剪出一块区域来，起点是从屏幕的左上角开始。
        canvas.drawCircle(eventX, eventY, mShaderRadio, mWaterRipplePaint);
        canvas.restore();
        //直到半径等于最大半径。若他不大于最大半径就一直调用postInvalidateDelayed方法来重绘。以DIFFUSE_GAP这个半径增加量来不断向外画圆重绘，形成波纹
        if(mShaderRadio < mMaxRadio){
            postInvalidateDelayed(INVALIDATE_DURATION,
                    pointX, pointY, pointX + mViewWidth, pointY + mViewHeight);
            mShaderRadio += DIFFUSE_GAP;
        }else{
            clearData();
        }
    }
}