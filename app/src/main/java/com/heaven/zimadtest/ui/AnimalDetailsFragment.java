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

import com.heaven.zimadtest.R;
import com.heaven.zimadtest.utils.GraphicUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AnimalDetailsFragment extends Fragment {

    protected static final String ARG_POSITION = "pos";
    protected static final String ARG_NAME = "name";
    protected static final String ARG_URL = "url";

    @BindView (R.id.details_image) ImageView mImage;
    @BindView (R.id.details_id) TextView mId;
    @BindView (R.id.details_name) TextView mName;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mId.setText(String.valueOf(getArguments().getInt(ARG_POSITION)));
            mName.setText(getArguments().getString(ARG_NAME));
            Picasso.get()
                    .load(getArguments().getString(ARG_URL))
                    .resize((int) getResources().getDimension(R.dimen.big_image_width),
                            (int) getResources().getDimension(R.dimen.big_image_height))
                    .into(mImage);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.tabLayout)
                .setVisibility(View.GONE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
