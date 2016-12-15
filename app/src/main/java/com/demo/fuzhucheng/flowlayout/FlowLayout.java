package com.demo.fuzhucheng.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${符柱成} on 2016/8/24.
 */
        /*1、对于FlowLayout，需要指定的LayoutParams，我们目前只需要能够识别margin即可，即使用MarginLayoutParams.
            * 2、onMeasure中计算所有childView的宽和高，然后根据childView的宽和高，计算自己的宽和高。（当然，如果不是wrap_content，直接使用父ViewGroup传入的计算值即可）
             *3、onLayout中对所有的childView进行布局。
        */
public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";


    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(
            ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    //直接使用系统的MarginLayoutParams
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     * <p/>
     * 两个参数分别代表宽度和高度的MeasureSpec，android2.2文档中对于MeasureSpec中的说明是:
     * 一个MeasureSpec封装了从父容器传递给子容器的布局需求.每一个MeasureSpec代表了一个宽度,或者高度的说明.
     * 一个MeasureSpec是一个大小跟模式的组合值.一共有三种模式.
     * 总之，这两个参数传进来的是本View(ViewGroup)显示的长和宽的值和某个模式的&值，具体取出模式或者值的方法如下：
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小,就是得到在xml设置的模式以及大小，得到的参数值是一个大小跟模式的组合值
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        Log.e(TAG, sizeWidth + "," + sizeHeight);

        // 如果是warp_content情况下，记录宽和高。这里的赋值处理一切都是为了warp_content这个情况，
        // 因为warp_content没有存进一个确切的值，这两个的设置都是为了warp_content，全因这个流式布局，
        // 如果子view控件靠尾端的太长或中间的一个太高就会影响到整个的viewgroup大小以及高度

        int width = 0;
        int height = 0;
        /**
         * lineWidth记录每一行的宽度，width则不断取最大宽度。其实就是记录每一行当前存进子view控件后的宽度
         */
        int lineWidth = 0;
        /**
         * lineHeight记录每一行的高度，累加至height
         */
        int lineHeight = 0;
        //得到孩子的数量
        int cCount = getChildCount();

        // 遍历每个子元素
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);           //详解此方法的链接  http://gold.xitu.io/entry/571c5020128fe12b4a3e41e3
            // 得到child的LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            // 当前孩子空间（就是所遍历到的这个子view控件）实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            // 当前孩子空间（就是所遍历到的这个子view控件）空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;
            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
             * 判断原来已经存进子view控件的lineWidth再加上现在准备再次存起的childWidth孩子宽度是否大于viewgroup在xml里面设置给我们的宽度
             */
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth);// 取最大的，为什么取最大呢?因为一会要把这个宽度设置给viewgroup来显示
                lineWidth = childWidth; // 重新开启新行，开始记录。意思就是一行存满了，那么此时的子view控件就要往下挤一行了，所以要用新的lineWidth的值来表示下一行的宽度
                // 叠加当前高度，为什么叠加呢？因为你一会要设置height给viewgroup。从而这个height才能显示多行嘛
                height += lineHeight;
                // 开启记录下一行的高度。意思就是一行存满了，那么此时的子view控件就要往下挤一行了，所以要用新的lineHeight的值来表示下一行的高度
                lineHeight = childHeight;
            } else
            // 否则累加值lineWidth,lineHeight取最大高度
            {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);     //还是要取到子view控件中最大的那个的高度，进而发给viewgroup去显示，才能显示完整
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }
        //把我们计算子view控件得到的宽度，高度都传给viewgroup
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : height);

    }

    /**
     * 存储所有的View，按行记录。View就是一个子view控件嘛，List<View>就是装了一行的子view控件嘛
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度。要取到每一行子view控件中最大的那个的高度，进而发给viewgroup去显示，才能显示完整
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //清空一下先
        mAllViews.clear();
        mLineHeight.clear();
        //获得在xml设置的viewgroup宽度
        int width = getWidth();
        //同理上面
        int lineWidth = 0;
        int lineHeight = 0;
        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();
        // 遍历所有的孩子,为每一个孩子设置位置
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //得到那个孩子的容器。（其实就是这个孩子）
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果已经需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<View>();
            }
            /**
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);

            Log.e(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
            Log.e(TAG, "第" + i + "行， ：" + lineHeight);

            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                //计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                Log.e(TAG, child + " , l = " + lc + " , t = " + t + " , r ="
                        + rc + " , b = " + bc);
                //根据得到的childView的left,top,right,bottom的数据设置位置
                child.layout(lc, tc, rc, bc);
                //其实得到这一行的此时已经装载的宽度，然后根据Margin就可以每个子view控件就可以相隔一小段位置啦
                left += child.getMeasuredWidth() + lp.rightMargin
                        + lp.leftMargin;
            }
            //然后就是把这一行的宽度至0，从新来过，记录下一行啦
            left = 0;
            top += lineHeight;
        }

    }
}
