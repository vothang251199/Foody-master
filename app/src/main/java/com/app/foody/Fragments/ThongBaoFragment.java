package com.app.foody.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.foody.Adapters.ViewPageAdapterFragmentThongBao;
import com.app.foody.R;
import com.google.android.material.tabs.TabLayout;


public class ThongBaoFragment extends Fragment {

    View myFragment;
    private TabLayout mTabLayout;
    private ViewPager mviewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_thongbao, container, false);
        mviewPager = myFragment.findViewById(R.id.view_pager_thongabo);
        mTabLayout = myFragment.findViewById(R.id.tab_layout_thongbao);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(mviewPager);
        mTabLayout.setupWithViewPager(mviewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager mviewPager) {
        ViewPageAdapterFragmentThongBao viewPageAdapterFragmentThongBao = new ViewPageAdapterFragmentThongBao(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mviewPager.setAdapter(viewPageAdapterFragmentThongBao);
        mTabLayout.setupWithViewPager(mviewPager);
    }
}