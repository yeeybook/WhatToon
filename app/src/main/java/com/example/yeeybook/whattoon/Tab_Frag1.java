package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Tab_Frag1 extends Fragment {

    private View view;

    //프래그먼트 상태 저장
    public static Tab_Frag1 newInstance() {

        Tab_Frag1 tab_frag1=new Tab_Frag1();
        return tab_frag1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_frag1,container,false);
        return view;
    }
}
