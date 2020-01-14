package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.kalininaleksandrv.clickleefiglee.R;
import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;

class PicViewHolder extends AbstractViewHolder {

    private View itemView;
    private TextView titleview;
    private TextView date;
    private TextView descriptionview;
    private ImageView imageviewrel;
    private ImageView imageviewlogo;

    PicViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.titleview = itemView.findViewById(R.id.pic_textView_title);
        this.date = itemView.findViewById(R.id.pic_textView_date);
        this.descriptionview = itemView.findViewById(R.id.pic_textView_description);
        this.imageviewrel = itemView.findViewById(R.id.pic_imageView_related);
        this.imageviewlogo = itemView.findViewById(R.id.pic_imageView_logo);
    }

    @Override
    void bind(final Article article, final OnArticleClickListener onArticleClickListener) {
        imageviewlogo.setImageResource(R.drawable.ic_new_releases_green_24dp);
        titleview.setText(article.getTitle());
        date.setText(article.getDate());
        descriptionview.setText(article.getContent());
        imageviewrel.setImageResource(R.drawable.samplepic_preview);

        //use interface OnArticleClickListener to pass movie instance when user clicked on item
        itemView.setOnClickListener(v -> onArticleClickListener.onArticleClick(article));
    }

    public View getItemView() {
        return itemView;
    }

    @Override
    ImageView getRelatedPic() {
        return imageviewrel;
    }

    public TextView getDescriptionview() {
        return descriptionview;
    }
}
