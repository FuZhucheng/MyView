package com.demo.fuzhucheng.pictureCarousel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ${符柱成} on 2016/8/27.
 */
public class BannerAdapter extends PagerAdapter {

    /**
     * 继承自 android.support.v4.view.PagerAdapter，复写4个方法
     * instantiateItem(ViewGroup, int)
     * destroyItem(ViewGroup, int, Object)
     * getCount()
     * isViewFromObject(View, Object)
     */

    private Context mContext;

    private List<Integer> resIds;

    public BannerAdapter(Context context) {
        this.mContext = context;
    }

    public void update(List<Integer> resIds) {
        if (this.resIds != null)
            this.resIds.clear();
        if (resIds != null)
            this.resIds = resIds;
    }

    //其实我们看到就是多调用了一个star方法，但是还不够，我们在适配器中还要做一点点小事情。
    // 为了能让轮播无限循环，所以我们在getCount中返回int的最大值：
    //返回视图的个数,，即将存放View的一个集合的大小返回，这里的大小也就是视图的个数
    // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量

    @Override
    public int getCount() {
        return resIds == null ? 0 : Integer.MAX_VALUE;
    }

    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    //作用就是用来判断 instantiateItem(ViewGroup, int)方法返回的Key是否和界面的View相关联，
    // 如果关联则返回true，否则返回false。关于返回true和返回false的区别谷歌官方文档没有说明
    //详细解释此原因的链接是：http://stackoverflow.com/questions/30995446/what-is-the-role-of-isviewfromobject-view-view-object-object-in-fragmentst
    //当返回为true时就将根据当前的position得到的view展示出来，否则就不展示。这里可以直接返回false发现viewpager一个界面也没有，
    // 直接返回true可以看到重合的界面，大家去自己去试试。
    @Override
    public boolean isViewFromObject(View view, Object object) {
        //当返回为true的时候，就将根据当前的position得到的view展示出来
        return view == object;
        //这里的view就是当前要展示的界面，这里的object就是instantiateItem方法的返回值，
        // 因为在instantiateItem方法中返回了这个界面所以这里view==object是为true的。

    }

    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    //作用就是从容器中移除position所对应的视图，而且这个移除的动作是在finishUpdate之前完成的。
    // 这个在 instantiateItem 方法中也提到过，也就是说在finishUpdate之前至少要完成两个动作①原来视图的移除②新视图的增加
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //resIds是我们的数据源。该了这个Count后，我们的instantiateItem()方法也要修改，
    // 不然会发生下标越界的异常，我们在拿到position后要做个处理：

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可

    //作用就是根据当前的posistion来创建对应的视图，并且将这个创建好的视图添加到容器中，这个添加操作是在调用finishUpdate(ViewGroup)这个方法之前完成的。
    // instantiateItem 方法会返回一个对象，
    // 这个对象代表这一个新的视图，这个对象不一定是一个View，可以是这个视图的其他容器，也就是说只要可以唯一代表这个界面的东西都可以作为这个对象。
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //~~~~~在这个方法中添加点击事件!!!!!!!!!!!!!!!

        ImageView imageView = new ImageView(mContext);
        // 如果是http://www.xx.com/xx.png这种连接，这里可以用ImageLoader之类的框架加载
        imageView.setImageResource(resIds.get(position % resIds.size()));
        container.addView(imageView);
        return imageView;
    }
}
