package com.demo.fuzhucheng.colourfulFontOrNeonTextView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 符柱成 on 2016/8/19.
 */
public class colourfulFontTextview extends TextView {

    int TextViewWidth;                      //TextView的宽度
    private LinearGradient mLinearGradient;     //渲染器
    private Paint paint;


    public colourfulFontTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //在 onSizeChanged 方法中获取到宽度，并对各个类进行初始化
        if (TextViewWidth == 0) {
            TextViewWidth = getMeasuredWidth();
            if (TextViewWidth > 0) {
                //得到 父类 TextView 中写字的那支笔
                paint = getPaint();
                //初始化线性渲染器
                mLinearGradient = new LinearGradient(0, 0, TextViewWidth, 0,
                        new int[]{Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.GRAY}, null, Shader.TileMode.CLAMP);
                //把渲染器给笔套上
                paint.setShader(mLinearGradient);

            }
        }
    }
}
