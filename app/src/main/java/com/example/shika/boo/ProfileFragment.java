package com.example.shika.boo;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FragmentManager fragmentManager = getFragmentManager();
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vp= inflater.inflate(R.layout.fragment_profile, container, false);


        tabLayout = (TabLayout) vp.findViewById(R.id.tablayout_id);
        //  appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) vp.findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);

        adapter.AddFragment(new FragmentC(),"About");
        adapter.AddFragment(new FragmentD(),"Rewards");
        adapter.AddFragment(new FragmentE(),"Favorites");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        return vp;
    }

}
