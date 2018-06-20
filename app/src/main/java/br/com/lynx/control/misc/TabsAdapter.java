package br.com.lynx.control.misc;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by viniciusthiengo on 5/18/15.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles = {"FINANCEIRO", "ARRASTÃO", "EQUIPAMENTOS"};

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);

        mContext = c;
        double scale = c.getResources().getDisplayMetrics().density;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        if(position == 0) // Financeiro
            frag = new TitulosCliente_Fragment();
        else if(position == 1) // Arrastão
            frag = new ArrastaoFragment();
        else if(position == 2) // Equipamentos
            frag = new TitulosCliente_Fragment();

        Bundle b = new Bundle();
        b.putInt("position", position);

        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ( titles[position] );
    }
}
