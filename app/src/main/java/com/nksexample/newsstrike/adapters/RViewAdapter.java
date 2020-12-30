package com.nksexample.newsstrike.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.nksexample.newsstrike.MainActivity;
import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.Utils;
import com.nksexample.newsstrike.model.ArticleModel;
import com.nksexample.newsstrike.model.FavModel;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.MyViewHolder> {

    private List<ArticleModel> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private MyViewHolder newholder;

    public RViewAdapter(List<ArticleModel> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        newholder = holders;
        ArticleModel model = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        newholder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        newholder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(newholder.imageView);

        Log.d("Title", model.getTitle());

        newholder.title.setText(model.getTitle());
        newholder.desc.setText(model.getDescription());
        newholder.source.setText(model.getSource().getName());
        newholder.time.setText(" \u2022 " + Utils.DateToTimeFormat(model.getPublishedAt()));
        newholder.published_ad.setText(Utils.DateFormat(model.getPublishedAt()));
        newholder.author.setText(model.getAuthor());

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = (OnItemClickListener) onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        TextView title, desc, author, published_ad, source, time;
        ImageView imageView;
        private FrameLayout newsItem;
        private CardView cardViewNews;

        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            title = itemView.findViewById(R.id.tvNewsTitle);
            desc = itemView.findViewById(R.id.tvNewsDesc);
            author = itemView.findViewById(R.id.tvAuthorName);
            published_ad = itemView.findViewById(R.id.tvNewsDate);
            source = itemView.findViewById(R.id.tvSourceName);
            time = itemView.findViewById(R.id.tvNewsTime);
            imageView = itemView.findViewById(R.id.ivNewsImage);
            newsItem = itemView.findViewById(R.id.newsItem);

            this.onItemClickListener = onItemClickListener;


        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            MenuItem m = contextMenu.add(this.getAdapterPosition(), 101,0,"Favourite");

            // Get the position and save that selected item : https://stackoverflow.com/a/33179957
            m.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    ArticleModel artModel = articles.get(getAdapterPosition());

                    int favCount =  MainActivity.databaseHelper.listALLFavItems().size();

                    // TODO Possible add a check where no duplication occur

                    int lastID = MainActivity.databaseHelper.listALLFavItems().get(favCount-1).getFavID();
                    lastID++; // +1 of the last item ID, to avoid duplication for now
                    FavModel favModel = new FavModel(lastID, artModel.getSource().getName(), artModel.getTitle(),artModel.getUrl());

                    boolean check = MainActivity.databaseHelper.insertONEFavItem(favModel);
                    if (check)
                        Snackbar.make(view, "Added to Favourite!", Snackbar.LENGTH_SHORT).show();

                    return false;
                }
            });

        }
    }
}





