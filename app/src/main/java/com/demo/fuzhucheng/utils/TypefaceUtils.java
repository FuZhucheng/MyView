package com.demo.fuzhucheng.utils;

import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by 符柱成 on 2016/8/20.
 */

//字体加载工具类，使用枚举实现单例模式
public enum TypefaceUtils {
    TYPEFACE;

    private static Typeface typeface50;
    private static Typeface typeface55;

    public void set50Typeface(TextView textView) {
        if (typeface50 == null)
            typeface50 = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/HYQiHei-50S.otf");
        textView.setTypeface(typeface50);
    }

    public void set55Typeface(TextView textView) {
        if (typeface55 == null)
            typeface55 = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/HYQiHei-55S.otf");
        textView.setTypeface(typeface55);
    }
}