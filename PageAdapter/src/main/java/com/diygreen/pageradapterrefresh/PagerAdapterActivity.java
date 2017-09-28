package com.diygreen.pageradapterrefresh;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapterActivity extends AppCompatActivity {

    private ViewPager mContentVP;
    private static final float MIN_SCALE = 0.75f;
    private PagerAdapter mPagerAdapter;
    private List<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageradapter);

        initView();
        initData();

    }

    private void initView() {
        this.mContentVP = (ViewPager) findViewById(R.id.vp_content);
    }

    private void initData() {
        mDataList = new ArrayList<>();
        mDataList.add("Java");
        mDataList.add("Android");
        mDataList.add("C&C++");
        //mDataList.add("OC");
        //mDataList.add("Swift");
        mContentVP.setPageMargin(30);
        mContentVP.setOffscreenPageLimit(3);
        mPagerAdapter = new MyPagerAdapter();
        mContentVP.setAdapter(mPagerAdapter);
        mContentVP.setCurrentItem(1);
        mContentVP.setPageTransformer(true, new ViewPager.PageTransformer() {
            public static final float MAX_SCALE = 1.2f;
            public static final float MIN_SCALE = 0.6f;

            @Override
            public void transformPage(View page, float position) {

                if (position < -1) {
                    position = -1;
                } else if (position > 1) {
                    position = 1;
                }

                float tempScale = position < 0 ? 1 + position : 1 - position;

                float slope = (MAX_SCALE - MIN_SCALE) / 1;
                //一个公式
                float scaleValue = MIN_SCALE + tempScale * slope;
                page.setScaleX(scaleValue);
                page.setScaleY(scaleValue);
            }

        });

        mContentVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(PagerAdapterActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refresh:
                refresh();
                break;
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_clean:
                clear();
                break;
        }
    }

    private void refresh() {
        if (checkData()) return;
        mDataList.set(0, "更新数据源测试");
        mPagerAdapter.notifyDataSetChanged();
    }

    private void add() {
        mDataList.add("这是新添加的Item");
        mPagerAdapter.notifyDataSetChanged();
    }

    private void delete() {
        if (checkData()) return;
        mDataList.remove(0);
        mPagerAdapter.notifyDataSetChanged();
    }

    private void clear() {
        if (checkData()) return;
        mDataList.clear();
        mPagerAdapter.notifyDataSetChanged();
    }

    private boolean checkData() {
        return mDataList.size() == 0;
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(PagerAdapterActivity.this, R.layout.item_vp_demopageradapter, null);
            TextView pageNumTV = (TextView) view.findViewById(R.id.tv_pagenum);
            pageNumTV.setText("DIY-PageNum-" + mDataList.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            // 解决数据源清空，Item 不销毁的 bug
            //  return mDataList != null && mDataList.size()==0 ? POSITION_NONE : super.getItemPosition(object);
            // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
            return POSITION_NONE;
        }
    }
}
