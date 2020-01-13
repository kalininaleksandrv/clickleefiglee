package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.R;
import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.dao.States;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;

import java.util.List;

public class AdvancedMovieAdapter extends RecyclerView.Adapter<AbstractViewHolder> {

    private Context context;
    private List<Article> articles;
    private final OnArticleClickListener onArticleClickListener;

    public AdvancedMovieAdapter(Context context, List<Article> articles, OnArticleClickListener onArticleClickListener) {
        this.context = context;
        this.articles = articles;
        this.onArticleClickListener = onArticleClickListener;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_pic, parent, false);
            return new PicViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_nopic, parent, false);
            return new NoPicViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder holder, int position) {
        Article article = articles.get(position);
        //pass interface OnArticleClickListener to adapter and then to OnBindViewHolder
        holder.bind(article, onArticleClickListener);
    }

    @Override
    public int getItemViewType(int position) {

        States state = articles.get(position).getState();

        int stateid;

        switch (state){
            case NOPICTURE: stateid = 0;
                break;
            case WITHPICTURE: stateid = 1;
                break;
            default: throw new IllegalStateException("Unexpected value: " + state);
        }
        return stateid;
    }

    @Override
    public int getItemCount() {
        if (articles == null){
            return 0;
        } else return articles.size();
    }
}