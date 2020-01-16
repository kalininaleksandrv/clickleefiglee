package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    private Paint paintSquare;

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

        paintSquare = new Paint();
        paintSquare.setColor(context.getColor(R.color.colorPrimaryDark));
        paintSquare.setStyle(Paint.Style.STROKE);
        paintSquare.setStrokeWidth(3f);

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
        int itemHeight = itemView.getMeasuredHeight();
        int thirdOfHeight = itemHeight/3;
        int halfOfHeight = itemHeight/2;

        int sendIconWidth = swipeRightIcon.getIntrinsicWidth();
        int halfOfSendIconHeight = swipeRightIcon.getIntrinsicHeight()/2;

        int disapointIconWidth = swipeLeftIcon.getIntrinsicWidth();
        int halfOfDisapointIconHeight = swipeLeftIcon.getIntrinsicHeight()/2;

        int squareMargin = 3;

        //when swipe right (else - when left)
        if (dX > 0 ){
            swipeRightBackground.setBounds(
                    0,
                    itemView.getTop(),
                    (int) (itemView.getLeft() + dX),
                    itemView.getBottom());

            //noinspection SuspiciousNameCombination
            swipeRightIcon.setBounds(
                    thirdOfHeight,
                    itemView.getTop()+itemHeight/2-halfOfSendIconHeight,
                    itemHeight/3+sendIconWidth,
                    itemView.getTop()+itemHeight/2+halfOfSendIconHeight);

            swipeRightBackground.draw(canvas);
            swipeRightIcon.draw(canvas);

            canvas.drawRect(thirdOfHeight-squareMargin,
                    itemView.getTop()+ halfOfHeight-halfOfSendIconHeight-squareMargin,
                    thirdOfHeight+sendIconWidth+squareMargin,
                    itemView.getTop()+halfOfHeight+halfOfSendIconHeight+squareMargin,
                    paintSquare);
        }
        if (dX < 0){
            swipeLeftIcon.setBounds(
                    itemView.getRight() - thirdOfHeight - disapointIconWidth,
                    itemView.getTop()+halfOfHeight-halfOfDisapointIconHeight,
                    itemView.getRight() - thirdOfHeight,
                    itemView.getTop()+halfOfHeight+halfOfDisapointIconHeight);

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
