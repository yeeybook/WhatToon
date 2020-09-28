package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab_Frag8 extends Fragment {

    private View view;

    //프래그먼트 상태 저장
    public static Tab_Frag8 newInstance() {

        Tab_Frag8 tab_frag8=new Tab_Frag8();
        return tab_frag8;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_frag8,container,false);
        return view;
    }
}
