package com.nksexample.newsstrike.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.bottomNav.SearchItem;

import java.util.ArrayList;

public class RVSearchAdapter extends RecyclerView.Adapter<RVSearchAdapter.SearchViewHolder> {

    private ArrayList<SearchItem> searchItemArrayList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public static class SearchViewHolder extends  RecyclerView.ViewHolder {

        public ImageView ivTopicLogo;
        public TextView tvTopicName;

        public SearchViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            ivTopicLogo = itemView.findViewById(R.id.ivTopicLogo);
            tvTopicName = itemView.findViewById(R.id.tvTopicName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onItemClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }

                }
            });

        }

    }

    public RVSearchAdapter(ArrayList<SearchItem> searchItemArrayList){

        this.searchItemArrayList = searchItemArrayList;

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item_layout, parent, false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(v, onItemClickListener);

        return searchViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        SearchItem currentItem = searchItemArrayList.get(position);

        holder.ivTopicLogo.setImageResource(currentItem.getImageID());
        holder.tvTopicName.setText(currentItem.getTopicName());

    }

    @Override
    public int getItemCount() {
        return searchItemArrayList.size();
    }
}
