package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;

public class Frag_Home extends Fragment {

    private View view;

    private FragmentPagerAdapter fragmentPagerAdapter;
    private Spinner spinner;
    int spinner_value=0;

//    Bundle bundle = new Bundle(1);////
//    Tab_Frag2 tab_frag2 = new Tab_Frag2();////


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_home,container,false);

        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //스피너 선택 시 값 다르게 넘어감
//                spinner_value=i;
//                String platform;////
//                if(i==0) platform = "naver";////
//                else platform = "daum";////
//
//                bundle.putString("platform", platform);////
//                tab_frag2.setArguments(bundle);////
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.constraintLayout, tab_frag2);
//                ft.show(getFragmentManager().findFragmentById(R.id.tab_layout));
//                ft.addToBackStack(null);
//                ft.commit();
////                FragmentManager fm = getFragmentManager();
////                FragmentTransaction fmt = fm.beginTransaction();
////
////                fmt.replace(R.id.tab_layout, tab_frag2).addToBackStack(null).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        fragmentPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentPagerAdapter);
        return view;

    }
}
