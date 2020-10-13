package com.example.yeeybook.whattoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //버튼 분기
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch (v.getId())
                {

                    //로그인(임시버튼). 홈으로 이동
                    case R.id.button:
                        Intent intent=new Intent(LoginActivity.this, BottomActivity.class);
                        startActivity(intent);
                        break;

                    // 회원가입 페이지 이동
                    case R.id.button2:
                        Intent intent2 = new Intent(LoginActivity.this, JoinActivity.class);
                        startActivity(intent2);
                        break;

                    // ID/PW 찾기 페이지 이동
                    //case R.id.button2:
                    //    break;

                }
            }
        };


        //로그인(임시버튼)
        Button temp_btn = (Button)findViewById(R.id.button);
        temp_btn.setOnClickListener(listener);
        //회원가입 버튼
        Button join_btn = (Button)findViewById(R.id.button2);
        join_btn.setOnClickListener(listener);
        //ID/PW찾기 버튼
        /*
        Button find_btn = (Button)findViewById(R.id.button2);
        find_btn.setOnClickListener(listener);
         */




    }

}