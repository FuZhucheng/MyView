package com.demo.fuzhucheng;

/**
 * Created by asus on 2016/7/27.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

//像listview的item，不过封装出来
public final class FeatureView extends FrameLayout {

    public FeatureView(Context context) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_main_item, this);
    }

    public synchronized void setTitleId(int titleId) {
        ((TextView) (findViewById(R.id.title))).setText(titleId);
    }

    public synchronized void setDescriptionId(int descriptionId) {
        ((TextView) (findViewById(R.id.description))).setText(descriptionId);
    }

}