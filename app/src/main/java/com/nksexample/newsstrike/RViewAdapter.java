package com.nksexample.newsstrike;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nksexample.newsstrike.model.NewsModel;

import java.util.List;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.NewsViewHolder> {

    private Context context;
    private List<NewsModel> headlineModelList;

    public RViewAdapter(Context context, List<NewsModel> headlineModelList) {
        this.context = context;
        this.headlineModelList = headlineModelList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int position) {
        final NewsModel headlineModel = headlineModelList.get(position);
        holder.newTitle.setText(headlineModel.getTitle());
        holder.newsDescription.setText(headlineModel.getDescription());
        holder.newsDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(headlineModel.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        holder.newsName.setText(headlineModel.getArticleName());
        holder.newsTime.setText(headlineModel.getPublishedAt());
        Glide.with(context).load(headlineModel.getUrlToImage())
                .thumbnail(0.5f)
                .into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return headlineModelList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newTitle;
        TextView newsDescription;
        TextView newsName;
        TextView newsTime;
        ImageView newsImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newTitle = itemView.findViewById(R.id.tvNewsTitle);
            newsDescription = itemView.findViewById(R.id.tvNewsDesc);
            newsName = itemView.findViewById(R.id.tvArticleName);
            newsTime = itemView.findViewById(R.id.tvNewsDate);
            newsImage = itemView.findViewById(R.id.ivNewsImage);
        }
    }


}
