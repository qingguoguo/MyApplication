package com.example.floattextview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * @author : qingguoguo
 * @datetime : 2017/10/17 17:35
 * @Describe :
 */

public class FloatTextView extends TextView {

    private final String TAG = FloatTextView.class.getSimpleName();
    private final int STATE_MOVE = 1;//定义两种状态 移动和停止移动
    private final int STATE_STOP = 0;
    private int parentWidth;//获取父布局的宽
    private int parentHeight;//获取父布局的高
    private int mState;//定义记录当前状态的变量
    private int currentX = 0;//初始化当前控件的位置
    private int currentY = 0;
    private int previousX = 0;//记录上一次控件的位置
    private int previousY = 0;
    private int minTopBottomDistance = 50;//悬浮框距离上下的最小距离
    private int minLeftRightDistance = 20;//悬浮框距离左右的最小距离

    public FloatTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    //构造函数，设置当前状态为STATE_STOP，表示不移动
    public FloatTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mState = STATE_STOP;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidth = ((ViewGroup) getParent()).getWidth();//获取父布局的宽
        parentHeight = ((ViewGroup) getParent()).getHeight();//获取父布局的高
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = (int) event.getX();  //获取当前X坐标
        currentY = (int) event.getY();  //获取当前Y坐标

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下控件时
                Log.i(TAG, "onTouchEvent---ACTION_DOWN: currentX:" + currentX + ",currentY:" + currentY);
                mState = STATE_MOVE;  //保存状态为移动
                previousX = currentX;  //记录上次X,Y坐标
                previousY = currentY;
                break;
            case MotionEvent.ACTION_MOVE: //开始移动控件
                mState = STATE_MOVE;
                int detelX = currentX - previousX;
                int detelY = currentY - previousY;
                int mTop = getTop();  //控件未移动前距离父窗口顶部的距离
                int mLeft = getLeft();  //控件未移动前距离父窗口左边的距离

                if (detelX != 0 || detelY != 0) { //控件被移动
                    Log.i(TAG, "onTouchEvent---ACTION_MOVE: 高:" + getHeight() + "宽:" + getWidth() + "，父布局的高:" + parentHeight + "，父布局的宽:" + parentWidth);
                    Log.i(TAG, "onTouchEvent---ACTION_MOVE: Top:" + getTop() + ",bottom:" + getBottom() + ",left:" + getLeft() + ",right:" + getRight());
                    //重新指定控件的位置和大小 l,  t,  r,  b
                    this.layout(detelX + mLeft, detelY + mTop, detelX + mLeft + getWidth(), detelY + mTop + getHeight());
                }
                previousX = currentX - detelX; //记录上一次的X，Y坐标
                previousY = currentY - detelY;
                break;

            case MotionEvent.ACTION_UP: //触摸事件结束，触摸离开屏幕
                //重新指定控件的位置和大小 l,  t,  r,  b
                Log.i(TAG, "onTouchEvent---ACTION_UP: currentX:" + currentX + ",currentY:" + currentY);
                Log.i(TAG, "onTouchEvent---ACTION_UP: Top:" + getTop() + ",bottom:" + getBottom() + ",left:" + getLeft() + ",right:" + getRight());

                int indexHeight;
                if (parentHeight - getTop() < getHeight()) {//悬浮框距离底部小于设置的最小距离，Top=等于最小距离
                    indexHeight = parentHeight - getHeight() - minTopBottomDistance;
                } else if (getTop() < minTopBottomDistance) {//悬浮框距离顶部小于设置的最小距离，Top=等于最小距离
                    indexHeight = minTopBottomDistance;
                } else {//其他情况Top不变
                    indexHeight = getTop();
                }
                //判断悬浮框是靠父view左，还是靠父view右
                if (getLeft() < ((parentWidth - getWidth()) / 2)) {
                    this.layout(minLeftRightDistance, indexHeight, minLeftRightDistance + getWidth(), indexHeight + getHeight());
                } else {
                    this.layout(parentWidth - getWidth() - minLeftRightDistance, indexHeight, parentWidth - minLeftRightDistance, indexHeight + getHeight());
                }
                mState = STATE_STOP;
                break;
        }
        return true;
    }
}

