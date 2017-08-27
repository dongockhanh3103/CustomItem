package com.example.ngockhanh.graphics2dandcustomview;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Ngoc Khanh on 8/25/2017.
 */

public class ItemView extends View {

    private final static int SQUARE_SIZE_DEF = 300;
    private final static int RADIUS_DEF = 50;
    private int itemColor;
    private int itemSize;
    private int itemRadius;
    private Paint mPaintSquare;
    private Bitmap putImage;
    private Item mItem;
    private float mDx;
    private Drawable drawable;


    public ItemView(Context context) {
        super(context);
        init(null);

    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    void init(@Nullable AttributeSet set) {
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (set == null) return;
        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.ItemView);
        itemColor = ta.getColor(R.styleable.ItemView_item_color, Color.GRAY);
        itemSize = ta.getDimensionPixelSize(R.styleable.ItemView_item_size, SQUARE_SIZE_DEF);
        itemRadius = ta.getDimensionPixelOffset(R.styleable.ItemView_item_radius, RADIUS_DEF);
        drawable = ta.getDrawable(R.styleable.ItemView_item_image_src);
        mItem = new Item(0, 0);


        if (drawable != null) {
            putImage = ((BitmapDrawable) drawable).getBitmap();
            putImage = Bitmap.createScaledBitmap(putImage, itemSize, itemSize, true);

        } else {
            putImage = BitmapFactory.decodeResource(getResources(), R.drawable.image);
             putImage = Bitmap.createScaledBitmap(putImage, itemSize, itemSize, true);
        }


        ta.recycle();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        int posX = (int) event.getRawX();
        int posY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                //mItem.setmPain(paint);
                Toast.makeText(getContext(), "X:" + event.getX(), Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Y:" + event.getY(), Toast.LENGTH_LONG).show();

                break;


            case MotionEvent.ACTION_MOVE:
                mItem.setmX(event.getX() - mDx);
                break;
            case MotionEvent.ACTION_UP:
                if (isHalfWayPassed()) {

                } else {

                }
                break;


        }
        return value;

    }

/*
   @Override
    protected void onDraw(Canvas canvas) {

     *//*    mPaintSquare.setShader(new BitmapShader(putImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(new RectF(50, 50, itemSize , itemSize ), itemRadius, itemRadius, mPaintSquare);*//*
     mPaintSquare.setColor(itemColor);
       canvas.drawRoundRect(new RectF(50,50,itemSize,itemSize),itemRadius,itemRadius,mPaintSquare);

    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        mItem.DrawItem(canvas);
    }

    public boolean isHalfWayPassed() {
        return false;
    }


    class Item {
        private float mX;
        private float mY;

        public void setmPain(Paint mPain) {
            this.mPain = mPain;
            invalidate();
        }

        private Paint mPain;
        private float mWidth, mHeight;

        public void setmWidth(float Width) {
            mWidth = Width;
        }

        public void setmHeight(float Height) {
            mHeight = Height;
        }

        public float getmX() {
            return mX;

        }

        public void setmX(float mX) {
            this.mX = mX;
            postInvalidate();
        }

        public float getmY() {

            return mY;
        }

        public void setmY(float mY) {

            this.mY = mY;
            postInvalidate();
        }


        public Item(int x, int y) {
            this.mX = x;
            this.mY = y;
            mPain = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPain.setColor(Color.GRAY);
            mWidth = itemSize;
            mHeight = itemSize;
        }

        public void DrawItem(Canvas canvas) {
            putImage = ((BitmapDrawable) drawable).getBitmap();
            putImage = Bitmap.createScaledBitmap(putImage, (int) (mWidth), (int) (mHeight), true);

            mPain.setShader(new BitmapShader(putImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.RED);
        /*
*/
            // paint.setShader(new BitmapShader(putImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(new RectF(mX, mY, mWidth + mX, mHeight + mY), itemRadius, itemRadius, mPain);
           float a= (float) (0.15*(mX+itemSize));
           // Toast.makeText(getContext(),"A:"+(float)(3.0/2),Toast.LENGTH_LONG).show();
            float radius=(float)(a*(2.0/3));
           canvas.drawCircle(mX+itemSize-(float)(a*1.0/2), mY+(float)(2.0*a/3),radius,paint);

        }

    }


}
