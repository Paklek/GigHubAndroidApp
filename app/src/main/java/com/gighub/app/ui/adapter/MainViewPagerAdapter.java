package com.gighub.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paklek on 6/10/2016.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {



    private final List<Fragment> mListFragment = new ArrayList<>();
    private final List<String> mListFragmentTitle = new ArrayList<>();
    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mListFragment.add(fragment);
        mListFragmentTitle.add(title);
    }

    public void removeFragment(Fragment fragment){
        mListFragment.remove(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListFragmentTitle.get(position);
    }

}
