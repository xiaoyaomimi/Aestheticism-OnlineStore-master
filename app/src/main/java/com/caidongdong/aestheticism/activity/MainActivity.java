package com.caidongdong.aestheticism.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.caidongdong.aestheticism.fragment.FragmentFactory;
import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.view.MyViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @Bind(R.id.fragment_content)
    MyViewPager fragmentContent;
    @Bind(R.id.rb_index)
    RadioButton rbIndex;
    @Bind(R.id.rb_buywhat)
    RadioButton rbBuywhat;
    @Bind(R.id.rb_order)
    RadioButton rbOrder;
    @Bind(R.id.rb_me)
    RadioButton rbMe;
    @Bind(R.id.main_group)
    RadioGroup mainGroup;
    //导航栏radio个数
    static final int NUM_ITEMS = 4;
    public Myadapter madapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        madapter = new Myadapter(getSupportFragmentManager());
        initData();
        fragmentContent.setAdapter(madapter);
        fragmentContent.setOffscreenPageLimit(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        mainGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_index:
                        fragmentContent.setCurrentItem(0);
                        break;
                    case R.id.rb_buywhat:
                        fragmentContent.setCurrentItem(1);
                        break;
                    case R.id.rb_order:
                        fragmentContent.setCurrentItem(2);
                        break;
                    case R.id.rb_me:
                        fragmentContent.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        });
        //默认选择第一页
        mainGroup.check(R.id.rb_index);
        //viewpager滑动监听
        fragmentContent.setOnPageChangeListener(new MyViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mainGroup.check(R.id.rb_index);
                        break;
                    case 1:
                        mainGroup.check(R.id.rb_buywhat);
                        break;
                    case 2:
                        mainGroup.check(R.id.rb_order);
                        break;
                    case 3:
                        mainGroup.check(R.id.rb_me);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static class Myadapter extends FragmentStatePagerAdapter {

        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //从FragmentFactory中取得对应的界面
            return FragmentFactory.getFragment(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
