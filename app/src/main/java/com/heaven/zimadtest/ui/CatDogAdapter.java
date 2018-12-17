package com.heaven.zimadtest.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heaven.zimadtest.CatDogCommunicable;
import com.heaven.zimadtest.R;
import com.heaven.zimadtest.utils.GraphicUtils;
import com.heaven.zimadtest.model.CatDog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CatDogAdapter extends RecyclerView.Adapter<CatDogAdapter.ViewHolder> {

    private final ArrayList<CatDog.Animal> mValues;
    private final CatDogCommunicable mListener;
    private final Context mContext;

    public CatDogAdapter(ArrayList<CatDog.Animal> items, CatDogCommunicable listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int pos = position;
        holder.mItem = mValues.get(position);
        holder.mId.setText(String.valueOf(position+1));
        holder.mName.setText(mValues.get(position).getTitle());
        Picasso.get()
                .load(mValues.get(position).getUrl())
                .resize(GraphicUtils.convertDpToPixels(120, mContext), GraphicUtils.convertDpToPixels(120, mContext))
                .into(holder.mImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onArticleSelected(pos+1, mValues.get(pos).getTitle(), mValues.get(pos).getUrl());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView (R.id.item_image) ImageView mImage;
        @BindView (R.id.item_id) TextView mId;
        @BindView (R.id.item_name) TextView mName;
        public CatDog.Animal mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
