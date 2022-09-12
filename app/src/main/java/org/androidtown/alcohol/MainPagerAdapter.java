package org.androidtown.alcohol;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.androidtown.alcohol.Community.CommunityFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static int PAGE_NUM =4;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:return SearchFragment.newInstance();
            case 1: return  CommunityFragment.newInstance();
            case 2: return FavoritesFragment.newInstance();
            case 3: return  MyInfoFragment.newInstance();
            default: return  null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }


    @Override
    public CharSequence getPageTitle(int position){
        return null;
    }
}



