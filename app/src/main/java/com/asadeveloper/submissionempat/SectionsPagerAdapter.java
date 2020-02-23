package com.asadeveloper.submissionempat;

import android.content.Context;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.asadeveloper.submissionempat.fragment.HomeFragment;
import com.asadeveloper.submissionempat.fragment.TvShowFragment;

public class SectionsPagerAdapter extends  FragmentPagerAdapter{
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {

        super(fm);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new TvShowFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
