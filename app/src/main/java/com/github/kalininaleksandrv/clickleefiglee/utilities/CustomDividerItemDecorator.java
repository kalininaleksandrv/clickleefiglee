package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.R;

import java.util.Objects;

public class CustomDividerItemDecorator extends RecyclerView.ItemDecoration {

    private Context context;
    private Drawable customDivider;
    private int distanceBetweenItems = 50;
    private Paint paint;

    public CustomDividerItemDecorator(Context context) {

        this.context = context;
        initDrawableElements();
    }


    private void initDrawableElements(){

        paint = new Paint();
        paint.setColor(context.getColor(R.color.colorDividerDark));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        customDivider = ContextCompat.getDrawable(context, R.drawable.simpledivider);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //do not draw divider if there is only one item
        if (parent.getChildViewHolder(view).getAdapterPosition() == 0) {
            return;
        }
        //here set distance between items with our custom constant to plus
        outRect.top = customDivider.getIntrinsicHeight()+ distanceBetweenItems;
    }

    //changing the height of outRect to the height of our divider to not overlapping othe views
    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        //take 10% of total with
        int customPadding = (int) Math.round(parent.getWidth()*0.1);

        //apply custom padding to start and finish drawing figure
        int dividerLeft = parent.getPaddingLeft()+customPadding;
        int dividerRight = parent.getWidth() - parent.getPaddingRight()-customPadding;


        //measure height of each elements in cycle cause height of each could be different (unlike width)
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            //height of divider
            int dividerheight = 3;


            //looking for center of distance between elements minus half of divider height and here will be start of divider
            int dividerTop = child.getBottom() + params.bottomMargin+(distanceBetweenItems /2)-(dividerheight/2);
            //bottom = start plus height of divider
            int dividerBottom = dividerTop + dividerheight;

            customDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            customDivider.draw(canvas);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);


        paint.setStrokeWidth(5);

        int extramarkLeft = parent.getPaddingLeft()+20;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {

            View child = parent.getChildAt(i);

            //take half of item height
            int halfHeight = child.getMeasuredHeight()/2;

            //apply custom padding to start and finish extra-mark figure
            int extraMarkCenter = child.getTop()+halfHeight;
            int extraMarkRadius = 10;

            //set different divider colour depend on view type (type returned by Adapter.getViewType method)
            int position = parent.getChildViewHolder(child).getAdapterPosition();
            int viewType = Objects.requireNonNull(parent.getAdapter()).getItemViewType(position);

            if(viewType == 0){
                canvas.drawCircle(extramarkLeft, extraMarkCenter, extraMarkRadius, paint);
            }
        }
    }
}
