package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab_Frag5 extends Fragment {

    private View view;

    //프래그먼트 상태 저장
    public static Tab_Frag5 newInstance() {

        Tab_Frag5 tab_frag5=new Tab_Frag5();
        return tab_frag5;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_frag5,container,false);
        return view;
    }
}
