package com.nksexample.newsstrike.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class VPagerAdapter extends FragmentPagerAdapter {

    //Initailize array list
    ArrayList<String> arrayListTitle = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();

    public VPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //return fragment position
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        //Return size of fragment
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayListTitle.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        arrayListTitle.add(title);
        fragmentList.add(fragment);
    }


}


