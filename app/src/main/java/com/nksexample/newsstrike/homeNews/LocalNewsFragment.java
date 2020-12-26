package com.nksexample.newsstrike.homeNews;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nksexample.newsstrike.model.NewsModel;
import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.RViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalNewsFragment extends Fragment {

    RecyclerView rvLocalNews;
    RViewAdapter rViewAdapter;
    private ProgressDialog progressDialog;

    TextView test;

    List<NewsModel> newsModelList;

    private static final String url="http://newsapi.org/v2/top-headlines?country=my&apiKey=b24fd2dbf4fa4d1c9364b00fe1cfeb82";


    public LocalNewsFragment() {}

    public static LocalNewsFragment newInstance(String param1, String param2) {
        LocalNewsFragment fragment = new LocalNewsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initialize view
        View viewLocal = inflater.inflate(R.layout.fragment_local_news, container, false);

        //Setup Recycle View
        rvLocalNews = viewLocal.findViewById(R.id.rvLocalNews);
        newsModelList = new ArrayList<>();
        rvLocalNews.setHasFixedSize(true);
        rvLocalNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        progressDialog = new ProgressDialog(getContext(), R.style.Widget_AppCompat_ProgressBar);
        progressDialog.setMessage("Fetching. . .");
        loadAllNews();

        return viewLocal;
    }


    //Whenever is visible to user, refresh to get the latest news
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        //If visible, load the news
        if (!menuVisible)
            return;


        loadAllNews();




    }


    private void loadAllNews() {

    }

}