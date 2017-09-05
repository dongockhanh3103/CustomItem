package com.example.ngockhanh.graphics2dandcustomview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Ngoc Khanh on 8/25/2017.
 */

public class ItemView extends View {
    CallbackDelete callbackDelete;
    ListenerOnDelete prepareDel;

    private final static int SQUARE_SIZE_DEF = 300;
    private final static int RADIUS_DEF = 50;
    private int itemColor;
    private int itemSize;
    private int itemRadius;
    private Paint mPaintSquare;
    private Bitmap putImage;
    private Item mItem;
    int width;
    int height;
    private boolean ensureClose = false;


    private Drawable drawable;
    private float _XCloseButton;
    private float _YCloseButton;
    private float circleRadius;
    private boolean rung = false;

    public String getItemText() {
        return itemText;
    }

    private String itemText = "myText";
    boolean goonaDelete = false;
    final static String TAG = "Cu";

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean isGoonaDelete() {
        return goonaDelete;
    }

    public void setGoonaDelete(boolean goonaDelete) {
        this.goonaDelete = goonaDelete;
    }

    public void setEnsureClose(boolean ensureClose) {
        this.ensureClose = ensureClose;
        invalidate();
    }

    public boolean isEnsureClose() {
        return ensureClose;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    //Construtor
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = 200;
        int desiredHeight = 200;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height + 50);


    }

    void init(@Nullable AttributeSet set) {
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (set == null) return;
        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.ItemView);
        itemColor = ta.getColor(R.styleable.ItemView_item_color, Color.GRAY);
        itemSize = ta.getDimensionPixelSize(R.styleable.ItemView_item_size, SQUARE_SIZE_DEF);

        itemRadius = ta.getDimensionPixelOffset(R.styleable.ItemView_item_radius, RADIUS_DEF);
        itemText = (String) ta.getText(R.styleable.ItemView_textItem);
        drawable = ta.getDrawable(R.styleable.ItemView_item_image_src);
        mItem = new Item(0, 0);
        ta.recycle();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        float cX = event.getX();
        float cY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (ensureClose) {
                    if (((0 <= cX) && (cX <= _XCloseButton + circleRadius)) && ((0 <= cY) && (cY <= _YCloseButton + circleRadius))) {


                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //  ItemView.this.setVisibility(getRootView().GONE);

                                        goonaDelete = true;
                                        callbackDelete.onDelete(itemText);
                                        invalidate();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                }
                break;
        }
        return value;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        mItem.DrawItem(canvas, ensureClose);

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

        public void DrawItem(Canvas canvas, boolean drawX) {
            if (drawable != null) {
                putImage = ((BitmapDrawable) drawable).getBitmap();
                putImage = Bitmap.createScaledBitmap(putImage, itemSize, itemSize, true);

            } else {
                putImage = BitmapFactory.decodeResource(getResources(), R.drawable.icon2);
                putImage = Bitmap.createScaledBitmap(putImage, itemSize, itemSize, true);
            }

            putImage = ((BitmapDrawable) drawable).getBitmap();
            putImage = Bitmap.createScaledBitmap(putImage, (int) (mWidth), (int) (mHeight), true);


            mPain.setShader(new BitmapShader(putImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            Paint paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
            //a number have defferent
            float diff = (float) (0.15 * (mX + itemSize));
            float radius = (float) (diff * (2.0 / 3));
            int diameter = (int) (radius * 2.0);
            float mainComponentPadding = (float) (0.45 * radius);
            canvas.drawRoundRect(new RectF(mX, mY + mainComponentPadding, mWidth + mX, mHeight + mY), itemRadius, itemRadius, mPain);
            Bitmap xButton = BitmapFactory.decodeResource(getResources(), R.drawable.xbutton);
            xButton = Bitmap.createScaledBitmap(xButton, diameter, diameter, true);
            paintCircle.setShader(new BitmapShader(xButton, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));


            //draw X button
            if (ensureClose) {
                float X_xButtonPadding = (float) ((diff * 1.0 / 2) + (6 * ((itemSize * 1.0) / 100 / 3)));
                float Y_xButtonPadding = (float) (2.0 * diff / 3);
                canvas.drawCircle(mX + X_xButtonPadding, mY + Y_xButtonPadding, radius, paintCircle);
                _XCloseButton = mX + X_xButtonPadding;
                _YCloseButton = mY + Y_xButtonPadding;
                circleRadius = radius;
            }
            //get X,Y of X button

            //draw text
            Paint pantText = new Paint(Paint.ANTI_ALIAS_FLAG);

            pantText.setColor(Color.BLACK);
            pantText.setTextSize(40);
            canvas.drawText(itemText, mX + mainComponentPadding + 30, mHeight + mX + 40, pantText);

        }


    }


}
