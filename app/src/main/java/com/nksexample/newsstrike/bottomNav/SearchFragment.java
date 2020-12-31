package com.nksexample.newsstrike.bottomNav;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nksexample.newsstrike.FeedbackActivity;
import com.nksexample.newsstrike.MainActivity;
import com.nksexample.newsstrike.NewsDetailActivity;
import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.SettingsActivity;
import com.nksexample.newsstrike.Utils;
import com.nksexample.newsstrike.adapters.RVSearchAdapter;
import com.nksexample.newsstrike.SearchQueryActivity;
import com.nksexample.newsstrike.adapters.RViewAdapter;
import com.nksexample.newsstrike.api.APIClient;
import com.nksexample.newsstrike.api.APIInterface;
import com.nksexample.newsstrike.model.ArticleModel;
import com.nksexample.newsstrike.model.FavModel;
import com.nksexample.newsstrike.model.NewsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nksexample.newsstrike.MainActivity.API_KEY;

public class SearchFragment extends Fragment {

    private RecyclerView rvSuggestions;
    private RViewAdapter rViewAdapter;

    ArrayList<SearchItem> searchItemArrayList;

    private List<ArticleModel> articles = new ArrayList<>();
    private ArrayList<FavModel> favs = new ArrayList<>();


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
        // Load from DB
        favs = MainActivity.databaseHelper.listALLFavItems();

        // Initialize the layout for this fragment
        View viewSearch = inflater.inflate(R.layout.fragment_search, container, false);

        // Create a list of topics
        buildTopicList();
        buildRecyclerView(viewSearch);

        rvSuggestions = viewSearch.findViewById(R.id.rvSuggestions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSuggestions.setLayoutManager(layoutManager);
        rvSuggestions.setItemAnimator(new DefaultItemAnimator());
        rvSuggestions.setNestedScrollingEnabled(false);

        //Load Suggestion
        loadJSON();

        return viewSearch;
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

        RecyclerView rvTopics = searchView.findViewById(R.id.rvTopics);
        rvTopics.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RVSearchAdapter rvAdapter = new RVSearchAdapter(searchItemArrayList);

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
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setQueryHint("Search News...");

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){

                    Intent intent = new Intent(getActivity(), SearchQueryActivity.class); //Showhow show the back buttom
                    intent.putExtra("query", query.toLowerCase());
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        String username = "johndoe";

        switch(id){
//            case R.id.menuFeedback:
//                intent = new Intent(getActivity(), FeedbackActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.menuLogout:
//                intent = new Intent(getActivity(), LoginActivity.class);
//                intent.putExtra("username", username);
//                startActivity(intent);
//                break;
//            case R.id.menuLogin:
//                intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//                break;
            case R.id.menuSettings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }

    public void loadJSON(){

        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);

        String language = SettingsActivity.language;
        if (language.isEmpty())
            language = "en";

        Call<NewsModel> call;

        //For now use Random to decide the Suggestion
        final String[] keywords = {"trend", "covid", "malaysia", "bitcoin", "health"};
        Random rand = new Random();
        String keyword = keywords[rand.nextInt(keywords.length)];

        call = apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    rViewAdapter = new RViewAdapter(articles, getActivity());
                    rvSuggestions.setAdapter(rViewAdapter);
                    rViewAdapter.notifyDataSetChanged();

                    initRVAdapterListener();

                }
                else {

                    String errorCode;
                    switch (response.code()){
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "Check network connectivity";
                            break;

                    }

                    // Possible make a proper one
                    Toast.makeText(getActivity(), errorCode, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

                Toast.makeText(getActivity(), "Nework Error!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initRVAdapterListener(){

        rViewAdapter.setOnItemClickListener(new RViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ImageView imageView = view.findViewById(R.id.ivNewsImage);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);

                ArticleModel articleModel = articles.get(position);
                intent.putExtra("authorname",  articleModel.getAuthor());
                intent.putExtra("source",  articleModel.getSource().getName());
                intent.putExtra("title",  articleModel.getTitle());
                intent.putExtra("date",  articleModel.getPublishedAt());
                intent.putExtra("img",  articleModel.getUrlToImage());
                intent.putExtra("url",  articleModel.getUrl());

                //Wanted to do Fragment to Activity with transition but not yet find how
//                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);

                startActivity(intent);

            }
        });

    }

}