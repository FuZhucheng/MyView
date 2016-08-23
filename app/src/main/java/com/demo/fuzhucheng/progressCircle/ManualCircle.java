package com.demo.fuzhucheng.progressCircle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.demo.fuzhucheng.R;

/**
 * Created by ${符柱成} on 2016/8/20.
 */
public class ManualCircle extends View {
    private Paint mCirclePaint, mArcPaint, mTextPaint;           //画圆的笔，画弧线的笔，画圆中文字的笔
    private RectF rectF;                            //画弧线--弧线根据矩形生成
    //弧线度数变量
    int i = 0;
    /**
     * 圆环的宽度
     */
    private int mCircleWidth;
    //中心圆的颜色
    private int centerCircleColor;
    //圆环的颜色
    private int ringColor;
    //圆文字的颜色
    private int textColor;

    public ManualCircle(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);//将获取的属性转化为我们最先设好的属性
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_circleWidth :
                    mCircleWidth = (int) typedArray.getDimension(attr, 0);
                    break;
                case R.styleable.CustomProgressBar_centercircleColor :
                    centerCircleColor=typedArray.getColor(attr,0);       //默认黄色
                    break;
                case R.styleable.CustomProgressBar_ringColor :
                    ringColor=typedArray.getColor(attr,0);          //默认绿色
                    break;
                case R.styleable.CustomProgressBar_textColor :
                    textColor=typedArray.getColor(attr,0);          //默认黑色
                    break;
            }
        }
        typedArray.recycle();           //回收属性对象
        initPaint();
    }


    private void initPaint() {
        //画文字
        mTextPaint = new TextPaint(0);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(80);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //实心圆画笔
        mCirclePaint = new Paint();
        mCirclePaint.setColor(centerCircleColor);
        //画弧线的画笔
        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.STROKE);    //设置不填充中间,样式为描边
        mArcPaint.setColor(ringColor);
        //是根据矩形来画弧线的
        rectF = new RectF();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //这是圆心的坐标，这个是在xml设置引用的这个布局控件的大小的中心点到这个布局控件的最左边
        float xy = getWidth() / 2;
        //圆圈的半径 这里是100
        float radius = xy - mCircleWidth;       //改半径会造成联动的，因为围绕着mCircleWidth这个变量去开展绘制，要理解就要画图
        //设置圆弧              //注意先确定圆弧的宽度，然后根据得到这个布局的中心点，然后进行演算，得到半径。然后确定一个矩形来画圆弧。圆弧
        rectF.left = xy - radius - mCircleWidth / 2;
        rectF.top = xy - radius - mCircleWidth / 2;
        rectF.right = xy + radius + mCircleWidth / 2;
        rectF.bottom = xy + radius + mCircleWidth / 2;
        mArcPaint.setStrokeWidth(mCircleWidth);

        //画里面的圆
        canvas.drawCircle(xy, xy, radius, mCirclePaint);
        //画弧线
        int progress=i*10/36;
        canvas.drawArc(rectF, 270, i, false, mArcPaint);
        //显示度数与提示
        canvas.drawText(String.valueOf(progress)+"%", xy, xy, mTextPaint);
    }

    /**
     * 公开一个方法，可以更新弧线的度数~
     */
    public void add() {
        //超过360度就还原到10度
        if (i >= 360) {
            i = 36;
            postInvalidate();           //通知onDraw重新绘制
        } else {
            this.i += 36;
            postInvalidate();
        }
    }
}
