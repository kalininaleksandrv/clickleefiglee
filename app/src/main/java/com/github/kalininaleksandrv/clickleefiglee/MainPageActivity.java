package com.github.kalininaleksandrv.clickleefiglee;

import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.dao.Constants;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.BaseUi;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.OnArticleClickListener;
import com.github.kalininaleksandrv.clickleefiglee.presenters.ArticlePresenter;
import com.github.kalininaleksandrv.clickleefiglee.utilities.AdvancedMovieAdapter;
import com.github.kalininaleksandrv.clickleefiglee.utilities.ArticleTouchHelperCallback;
import com.github.kalininaleksandrv.clickleefiglee.utilities.CustomDividerItemDecorator;
import com.github.kalininaleksandrv.clickleefiglee.viewmodels.PresenterViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class MainPageActivity extends AppCompatActivity implements OnArticleClickListener, BaseUi {

    AdvancedMovieAdapter advancedMovieAdapter;
    ProgressBar progressBar;
    FloatingActionButton fab;
    Handler uiHandler;
    List<Article> commonlist;
    List<Article> cliclbaitlist;

    RecyclerView recyclerView;
    ArticlePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpageactivity);

        viewInitializer();

        PresenterViewModel model = ViewModelProviders.of(this).get(PresenterViewModel.class);
        presenter = model.getPresenter();
        presenter.setDefaults(this, uiHandler);

        recyclerViewInitializer();

    }

    @Override
    protected void onResume() {
        super.onResume();

        uiHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == Constants.SUCSESS){
                    ArrayList<Article> data = presenter.getDataToActivity();
                    if(data!=null) {
                        hideProgress();
                        commonlist.clear();
                        separateArticleList(data);

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
        presenter.onUserFetchData(0,10, false);

        fab.setOnClickListener(v -> presenter.onUserFetchData(0,10, true));
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onActivityDeattach();
        advancedMovieAdapter = null;
    }


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

    //implement interface to hold click within recycle view
    @Override
    public void onArticleClick(int position) {
        commonlist.get(position).setTitle("Some changed title");
        advancedMovieAdapter.notifyItemChanged(position);
        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    private void recyclerViewInitializer() {
        commonlist = new ArrayList<>();
        cliclbaitlist = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //pass interface OnArticleClickListener to adapter and then to OnBindViewHolder
        advancedMovieAdapter = new AdvancedMovieAdapter(this, commonlist, this, presenter);
        recyclerView.setAdapter(advancedMovieAdapter);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.Callback callback = new ArticleTouchHelperCallback(advancedMovieAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.simpledivider);
        int dividerColour = R.color.colorDividerDark;
        RecyclerView.ItemDecoration decoration = new CustomDividerItemDecorator(dividerDrawable, dividerColour);
        recyclerView.addItemDecoration(decoration);
    }

    private void viewInitializer() {
        progressBar = findViewById(R.id.recycling_progressBar);
        recyclerView = findViewById(R.id.recycling_recycleview);
        fab = findViewById(R.id.recycling_fab);
    }


    private void separateArticleList(ArrayList<Article> data) {
        //separate list for click-bait and fakenews elements
        for (Article article : data) {
            if (article.isClickBait() || article.isFakeNews()){
                cliclbaitlist.add(article);
            } else {
                commonlist.add(article);
            }
        }
    }

}
