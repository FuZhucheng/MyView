package com.demo.fuzhucheng.mycirclemenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.fuzhucheng.R;

import java.util.List;

public class MyCircleMenuActivity extends AppCompatActivity  {

    private UpCircleMenuLayout myCircleMenuLayout;


    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;

    private TextView tv_homepage;
    private TextView tv_setting;
    private TextView tv_history;

    private ImageView img_homepage;
    private ImageView img_setting;
    private ImageView img_history;

    List<Fragment> fragmentList;//页面集合
    //四个fragment页面
    private HomepageFragment homepageFragment;
    private SettingFragment settingFragment;
    private HistoryFragment historyFragment;

    private int position;


    private String[] mItemTexts = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "安全中心", "特色服务", "投资理财", "转账汇款", "我的账户"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal, R.drawable.home_mbank_1_normal, R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal, R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle_menu);

        //第一次初始化首页默认显示第一个fragment
        initFragment1();

        myCircleMenuLayout = (UpCircleMenuLayout) findViewById(R.id.id_mymenulayout);
        myCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs);
        myCircleMenuLayout.setOnMenuItemClickListener(new UpCircleMenuLayout.OnMenuItemClickListener() {


//            @Override
//            public void itemClick(View view, int pos) {
//                Toast.makeText(MyCircleMenuActivity.this, mItemTexts[pos],
//                        Toast.LENGTH_SHORT).show();
//
//            }

            @Override
            public void itemClick(int pos) {
                position=pos;
                Toast.makeText(MyCircleMenuActivity.this, mItemTexts[position],
                        Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        initFragment1();
                        break;
                    case 1:
                        initFragment2();
                        break;
                    case 2:
                        initFragment3();
                        break;
                    case 3:
                        initFragment1();
                        break;
                    case 4:
                        initFragment2();
                        break;
                    case 5:
                        initFragment3();
                        break;
                    case 6:
                        initFragment1();
                        break;
                    case 7:
                        initFragment2();
                        break;
                    case 8:
                        initFragment3();
                        break;
                    case 9:
                        initFragment1();

                        break;
                }
            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(MyCircleMenuActivity.this,
                        "you can do something just like ccb  ",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


     //显示第一个fragment
    private void initFragment1(){
        //开启事务，fragment的控制是由事务来实现的

        homepageFragment = new HomepageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_tv,homepageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //显示第二个fragment
    private void initFragment2(){
        //开启事务，fragment的控制是由事务来实现的

        settingFragment = new SettingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_tv,settingFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void initFragment3(){
        //开启事务，fragment的控制是由事务来实现的

        historyFragment = new HistoryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_tv,historyFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
