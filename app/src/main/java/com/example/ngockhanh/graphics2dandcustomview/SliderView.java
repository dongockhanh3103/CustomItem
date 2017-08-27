package com.example.ngockhanh.graphics2dandcustomview;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ngoc Khanh on 8/22/2017.
 */

public class SliderView extends View {

    private int mSliderColor;
    private int mSliderBackground;
    private Slider mSlider;
    private float mDx;
    private boolean mDragMode;

    public int getmSliderColor() {
        return mSliderColor;
    }

    public void setmSliderColor(int mSliderColor) {
        this.mSliderColor = mSliderColor;
    }

    public int getmSliderBackground() {
        return mSliderBackground;
    }

    public void setmSliderBackground(int mSliderBackground) {
        this.mSliderBackground = mSliderBackground;
        invalidate();
        requestLayout();
    }


    public SliderView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SliderView, defStyleAttr, 0);
        try {
            mSliderColor = array.getColor(R.styleable.SliderView_sliderColor, 0);
            mSliderBackground = array.getColor(R.styleable.SliderView_sliderBackground, 0);

            setBackgroundColor(mSliderBackground);
        } catch (Exception e) {

        } finally {
            array.recycle();
        }
        mSlider = new Slider(0, 0, 250, 200);

    }

    //dài rộng của view cha, trả về
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        int height = getResources().getDimensionPixelOffset(R.dimen.slide_view_height);
        mSlider.setmHeight(height);
        mSlider.setmWidth(measureWidth / 6);
        setMeasuredDimension(getMeasuredWidth(), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            return;
        }
        mSlider.drawSlider(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mSlider.isGripped((int) event.getX(), (int) event.getY(), 3, 3)) {
                    setInDragMode(false);
                }
                mDx = event.getX() - mSlider.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDragMode)
                {
                    mSlider.setX(event.getX() - mDx);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isHalfWayPassed()) {

                } else {

                }
                setInDragMode(false);
                break;

        }
        return true;

    }

    private void setInDragMode(boolean mode) {
        this.mDragMode = mode;
    }

    public boolean isHalfWayPassed() {
        return false;
    }

    private class Slider {
        private Paint mPaint;
        private float mX, mY;
        private int mWidth;
        private int mHeight;

        public int getmWidth() {
            return mWidth;
        }

        public void setmWidth(int mWidth) {
            this.mWidth = mWidth;
        }

        public int getmHeight() {
            return mHeight;
        }

        public void setmHeight(int mHeight) {
            this.mHeight = mHeight;
        }


        public Slider(int x, int y, int mWidth, int mHeight) {
            this.mX = x;
            this.mY = y;
            this.mWidth = mWidth;
            this.mHeight = mHeight;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.RED);

        }


        public float getX() {
            return mX;
        }

        public void setX(float mX) {
            this.mX = mX;
            invalidate();
        }

        public float getY() {
            return mY;
        }

        public void setY(float mY) {
            this.mY = mY;
            invalidate();
        }

        public boolean isGripped(int x, int y, int w, int h) {
            Rect rect = new Rect((int) mX, (int) mY, (int) (mWidth + mX), mHeight);

            return rect.intersect(x, y, w, h);
        }

        public void drawSlider(Canvas canvas) {

            canvas.drawRect(mX, mY, mWidth + mX, mHeight, mPaint);
            Rect rect=new Rect(10,10,20,20);

        }


    }
    public interface  SliderListener{
        enum SliderStatus{
            OPENED, CLOSED
        }
        void onSlide(float progress, SliderStatus status);
    }
}
