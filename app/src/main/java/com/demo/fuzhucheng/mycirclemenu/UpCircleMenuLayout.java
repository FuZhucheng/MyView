package com.demo.fuzhucheng.mycirclemenu;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.demo.fuzhucheng.R;
import com.demo.fuzhucheng.utils.DensityUtil;

/**
 * Created by ${符柱成} on 2016/10/24.
 */

public class UpCircleMenuLayout extends ViewGroup {
    private int mRadius;


    public static final float DEFAULT_BANNER_WIDTH = 750.0f;
    public static final float DEFAULT_BANNER_HEIGTH = 420.0f;

    /**
     * 该容器内child item的默认尺寸
     */
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 40.0f;

    private static final float RADIO_TOP_CHILD_DIMENSION = 60.0f;

    /**
     * 菜单的中心child的默认尺寸
     */
    private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private static final float RADIO_PADDING_LAYOUT = 20;


    private static final int RADIO_MARGIN_LAYOUT = 20;
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 300;

    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private static final int NOCLICK_VALUE = 3;

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private float mPadding;
    /**
     * 菜单项的文本
     */
    private String[] mItemTexts;
    /**
     * 布局时的开始角度
     */
    private double mStartAngle = 18;
    /**
     * 菜单项的图标
     */
    private int[] mItemImgs;

    /**
     * 菜单的个数
     */
    private int mMenuItemCount;

    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;
    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;

    /**
     * 判断是否正在自动滚动
     */
    private boolean isTouchUp = true;

    private int mMenuItemLayoutId = R.layout.circle_menu_item;

    private Paint mTextPaint;
    private Rect mBound;


    private int mCurrentPosition = 0;


    public UpCircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 无视padding
        setPadding(0, 0, 0, 0);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DensityUtil.dip2px(getContext(), 14));
        mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    /**
     * 设置布局的宽高，并策略menu item宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;
        double startAngle = mStartAngle;

        double angle = 360 / 10;
        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY
                || heightMode != MeasureSpec.EXACTLY) {
            // 主要设置为背景图的高度

            resWidth = getDefaultWidth();

            resHeight = (int) (resWidth * DEFAULT_BANNER_HEIGTH /
                    DEFAULT_BANNER_WIDTH);

        } else {
            // 如果都设置为精确值，则直接取小值；
            resWidth = resHeight = Math.min(width, height);
        }

        setMeasuredDimension(resWidth, resHeight);

        // 获得直径
        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());

        // menu item数量
        final int count = getChildCount();
        // menu item尺寸
        int childSize;

        // menu item测量模式
        int childMode = MeasureSpec.EXACTLY;

        // 迭代测量
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            startAngle = startAngle % 360;
            if (startAngle > 269 && startAngle < 271 && isTouchUp) {
                mOnMenuItemClickListener.itemClick(i);
                mCurrentPosition = i;
                childSize = DensityUtil.dip2px(getContext(), RADIO_TOP_CHILD_DIMENSION);
            } else {
                childSize = DensityUtil.dip2px(getContext(), RADIO_DEFAULT_CHILD_DIMENSION);
            }
            if (child.getVisibility() == GONE) {
                continue;
            }
            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
            int makeMeasureSpec = -1;

            makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
                    childMode);
            child.measure(makeMeasureSpec, makeMeasureSpec);
            startAngle += angle;
        }

        mPadding = DensityUtil.dip2px(getContext(), RADIO_MARGIN_LAYOUT);

    }

    /**
     * MenuItem的点击事件接口
     *
     * @author zhy
     */
    public interface OnMenuItemClickListener {
        void itemClick(int pos);

        void itemCenterClick(View view);
    }

    /**
     * MenuItem的点击事件接口
     */
    private OnMenuItemClickListener mOnMenuItemClickListener;

    /**
     * 设置MenuItem的点击事件接口
     *
     * @param mOnMenuItemClickListener
     */
    public void setOnMenuItemClickListener(
            OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (!isTouchUp) {
//            return;
//        }
//        double startAngle = mStartAngle;
//        int layoutRadius = mRadius;
//        int left, top;
//        float angleDelay = 360 / 9;
//
//        int count = getChildCount();
//        int cWidth = DensityUtil.dip2px(getContext(), RADIO_DEFAULT_CHILD_DIMENSION);
//        for (int i = 0; i < count; i++) {
//            startAngle = startAngle % 360;
//
//            String intelli = mItemTexts[i];
//            mBound = new Rect();
//            mTextPaint.getTextBounds(intelli, 0, intelli.length(), mBound);
//
//            float tmp = 0;
//            // 计算，中心点到menu item中心的距离
//            Log.d("",mRadius + "");
//            if (mRadius == 720) {
//                tmp = layoutRadius / 2f - DensityUtil.dip2px(getContext(), 28.0f);
//            } else if (mRadius == 1080) {
//                tmp = layoutRadius / 2f - DensityUtil.dip2px(getContext(), 28.0f);
//            }
//            // tmp cosa 即menu item中心点的横坐标
//            left = layoutRadius
//                    / 2
//                    + (int) Math.round(tmp
//                    * Math.cos(Math.toRadians(startAngle)) - 1 / 2f
//                    * cWidth) + DensityUtil
//                    .dip2px(getContext(), 1);
//            // tmp sina 即menu item的纵坐标
//            top = layoutRadius
//                    / 2
//                    + (int) Math.round(tmp
//                    * Math.sin(Math.toRadians(startAngle)) - 1 / 2f * cWidth) + DensityUtil
//                    .dip2px(getContext(), 8);
//
//            startAngle = Integer.parseInt(new DecimalFormat("0").format(startAngle));
//            if (startAngle == 190 || startAngle == 230 || startAngle == 310
//                    || startAngle == 350) {
//                if (startAngle > 270) {
//                    canvas.drawText(intelli, left - mBound.width() - DensityUtil.dip2px(getContext
//                            (), 4), top + cWidth / 2 +
//                            mBound.height() / 2, mTextPaint);
//                } else {
//                    canvas.drawText(intelli, left + cWidth + DensityUtil.dip2px(getContext(), 4),
//                            top + cWidth /
//                                    2 +
//                                    mBound.height() / 2, mTextPaint);
//                }
//            }
//
//            // 叠加尺寸
//            startAngle += angleDelay;
//        }
//    }

    public void setTexts(String[] texts) {
        this.mItemTexts = texts;
    }

    /**
     * 设置menu item的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutRadius = mRadius;
        // Laying out the child views
        final int childCount = getChildCount();

        int left, top;
        // menu item 的尺寸
        int cWidth;

        // 根据menu item的个数，计算角度
        float angleDelay = 360 / 10;
        // 遍历去设置menuitem的位置
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (mStartAngle > 269 && mStartAngle < 271 && isTouchUp) {
                cWidth = DensityUtil.dip2px(getContext(), RADIO_TOP_CHILD_DIMENSION);
                child.setSelected(true);
            } else {
                cWidth = DensityUtil.dip2px(getContext(), RADIO_DEFAULT_CHILD_DIMENSION);
                child.setSelected(false);
            }

            if (child.getVisibility() == GONE) {
                continue;
            }
            mStartAngle = mStartAngle % 360;

            float tmp = 0;
            // 计算，中心点到menu item中心的距离。！！！这里很关键，将决定item所呈现的位置
//            if (mRadius == 720) {
//                tmp = layoutRadius / 2f - DensityUtil.dip2px(getContext(), 28.0f);
//            } else if (mRadius == 1080) {
//                tmp = layoutRadius / 2f - DensityUtil.dip2px(getContext(), 29.0f);
//            }
            tmp = layoutRadius / 2f - cWidth / 2 - mPadding;
            // tmp cosa 即menu item中心点的横坐标。计算的是item的位置，是计算位置！！！
            left = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
                    * cWidth) + DensityUtil
                    .dip2px(getContext(), 1);
            // tmp sina 即menu item的纵坐标
            top = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f * cWidth) + DensityUtil
                    .dip2px(getContext(), 8);

            child.layout(left, top, left + cWidth, top + cWidth);

            // 叠加尺寸
            mStartAngle += angleDelay;
        }
    }

    private void backOrPre() {     //缓冲的角度。即我们将要固定几个位置，而不是任意位置。我们要设计一个可能的角度去自动帮他选择。
        isTouchUp = true;
        float angleDelay = 360 / 10;              //这个是每个图形相隔的角度
        Log.d("TAG","angleDelay"+angleDelay);
        Log.d("TAG","backOrPre"+mStartAngle);
        //我们本来的上半圆的图片角度应该是：18,54，90,126,162。所以我们这里是：先让当前角度把初始的18度减去再取余每个图形相隔角度。得到的是什么呢？就是一个图片本来应该在的那堆角度。所以如果是就直接return了。
        if ((mStartAngle-18)%angleDelay==0){
            return;
        }
        //angle就是那个不是18度开始布局，然后是36度的整数的多出来的部分角度
        float angle = (float)((mStartAngle-18)%36);
        Log.d("TAT","angle: "+angle);
        //以下就是我们做的缓冲角度处理啦，如果多出来的部分角度大于图片相隔角度的一半就往前进一个，如果小于则往后退一个。
        if (angleDelay/2 > angle){
            mStartAngle -= angle;
        }else if (angleDelay/2<angle){
            mStartAngle = mStartAngle - angle + angleDelay;             //mStartAngle就是当前角度啦，取余36度就是多出来的角度，拿这个多出来的角度去数据处理。
        }

//错误的方法---原因：此方法取余有个极限，就是342度就是转的极限，而没有进行处理，导致可能直接就18度成为360度，而下一轮就不能布置18度开始
//        if (mStartAngle%36 != 0&&(mStartAngle ) % 18 == 0  ) {
//            return;
//        }
//        float angle = (float) ((mStartAngle ) % 18);            //angle就是那个不是18度开始布局，然后是36度的整数的多出来的部分角度

//        if (angle > 18){
//            mStartAngle = mStartAngle - angle + angleDelay;           //mStartAngle就是当前角度啦，取余36度就是多出来的角度，拿这个多出来的角度去数据处理。
//        }else if (angle<18&&mStartAngle%36 != 0){
//            mStartAngle -= angle;
//        }
//        if(mStartAngle%36 == 0){
//            mStartAngle += 18;
//        }

        requestLayout();
    }

    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;

//    /**
//     * 自动滚动的Runnable
//     */
//    private AutoFlingRunnable mFlingRunnable;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG","down");
                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("","ACTION_MOVE");
                isTouchUp = false;
                /**
                 * 获得开始的角度
                 */
                float start = getAngle(mLastX, mLastY);
                /**
                 * 获得当前的角度
                 */
                float end = getAngle(x, y);
                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += end - start;
                    mTmpAngle += end - start;
                } else
                // 二、三象限，色角度值是付值
                {
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }
                // 重新布局
                if (mTmpAngle != 0) {
                    Log.e("TAG",mTmpAngle + "");
                    requestLayout();
                }

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG","ACTION_UP");
                backOrPre();
//                isTouchUp = true;

//                requestLayout();
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    /**
     * 根据触摸的位置，计算角度
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    /**
     * 根据当前位置计算象限
     *
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }

    }


    public void setAngle(int position) {
        float angleDelay = 360 / 9;
        if (position > mCurrentPosition) {
            mStartAngle += (mCurrentPosition - position) * angleDelay;
        } else {
            mStartAngle -= (position - mCurrentPosition) * angleDelay;
        }
        requestLayout();
    }

    /**
     * 设置菜单条目的图标和文本
     *
     * @param resIds
     */
    public void setMenuItemIconsAndTexts(int[] resIds) {
        mItemImgs = resIds;

        // 参数检查
        if (resIds == null) {
            throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
        }

        // 初始化mMenuCount
        mMenuItemCount = resIds == null ? 0 : resIds.length;


        addMenuItems();

    }

    /**
     * 设置MenuItem的布局文件，必须在setMenuItemIconsAndTexts之前调用
     *
     * @param mMenuItemLayoutId
     */
    public void setMenuItemLayoutId(int mMenuItemLayoutId) {
        this.mMenuItemLayoutId = mMenuItemLayoutId;
    }

    /**
     * 添加菜单项
     */
    private void addMenuItems() {
//        LayoutInflater mInflater = LayoutInflater.from(getContext());

        /**
         * 根据用户设置的参数，初始化view
         */
        for (int i = 0; i < mMenuItemCount; i++) {
            final int j = i;
//            View view = mInflater.inflate(mMenuItemLayoutId, this, false);
            ImageView iv = new ImageView(getContext());

            if (iv != null) {
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(mItemImgs[i]);
                iv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        isTouchUp = true;
                    }
                });
            }

            // 添加view到容器中
            addView(iv);
        }
    }

    /**
     * 如果每秒旋转角度到达该值，则认为是自动滚动
     *
     * @param mFlingableValue
     */
    public void setFlingableValue(int mFlingableValue) {
        this.mFlingableValue = mFlingableValue;
    }

    /**
     * 设置内边距的比例
     *
     * @param mPadding
     */
    public void setPadding(float mPadding) {
        this.mPadding = mPadding;
    }

    /**
     * 获得默认该layout的尺寸
     *
     * @return
     */
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    /**
     * 自动滚动的任务
     *
     * @author zhy
     */
    private class AutoFlingRunnable implements Runnable {

        private float angelPerSecond;
        private float angleDelay;

        public AutoFlingRunnable(float velocity, float angleDelay) {
            this.angelPerSecond = velocity;
            this.angleDelay = angleDelay;
        }

        public void run() {
            // 如果小于20,则停止
            if (angleDelay <= 0) {
//                isFling = false;
//                test();
                return;
            }
//            isFling = true;
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += angelPerSecond;
            // 逐渐减小这个值
            if (angleDelay - angelPerSecond < 0) {
                angelPerSecond = angleDelay;
                angleDelay = 0;
            } else {
                angleDelay -= angelPerSecond;
            }
            postDelayed(this, 30);
            // 重新布局
            requestLayout();
        }
    }

}
