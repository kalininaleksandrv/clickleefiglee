package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.interfaces.ItemTouchHelperAdapter;

public class ArticleTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter itemTouchHelperAdapter;

    public ArticleTouchHelperCallback(ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
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

        ColorDrawable background;
        View itemView = viewHolder.itemView;

        if (dX > 0 ){
            background = new ColorDrawable(Color.BLUE);
            background.setBounds(0, itemView.getTop(), (int) (itemView.getLeft() + dX), itemView.getBottom());
        } else {
            background = new ColorDrawable(Color.RED);
            background.setBounds((int)(itemView.getRight() + dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
        }
        background.draw(canvas);
    }
}
