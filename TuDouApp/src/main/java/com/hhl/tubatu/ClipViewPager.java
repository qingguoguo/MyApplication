package com.hhl.tubatu;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HanHailong on 15/9/27.
 */
public class ClipViewPager extends ViewPager {

    //默认距离
    private final static float DISTANCE = 10;
    private float downX;
    private float downY;
    private InnerAdapter mAdapter;
    private PagerAdapter mOriginAdapter;
    private int mIndex = 1;
    private int mDuration = 4000;
    private PagerIndicator mPagerIndicator;

    public ClipViewPager(Context context) {
        this(context, null);
    }

    public ClipViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new InnerPagerChangeListener(null));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            downX = ev.getX();
            downY = ev.getY();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            mIndex = getCurrentItem();

            float upX = ev.getX();
            float upY = ev.getY();
            //如果 up的位置和down 的位置 距离 > 设置的距离,则事件继续传递,不执行下面的点击切换事件
            if (Math.abs(upX - downX) > DISTANCE || Math.abs(upY - downY) > DISTANCE) {
                return super.dispatchTouchEvent(ev);
            }

            View view = viewOfClickOnScreen(ev);
            if (view != null) {
                int index = (Integer) view.getTag();
                if (getCurrentItem() != index) {
                    setCurrentItem(index);
                }
            }
        } else if (ev.getAction() == MotionEvent.ACTION_CANCEL) {
            mIndex = getCurrentItem();
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param ev
     * @return
     */
    private View viewOfClickOnScreen(MotionEvent ev) {
        int childCount = getChildCount();
        int currentIndex = getCurrentItem();
        int[] location = new int[2];
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            int position = (Integer) v.getTag();
            v.getLocationOnScreen(location);
            int minX = location[0];
            int minY = location[1];

            int maxX = location[0] + v.getWidth();
            int maxY = location[1] + v.getHeight();

            if (position < currentIndex) {
                maxX -= v.getWidth() * (1 - ScalePageTransformer.MIN_SCALE) * 0.5 + v.getWidth() * (Math.abs(1 - ScalePageTransformer.MAX_SCALE)) * 0.5;
                minX -= v.getWidth() * (1 - ScalePageTransformer.MIN_SCALE) * 0.5 + v.getWidth() * (Math.abs(1 - ScalePageTransformer.MAX_SCALE)) * 0.5;
            } else if (position == currentIndex) {
                minX += v.getWidth() * (Math.abs(1 - ScalePageTransformer.MAX_SCALE));
            } else if (position > currentIndex) {
                maxX -= v.getWidth() * (Math.abs(1 - ScalePageTransformer.MAX_SCALE)) * 0.5;
                minX -= v.getWidth() * (Math.abs(1 - ScalePageTransformer.MAX_SCALE)) * 0.5;
            }
            float x = ev.getRawX();
            float y = ev.getRawY();

            if ((x > minX && x < maxX) && (y > minY && y < maxY)) {
                return v;
            }
        }
        return null;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        super.addOnPageChangeListener(new InnerPagerChangeListener(listener));
    }

    public void setPagerIndicator(PagerIndicator pagerIndicator) {
        mPagerIndicator = pagerIndicator;
        if (mOriginAdapter != null) {
            mPagerIndicator.updateData(mOriginAdapter.getCount());
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mOriginAdapter = adapter;
        mAdapter = new InnerAdapter(adapter);
        super.setAdapter(mAdapter);
        setCurrentItem(1);
        updatePagerIndicatorCount();
    }

    public void notifyDataSetChanged() {
        if (mOriginAdapter != null) {
            mOriginAdapter.notifyDataSetChanged();
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            setCurrentItem(1, false);
            mIndex = 1;
            updatePagerIndicatorCount();
        }
    }

    private void updatePagerIndicatorCount() {
        if (mPagerIndicator != null) {
            mPagerIndicator.updateData(mOriginAdapter.getCount());
        }
    }



    private class InnerAdapter extends PagerAdapter {

        PagerAdapter mPageAdapter;

        public InnerAdapter(PagerAdapter mPageAdapter) {
            this.mPageAdapter = mPageAdapter;
        }

        @Override
        public int getCount() {
            if (mPageAdapter.getCount() == 0) {
                return 0;
            }
            return mPageAdapter.getCount() + 2;
        }

        public int getPosition(int position) {
//            if (position == 0) {
//                position = mPageAdapter.getCount() - 1;
//            } else if (position == getCount() - 1) {
//                position = 0;
//            } else {
//                position -= 1;
//            }
            return position;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return mPageAdapter.instantiateItem(container, getPosition(position));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mPageAdapter.destroyItem(container, position, object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return mPageAdapter.isViewFromObject(view, object);
        }
    }

    private int convertPosition(int position) {
        if (position == 0) {
            position = mAdapter.getCount() - 2;
        } else if (position == mAdapter.getCount() - 1) {
            position = 1;
        }
        return position;
    }

    private class InnerPagerChangeListener implements OnPageChangeListener {

        OnPageChangeListener mListener;

        public InnerPagerChangeListener(OnPageChangeListener listener) {
            mListener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mListener != null) {
                mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            position = convertPosition(position);
            int select = mAdapter.getPosition(position);
            if (mListener != null) {
                mListener.onPageSelected(select);
            }
            if (mPagerIndicator != null) {
                mPagerIndicator.setSelect(select);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mListener != null) {
                mListener.onPageScrollStateChanged(state);
            }
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (mAdapter == null) {
                    return;
                }
                int currentItem = getCurrentItem();
                int changeItem = convertPosition(currentItem);
                if (currentItem != changeItem) {
                    setCurrentItem(changeItem, false);
                }
            }
        }
    }

}
