package com.heaven.zimadtest.ui;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.heaven.zimadtest.CatDogCommunicable;
import com.heaven.zimadtest.R;
import com.heaven.zimadtest.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatDogActivity extends AppCompatActivity implements CatDogMvp.View, CatDogCommunicable {
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    private CatDogPresenter catDogPresenter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        catDogPresenter = new CatDogPresenter(this);

        if (savedInstanceState == null) {
            addArticleList(Constants.CAT_FRAGMENT, Constants.DOG_FRAGMENT);
        }
    }

    @Override
    public void onArticleSelected(int pos, String name, String url) {
        catDogPresenter.onArticleSelected(pos, name, url);
    }

    @Override
    public void setTabVisibility(boolean visibility) {
        if (visibility) {
            tabLayout.setVisibility(View.VISIBLE);
        } else {
            tabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showArticleDetails(int pos, String name, String url) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AnimalDetailsFragment detFragment = new AnimalDetailsFragment();

        // put bundle here
        Bundle bundle = new Bundle();
        bundle.putInt(AnimalDetailsFragment.ARG_POSITION, pos);
        bundle.putString(AnimalDetailsFragment.ARG_NAME, name);
        bundle.putString(AnimalDetailsFragment.ARG_URL, url);
        detFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container, detFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void addArticleList(String addFragTag, String hideFragTag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AnimalListFragment listFragment = new AnimalListFragment();

         // put bundle here
        Bundle bundle = new Bundle();
        bundle.putString(AnimalListFragment.TAG, addFragTag);
        listFragment.setArguments(bundle);

        if (fragmentManager.findFragmentByTag(hideFragTag) != null) {
            fragmentTransaction.hide(fragmentManager.findFragmentByTag(hideFragTag));
        }
        fragmentTransaction.add(R.id.fragment_container, listFragment, addFragTag);
        fragmentTransaction.commit();
    }

    @Override
    public void replaceArticleList(String showFragTag, String hideFragTag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(fragmentManager.findFragmentByTag(hideFragTag));
        fragmentTransaction.show(fragmentManager.findFragmentByTag(showFragTag));
        fragmentTransaction.commit();
    }

    @Override
    public void init() {
        fragmentManager = getSupportFragmentManager();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                catDogPresenter.onTabSelected(String.valueOf(tab.getText()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        catDogPresenter.detachView();
    }

    @Override
    public FragmentManager getFragManager() {
        return fragmentManager;
    }

}