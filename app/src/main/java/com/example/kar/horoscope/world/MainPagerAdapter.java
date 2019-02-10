package com.example.kar.horoscope.world;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class MainPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String[] dates;


    MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        dates = context.getResources().getStringArray(R.array.date);
    }


    @Override
    public Fragment getItem(int position) {
        return Forecast.PostsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dates[position];
    }
}