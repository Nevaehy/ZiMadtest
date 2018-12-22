package com.heaven.zimadtest.ui;

import android.support.v4.app.FragmentManager;

import com.heaven.zimadtest.model.CatDog;

import java.util.ArrayList;

public interface CatDogMvp {

    interface MainView {
        void init();
        void addArticleList(String addFragTag, String hideFragTag);
        void replaceArticleList(String showFragTag, String hideFragTag);
        boolean isFragmentExist(String tag);
    }

    interface MainPresenter {
        void attachView(CatDogMvp.MainView view);
        void detachView();
        void onTabSelected(String tabText);
    }

    interface ListView {
        void showLoading();
        void hideLoading();
        void showError(String error);
        void processResponse(ArrayList<CatDog.Animal> animals);
        void showArticleDetails(int id, String name, String url);
    }

    interface ListPresenter {
        void attachView(CatDogMvp.ListView view);
        void detachView();
        void getAnimals(String tag);
        void onArticleSelected(int id, String name, String url);
    }
}
