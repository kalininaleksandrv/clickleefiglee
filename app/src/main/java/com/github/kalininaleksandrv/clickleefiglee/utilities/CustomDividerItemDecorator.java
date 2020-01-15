package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.R;

public class CustomDividerItemDecorator extends RecyclerView.ItemDecoration {

    private Drawable customdivider;
    private int distanceBeetwinItems = 50;

    public CustomDividerItemDecorator(Drawable customdivider) {
        this.customdivider = customdivider;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //do not draw divider if there is only one item
        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }


        //here set distance between items with our custom constant to plus
        outRect.top = customdivider.getIntrinsicHeight()+distanceBeetwinItems;
    }

    //changing the height of outRect to the height of our divider to not overlapping othe views
    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        //take 10% of total with
        int custompadding = (int) Math.round(parent.getWidth()*0.1);

        //apply custom padding to start and finish drawing figure
        int dividerLeft = parent.getPaddingLeft()+custompadding;
        int dividerRight = parent.getWidth() - parent.getPaddingRight()-custompadding;

        //measure height of each elements in cycle cause height of each could be different (unlike width)
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            //apply custom distance to top and bottom drawing divider
            //height of divider
            int dividerheight = 3;
            //looking for center of distance between elements minus half of divider height and here will be start of divider
            int dividerTop = child.getBottom() + params.bottomMargin+(distanceBeetwinItems/2)-(dividerheight/2);
            //bottom = start plus height of divider
            int dividerBottom = dividerTop + dividerheight;

            customdivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            customdivider.draw(canvas);
        }

    }
}
