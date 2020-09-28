package com.example.yeeybook.whattoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch (v.getId())
                {

                    case R.id.button2:
                        Intent intent = new Intent(activity_login.this, activity_join.class);
                        startActivity(intent);
                        break;

                    case R.id.button3:
                        break;

                    case R.id.temporary:
                        Intent intent2=new Intent(activity_login.this,activity_bottom.class);
                        startActivity(intent2);
                        break;

                }
            }
        };

        //회원가입 버튼
        Button join_btn = (Button)findViewById(R.id.button2);
        join_btn.setOnClickListener(listener);
        //ID/PW찾기 버튼
        Button find_btn = (Button)findViewById(R.id.button3);
        find_btn.setOnClickListener(listener);
        //임시버튼
        Button temp_btn = (Button)findViewById(R.id.temporary);
        temp_btn.setOnClickListener(listener);



    }

}