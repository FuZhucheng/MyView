package com.demo.fuzhucheng.meituanViewPager2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.fuzhucheng.R;
import com.demo.fuzhucheng.meituanViewPager.GridViewAdapter;
import com.demo.fuzhucheng.meituanViewPager.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyViewPagerActivity2 extends Activity {

    @BindView(R.id.my_viewpager)
    ViewPager vPager;

    //用于包含底部小圆点的图片，只要设置每个imageview的图片资源为刚刚写的selector
    // ，选择和没选中就会有不同的效果，实现导航的功能。
    private ArrayList<ImageView> imageViewsCircle;

    private ArrayList<GridView> gridViews;//用于包含引导页要显示的图片


    private GridView gridViewFirst;
    private GridView gridViewSecond;
    private GridView gridViewThird;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2_gridview);
        ButterKnife.bind(this);

        initGridView();
        initDots();
        initViewPager();


    }


    //动态添加gridview
    private void initGridView() {
        gridViewFirst = (GridView) LayoutInflater.from(MyViewPagerActivity2.this).inflate(R.layout.activity_viewpager_gridview, null);
        gridViewFirst.setAdapter(new GridViewAdapter(MyViewPagerActivity2.this, 0));
        gridViewSecond = (GridView) LayoutInflater.from(MyViewPagerActivity2.this).inflate(R.layout.activity_viewpager_gridview, null);
        gridViewSecond.setAdapter(new GridViewAdapter(MyViewPagerActivity2.this, 1));
        gridViewThird = (GridView) LayoutInflater.from(MyViewPagerActivity2.this).inflate(R.layout.activity_viewpager_gridview, null);
        gridViewThird.setAdapter(new GridViewAdapter(MyViewPagerActivity2.this, 2));
        //把gridview添加进集合
        gridViews = new ArrayList<GridView>();
        gridViews.add(gridViewFirst);
        gridViews.add(gridViewSecond);
        gridViews.add(gridViewThird);
    }

    /**
     * 根据引导页的数量，动态生成相应数量的导航小圆点，并添加到LinearLayout中显示。
     */
    private void initDots() {
        //动态设置布局
        LinearLayout layout = (LinearLayout) findViewById(R.id.dot_layout);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mParams.setMargins(10, 0, 10, 0);//设置小圆点左右之间的间隔

        imageViewsCircle = new ArrayList<ImageView>();          //初始化小圆点集合

        //为每个小圆点设置属性，selector，还有初始化小圆点
        for (int i = 0; i < gridViews.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.first_viewpager2_meituan_selector);
//            imageView.setImageResource(R.drawable.home_serve_dot);                //此set图片的方案，代码复用不好，性能略低
            imageView.setSelected(false);
            imageViewsCircle.add(imageView);//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            layout.addView(imageView);//添加到布局里面显示
        }

    }


    private void initViewPager() {   //初始化viewpager
        List<View> listGridView = new ArrayList<View>();  //以下实现动态添加三组gridview
        listGridView.add(gridViewFirst);
        listGridView.add(gridViewSecond);
        listGridView.add(gridViewThird);
        vPager.setAdapter(new MyViewPagerAdapter(listGridView));
        imageViewsCircle.get(0).setSelected(true);
//        dotViews.get(0).setImageResource(R.drawable.home_serve_dot_pressed);            //此set图片的方案，代码复用不好，性能略低

        vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {  //实现划到那个页面，那个页面下面的点会被选中
                // TODO Auto-generated method stub


                //此读取方案代码复用性不强
//                if (position == 0) {
////                    dotViews.get(0).setImageResource(R.drawable.home_serve_dot_pressed);            //此set图片的方案，代码复用不好，性能略低
////                    dotViews.get(1).setImageResource(R.drawable.home_serve_dot);
////                    dotViews.get(2).setImageResource(R.drawable.home_serve_dot);
//                    imageViewsCircle.get(0).setSelected(true);
//                    imageViewsCircle.get(1).setSelected(false);
//                    imageViewsCircle.get(2).setSelected(false);
//
//                } else if (position == 1) {
////                    dotViews.get(0).setImageResource(R.drawable.home_serve_dot);            //此set图片的方案，代码复用不好，性能略低
////                    dotViews.get(1).setImageResource(R.drawable.home_serve_dot_pressed);
////                    dotViews.get(2).setImageResource(R.drawable.home_serve_dot);
//                    imageViewsCircle.get(0).setSelected(false);
//                    imageViewsCircle.get(1).setSelected(true);
//                    imageViewsCircle.get(2).setSelected(false);
//
//                } else if (position == 2) {
////                    dotViews.get(0).setImageResource(R.drawable.home_serve_dot);            //此set图片的方案，代码复用不好，性能略低
////                    dotViews.get(1).setImageResource(R.drawable.home_serve_dot);
////                    dotViews.get(2).setImageResource(R.drawable.home_serve_dot_pressed);
//                    imageViewsCircle.get(0).setSelected(false);
//                    imageViewsCircle.get(1).setSelected(false);
//                    imageViewsCircle.get(2).setSelected(true);
//
//                }

                //此读取方案虽然刚开始复杂度高，但是后面的代码复用性很强
                for (int i = 0; i < imageViewsCircle.size(); i++) {
                    if (position == i) {
                        imageViewsCircle.get(i).setSelected(true);
                    } else {
                        imageViewsCircle.get(i).setSelected(false);
                    }
                }


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

    }
}
