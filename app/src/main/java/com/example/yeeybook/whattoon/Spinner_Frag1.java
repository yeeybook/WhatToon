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

import com.example.yeeybook.whattoon.naver.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class Spinner_Frag1 extends Fragment {

    private View view;
    private FragmentPagerAdapter fragmentPagerAdapter;
    int sWeek;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.spinner_frag1,container,false);

        ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewPager1);
        fragmentPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tab_layout1);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentPagerAdapter);

        viewPager.setCurrentItem(doDayOfWeek());

        return view;
    }

    public int doDayOfWeek(){
        Calendar cal = Calendar.getInstance();


        int nWeek = cal.get(Calendar.DAY_OF_WEEK);

        if(nWeek ==1){//일
            sWeek=7;
        }
        else if(nWeek ==2){//월
            sWeek=1;
        }
        else if(nWeek ==3){//화
            sWeek=2;
        }
        else if(nWeek ==4){//수
            sWeek=3;
        }
        else if(nWeek ==5){//목
            sWeek=4;
        }
        else if(nWeek ==6){//금
            sWeek=5;
        }
        else if(nWeek ==7){//토
            sWeek=6;
        }

        return sWeek;
    }
}
