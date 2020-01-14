package com.github.kalininaleksandrv.clickleefiglee.presenters;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.github.kalininaleksandrv.clickleefiglee.MainPageActivity;
import com.github.kalininaleksandrv.clickleefiglee.dao.Article;
import com.github.kalininaleksandrv.clickleefiglee.dao.Constants;
import com.github.kalininaleksandrv.clickleefiglee.dao.States;
import com.github.kalininaleksandrv.clickleefiglee.interfaces.BasePresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticlePresenter implements BasePresenter {

    private WeakReference<MainPageActivity> weakactivity;
    private Handler handler;
    private ArrayList<Article> data;
    private Calendar calendar;

    public ArticlePresenter() {

        Log.i("MY", "Create new presenter");
        calendar = Calendar.getInstance();
    }

    public void setDefaults(MainPageActivity mainPageActivity, Handler uiHandler){
        this.weakactivity = new WeakReference<>(mainPageActivity);
        this.handler = uiHandler;
    }

    @Override
    public void onAcivityAttach(MainPageActivity mainPageActivity, Handler handler) {
        this.weakactivity = new WeakReference<>(mainPageActivity);
        this.handler = handler;
        Log.i("MY", "Activity attached: "+ mainPageActivity.getTitle());
    }

    @Override
    public void onActivityDeattach() {
        Log.i("MY", "Activity detached: ");
    }

    @Override
    public boolean isActivityAttached() {
        return weakactivity != null;
    }

    @Override
    public void onUserFetchData(int startposition, int quantity, boolean requirenew) {
        Log.i("MY", "Trying to get data");

        if(requirenew || (data != null ? data.size() : 0) != quantity) {
            Log.i("MY", "Trying to get data NEW");

            if (isActivityAttached()){
                weakactivity.get().showProgress();
            }
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> getDataFromModel(startposition, quantity));
        } else {
            Log.i("MY", "Trying to get data OLD");
            sentSucsessToHandler();
        }
    }


    @Override
    public ArrayList<Article> getDataToActivity() {
        Log.i("MY", "Trying to fetch data to activity");
        return data;
    }

    private void getDataFromModel(int startposition, int quantity) {

        Log.i("MY", "Working on thread: " + Thread.currentThread().getName());

        ArrayList<Article> generatedListOfMovies = new ArrayList<>();

        ArrayList<Long> uniqIdArray = new ArrayList<>(quantity);
        for (long i = startposition; i <= quantity; i++){
            uniqIdArray.add(i);
        }
        Collections.shuffle(uniqIdArray);

        for(int i=0; i<quantity; i++){
            Article nextarticle;
            String movietitle = "Some click-bait title here " + uniqIdArray.get(i);
            long id = uniqIdArray.get(i);
            nextarticle = new Article(id, movietitle);
            String moviedescription = "Article description has describe us an article, sic! " + nextarticle.hashCode();
            nextarticle.setContent(moviedescription);
            nextarticle.setMediatitle("Fake News Corp");
            if (calendar !=null){
                nextarticle.setDate(calendar.getTime().toString());
            }
            if(id%2!=0) {
                nextarticle.setState(States.NOPICTURE);
            } else {
                nextarticle.setState(States.WITHPICTURE);
            }
            //emulating delay of internet fetching data
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            generatedListOfMovies.add(nextarticle);
        }

        if (generatedListOfMovies.size() == quantity){
            data = new ArrayList<>();
            data.addAll(generatedListOfMovies);
            sentSucsessToHandler();
        } else {
            Message msg = Message.obtain();
            msg.what = Constants.ERROR;
            msg.obj = "error with loading data";
            handler.sendMessageDelayed(msg, 500);
            Log.i("MY", "handler error");
        }
    }

    private void sentSucsessToHandler() {
        handler.sendEmptyMessageDelayed(Constants.SUCSESS, 500);
        Log.i("MY", "handler success");
    }
}
