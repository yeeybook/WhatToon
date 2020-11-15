package com.example.yeeybook.whattoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.yeeybook.whattoon.naver.Tab_Frag2;

public class WebtoonProfileActivity extends AppCompatActivity {
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_profile);

        test = findViewById(R.id.test);
        Intent a = getIntent();
        test.setText("웹툰 아이디 : " +String.valueOf(a.getIntExtra("id", 100)));

    }

}