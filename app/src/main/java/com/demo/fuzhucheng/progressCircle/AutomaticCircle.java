package com.demo.fuzhucheng.progressCircle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.demo.fuzhucheng.R;

/**
 * Created by ${符柱成} on 2016/8/21.
 */
public class AutomaticCircle extends View {
    private int mScore;      //方法传进来的表示进度
    private Paint mBlackPaint, mWhitePaint, mCirclePaint, mTextPaint;        //白色圆弧与黑色圆弧
    /**
     * 圆环的宽度
     */
    private int mCircleWidth;
    //圆弧的矩形
    private RectF mRectF;

    //白弧线度数变量
    int i = 0;
    //根据进度来白圆扫
    int count;
    //中心圆的颜色
    private int centerCircleColor;
    //底圆环的颜色
    private int bottomRingColor;
    //旋转圆环的颜色
    private int drawRingColor;
    //圆文字的颜色
    private int textColor;


    public AutomaticCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutomaticCircleProgress);//将获取的属性转化为我们最先设好的属性
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.AutomaticCircleProgress_automaticCircleWidth:
                    mCircleWidth = (int) typedArray.getDimension(attr, 0);
                    break;
                case R.styleable.AutomaticCircleProgress_automaticCentercircleColor:
                    centerCircleColor = typedArray.getColor(attr, 0);
                    Log.e("centerColor", String.valueOf(centerCircleColor));
                    break;
                case R.styleable.AutomaticCircleProgress_automaticBottomRingColor:
                    bottomRingColor =  typedArray.getColor(attr, 0);
                    Log.e("centerColor", String.valueOf(bottomRingColor));
                    break;
                case R.styleable.AutomaticCircleProgress_automaticDrawRingColor:
                    drawRingColor =  typedArray.getColor(attr, 0);
                    break;
                case R.styleable.AutomaticCircleProgress_automaticTextColor:
                    textColor = typedArray.getColor(attr, 0);
                    break;
            }
        }
        initPaint();
    }

    private void initPaint() {
        //初始化中心圆的黑色笔
        mCirclePaint = new Paint();
        //设置抗锯齿，优化绘制效果的精细度
        mCirclePaint.setAntiAlias(true);
        //设置图像抖动处理,也是用于优化图像的显示效果
        mCirclePaint.setDither(true);
        //设置画笔的颜色
        mCirclePaint.setColor(centerCircleColor);

        //初始圆弧黑色笔
        mBlackPaint = new Paint();
        //设置抗锯齿，优化绘制效果的精细度
        mBlackPaint.setAntiAlias(true);
        //设置图像抖动处理,也是用于优化图像的显示效果
        mBlackPaint.setDither(true);
        //设置画笔的颜色
        mBlackPaint.setColor(bottomRingColor);
        //设置画笔的风格为空心
        mBlackPaint.setStyle(Paint.Style.STROKE);
        //设置“空心”的外框宽度为2dp
        mBlackPaint.setStrokeWidth(mCircleWidth);

        //初始白色笔
        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setStyle(Paint.Style.STROKE);
        mWhitePaint.setStrokeWidth(mCircleWidth);
        mWhitePaint.setDither(true);
        mWhitePaint.setColor(drawRingColor);

        //文本的笔
        mTextPaint = new Paint();
        //设置文本的字号大小
        mTextPaint.setTextSize(40);
        mTextPaint.setDither(true);
        //设置文本的对其方式为水平居中
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(textColor);


        //获取该view的视图树观察者并添加绘制变化监听者
        //实现有绘制变化时的回调方法
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //2.开启子线程对绘制用到的数据进行修改
                new DrawThread();
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //这是圆心的坐标，这个是在xml设置引用的这个布局控件的大小的中心点到这个布局控件的最左边
        float xy = getWidth() / 2;
        //圆圈的半径
        float radius = xy - mCircleWidth;       //改半径会造成联动的，因为围绕着mCircleWidth这个变量去开展绘制

        //初始化圆弧所需条件（及设置圆弧的外接矩形的四边）
        mRectF = new RectF();
        mRectF.set(xy - radius - mCircleWidth / 2, xy - radius - mCircleWidth / 2, xy + radius + mCircleWidth / 2, xy + radius + mCircleWidth / 2);

        //画里面的灰色圆
        canvas.drawCircle(xy, xy, radius, mCirclePaint);


        //绘制弧形
        //黑笔画的是一个整圆所有从哪里开始都一样
        canvas.drawArc(mRectF, 0, 360, false, mBlackPaint);
        //白笔之所以从-90度开始，是因为0度其实使我们的3点钟的位置，所以-90才是我们的0点的位置
        canvas.drawArc(mRectF, -90, i, false, mWhitePaint);
        //绘制文本
        canvas.drawText(count + "", xy, xy, mTextPaint);


    }
    //暴露一个方法给外部调用来调整进度
    public void setScore(int score) {
        this.mScore = score;
    }
    //开启子线程,并通过绘制监听实时更新绘制数据
    public class DrawThread implements Runnable {
        private final Thread mDrawThread;
        private int statek;

        public DrawThread() {
            mDrawThread = new Thread(this);
            mDrawThread.start();
        }

        @Override
        public void run() {
            while (true) {
                switch (statek) {
                    case 0://给一点点缓冲的时间
                        try {
                            Thread.sleep(200);
                            statek = 1;
                        } catch (InterruptedException e) {

                        }
                        break;
                    case 1:
                        try {//更新显示的数据
                            Thread.sleep(20);
                            i += 3.6f;
                            count++;
                            postInvalidate();       //每count加1就去刷新onDraw来刷新图
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (count >= mScore)//满足该条件就结束循环
                    break;
            }

        }
    }
}

