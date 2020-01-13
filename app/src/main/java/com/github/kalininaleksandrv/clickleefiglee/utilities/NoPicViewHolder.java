package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.kalininaleksandrv.clickleefiglee.R;
import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;

class NoPicViewHolder extends AbstractViewHolder {
    private View itemView;
    private TextView idview;
    private TextView titleview;
    private TextView descriptionview;

    NoPicViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.idview = itemView.findViewById(R.id.nopic_textView_id);
        this.titleview = itemView.findViewById(R.id.nopic_textView_title);
        this.descriptionview = itemView.findViewById(R.id.nopic_textView_description);
    }

    @Override
    void bind(final Article article, final OnArticleClickListener onArticleClickListener) {
        idview.setText(article.getIdAsString());
        titleview.setText(article.getTitle());
        descriptionview.setText(article.getContent());

        //use interface OnArticleClickListener to pass movie instance when user clicked on item
        itemView.setOnClickListener(v -> onArticleClickListener.onArticleClick(article));
    }
 }

