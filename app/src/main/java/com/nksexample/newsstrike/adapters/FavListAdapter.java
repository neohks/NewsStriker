package com.nksexample.newsstrike.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.model.FavModel;

import java.util.ArrayList;

public class FavListAdapter extends ArrayAdapter<FavModel> {

    private Context context;
    int resource;

    public FavListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FavModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Get fav data
        int favID = getItem(position).getFavID();
        String publisherName = getItem(position).getPublisherName();
        String url = getItem(position).getUrl();

        // Create favmodel
        FavModel favModel = new FavModel(favID, publisherName, url);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvPublisherName = convertView.findViewById(R.id.tvPublisherName);
        TextView tvURL = convertView.findViewById(R.id.tvURL);

        tvPublisherName.setText(publisherName);
        tvURL.setText(url);

        return convertView;

    }
}
