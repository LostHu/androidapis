package com.lity.android.apis.tabs;

import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.lity.android.apis.R;

public class Tab2 extends Activity {

    private Field mBottomLeftStrip;
    private TabHost mTabHost;
    private TabWidget mTabWidget;
    private Field mBottomRightStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2);
        mTabHost = (TabHost) findViewById(R.id.tab2_tabhost);
        mTabHost.setup();

        TabHost.TabSpec tab1 = mTabHost.newTabSpec("tab_1");
        tab1.setContent(R.id.tab1_linearlayout);
        tab1.setIndicator("TAB1");
        mTabHost.addTab(tab1);

        tab1 = mTabHost.newTabSpec("tab_2");
        tab1.setContent(R.id.tab2_linearlayout);
        tab1.setIndicator("TAB2");
        mTabHost.addTab(tab1);

        tab1 = mTabHost.newTabSpec("tab_3");
        tab1.setContent(R.id.tab3_linearlayout);
        tab1.setIndicator("TAB3");
        mTabHost.addTab(tab1);

        // mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.RED);
//        mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.BLUE);
//        mTabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.GREEN);
//        mTabHost.getTabWidget().setBackgroundResource(R.drawable.icon);

        // √¿ªØTabHost
        mTabWidget = mTabHost.getTabWidget();
        if (Integer.valueOf(Build.VERSION.SDK) <= 7) {
            try {
                mBottomLeftStrip = mTabWidget.getClass().getDeclaredField(
                        "mBottomLeftStrip");
                mBottomRightStrip = mTabWidget.getClass().getDeclaredField(
                        "mBottomRightStrip");
                if (!mBottomLeftStrip.isAccessible()) {
                    mBottomLeftStrip.setAccessible(true);
                }
                if (!mBottomRightStrip.isAccessible()) {
                    mBottomRightStrip.setAccessible(true);
                }
                mBottomLeftStrip.set(mTabWidget, R.drawable.tabwidget_bottom_bg);
                mBottomRightStrip.set(mTabWidget, R.drawable.tabwidget_bottom_bg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                mBottomLeftStrip = mTabWidget.getClass().getDeclaredField(
                        "mLeftStrip");
                mBottomRightStrip = mTabWidget.getClass().getDeclaredField(
                        "mRightStrip");
                if (!mBottomLeftStrip.isAccessible()) {
                    mBottomLeftStrip.setAccessible(true);
                }
                if (!mBottomRightStrip.isAccessible()) {
                    mBottomRightStrip.setAccessible(true);
                }
                mBottomLeftStrip.set(mTabWidget, getResources().getDrawable(R.drawable.icon));
                mBottomRightStrip.set(mTabWidget, getResources().getDrawable(R.drawable.icon));
            } catch (Exception e) { // TODO Auto-generated catch block
                // e.printStackTrace(); } }
            }
        }
    }

}
