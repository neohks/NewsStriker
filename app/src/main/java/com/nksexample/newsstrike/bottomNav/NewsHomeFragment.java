package com.nksexample.newsstrike.bottomNav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.nksexample.newsstrike.homeNews.LocalNewsFragment;
import com.nksexample.newsstrike.R;
import com.nksexample.newsstrike.homeNews.TrendNewsFragment;
import com.nksexample.newsstrike.VPagerAdapter;
import com.nksexample.newsstrike.homeNews.WorldNewsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsHomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;


    public NewsHomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewsHomeFragment newInstance(String param1, String param2) {
        NewsHomeFragment fragment = new NewsHomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_home, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        //Setup Tablayout with ViewPager
        prepareViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        // How to recycleview : https://stackoverflow.com/a/38037171

        return view;
    }


    //Pager
    private void prepareViewPager(ViewPager vp){
        //Initialize VPager
        VPagerAdapter adapter = new VPagerAdapter(getChildFragmentManager()); // https://stackoverflow.com/a/45006219

        //Initialize all fragments
        LocalNewsFragment localNewsFragment = new LocalNewsFragment();
        WorldNewsFragment worldNewsFragment = new WorldNewsFragment();
        TrendNewsFragment trendNewsFragment = new TrendNewsFragment();

        adapter.addFragment(localNewsFragment, "Local");
        adapter.addFragment(worldNewsFragment, "World");
        adapter.addFragment(trendNewsFragment, "Trend");

        viewPager.setAdapter(adapter);

    }


}