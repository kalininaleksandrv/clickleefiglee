package com.github.kalininaleksandrv.clickleefiglee.viewmodels;

import androidx.lifecycle.ViewModel;

import com.github.kalininaleksandrv.clickleefiglee.interactors.DataInteractor;

public class NewsDataViewModel extends ViewModel {

    private DataInteractor dataInteractor;

    public NewsDataViewModel() {
        init(false);
    }

    private void init(boolean isNeedNew) {
        dataInteractor = DataInteractor.getInstance(isNeedNew);
    }

    public void initDataInteractor (boolean isNeedNew){
        init(isNeedNew);
    }
}
