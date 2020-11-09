package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import com.example.yeeybook.whattoon.naver.Tab_Frag1;
import com.example.yeeybook.whattoon.naver.Tab_Frag2;
import com.example.yeeybook.whattoon.naver.Tab_Frag3;
import com.example.yeeybook.whattoon.naver.Tab_Frag4;
import com.example.yeeybook.whattoon.naver.Tab_Frag5;
import com.example.yeeybook.whattoon.naver.Tab_Frag6;
import com.example.yeeybook.whattoon.naver.Tab_Frag7;
import com.example.yeeybook.whattoon.naver.Tab_Frag8;
import com.example.yeeybook.whattoon.naver.Tab_Frag9;
import com.example.yeeybook.whattoon.naver.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Frag_Home extends Fragment {


    private View view;

    private Spinner spinner;

    Spinner_Frag1 spinner1;
    Spinner_Frag2 spinner2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag_home,container,false);
        spinner = (Spinner)view.findViewById(R.id.spinner);

        spinner1 = new Spinner_Frag1();
        spinner2 = new Spinner_Frag2();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.test));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0:
                        setFragment(spinner1);

                        break;

                    case 1:
                        setFragment(spinner2);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }


    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.sub_frame,fragment);
        fragmentTransaction.commit();
    }


}
