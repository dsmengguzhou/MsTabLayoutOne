package com.musi.awe.mstablayoutone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {

    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private Class fragmentArray[] = {Fragment1.class, Fragment2.class};
    private int imageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_view_btn};
    private String textViewArray[] = {"精彩活动", "往期活动"};
    private List<Fragment> list = new ArrayList<Fragment>();
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题栏
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        initView();//初始化控件
        initPage();//初始化页面
    }

    /*初始化Fragment*/
    private void initPage() {
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        list.add(fragment1);
        list.add(fragment2);
        //绑定Fragment适配器
        vp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),list));
        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    //    控件初始化控件
    private void initView() {
        vp = (ViewPager) findViewById(R.id.pager);

        /*实现OnPageChangeListener接口,目的是监听Tab选项卡的变化，然后通知ViewPager适配器切换界面*/
        /*简单来说,是为了让ViewPager滑动的时候能够带着底部菜单联动*/

        vp.addOnPageChangeListener(this);   //设置页面切换时的监听器
        layoutInflater = LayoutInflater.from(this);//加载布局管理器

        /*实例化FragmentTabHost对象并进行绑定*/
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);//绑定tabhost
        mTabHost.setup(this , getSupportFragmentManager() , R.id.pager);//绑定viewpager

        /*实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换*/
        /*简单来说,是为了当点击下面菜单时,上面的ViewPager能滑动到对应的Fragment*/
        mTabHost.setOnTabChangedListener(this);

        int count = textViewArray.length;

        /*新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment*/
        for (int i = 0; i < count; i++) {
            //给每个Tab按钮设置标签、图片和文字
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textViewArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中，并绑定Fragment
            mTabHost.addTab(tabSpec , fragmentArray[i] , null);
            mTabHost.setTag(i);
            mTabHost.getTabWidget().getChildAt(i)
                    //设置Tab被选中的时候颜色改变
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    private View getTabItemView(int i) {
        //将xml布局转换为view对象
        View view = layoutInflater.inflate(R.layout.tab_content, null);
        //利用view对象，找到布局中的组件,并设置内容，然后返回视图
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setBackgroundResource(imageViewArray[i]);
        mTextView.setText(textViewArray[i]);
        return view;
    }

    //表示在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用的方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //position是表示你当前选中的页面位置Postion，
    //这事件是在你页面跳转完毕的时候调用的。
    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        //设置View覆盖子类控件而直接获得焦点
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        //根据位置Postion设置当前的Tab
        mTabHost.setCurrentTab(position);
        //设置取消分割线
        widget.setDescendantFocusability(oldFocusability);
    }

    //state ==1的时候表示正在滑动，
    //state==2的时候表示滑动完毕了，
    //state==0的时候表示什么都没做，就是停在那。
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //Tab改变的时候调用
    @Override
    public void onTabChanged(String tabId) {
        int position = mTabHost.getCurrentTab();
        //把选中的Tab的位置赋给适配器，让它控制页面切换
        vp.setCurrentItem(position);
    }
}
