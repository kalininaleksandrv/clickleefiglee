package com.github.kalininaleksandrv.clickleefiglee.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.github.kalininaleksandrv.clickleefiglee.R;

public class CustomIndicatedView extends View {

    Paint activePaint;
    Paint notActivePaint;
    Context context;
    int countOfIndicators;
    int indicatorRadius;
    int activeIndicator;

    // TODO: 16.01.20 implement saving state or dependent behavior in activity

    public CustomIndicatedView(Context context) {
        super(context);
        this.context = context;
        initDrawable();
    }

    public CustomIndicatedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initDrawable();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomIndicatedView);
        countOfIndicators = typedArray.getInt(R.styleable.CustomIndicatedView_cust_indicators_count, 1);
        typedArray.recycle();
    }

    protected void initDrawable(){

        activeIndicator = 0;

        indicatorRadius = 20;

        activePaint = new Paint();
        notActivePaint = new Paint();

        activePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        activePaint.setColor(context.getColor(R.color.colorPrimary));

        notActivePaint.setStyle(Paint.Style.STROKE);
        notActivePaint.setStrokeWidth(3f);
        notActivePaint.setColor(context.getColor(R.color.colorPrimary));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight();
        int desiredHeight = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();

        int setWidth = measureDimension(desiredWidth, widthMeasureSpec);
        int setHeight = measureDimension(desiredHeight, heightMeasureSpec);


        if(countOfIndicators>setWidth) {
            countOfIndicators = setWidth/indicatorRadius/2;
        }
        setMeasuredDimension(setWidth, setHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int measuredHeight = getMeasuredHeight();
        int measuredwidth = getMeasuredWidth();
        int heightcenter = measuredHeight/2;

        int countOfSpaces = countOfIndicators+1;
        int spaceWidth = measuredwidth/countOfSpaces;

        for (int i = 0; i < countOfIndicators; i++) {
            if(i == activeIndicator){
                canvas.drawCircle(spaceWidth*(i+1), heightcenter, indicatorRadius, activePaint);
            } else{
                canvas.drawCircle(spaceWidth*(i+1), heightcenter, indicatorRadius, notActivePaint);
            }
        }
    }

    // return exactly size or min between desirable and measured by parent
    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        if (result < desiredSize){
            Log.e("MY", "The view is too small, the content might get cut");
        }
        return result;
    }

    public void setActiveIndicator(int activeIndicator) {
        if (activeIndicator < countOfIndicators){
            this.activeIndicator = activeIndicator;
        } else this.activeIndicator = 0;
    }

    public int getActiveIndicator() {
        return activeIndicator;
    }
}

