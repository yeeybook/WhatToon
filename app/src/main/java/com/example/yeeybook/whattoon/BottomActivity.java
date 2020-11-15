package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;//하단바

    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag_Home frag_home;
    private Frag_Favorite frag_favorite;
    private Frag_Search frag_search;
    private Frag_My frag_my;
    private Frag_Rated frag_rated; // 숨겨놓은 5번째 프래그먼트

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        bottomNavigationView=findViewById(R.id.bottomNavi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        setFrag(0);
                        break;

                    case R.id.action_favorite:
                        setFrag(1);
                        break;

                    case R.id.action_search:
                        setFrag(2);
                        break;

                    case R.id.action_my:
                        setFrag(3);
                        break;

                    case R.id.action_rated:
                        setFrag(4);
                }
                return true;
            }
        });

        frag_home = new Frag_Home();
        frag_favorite = new Frag_Favorite();
        frag_search = new Frag_Search();
        frag_my = new Frag_My();
        frag_rated = new Frag_Rated();
        setFrag(0);//첫 화면 설정

    }

    //프래그먼트 교체 일어나는 실행문

    public void setFrag(int n){

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n){
            case 0:
                ft.replace(R.id.main_frame,frag_home);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.main_frame,frag_favorite);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.main_frame,frag_search);
                ft.commit();
                break;

            case 3:
                ft.replace(R.id.main_frame,frag_my);
                ft.commit();
                break;

            case 4:
                ft.replace(R.id.main_frame, frag_rated);
                ft.commit();
                break;
        }
    }


}
