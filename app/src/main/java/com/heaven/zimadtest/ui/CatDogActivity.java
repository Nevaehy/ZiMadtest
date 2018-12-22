package com.heaven.zimadtest.ui;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.heaven.zimadtest.R;
import com.heaven.zimadtest.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatDogActivity extends AppCompatActivity implements CatDogMvp.MainView {
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    private static final String CURRENT_TAB = "current_tab";

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
    public boolean isFragmentExist(String tag) {
        return fragmentManager.findFragmentByTag(tag) == null;
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_TAB, tabLayout.getSelectedTabPosition());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        tabLayout.getTabAt(savedState.getInt(CURRENT_TAB, 0)).select();
    }

}
