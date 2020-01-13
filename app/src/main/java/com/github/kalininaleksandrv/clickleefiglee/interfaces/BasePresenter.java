package com.github.kalininaleksandrv.clickleefiglee.interfaces;

import android.os.Handler;

import com.github.kalininaleksandrv.clickleefiglee.MainPageActivity;
import com.github.kalininaleksandrv.clickleefiglee.dao.Article;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface BasePresenter {

    void onAcivityAttach(MainPageActivity view, Handler handler);
    void onActivityDeattach();
    boolean isActivityAttached();
    void onUserStartInteraction();
    void onUserFetchData(int startposition, int quantity, boolean requirenew);
    ArrayList<Article> getDataToActivity() throws ExecutionException, InterruptedException;
    void setDefaults(MainPageActivity view, Handler uiHandler);

}
