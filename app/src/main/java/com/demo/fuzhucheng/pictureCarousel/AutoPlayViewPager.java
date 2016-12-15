package com.demo.fuzhucheng.pictureCarousel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by ${符柱成} on 2016/8/27.
 */
public class AutoPlayViewPager extends ViewPager {

    public AutoPlayViewPager(Context context) {
        super(context);
    }

    public AutoPlayViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 上一个被选中的小圆点的索引，默认值为0
     */
    private   int preDotPosition = 0;
    /**
     * 播放时间，默认3秒
     */
    private int showTime = 3 * 1000;

    /**
     *播放切换的瞬间
     */
    private int changeTime=500;

    /**
     * 滚动方向,设置滚动方向，默认向左滚动
     */
    private Direction direction = Direction.LEFT;

    /**
     * 设置播放时间，默认3秒
     *
     * @param showTimeMillis 毫秒
     */
    public void setShowTime(int showTimeMillis) {
        this.showTime = showTime;
    }

    /**
     * 设置滚动方向，默认向左滚动
     *
     * @param direction 方向
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * 开始,，这个就是设定开始轮播
     */
    public void start() {
        stop();
        postDelayed(player, showTime);

    }

    /**
     * 停止，这个就是设定停止轮播
     */
    public void stop() {
        removeCallbacks(player);
    }

    /**
     * 播放上一个
     */
    public void previous() {
        if (direction == Direction.RIGHT) {
            play(Direction.LEFT);
        } else if (direction == Direction.LEFT) {
            play(Direction.RIGHT);
        }
    }

    /**
     * 播放下一个
     */
    public void next() {
        play(direction);
    }

    /**
     * 执行播放
     *
     * @param direction 播放方向
     */
    private synchronized void play(Direction direction) {
        // 拿到ViewPager的适配器
        PagerAdapter pagerAdapter = getAdapter();
        // 判断不为空
        if (pagerAdapter != null) {
            // Item数量
            int count = pagerAdapter.getCount();
            // ViewPager现在显示的第几个？
            int currentItem = getCurrentItem();
            switch (direction) {
                case LEFT:
                    // 如是向左滚动的，currentItem+1播放下一个
                    currentItem++;
                    // 如果+到最后一个了，就到第一个
                    if (currentItem > count)
                        currentItem = 0;
                    break;
                case RIGHT:
                    // 如是向右滚动的，currentItem-1播放上一个
                    currentItem--;
                    // 如果-到低一个了，就到最后一个
                    if (currentItem < 0)
                        currentItem = count;
                    break;
            }
            // 播放根据方向计算出来的position的item
            setCurrentItem(currentItem);
        }
        // 这就是当可以循环播放的重点，每次播放完成后，再次开启一个定时任务
        start();
    }

    /**
     * 播放器
     */
    private Runnable player = new Runnable() {
        @Override
        public void run() {
            play(direction);
        }
    };

    public enum Direction {
        /**
         * 向左行动，播放的应该是右边的
         */
        LEFT,

        /**
         * 向右行动，播放的应该是左边的
         */
        RIGHT
    }


    //到这里其实我们已经实现轮播啦，但是我们在使用的时候发现存在一种情况，当我们用手指刚滑完一张，紧接着第二张又出来了，
    // 不卖关子了，原因就是我们手指滑动的时候private Runnable player这个任务没有停止，所以我们在手指滑动时停止player，手指松开的时候再次开启player：
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //有些同学可能不知道为什么不在构造方法中而要在onFinishInflate中addOnPageChangeListener，
        // 是因为这个方法会在view被加载完成后调用，所以我们在这里做一些初始化的工作比较合理。
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // 取余后的索引，得到新的page的索引
                int newPositon = position % PictureCarouselActivity.resIds.size();
                // 根据索引设置图片的描述
//            tvBannerTextDesc.setText(bannerTextDescArray[newPositon]);
                // 把上一个点设置为被选中
                PictureCarouselActivity.llDot.getChildAt(preDotPosition).setSelected(false);
//                PictureCarouselActivity.animatorToSmall.setTarget(PictureCarouselActivity.llDot.getChildAt(PictureCarouselActivity.preDotPosition));
//                PictureCarouselActivity.animatorToSmall.start();
//                PictureCarouselActivity.isLarge.put(PictureCarouselActivity.preDotPosition, false);
                // 根据索引设置那个点被选中
                PictureCarouselActivity.llDot.getChildAt(newPositon).setSelected(true);
//                PictureCarouselActivity.animatorToLarge.setTarget(newPositon);
//                PictureCarouselActivity.animatorToLarge.start();
//                PictureCarouselActivity.isLarge.put(newPositon, true);
                // 新索引赋值给上一个索引的位置
                preDotPosition = newPositon;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_IDLE)
                    start();
                else if (state == SCROLL_STATE_DRAGGING)
                    stop();
            }
        });
    }
}