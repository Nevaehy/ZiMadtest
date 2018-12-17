package com.heaven.zimadtest.ui;

import com.heaven.zimadtest.utils.Constants;

public class CatDogPresenter implements CatDogMvp.Presenter {
    CatDogMvp.View mView;

    public CatDogPresenter(CatDogMvp.View view) {
        attachView(view);
        mView.init();
    }

    @Override
    public void attachView(CatDogMvp.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onArticleSelected(int pos, String name, String url) {
        mView.showArticleDetails(pos, name, url);
    }

    @Override
    public void onTabSelected(String tabText) {
        if (tabText.equals(Constants.CAT)){
            if (mView.getFragManager().findFragmentByTag(Constants.CAT_FRAGMENT) == null) {
                mView.addArticleList(Constants.CAT_FRAGMENT, Constants.DOG_FRAGMENT);
            } else {
                mView.replaceArticleList(Constants.CAT_FRAGMENT, Constants.DOG_FRAGMENT);
            }
        } else {
            if (mView.getFragManager().findFragmentByTag(Constants.DOG_FRAGMENT) == null) {
                mView.addArticleList(Constants.DOG_FRAGMENT, Constants.CAT_FRAGMENT);
            } else {
                mView.replaceArticleList(Constants.DOG_FRAGMENT, Constants.CAT_FRAGMENT);
            }
        }
    }


}

