package com.demo.fuzhucheng.colourfulFontOrNeonTextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 符柱成 on 2016/8/19.
 */
public class NenoTextview extends TextView {
    int mViewWidth;                          //TextView的宽度
    private LinearGradient mLinearGradient;     //渲染器
    private Matrix mMatrix;         //图片变换处理器
    private Paint mPaint;           //字体的笔
    int mTranslate=0;       //表示平移的速度

    public NenoTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //在 onSizeChanged 方法中获取到宽度，并对各个类进行初始化
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();

            if (mViewWidth > 0) {
                //得到 父类 TextView 中写字的那支笔.，注意是继承Textview
                mPaint = getPaint();
                //初始化线性渲染器 不了解的请看上面连接
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                        new int[]{Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN}, null, Shader.TileMode.CLAMP);
                //把渲染器给笔套上
                mPaint.setShader(mLinearGradient);
                //初始化 Matrix，Matrix的原意是对一个Bitmap的图片变化进行处理，它本身不能对图像或者View进行变换，但是可以与其他的API结合进行图形和View的变换，比如Canvas
                mMatrix = new Matrix();

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先让父类方法执行，由于上面我们给父类的 Paint 套上了渲染器，所以这里出现的文字已经是彩色的了
        super.onDraw(canvas);

        if (mMatrix != null) {
            //利用 Matrix 的平移动作实现霓虹灯的效果，这里是每次滚动1/10
            mTranslate += mViewWidth / 10;
            //如果滚出了控件边界，就要拉回来重置开头，这里重置到了屏幕左边的空间
            if (mTranslate >  mViewWidth) {
                mTranslate = -mViewWidth/2;
            }
            //设置平移距离
            mMatrix.setTranslate(mTranslate, 0);
            //平移效果生效
            mLinearGradient.setLocalMatrix(mMatrix);
            //延迟 100 毫秒再次刷新 View 也就是再次执行本 onDraw 方法
            postInvalidateDelayed(50);

        }
    }
}