package com.demo.fuzhucheng.colourfulFontOrNeonTextView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import com.demo.fuzhucheng.R;

/**
 * Created by 符柱成 on 2016/8/19.
 */

//封装好了Textview多种字体
public class SpannableStringFont {
    public static SpannableString changeFont(Context context, String content) {
        SpannableString ss = new SpannableString(content);
        // flag:标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果
        //  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、
        //  Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)、
        // Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)、
        // Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
        //设置字体(default,default-bold,monospace,serif,sans-serif)
        ss.setSpan(new TypefaceSpan("monospace"), 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置网络超链接
        ss.setSpan(new URLSpan("http://www.baidu.com"), content.indexOf("baidu"), content.indexOf("or"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new URLSpan("http://www.youku.com"), content.indexOf("youku"), content.indexOf("正常"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置字体颜色
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), content.indexOf("baidu"), content.indexOf("or"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#0000FF")), content.indexOf("or"), content.indexOf("youku"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#ff00ff")), content.indexOf("youku"), content.indexOf("正常"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置字体大小
        ss.setSpan(new AbsoluteSizeSpan(sp2px(context, 25)), content.indexOf("baidu"), content.indexOf("or"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan(sp2px(context, 30)), content.indexOf("youku"), content.indexOf("正常"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置下划线
        ss.setSpan(new MyUnderlineSpan(), content.indexOf("youku"), content.indexOf("正常"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体样式正常，粗体，斜体，粗斜体
        ss.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), content.indexOf("正常"), content.indexOf("粗体"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //正常
        ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), content.indexOf("粗体"), content.indexOf("斜体"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
        ss.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), content.indexOf("斜体"), content.indexOf("粗斜体"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体
        ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), content.indexOf("粗斜体"), content.indexOf("上标"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗斜体

        //设置上下标
        ss.setSpan(new SubscriptSpan(), content.indexOf("上标"), content.indexOf("下标"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //下标
        ss.setSpan(new SuperscriptSpan(), content.indexOf("下标"), content.indexOf("前景色"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标

        //设置字体前景色
        ss.setSpan(new ForegroundColorSpan(Color.MAGENTA), content.indexOf("前景色"), content.indexOf("背景色"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色

        //设置字体背景色
        ss.setSpan(new BackgroundColorSpan(Color.CYAN), content.indexOf("背景色"), content.indexOf("图片"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色
//设置图片
        Drawable drawable = context.getDrawable(R.drawable.home_serve_dot_pressed);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ss.setSpan(new ImageSpan(drawable), content.indexOf("图片"), ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    //这里是干嘛的呢？为了设置下划线呗
    static class MyUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(true);
        }
    }

    //这又是干嘛的呢？计算字体大小嘛
    private static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
