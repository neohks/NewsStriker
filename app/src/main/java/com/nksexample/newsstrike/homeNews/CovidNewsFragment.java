package com.nksexample.newsstrike.homeNews;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nksexample.newsstrike.NewsDetailActivity;
import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.RViewAdapter;
import com.nksexample.newsstrike.Utils;
import com.nksexample.newsstrike.api.APIClient;
import com.nksexample.newsstrike.api.APIInterface;
import com.nksexample.newsstrike.model.ArticleModel;
import com.nksexample.newsstrike.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CovidNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String API_KEY = "b24fd2dbf4fa4d1c9364b00fe1cfeb82";
    private RecyclerView rvLocalNews;
    private RViewAdapter rViewAdapter;
    private List<ArticleModel> articles = new ArrayList<>();

    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    public CovidNewsFragment() {   }

    public static CovidNewsFragment newInstance(String param1, String param2) {
        CovidNewsFragment fragment = new CovidNewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View covidView = inflater.inflate(R.layout.fragment_covid_news, container, false);

        swipeRefreshLayout = covidView.findViewById(R.id.srLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        rvLocalNews = covidView.findViewById(R.id.rvLocalNews);
        layoutManager = new LinearLayoutManager(getContext());
        rvLocalNews.setLayoutManager(layoutManager);
        rvLocalNews.setItemAnimator(new DefaultItemAnimator());
        rvLocalNews.setNestedScrollingEnabled(false);

        loadAllNews();

        return covidView;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        //If visible, load the news
        if (!menuVisible)
            return;
        if (swipeRefreshLayout != null)
            loadAllNews();

    }


    @Override
    public void onRefresh() {

        loadJSON();

    }

    private void loadAllNews() {

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadJSON();
            }
        });

    }

    public void loadJSON(){

        swipeRefreshLayout.setRefreshing(true);

        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);

        String language = Utils.getLanguage();

        Call<NewsModel> call;

        call = apiInterface.getNews("covid", language, API_KEY);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    rViewAdapter = new RViewAdapter(articles, getActivity());
                    rvLocalNews.setAdapter(rViewAdapter);
                    rViewAdapter.notifyDataSetChanged();

                    initRVAdapterListener();

                    swipeRefreshLayout.setRefreshing(false);

                }
                else {

                    swipeRefreshLayout.setRefreshing(false);

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

                swipeRefreshLayout.setRefreshing(false);
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