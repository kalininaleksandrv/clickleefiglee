package com.github.kalininaleksandrv.clickleefiglee.interactors;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataInteractor {

    private static volatile  DataInteractor instance;

    private static boolean value;

    private String data;

    private DataInteractor() {
    }

    public void getData() {

        final OkHttpClient client = new OkHttpClient();
        Handler mainHandler = new Handler(Looper.getMainLooper()); // TODO: 19.02.20 do smthn with this handler

        Request request = new Request.Builder()
                .url("https://puppyexpert.ru/api/breeds")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jstring = Objects.requireNonNull(response.body()).string();
                System.out.println(jstring);
            }
        });

    }

    public static DataInteractor getInstance(boolean value){
        DataInteractor.value = value;
        DataInteractor result = instance;
        if (result != null && !value) {
            System.out.println("return same instance of Data Interactor");
            return result;
        }
        synchronized(DataInteractor.class) {
            if (instance == null || value) {
                System.out.println("create new instance of Data Interactor");
                instance = new DataInteractor();
            }
            return instance;
        }
    }



}
