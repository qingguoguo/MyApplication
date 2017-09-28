package com.example.administrator.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.nineoldandroids.view.ViewHelper;

public class MyHorizontalScrollView extends HorizontalScrollView {
	private int oneViewWidth;

	private Context context;
	private ViewGroup oneView;
	private ViewGroup twoView;
	private int twoPoint;
	private int oneViewMarginLeft;
	private int oneViewHeight;
	private OnViewChangedListener listener;
	private boolean isAnim = false;

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

	}

	public MyHorizontalScrollView(Context context) {
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		ViewGroup wrapper = (ViewGroup) getChildAt(0);
		oneView = (ViewGroup) wrapper.getChildAt(0);
		twoView = (ViewGroup) wrapper.getChildAt(1);
		// 获取第一个view的marginleft;
		MarginLayoutParams olp = (MarginLayoutParams) oneView.getLayoutParams();
		oneViewMarginLeft = olp.leftMargin;
		// 获取第一个view的宽度
		oneViewWidth = oneView.getMeasuredWidth();
		//oneViewHeight = oneView.getMeasuredHeight();

		MarginLayoutParams tlp = (MarginLayoutParams) twoView.getLayoutParams();
		int twoViewMarginLeft = tlp.leftMargin;

		// view的坐标
		twoPoint = oneViewMarginLeft + oneViewWidth + twoViewMarginLeft;

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		//这里为了解决滑动冲突,如果不需要忽略即可
		requestDisallowInterceptTouchEvent(true);

		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			
			//记录滑动的位置
			int scrollX = getScrollX();
			
			//如果滑动的位置大于第一个控件的左边距加上宽度的1/3,那么就自动滑到第二个控件
			if (scrollX > (oneViewMarginLeft + oneViewWidth / 3)) {
				this.smoothScrollTo(twoPoint, 0);
				if (listener != null) {
					//第二个控件回调
					listener.secondViewShow();
				}
			} else {
				//反之,就滑到起始的位置
				this.smoothScrollTo(0, 0);
				if (listener != null) {
					//第一个控件回调
					listener.firstViewShow();
				}
			}
			return true;

		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if (!isAnim) {
			return;
		}
		//缩放比例
		float scale = l * 1.0f / oneViewWidth;
		
		//第一个控件真实缩放比例
		float leftScale = 1 - 0.1f * scale;
		
		//第二个控件真实缩放比例
		float rightScale = 0.9f + scale * 0.1f;

		//设置各自的缩放比例
		ViewHelper.setPivotX(oneView, 0);
		ViewHelper.setPivotY(oneView, oneView.getHeight() / 2);
		ViewHelper.setScaleY(oneView, leftScale);

		ViewHelper.setPivotX(twoView, 0);
		ViewHelper.setPivotY(twoView, twoView.getHeight() / 2);
		ViewHelper.setScaleY(twoView, rightScale);

	}

	/** 滑到第一个view */
	public void scrollToFirstView() {
		this.smoothScrollTo(0, 0);
	}

	/** 滑到第二个view */
	public void scrollToSecondView() {
		this.smoothScrollTo(twoPoint, 0);
	}

	/** 是否开启动画,默认不打开 */
	public void setIsAnim(boolean isAnim) {
		this.isAnim = isAnim;
	}

	public void setOnViewChangedListener(OnViewChangedListener listener) {
		this.listener = listener;
	}

	/** 实现此借口监听view切换 */
	public interface OnViewChangedListener {
		void firstViewShow();

		void secondViewShow();
	}

}
