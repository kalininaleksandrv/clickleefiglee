package com.github.kalininaleksandrv.clickleefiglee;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.dao.Constants;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.BaseUi;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;
import com.github.kalininaleksandrv.clickleefiglee.presenters.ArticlePresenter;
import com.github.kalininaleksandrv.clickleefiglee.utilities.AdvancedMovieAdapter;
import com.github.kalininaleksandrv.clickleefiglee.viewmodels.PresenterViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity implements OnArticleClickListener, BaseUi {

    AdvancedMovieAdapter advancedMovieAdapter;
    ProgressBar progressBar;
    FloatingActionButton fab;
    Handler uiHandler;
    List<Article> internalList;
    RecyclerView recyclerView;
    ArticlePresenter presenter;

    @Override
    public void showProgress() {
        if (fab!=null) fab.hide();
        if (progressBar!=null) progressBar.setVisibility(View.VISIBLE);
        Log.i("MY", "ACTIVITY showing progress");
    }

    @Override
    public void hideProgress() {
        if (fab!=null) fab.show();
        if (progressBar!=null) progressBar.setVisibility(View.GONE);
        Log.i("MY", "ACTIVITY hiding progress");

    }

    @Override
    public void showData() {
        advancedMovieAdapter.notifyDataSetChanged();
        Log.i("MY", "ACTIVITY showing data");
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        Log.i("MY", "ACTIVITY showing error");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpageactivity);

        progressBar = findViewById(R.id.recycling_progressBar);
        recyclerView = findViewById(R.id.recycling_recycleview);
        fab = findViewById(R.id.recycling_fab);

        PresenterViewModel model = ViewModelProviders.of(this).get(PresenterViewModel.class);
        presenter = model.getPresenter();
        presenter.setDefaults(this, uiHandler);

        internalList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //pass interface OnArticleClickListener to adapter and then to OnBindViewHolder
        advancedMovieAdapter = new AdvancedMovieAdapter(this, internalList, this);
        recyclerView.setAdapter(advancedMovieAdapter);
        recyclerView.setLayoutManager(layoutManager);

        fab.setOnClickListener(v -> {
//            Movie nextmovie;
//            long id = maxid;
//            maxid++;
//            String movietitle = "Movie title " + id;
//            nextmovie = new Movie(id, movietitle);
//            String moviedescription = "Movie description " + nextmovie.hashCode();
//            nextmovie.setDescription(moviedescription);
//            if(id%2!=0) {
//                nextmovie.setState(States.NOPICTURE);
//            } else {
//                nextmovie.setState(States.WITHPICTURE);
//            }
//            generatedListOfMovies.add(nextmovie);
//
//            internalList.clear();
//            internalList.addAll(generatedListOfMovies);
//            advancedMovieAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        uiHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == Constants.SUCSESS){
                    ArrayList<Article> data;
                    data = presenter.getDataToActivity();
                    if(data!=null) {
                        hideProgress();
                        internalList.clear();
                        internalList.addAll(data);
                        if (recyclerView.getAdapter() != null) {
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                } else {
                    hideProgress();
                    showError((String)msg.obj);
                }
            }
        };

        presenter.onAcivityAttach(this, uiHandler);
        presenter.onUserStartInteraction();
        presenter.onUserFetchData(0,10, false);

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onActivityDeattach();
        advancedMovieAdapter = null;
    }

    //implement interface to hold click within recycle view
    @Override
    public void onArticleClick(Article article) {
        Toast.makeText(getApplicationContext(), article.toString(), Toast.LENGTH_SHORT).show();
    }



}
