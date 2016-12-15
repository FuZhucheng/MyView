package com.demo.fuzhucheng.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.demo.fuzhucheng.R;

import java.util.ArrayList;
import java.util.List;

    public class RecyclerViewActivity extends Activity {


    //RecyclerView代表的意义是，我只管Recycler View，也就是说RecyclerView只管回收与复用View，其他的你可以自己去设置。可以看出其高度的解耦，给予你充分的定制自由
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        //初始化数据
        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        //设置adapter
        mAdapter = new HomeAdapter(this, mDatas);
        //设置布局管理器。...StaggeredGridLayoutManager 瀑布就式布局管理器,，，GridLayoutManager 网格布局管理器。。。LinearLayoutManager 现行管理器，支持横向、纵向
        //当然了，改为GridLayoutManager以后，对于分割线，前面的DividerItemDecoration就不适用了，主要是因为它在绘制的时候，
        // 比如水平线，针对每个child的取值为：final int left = parent.getPaddingLeft();             final int right = parent.getWidth() - parent.getPaddingRight();
        //因为每个Item一行，这样是没问题的。而GridLayoutManager时，一行有多个childItem，这样就多次绘制了，并且GridLayoutManager时，
        // Item如果为最后一列（则右边无间隔线）或者为最后一行（底部无分割线）。所以需要一个DividerGridItemDecoration


        //注意StaggeredGridLayoutManager构造的第二个参数传一个orientation，如果传入的是StaggeredGridLayoutManager.VERTICAL代表有多少列；
        // 那么传入的如果是StaggeredGridLayoutManager.HORIZONTAL就代表有多少行
        //有一点需要注意，如果是横向的时候，item的宽度需要注意去设置，毕竟横向的宽度没有约束了，应为控件可以横向滚动了。
        //只是上面的item布局我们使用了固定的高度，下面我们仅仅在适配器的onBindViewHolder方法中为我们的item设置个随机的高度
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        mRecyclerView.setAdapter(mAdapter);
        //添加分割线
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线效果，五彩斑斓
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        initEvent();

    }


        //注意，这里更新数据集不是用adapter.notifyDataSetChanged()而是
        //notifyItemInserted(position)与notifyItemRemoved(position)
        //否则没有动画效果。
        private void initEvent()
        {
            mAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener()
            {
                @Override
                public void onItemClick(View view, int position)
                {
                    Toast.makeText(RecyclerViewActivity.this, position + " click",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(View view, int position)
                {
                    Toast.makeText(RecyclerViewActivity.this, position + " long click",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.id_action_add:
                mAdapter.addData(1);
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(1);
                break;
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.id_action_horizontalGridView:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                        StaggeredGridLayoutManager.HORIZONTAL));
                break;

            case R.id.id_action_staggeredgridview:
                Intent intent = new Intent(this , StaggeredGridLayoutActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
