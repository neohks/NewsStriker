package com.nksexample.newsstrike.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.nksexample.newsstrike.FavListActivity;
import com.nksexample.newsstrike.MainActivity;
import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.WebViewActivity;
import com.nksexample.newsstrike.model.FavModel;

import java.util.ArrayList;

import static com.nksexample.newsstrike.FavListActivity.favListAdapter;

public class FavListAdapter extends ArrayAdapter<FavModel>  {

    ArrayList<FavModel> favModels;
    private Context context;
    int resource;

    public FavListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FavModel> favModels) {
        super(context, resource, favModels);
        this.context = context;
        this.resource = resource;
        this.favModels = favModels;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Get fav data
        int favID = getItem(position).getFavID();
        String publisherName = getItem(position).getPublisherName();
        String articleName = getItem(position).getArticleName();
        String url = getItem(position).getUrl();

        // Create favmodel
        FavModel favModel = new FavModel(favID, publisherName, articleName, url);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvPublisherName = convertView.findViewById(R.id.tvPublisherName);
        TextView tvArticleName = convertView.findViewById(R.id.tvArticleName);
        TextView tvURL = convertView.findViewById(R.id.tvURL);
        ConstraintLayout constraintLFavItem = convertView.findViewById(R.id.constraintLFavItem);

        constraintLFavItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavModel fav = favModels.get(position);

                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("title", fav.getPublisherName());
                intent.putExtra("url", fav.getUrl());

                context.startActivity(intent);

            }
        });

        constraintLFavItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Snackbar.make(view, "Item selected removed!", Snackbar.LENGTH_SHORT).show();

                                MainActivity.databaseHelper.deleteONEFavItem(favModel);
                                favModels.remove(position);
                                favListAdapter.notifyDataSetChanged();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // If no do nothing

                            }
                        }).show();


                return false;
            }
        });

        tvPublisherName.setText(publisherName);
        tvArticleName.setText(articleName);
        tvURL.setText(url);

        return convertView;

    }
}
