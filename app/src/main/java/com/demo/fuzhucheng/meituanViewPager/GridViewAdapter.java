package com.demo.fuzhucheng.meituanViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.fuzhucheng.R;
import com.demo.fuzhucheng.meituanViewPager.MyGridViewData;

import butterknife.ButterKnife;

/**
 * Created by asus on 2016/8/18.
 */
//gridview 的适配器
public class GridViewAdapter extends BaseAdapter {

    //数据在MyConstant中定义好了
    private LayoutInflater inflater;
    private int page;

    public GridViewAdapter(Context context, int page) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.page=page;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(page!=-1){
            return 8;
        }else{
            return MyGridViewData.navSort.length;
        }
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.activity_viewpager_gridview_item, null);
            viewHolder.iv_navsort=(ImageView)convertView.findViewById(R.id.index_home_iv_navsort);
            viewHolder.tv_navsort=(TextView) convertView.findViewById(R.id.index_home_tv_navsort);
            ButterKnife.bind(viewHolder,convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.iv_navsort.setImageResource(MyGridViewData.navSortImages[position+8*page]);
        viewHolder.tv_navsort.setText(MyGridViewData.navSort[position+8*page]);

        //在这里处理gridview每个icon的逻辑，跳转activity
        if(position==8-1 && page==2){
            viewHolder.iv_navsort.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }else{
            viewHolder.iv_navsort.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }


        return convertView;
    }
    //gridview 适配器的holder类
     class ViewHolder{
        public ImageView iv_navsort;
        public TextView tv_navsort;
    }
}