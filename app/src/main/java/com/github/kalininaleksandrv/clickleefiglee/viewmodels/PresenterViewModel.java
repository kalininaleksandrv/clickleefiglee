package com.github.kalininaleksandrv.clickleefiglee.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.github.kalininaleksandrv.clickleefiglee.presenters.ArticlePresenter;

public class PresenterViewModel extends ViewModel {

    private ArticlePresenter presenter;

    public ArticlePresenter getPresenter() {

        if(presenter != null){
            Log.i("MY", "Return OLD presenter");
            return presenter;
        } else {
            Log.i("MY", "Return NEW presenter");
            presenter = new ArticlePresenter();
            return presenter;
        }
    }
}
