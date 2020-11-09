package com.example.yeeybook.whattoon.daum;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.yeeybook.whattoon.naver.Tab_Frag1;
import com.example.yeeybook.whattoon.naver.Tab_Frag2;
import com.example.yeeybook.whattoon.naver.Tab_Frag3;
import com.example.yeeybook.whattoon.naver.Tab_Frag4;
import com.example.yeeybook.whattoon.naver.Tab_Frag5;
import com.example.yeeybook.whattoon.naver.Tab_Frag6;
import com.example.yeeybook.whattoon.naver.Tab_Frag7;
import com.example.yeeybook.whattoon.naver.Tab_Frag8;
import com.example.yeeybook.whattoon.naver.Tab_Frag9;

public class ViewPagerAdapter2 extends FragmentPagerAdapter {


    public ViewPagerAdapter2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Tab_Frag11.newInstance();

            case 1:
                return Tab_Frag22.newInstance();

            case 2:
                return Tab_Frag33.newInstance();

            case 3:
                return Tab_Frag44.newInstance();

            case 4:
                return Tab_Frag55.newInstance();

            case 5:
                return Tab_Frag66.newInstance();

            case 6:
                return Tab_Frag77.newInstance();

            case 7:
                return Tab_Frag88.newInstance();

            case 8:
                return Tab_Frag99.newInstance();

            default:
                return null;
        }

    }


    @Override
    public int getCount() {
        return 9;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "인기";
            case 1:
                return "월";
            case 2:
                return "화";
            case 3:
                return "수";
            case 4:
                return "목";
            case 5:
                return "금";
            case 6:
                return "토";
            case 7:
                return "일";
            case 8:
                return "완결";
            default:
                return null;
        }
    }
}

