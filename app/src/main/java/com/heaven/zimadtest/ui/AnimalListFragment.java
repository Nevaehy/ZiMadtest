package com.heaven.zimadtest.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heaven.zimadtest.CatDogCommunicable;
import com.heaven.zimadtest.R;
import com.heaven.zimadtest.api.CatDogInterface;
import com.heaven.zimadtest.utils.Constants;
import com.heaven.zimadtest.model.CatDog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalListFragment extends Fragment implements CatDogCommunicable {
    public static final String TAG = "tag";
    public static final String ANIMALS_LIST = "animals_list";

    private ArrayList<CatDog.Animal> animals;
    private CatDogCommunicable mListener;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;

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
        if (getArguments() != null) {
            tag = getArguments().getString(TAG);
        }
        if (savedInstanceState != null) {
            animals = savedInstanceState.getParcelableArrayList(ANIMALS_LIST);
        } else {
            getAnimals(tag);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTabVisibility(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if (animals != null) {
                recyclerView.setAdapter(new CatDogAdapter(animals, mListener, getContext()));
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CatDogCommunicable) {
            mListener = (CatDogCommunicable) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CatDogCommunicable");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onArticleSelected(int pos, String name, String url) {
        mListener.onArticleSelected(pos, name, url);
    }

    @Override
    public void setTabVisibility(boolean visibility) {
        mListener.setTabVisibility(visibility);
    }


    public void getAnimals(String tag) {
        showLoading();
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
                if (response != null && response.body() != null)  {
                    animals = response.body().getData();
                    recyclerView.setAdapter(new CatDogAdapter(animals, mListener, getContext()));
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<CatDog> call, Throwable t) {
                hideLoading();
                Toast.makeText(getContext(), "Something went wrong. Check internet connection or try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showLoading() {
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
    public void onDestroy() {
        super.onDestroy();
        if((progressDialog != null) && progressDialog.isShowing() ){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ANIMALS_LIST, animals);
    }
}
