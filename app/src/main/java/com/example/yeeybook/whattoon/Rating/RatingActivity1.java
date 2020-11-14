package com.example.yeeybook.whattoon.Rating;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.example.yeeybook.whattoon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RatingActivity1 extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    RatingBar star1, star2, star3, star4, star5, star6;
    Button nextBtn, RatingBtn;
    LinearLayout RatingLayout;
    int [] webtoonList = {370, 27, 361, 366, 161, 166};
    String [] titleList = {"대학일기", "신의 탑", "신과 함께", "하이브", "나노마신", "가담항설"};
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public static AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        activity = RatingActivity1.this;

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        star6 = findViewById(R.id.star6);
        RatingLayout = findViewById(R.id.RatingLayout);
        nextBtn = findViewById(R.id.nextBtn);
        RatingBtn = findViewById(R.id.RatingBtn);

        RatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingLayout.setVisibility(View.GONE);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RatingActivity2.class);
                startActivity(intent);
            }
        });

        star1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[0]);
                hashMap.put("title", titleList[0]);
                hashMap.put("rate", v); //별점 값 받아옴
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(String.valueOf(webtoonList[0])).updateChildren(hashMap);
            }
        });
        star2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[1]);
                hashMap.put("title", titleList[1]);
                hashMap.put("rate", v);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(String.valueOf(webtoonList[1])).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
        star3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[2]);
                hashMap.put("title", titleList[2]);
                hashMap.put("rate", v);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(String.valueOf(webtoonList[2])).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
        star4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[3]);
                hashMap.put("title", titleList[3]);
                hashMap.put("rate", v);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(String.valueOf(webtoonList[3])).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
        star5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[4]);
                hashMap.put("title", titleList[4]);
                hashMap.put("rate", v);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(String.valueOf(webtoonList[4])).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
        star6.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[5]);
                hashMap.put("title", titleList[5]);
                hashMap.put("rate", v);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(String.valueOf(webtoonList[5])).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
    }
}