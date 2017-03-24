package com.smcculley.mycontacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by smcculley on 3/23/2017.
 */

public class FavoriteView extends View {
    private Paint mPaint;
    private Bitmap mStarEmpty;
    private Bitmap mStarFull;
    private OnSelectedChangedListener mOnSelectedChangedListener;


    public FavoriteView(Context context) {
        super(context);
        initialize();
    }



    public FavoriteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap image = isSelected() ? mStarFull : mStarEmpty;
        canvas.drawBitmap(image, 0, 0, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int desiredWidth = mStarEmpty.getWidth();
        int desiredHeight = mStarEmpty.getHeight();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(desiredWidth, widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                width = desiredWidth;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(desiredHeight, heightSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    private void initialize() {
        mPaint = new Paint();
        mStarEmpty = BitmapFactory.decodeResource(getResources(), R.drawable.ic_star_empty);
        mStarFull = BitmapFactory.decodeResource(getResources(), R.drawable.ic_star_filled);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(!isSelected());
                invalidate();
                if(mOnSelectedChangedListener != null) {
                    mOnSelectedChangedListener.onSelectedChanged(isSelected());
                }
            }


        });


    }

    public void setOnSelectedChangedListener(OnSelectedChangedListener onSelectedChangedListener) {
        mOnSelectedChangedListener = onSelectedChangedListener;
    }

    public static interface OnSelectedChangedListener {
        void onSelectedChanged(boolean selected);
    }


}
