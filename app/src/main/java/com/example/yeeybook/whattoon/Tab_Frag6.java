package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab_Frag6 extends Fragment {

    private View view;

    //프래그먼트 상태 저장
    public static Tab_Frag6 newInstance() {

        Tab_Frag6 tab_frag6=new Tab_Frag6();
        return tab_frag6;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_frag6,container,false);
        return view;
    }
}
