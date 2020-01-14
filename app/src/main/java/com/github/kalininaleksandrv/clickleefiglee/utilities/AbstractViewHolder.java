package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;

abstract class AbstractViewHolder extends RecyclerView.ViewHolder {
    AbstractViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    abstract void bind(Article article, OnArticleClickListener onArticleClickListener);
    abstract View getItemView();
    abstract ImageView getRelatedPic();
    abstract TextView getDescriptionview();
}