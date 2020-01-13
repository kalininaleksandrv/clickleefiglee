package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.kalininaleksandrv.clickleefiglee.R;
import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;

class PicViewHolder extends AbstractViewHolder {

    private View itemView;
    private TextView idview;
    private TextView titleview;
    private TextView descriptionview;
    private ImageView imageview;

    PicViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.idview = itemView.findViewById(R.id.pic_textView_id);
        this.titleview = itemView.findViewById(R.id.pic_textView_title);
        this.descriptionview = itemView.findViewById(R.id.pic_textView_description);
        this.imageview = itemView.findViewById(R.id.pic_imageView);
    }

    @Override
    void bind(final Article article, final OnArticleClickListener onArticleClickListener) {
        imageview.setImageResource(R.drawable.samplepic_preview);
        idview.setText(article.getIdAsString());
        titleview.setText(article.getTitle());
        descriptionview.setText(article.getContent());

        //use interface OnArticleClickListener to pass movie instance when user clicked on item
        itemView.setOnClickListener(v -> onArticleClickListener.onArticleClick(article));
    }
}
