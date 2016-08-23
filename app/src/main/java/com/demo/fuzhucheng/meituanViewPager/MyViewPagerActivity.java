package com.demo.fuzhucheng.meituanViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RadioButton;

import com.demo.fuzhucheng.R;
import com.demo.fuzhucheng.meituanViewPager.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyViewPagerActivity extends Activity {

    private GridView gridViewFirst;
    private GridView gridViewSecond;
    private GridView gridViewThird;


    @BindView(R.id.index_home_viewpager)
    ViewPager viewPager;

    @BindView(R.id.index_home_rb1)//radiogroup 1组以及3个radiobutton
     RadioButton rbFirst;
    @BindView(R.id.index_home_rb2)
     RadioButton rbSecond;
    @BindView(R.id.index_home_rb3)
     RadioButton rbThird;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_pager);
        ButterKnife.bind(this);
        initGridView();
        initViewPager();
    }

    //动态添加gridview
    private void initGridView() {
        gridViewFirst = (GridView) LayoutInflater.from(MyViewPagerActivity.this).inflate(R.layout.activity_viewpager_gridview, null);
        gridViewFirst.setAdapter(new com.demo.fuzhucheng.meituanViewPager.GridViewAdapter(MyViewPagerActivity.this, 0));
        gridViewSecond = (GridView) LayoutInflater.from(MyViewPagerActivity.this).inflate(R.layout.activity_viewpager_gridview, null);
        gridViewSecond.setAdapter(new com.demo.fuzhucheng.meituanViewPager.GridViewAdapter(MyViewPagerActivity.this, 1));
        gridViewThird = (GridView) LayoutInflater.from(MyViewPagerActivity.this).inflate(R.layout.activity_viewpager_gridview, null);
        gridViewThird.setAdapter(new com.demo.fuzhucheng.meituanViewPager.GridViewAdapter(MyViewPagerActivity.this, 2));
    }

    private void initViewPager() {   //初始化viewpager
        List<View> listGridView = new ArrayList<View>();  //以下实现动态添加三组gridview
        listGridView.add(gridViewFirst);
        listGridView.add(gridViewSecond);
        listGridView.add(gridViewThird);
        viewPager.setAdapter(new MyViewPagerAdapter(listGridView));
        rbFirst.setChecked(true);//设置默认  下面的点选中的是第一个
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {  //实现划到那个页面，那个页面下面的点会被选中
                // TODO Auto-generated method stub
                if (position == 0) {
                    rbFirst.setChecked(true);
                } else if (position == 1) {
                    rbSecond.setChecked(true);
                } else if (position == 2) {
                    rbThird.setChecked(true);
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

    	@Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.e("jhd", "onStop");
    }


}
