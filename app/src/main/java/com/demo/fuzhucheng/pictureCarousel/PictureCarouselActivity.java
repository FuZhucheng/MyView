package com.demo.fuzhucheng.pictureCarousel;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.fuzhucheng.R;

import java.util.ArrayList;
import java.util.List;

public class PictureCarouselActivity extends Activity implements View.OnClickListener {


    private AutoPlayViewPager autoPlayViewPage;
    //    @BindView(R.id.ll_dot)
//    LinearLayout llDot;   //小圆点的布局
    public static LinearLayout llDot;

    public static List<Integer> resIds;   //装载图片的集合

    //用于包含底部小圆点的图片，只要设置每个imageview的图片资源为刚刚写的selector
    // ，选择和没选中就会有不同的效果，实现导航的功能。
    public static ArrayList<ImageView> imageViewsCircle;
    private int dotSize = 26;           //圆点大小
    //小圆点的动画
    public static Animator animatorToLarge;
    public static Animator animatorToSmall;
    //动画的执行标记，这个很重要，涉及到后面每个小圆点选中后，其他是否还原成原来的样子
    public static SparseBooleanArray isLarge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_carousel);


        findViewById(R.id.btn_scroll_change_left).setOnClickListener(this);
        findViewById(R.id.btn_scroll_change_right).setOnClickListener(this);
        findViewById(R.id.btn_scroll_previous).setOnClickListener(this);
        findViewById(R.id.btn_scroll_next).setOnClickListener(this);
        autoPlayViewPage = (AutoPlayViewPager) findViewById(R.id.view_pager);
        llDot = (LinearLayout) findViewById(R.id.ll_dot);


        // 这里可以换成http://www.xx.com/xx.png这种连接的集合
        resIds = new ArrayList<>();
        // 模拟几张图片
        resIds.add(R.drawable.timg);
        resIds.add(R.drawable.timg);
        resIds.add(R.drawable.timg);
        resIds.add(R.drawable.timg);
        resIds.add(R.drawable.timg);
        resIds.add(R.drawable.timg);
        resIds.add(R.drawable.timg);
        resIds.add(R.drawable.timg);


        BannerAdapter bannerAdapter = new BannerAdapter(this);
        bannerAdapter.update(resIds);
        initDots();
        initAnimator();
        initViewPager(bannerAdapter);
    }

    private void initAnimator() {
        animatorToLarge = AnimatorInflater.loadAnimator(PictureCarouselActivity.this, R.animator.scale_to_large);
        animatorToSmall = AnimatorInflater.loadAnimator(PictureCarouselActivity.this, R.animator.scale_to_small);
    }

    /**
     * 根据引导页的数量，动态生成相应数量的导航小圆点，并添加到LinearLayout中显示。
     */
    private void initDots() {
        isLarge = new SparseBooleanArray();

        //动态设置布局
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_dot);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(dotSize, dotSize);
        mParams.setMargins(10, 0, 10, 0);//设置小圆点左右之间的间隔

        imageViewsCircle = new ArrayList<ImageView>();          //初始化小圆点集合

        //为每个小圆点设置属性，selector，还有初始化小圆点
        for (int i = 0; i < resIds.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.first_viewpager2_meituan_selector);

            imageView.setSelected(false);
            imageViewsCircle.add(imageView);//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            layout.addView(imageView);//添加到布局里面显示
            isLarge.put(i, false);          //一开始为每个圆点标记为false
        }
    }

    private void initViewPager(BannerAdapter bannerAdapter) {
        autoPlayViewPage.setAdapter(bannerAdapter);
        // 以下两个方法不是必须的，因为有默认值
        autoPlayViewPage.setDirection(AutoPlayViewPager.Direction.LEFT);// 设置播放方向
        autoPlayViewPage.setCurrentItem(200); // 设置每个Item展示的时间
        autoPlayViewPage.start(); // 开始轮播
        autoPlayViewPage.setPageTransformer(true, new DepthPageTransformer());
        imageViewsCircle.get(0).setSelected(true);
//        //动画资源
//        animatorToLarge.setTarget(imageViewsCircle.get(0));
//        animatorToLarge.start();
//        isLarge.put(0, true);


//        autoPlayViewPage.setOnPageChangeListener(new BannerPageChangeListener());
    }


//    /**
//     * Banner的Page切换监听器
//     */
//    private class BannerPageChangeListener implements ViewPager.OnPageChangeListener {
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//            // Nothing to do
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            // Nothing to do
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            // 取余后的索引，得到新的page的索引
//            int newPositon = position % resIds.size();
//            // 根据索引设置图片的描述
////            tvBannerTextDesc.setText(bannerTextDescArray[newPositon]);
//            // 把上一个点设置为被选中
//            llDot.getChildAt(preDotPosition).setSelected(false);
//            // 根据索引设置那个点被选中
//            llDot.getChildAt(newPositon).setSelected(true);
//            // 新索引赋值给上一个索引的位置
//            preDotPosition = newPositon;
//
//
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scroll_previous:// 播放上一个
                autoPlayViewPage.previous();
                break;
            case R.id.btn_scroll_next:// 播放下一个
                autoPlayViewPage.next();
                break;
            case R.id.btn_scroll_change_left:// 改变为向左滑动
                autoPlayViewPage.setDirection(AutoPlayViewPager.Direction.LEFT);
                break;
            case R.id.btn_scroll_change_right:// 改变为向右滑动
                autoPlayViewPage.setDirection(AutoPlayViewPager.Direction.RIGHT);
                break;
        }
    }
}
