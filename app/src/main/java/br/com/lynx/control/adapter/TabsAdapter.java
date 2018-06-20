package br.com.lynx.control.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rogerio on 24/10/2016.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] titles = {"Coca-Cola", "Mondelez", "Alimentar", "RM"};

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.context = c;
    }

    @Override
    public Fragment getItem(int position) {


        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return (titles[position]);
    }
}
