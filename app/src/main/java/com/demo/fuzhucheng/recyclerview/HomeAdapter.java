package com.demo.fuzhucheng.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.fuzhucheng.R;

import java.util.List;

//需要导入这个支持recyclerview的适配器包

/**
 * Created by ${符柱成} on 2016/9/18.
 */
class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{
    //系统没有提供ClickListener和LongClickListener。
    //不过我们也可以自己去添加，只是会多了些代码而已。
    //实现的方式比较多，你可以通过mRecyclerView.addOnItemTouchListener去监听然后去判断手势，
    //当然你也可以通过adapter中自己去提供回调，这里我们选择后者


    private List<String> mDatas;
    private LayoutInflater mInflater;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public HomeAdapter(Context context, List<String> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.item_home, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv.setText(mDatas.get(position));

        // 如果设置了回调，则设置点击事件!!!!!!！！！！！！！！！！！！！！！！！！
        //adapter中自己定义了个接口，然后在onBindViewHolder中去为holder.itemView去设置相应
        //的监听最后回调我们设置的监听。
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    removeData(pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

   // 注意，这里更新数据集不是用adapter.notifyDataSetChanged()而是
    //notifyItemInserted(position)与notifyItemRemoved(position)
    //否则没有动画效果。
    public void addData(int position) {
        mDatas.add(position, "Insert One");
        notifyItemInserted(position);
    }


    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends ViewHolder
    {

        TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);


        }
    }
}