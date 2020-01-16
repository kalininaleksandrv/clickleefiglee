package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.R;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.ItemTouchHelperAdapter;

public class ArticleTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter itemTouchHelperAdapter;
    private Context context;
    private ColorDrawable swipeLeftBackground;
    private ColorDrawable swipeRightBackground;
    private Drawable swipeLeftIcon;
    private Drawable swipeRightIcon;

    public ArticleTouchHelperCallback(ItemTouchHelperAdapter itemTouchHelperAdapter, Context context) {
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
        this.context = context;
        initDrawableElements();
    }

    private void initDrawableElements(){
        swipeLeftBackground = new ColorDrawable(context.getColor(R.color.colorBackgroundLight));
        swipeRightBackground = new ColorDrawable(context.getColor(R.color.colorBackgroundNative));
        swipeLeftIcon = context.getDrawable(R.drawable.ic_sentiment_very_dissatisfied_red_48dp);
        swipeRightIcon = context.getDrawable(R.drawable.ic_send_blue_48dp);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition(), direction);
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(canvas, parent, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemheight = itemView.getMeasuredHeight();

        int sendiconwidth = swipeRightIcon.getIntrinsicWidth();
        int sendiconheight = swipeRightIcon.getIntrinsicHeight();

        int disapointiconwidth = swipeLeftIcon.getIntrinsicWidth();
        int disapointiconheight = swipeLeftIcon.getIntrinsicHeight();

        //when swipe right (else - when left)
        if (dX > 0 ){
            swipeRightBackground.setBounds(
                    0,
                    itemView.getTop(),
                    (int) (itemView.getLeft() + dX),
                    itemView.getBottom());

            swipeRightIcon.setBounds(
                    itemheight/3,
                    itemView.getTop()+itemheight/2-sendiconheight/2,
                    itemheight/3+sendiconwidth,
                    itemView.getTop()+itemheight/2+sendiconheight/2);

            swipeRightBackground.draw(canvas);
            swipeRightIcon.draw(canvas);
        }
        if (dX < 0){
            swipeLeftIcon.setBounds(
                    itemView.getRight() - itemheight/3 - disapointiconwidth,
                    itemView.getTop()+itemheight/2-disapointiconheight/2,
                    itemView.getRight() - itemheight/3,
                    itemView.getTop()+itemheight/2+disapointiconheight/2);

            swipeLeftBackground.setBounds(
                    (int)(itemView.getRight() + dX),
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom());

            swipeLeftBackground.draw(canvas);
            swipeLeftIcon.draw(canvas);
        }
    }
}
