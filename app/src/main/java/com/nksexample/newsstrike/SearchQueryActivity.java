package com.nksexample.newsstrike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nksexample.newsstrike.adapters.RViewAdapter;
import com.nksexample.newsstrike.api.APIClient;
import com.nksexample.newsstrike.api.APIInterface;
import com.nksexample.newsstrike.model.ArticleModel;
import com.nksexample.newsstrike.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nksexample.newsstrike.MainActivity.API_KEY;

public class SearchQueryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvLocalNews;
    private RViewAdapter rViewAdapter;
    private List<ArticleModel> articles = new ArrayList<>();

    private String keyword = "";

    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_query);

        keyword = getIntent().getStringExtra("query");

        setTitle(keyword.toUpperCase());

        swipeRefreshLayout = findViewById(R.id.srLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        rvLocalNews = findViewById(R.id.rvLocalNews);
        layoutManager = new LinearLayoutManager(SearchQueryActivity.this);
        rvLocalNews.setLayoutManager(layoutManager);
        rvLocalNews.setItemAnimator(new DefaultItemAnimator());
        rvLocalNews.setNestedScrollingEnabled(false);

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

        String language = Utils.getLanguage(); //If only when search then need

        Call<NewsModel> call;

        call = apiInterface.getNewsSearch(keyword, language,"relevancy", API_KEY);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    if (articles.size() == 0) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(SearchQueryActivity.this);
                        materialAlertDialogBuilder.setTitle("No Result");
                        materialAlertDialogBuilder.setMessage("There is nothing to show for '" + keyword + "'.\n" + "Please try other keyword. Thank you!");
                        materialAlertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        materialAlertDialogBuilder.show();


                    }
                    rViewAdapter = new RViewAdapter(articles, SearchQueryActivity.this);
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
                    Toast.makeText(SearchQueryActivity.this, errorCode, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(SearchQueryActivity.this, "Nework Error!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initRVAdapterListener(){

        rViewAdapter.setOnItemClickListener(new RViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ImageView imageView = view.findViewById(R.id.ivNewsImage);
                Intent intent = new Intent(SearchQueryActivity.this, NewsDetailActivity.class);

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


    @Override
    public void onBackPressed() {
        finish();
    }
}