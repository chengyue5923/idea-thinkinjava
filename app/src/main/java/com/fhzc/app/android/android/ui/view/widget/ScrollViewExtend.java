package com.fhzc.app.android.android.ui.view.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 能够兼容ViewPager的ScrollView
 *
 * @Description: 解决了ViewPager在ScrollView中的滑动反弹问题
 * @File: ScrollViewExtend.java
 * @Package com.image.indicator.control
 * @Author Hanyonglu
 * @Date 2012-6-18 下午01:34:50
 * @Version V1.0
 */
public class ScrollViewExtend extends ScrollView {
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;
    private ObservableScrollViewCallbacks callbacks;
    public ScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCallbacks(ObservableScrollViewCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (callbacks!=null){
            callbacks.onScrollChanged(t);
        }

    }

    public interface ObservableScrollViewCallbacks {
        void onScrollChanged(int scrollY);
    }
}