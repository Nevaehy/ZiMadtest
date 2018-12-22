package com.heaven.zimadtest.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heaven.zimadtest.R;
import com.heaven.zimadtest.api.CatDogInterface;
import com.heaven.zimadtest.utils.Constants;
import com.heaven.zimadtest.model.CatDog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalListFragment extends Fragment implements CatDogMvp.ListView {
    public static final String TAG = "tag";
    public static final String ANIMALS_LIST = "animals_list";

    private ArrayList<CatDog.Animal> animals;
    private ProgressDialog progressDialog;

    ListActionsListener mListener;

    CatDogListPresenter catDogListPresenter;

    @BindView(R.id.list) RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnimalListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tag = Constants.CAT_FRAGMENT;

        catDogListPresenter = new CatDogListPresenter(this);

        if (getArguments() != null) {
            tag = getArguments().getString(TAG);
        }
        if (savedInstanceState != null) {
            animals = savedInstanceState.getParcelableArrayList(ANIMALS_LIST);
        } else {
            catDogListPresenter.getAnimals(tag);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);

        // Set the adapter
        if (animals != null) {
            recyclerView.setAdapter(new CatDogAdapter(animals, mListener, getContext()));
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.tabLayout)
                .setVisibility(View.VISIBLE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = new ListActionsListener() {
            @Override
            public void onArticleSelected(int id, String name, String url) {
                catDogListPresenter.onArticleSelected(id, name, url);
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void showLoading() {
        if (progressDialog == null) {
            try {
                progressDialog = ProgressDialog.show(getContext(), "", "Loading...");
                progressDialog.setCancelable(false);
            } catch (Exception e) {

            }
        }
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void processResponse(ArrayList<CatDog.Animal> ani) {
        animals = ani;
        recyclerView.setAdapter(new CatDogAdapter(animals, mListener, getContext()));
    }

    @Override
    public void showArticleDetails(int pos, String name, String url) {
        FragmentManager fragmentManager = getFragmentManager();
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
    public void onDestroy() {
        super.onDestroy();
        if((progressDialog != null) && progressDialog.isShowing() ){
            progressDialog.dismiss();
        }
        catDogListPresenter.detachView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ANIMALS_LIST, animals);
    }

}
