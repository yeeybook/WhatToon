package com.example.yeeybook.whattoon.naver;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Calendar;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int sWeek;


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Tab_Frag1.newInstance();

            case 1:
                return Tab_Frag2.newInstance();

            case 2:
                return Tab_Frag3.newInstance();

            case 3:
                return Tab_Frag4.newInstance();

            case 4:
                return Tab_Frag5.newInstance();

            case 5:
                return Tab_Frag6.newInstance();

            case 6:
                return Tab_Frag7.newInstance();

            case 7:
                return Tab_Frag8.newInstance();

            case 8:
                return Tab_Frag9.newInstance();

            default:

                switch (doDayOfWeek()){
                    case 1:
                        return Tab_Frag2.newInstance();

                    case 2:
                        return Tab_Frag3.newInstance();

                    case 3:
                        return Tab_Frag4.newInstance();

                    case 4:
                        return Tab_Frag5.newInstance();

                    case 5:
                        return Tab_Frag6.newInstance();

                    case 6:
                        return Tab_Frag7.newInstance();

                    case 7:
                        return Tab_Frag8.newInstance();

                    default:
                        return null;
                }

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

