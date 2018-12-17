package com.heaven.zimadtest.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heaven.zimadtest.CatDogCommunicable;
import com.heaven.zimadtest.R;
import com.heaven.zimadtest.utils.GraphicUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AnimalDetailsFragment extends Fragment implements CatDogCommunicable {
    // TODO: Rename parameter arguments, choose names that match

    protected static final String ARG_POSITION = "pos";
    protected static final String ARG_NAME = "name";
    protected static final String ARG_URL = "url";

    @BindView (R.id.details_image) ImageView mImage;
    @BindView (R.id.details_id) TextView mId;
    @BindView (R.id.details_name) TextView mName;

    private CatDogCommunicable mListener;

    public AnimalDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTabVisibility(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.fragment_details_landscape, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_details, container, false);
        }
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mId.setText(String.valueOf(getArguments().getInt(ARG_POSITION)));
            mName.setText(getArguments().getString(ARG_NAME));
            Picasso.get()
                    .load(getArguments().getString(ARG_URL))
                    .resize(GraphicUtils.getScreenWidth(getActivity()), GraphicUtils.convertDpToPixels(300, getContext()))
                    .into(mImage);
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

    }

    @Override
    public void setTabVisibility(boolean visibility) {
        mListener.setTabVisibility(visibility);
    }


}