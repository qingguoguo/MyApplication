package com.example.bbbapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 作者:wangqing
 * 创建日期：2017/2/10 on 17:45
 * 描述:TextView
 */

public class FloatTextView extends TextView {

    //定义两种状态 移动和停止移动
    private final int STATE_MOVE = 1;
    private final int STATE_STOP = 0;
    //定义记录当前状态的变量
    private int mState;
    //定义一个记录当前动作的变量
    private int mAction;
    //初始化当前控件的位置
    private int currentX = 0;
    private int currentY = 0;
    //记录上一次控件的位置
    private int previousX = 0;
    private int previousY = 0;

    public FloatTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    //构造函数，设置当前状态为STATE_STOP，表示不移动
    public FloatTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mState = STATE_STOP;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mAction = event.getAction(); //获取当前动作
        currentX = (int) event.getX();  //获取当前X坐标
        currentY = (int) event.getY();  //获取当前Y坐标
        int parentWidth = ((ViewGroup) getParent()).getWidth();//获取父布局的宽
        int parentHeight = ((FrameLayout) getParent()).getHeight();//获取父布局的高
        switch (mAction) {
            case MotionEvent.ACTION_DOWN: //按下控件时
                mState = STATE_MOVE;  //状态设置为可移动
                previousX = currentX;  //记录上次X,Y坐标
                previousY = currentY;
                break;
            case MotionEvent.ACTION_MOVE: //开始移动控件
                mState = STATE_MOVE;
                int detelX = currentX - previousX;
                int detelY = currentY - previousY;
                int mTop = getTop();  //控件未移动前距离父窗口顶部的距离
                int mLeft = getLeft();  //控件未移动前距离父窗口左边的距离

                if (detelX != 0 || detelY != 0) //控件被移动
                {
                    Log.i("ACTION_MOVE", "onTouchEvent---getParent: 高:" + getHeight() + "宽:" + getWidth() + "，父布局的高:" + parentHeight + "，父布局的宽:" + parentWidth);
                    Log.i("ACTION_MOVE", "onTouchEvent---ACTION_MOVE: Top:" + getTop() + ",bottom:" + getBottom() + ",left:" + getLeft() + ",right:" + getRight());
                    //重新指定控件的位置和大小 l,  t,  r,  b
                    this.layout(detelX + mLeft, detelY + mTop, detelX + mLeft + getWidth(), detelY + mTop + getHeight());
                }
                previousX = currentX - detelX; //记录上一次的X，Y坐标
                previousY = currentY - detelY;
                break;

            case MotionEvent.ACTION_UP: //触摸事件结束，触摸离开屏幕
                //重新指定控件的位置和大小 l,  t,  r,  b
                Log.i("ACTION_UP", "onTouchEvent---ACTION_UP: " + currentX + "/" + currentY);
                Log.i("ACTION_UP", "onTouchEvent---ACTION_MOVE: Top:" + getTop() + ",bottom:" + getBottom() + ",left:" + getLeft() + ",right:" + getRight());
                int indexHeight = 0;
                if (parentHeight - getTop() < getHeight()) {
                    indexHeight = parentHeight - getHeight() - 50;
                } else if (getTop() < 50) {
                    indexHeight = 50;
                } else {
                    indexHeight = getTop();
                }
                if (getLeft() < ((parentWidth - getWidth()) / 2)) {
                    this.layout(20, indexHeight, 20 + getWidth(), indexHeight + getHeight());
                } else {
                    this.layout(parentWidth - getWidth() - 20, indexHeight, parentWidth - 20, indexHeight + getHeight());
                }
                mState = STATE_STOP;
                break;

            case MotionEvent.ACTION_CANCEL: //取消触摸事件
                mState = STATE_STOP;
                break;
        }
        return true;
    }
}

