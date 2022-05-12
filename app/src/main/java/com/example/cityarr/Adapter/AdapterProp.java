package com.example.cityarr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityarr.R;
import com.example.cityarr.entity.Proposition;

import java.util.ArrayList;
import java.util.List;

public class AdapterProp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADING = 1;
    private boolean mIsLoadingAdded = false;
    private List<Proposition> mPropositionList;
    Context mContext ;

    public AdapterProp(Context context) {
        mPropositionList = new ArrayList<>();
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.proposition_row, parent, false);
                viewHolder = new PropositionViewHolder(itemView);
                break;
            case TYPE_LOADING:
                View loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_row, parent, false);
                viewHolder = new LoadingViewHolder(loadingView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                Proposition proposition = mPropositionList.get(position);

                ((PropositionViewHolder) holder).title.setText(proposition.gettitle());
                break;
            case TYPE_LOADING:
                // Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mPropositionList == null ? 0 : mPropositionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mPropositionList.size() - 1 && mIsLoadingAdded) ? TYPE_LOADING : TYPE_ITEM;
    }

    /**
     * ----------------------------------   Helper --------------------------------------------
     */

    public void add(Proposition proposition) {
        mPropositionList.add(proposition);
        notifyItemInserted(mPropositionList.size() - 1);
    }

    public void addAll(List<Proposition> propositionsList) {
        if(propositionsList != null && !propositionsList.isEmpty()) {
            for (Proposition proposition : propositionsList) {
                add(proposition);
            }
        }
    }

    /**
     * -----------------------------------  ViewHolder ------------------------------------------
     */

    public class PropositionViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public PropositionViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }

    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }


}

