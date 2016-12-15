package com.demo.fuzhucheng.meituanViewPager;

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

    public MyViewPagerAdapter(List<View> list) {
        // TODO Auto-generated constructor stub

        this.list = list;
    }
    /**
     * 获取当前要显示对象的数量
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    //  判断  当前的view 是否是  Object 对象,判断是否用对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }
    /**
     * 当前要显示的对象（图片）
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(list.get(position));
        Log.e("fuzhu", "添加--" + position);

        return list.get(position);
    }
    /**
     * 从ViewGroup中移除当前对象（图片）
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub

        container.removeView(list.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        return "1";  //暂时没用的
    }
}