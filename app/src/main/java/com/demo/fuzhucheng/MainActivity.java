package com.demo.fuzhucheng;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.demo.fuzhucheng.R;
import com.demo.fuzhucheng.arcmenu.ArcMenuActivity;
import com.demo.fuzhucheng.colourfulFontOrNeonTextView.ColourfulFontOrNeonAcitivity;
import com.demo.fuzhucheng.meituanViewPager.MyViewPagerActivity;
import com.demo.fuzhucheng.meituanViewPager2.MyViewPagerActivity2;
import com.demo.fuzhucheng.progressCircle.ProgressCircleActivity;
import com.demo.fuzhucheng.someShapesImageview.ImageViewActivity;

public class MainActivity extends ListActivity {

    private static class DemoDetails {
        private final int titleId;
        private final int descriptionId;
        private final Class<? extends android.app.Activity> activityClass;

        public DemoDetails(int titleId, int descriptionId,
                           Class<? extends android.app.Activity> activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;

        }
    }


    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.activity_main_item, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            com.demo.fuzhucheng.FeatureView featureView;
            if (convertView instanceof com.demo.fuzhucheng.FeatureView) {
                featureView = (com.demo.fuzhucheng.FeatureView) convertView;
            } else {
                featureView = new com.demo.fuzhucheng.FeatureView(getContext());
            }
            DemoDetails demo = getItem(position);
            featureView.setTitleId(demo.titleId);
            featureView.setDescriptionId(demo.descriptionId);
            return featureView;
        }
    }

    private static final DemoDetails[] demos = {
            new DemoDetails(R.string.viewpager, R.string.viewpager_descirbe,             //自定义viewpager，美团上方轮播
                    MyViewPagerActivity.class),
            new DemoDetails(R.string.viewpager, R.string.viewpager_descirbe2,             //自定义viewpager，美团上方轮播
                    MyViewPagerActivity2.class),
            new DemoDetails(R.string.fontOrNenoTextview, R.string.fontOrNenoTextviewDescribe,             //自定义Textview结合LinearGradient和Matrix与TextView的多向字体效果
                    ColourfulFontOrNeonAcitivity.class),
            new DemoDetails(R.string.progressCircle, R.string.progressCircleDescribe,             //手动选择程度圆与进度圆（完全自定义）
                    ProgressCircleActivity.class),
            new DemoDetails(R.string.circleHead, R.string.circleHeadDescribe,             //里面含有圆形头像的三大方案，其中，最终方案已经彻底封装
                    ImageViewActivity.class),
            new DemoDetails(R.string.arcmenu, R.string.arcmenuDescribe,             //卫星导航菜单
                    ArcMenuActivity.class),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("自定义ViewDemo");
        ListAdapter adapter = new CustomArrayAdapter(
                this.getApplicationContext(), demos);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        startActivity(new Intent(this.getApplicationContext(),
                demo.activityClass));
    }
}
