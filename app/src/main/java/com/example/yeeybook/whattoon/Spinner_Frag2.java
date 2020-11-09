package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.yeeybook.whattoon.daum.ViewPagerAdapter2;
import com.example.yeeybook.whattoon.naver.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Spinner_Frag2 extends Fragment {

    private View view;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.spinner_frag2,container,false);

        ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewPager1);
        fragmentPagerAdapter=new ViewPagerAdapter2(getChildFragmentManager());
        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tab_layout1);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(0);

        return view;
    }
}
