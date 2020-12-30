package com.nksexample.newsstrike;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

    ImageView ivUserAvatar;
    TextView tvUserName;
    Button btnLogout, btnFavList;

    public ProfileFragment() {   }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileView =  inflater.inflate(R.layout.fragment_profile, container, false);

        //Association
        ivUserAvatar = profileView.findViewById(R.id.ivUserAvatar);
        tvUserName = profileView.findViewById(R.id.tvUserName);
        btnLogout = profileView.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logout all status : https://stackoverflow.com/questions/3007998/on-logout-clear-activity-history-stack-preventing-back-button-from-opening-l
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        btnFavList = profileView.findViewById(R.id.btnFavList);
        btnFavList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), FavListActivity.class);
                startActivity(intent);

            }
        });

        return profileView;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menudots, menu);
        MenuItem menuSearch = menu.findItem(R.id.action_search);
        menuSearch.setVisible(false);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuSearch = menu.findItem(R.id.action_search);
        menuSearch.setVisible(false);

    }
}