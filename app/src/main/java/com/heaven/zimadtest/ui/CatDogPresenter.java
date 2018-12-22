package com.heaven.zimadtest.ui;

import com.heaven.zimadtest.utils.Constants;

public class CatDogPresenter implements CatDogMvp.MainPresenter {
    CatDogMvp.MainView mView;

    public CatDogPresenter(CatDogMvp.MainView view) {
        attachView(view);
        mView.init();
    }

    @Override
    public void attachView(CatDogMvp.MainView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onTabSelected(String tabText) {
        if (tabText.equals(Constants.CAT)){
            if (mView.isFragmentExist(Constants.CAT_FRAGMENT)) {
                mView.addArticleList(Constants.CAT_FRAGMENT, Constants.DOG_FRAGMENT);
            } else {
                mView.replaceArticleList(Constants.CAT_FRAGMENT, Constants.DOG_FRAGMENT);
            }
        } else {
            if (mView.isFragmentExist(Constants.DOG_FRAGMENT)) {
                mView.addArticleList(Constants.DOG_FRAGMENT, Constants.CAT_FRAGMENT);
            } else {
                mView.replaceArticleList(Constants.DOG_FRAGMENT, Constants.CAT_FRAGMENT);
            }
        }
    }


}

