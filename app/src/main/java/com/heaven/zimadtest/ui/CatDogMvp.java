package com.heaven.zimadtest.ui;

import android.support.v4.app.FragmentManager;
import android.widget.TableLayout;

public interface CatDogMvp {

    interface View {
        void init();
        void showArticleDetails(int pos, String name, String url);
        void addArticleList(String addFragTag, String hideFragTag);
        void replaceArticleList(String showFragTag, String hideFragTag);
        FragmentManager getFragManager();
    }


    interface Presenter {
        void attachView(CatDogMvp.View view);
        void detachView();
        void onArticleSelected(int pos, String name, String url);
        void onTabSelected(String tabText);
    }
}
