package com.heaven.zimadtest.ui;

import com.heaven.zimadtest.api.CatDogInterface;
import com.heaven.zimadtest.model.CatDog;
import com.heaven.zimadtest.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatDogListPresenter implements CatDogMvp.ListPresenter {
    CatDogMvp.ListView mView;

    public CatDogListPresenter (CatDogMvp.ListView view) {
        attachView(view);
    }

    @Override
    public void attachView(CatDogMvp.ListView view) {
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
    public void getAnimals(String tag) {
        mView.showLoading();
        Call<CatDog> call;
        if (tag.equals(Constants.CAT_FRAGMENT)) {
            call = CatDogInterface.Creator.getRetrofitClient().getAnimals(Constants.CAT);
        } else {
            call = CatDogInterface.Creator.getRetrofitClient().getAnimals(Constants.DOG);
        }

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<CatDog>() {
            @Override
            public void onResponse(Call<CatDog> call, Response<CatDog> response) {
                mView.hideLoading();
                if (response != null && response.body() != null)  {
                    mView.processResponse(response.body().getData());
                } else {
                    mView.showError("Something went wrong. Check internet connection or try again later.");
                }
            }

            @Override
            public void onFailure(Call<CatDog> call, Throwable t) {
                mView.hideLoading();
                mView.showError("Something went wrong. Check internet connection or try again later.");
            }
        });
    }
}
