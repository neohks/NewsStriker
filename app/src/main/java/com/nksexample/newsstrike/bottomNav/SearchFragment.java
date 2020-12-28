package com.nksexample.newsstrike.bottomNav;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.SearchView;
import android.widget.Toast;

import com.nksexample.newsstrike.MainActivity;
import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.RVSearchAdapter;
import com.nksexample.newsstrike.SearchQueryActivity;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView rvTopics;
    private RVSearchAdapter rvAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<SearchItem> searchItemArrayList;

    public SearchFragment() {    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        // Initialize the layout for this fragment
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);

        //Create a list
        buildTopicList();
        buildRecyclerView(searchView);

        return searchView;
    }


    void buildTopicList(){
        searchItemArrayList = new  ArrayList<>();
        searchItemArrayList.add(new SearchItem("Business", R.drawable.ic_business));
        searchItemArrayList.add(new SearchItem("Entertainment", R.drawable.ic_entertainment));
        searchItemArrayList.add(new SearchItem("Health", R.drawable.ic_health));
        searchItemArrayList.add(new SearchItem("Science", R.drawable.ic_science));
        searchItemArrayList.add(new SearchItem("Sport", R.drawable.ic_sport));
        searchItemArrayList.add(new SearchItem("Technology", R.drawable.ic_technology));
    }

    void buildRecyclerView(View searchView) {

        rvTopics = searchView.findViewById(R.id.rvTopics);
        rvTopics.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvAdapter = new RVSearchAdapter(searchItemArrayList);

        rvTopics.setLayoutManager(layoutManager);
        rvTopics.setAdapter(rvAdapter);

        rvAdapter.setOnItemClickListener(new RVSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String selectedTopic = searchItemArrayList.get(position).getTopicName().toLowerCase();

                Intent intent = new Intent(getActivity(), SearchQueryActivity.class); //somehow show the back buttom
                intent.putExtra("query", selectedTopic);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menudots, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){

                    Intent intent = new Intent(getActivity(), SearchQueryActivity.class); //Showhow show the back buttom
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "Type more than two letters!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


    }

}