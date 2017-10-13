package com.giuseppeliguori.today;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by giuseppeliguori on 20/07/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private RecycleViewContract.Presenter presenter;

    public RecycleViewAdapter(RecycleViewContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        presenter.onBindRepositoryRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getSize();
    }
}