package com.github.kalininaleksandrv.clickleefiglee.utilities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.R;
import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.dao.States;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.BasePresenter;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.ItemTouchHelperAdapter;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;

import java.util.List;

public class AdvancedMovieAdapter extends RecyclerView.Adapter<AbstractViewHolder> implements ItemTouchHelperAdapter {

    private Context context;
    private List<Article> articles;
    private final OnArticleClickListener onArticleClickListener;
    private BasePresenter basePresenter;

    public AdvancedMovieAdapter(Context context, List<Article> articles, OnArticleClickListener onArticleClickListener, BasePresenter basePresenter) {
        this.context = context;
        this.articles = articles;
        this.onArticleClickListener = onArticleClickListener;
        this.basePresenter = basePresenter;

        //to use notifyDataSetChange
        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        AbstractViewHolder abstractViewHolder;

        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_pic, parent, false);
            abstractViewHolder = new PicViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_nopic, parent, false);
            abstractViewHolder = new NoPicViewHolder(view);
        }

        //use interface OnArticleClickListener to pass movie instance when user clicked on item
        view.setOnClickListener(v -> {
            int i = abstractViewHolder.getAdapterPosition();
            //it's very important to check out if RV return the proper position fot element
            if (i != RecyclerView.NO_POSITION) {
                onArticleClickListener.onArticleClick(i);
            }
        });

        return abstractViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder holder, int position) {
        Article article = articles.get(position);
        //pass interface OnArticleClickListener to adapter and then to OnBindViewHolder
        holder.bind(article);
        holder.getItemView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.simple_animation_article));
        holder.getDescriptionview().setAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha_animation));
        if(holder.getRelatedPic()!=null) holder.getRelatedPic().setAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_plus_alpha));
    }

    @Override
    public int getItemViewType(int position) {

        States state;
        if (position >= 0 && position < articles.size()){
            state = articles.get(position).getState();
        } else{
            state = States.NOPICTURE;
            Log.i("MY", "INCORECT POSITION " + position);
        }

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

    //it must be implemented to correct work setHasStableIds(true)
    @Override
    public long getItemId(int position) {
        return articles.get(position).hashCode();
    }

    //carry out on Article swipe to dismiss
    @Override
    public void onItemDismiss(int position, int direction) {
        if (direction == ItemTouchHelper.START){
            articles.get(position).setFakeNews(true);
        }
        if (direction == ItemTouchHelper.END){
            articles.get(position).setClickBait(true);
        }
        basePresenter.changeDataById(position, direction);
        articles.remove(position);
        notifyItemRemoved(position);
    }
}