package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.kalininaleksandrv.clickleefiglee.R;
import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;

class NoPicViewHolder extends AbstractViewHolder {

    private ImageView imageviewlogo;
    private View itemView;
    private TextView titleview;
    private TextView date;
    private TextView descriptionview;

    NoPicViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.imageviewlogo = itemView.findViewById(R.id.nopic_imageView_logo);
        this.titleview = itemView.findViewById(R.id.nopic_textView_title);
        this.date = itemView.findViewById(R.id.nopic_textView_date);
        this.descriptionview = itemView.findViewById(R.id.nopic_textView_description);
    }

    @Override
    void bind(final Article article) {
        imageviewlogo.setImageResource(R.drawable.ic_new_releases_green_24dp);
        titleview.setText(article.getTitle());
        date.setText(article.getDate());
        descriptionview.setText(article.getContent());
    }

    @Override
    View getItemView() {
        return itemView;
    }

    @Override
    ImageView getRelatedPic() {
        return null;
    }

    @Override
    TextView getDescriptionview() {
        return descriptionview;
    }
}

