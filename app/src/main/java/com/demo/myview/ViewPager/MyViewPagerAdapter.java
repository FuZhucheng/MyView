package com.demo.myview.ViewPager;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by asus on 2016/8/18.
 */
//自定义viewpager的适配器
public class MyViewPagerAdapter extends PagerAdapter {

    List<View> list;

    //List<String> titles;
    public MyViewPagerAdapter(List<View> list) {
        // TODO Auto-generated constructor stub

        this.list = list;
        //this.titles=titles;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    //  判断  当前的view 是否是  Object 对象
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(list.get(position));
        Log.e("jhd", "添加--" + position);

        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub

        container.removeView(list.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        //return titles.get(position);
        return "1";  //暂时没用的
    }
}