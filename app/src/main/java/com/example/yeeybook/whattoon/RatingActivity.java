package com.example.yeeybook.whattoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RatingActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    RatingBar star1, star2, star3, star4, star5, star6;
    Button nextBtn;
    String [] webtoonList = {"370", "27", "361", "366", "161", "166"};
    String [] titleList = {"대학일기", "신의 탑", "신과 함께", "하이브", "나노마신", "가담항설"};
    String rate, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        star6 = findViewById(R.id.star6);
        nextBtn = findViewById(R.id.nextBtn);


        star1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rate = String.valueOf(v); //별점 값 받아옴
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[0]);
                hashMap.put("title", titleList[0]);
                hashMap.put("rate", rate);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(webtoonList[0]).updateChildren(hashMap);

            }
        });
        star2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rate = String.valueOf(v); //별점 값 받아옴
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[1]);
                hashMap.put("title", titleList[1]);
                hashMap.put("rate", rate);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(webtoonList[1]).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
        star3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rate = String.valueOf(v); //별점 값 받아옴
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[2]);
                hashMap.put("title", titleList[2]);
                hashMap.put("rate", rate);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(webtoonList[2]).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
        star4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rate = String.valueOf(v); //별점 값 받아옴
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[3]);
                hashMap.put("title", titleList[3]);
                hashMap.put("rate", rate);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(webtoonList[3]).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
        star5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rate = String.valueOf(v); //별점 값 받아옴
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[4]);
                hashMap.put("title", titleList[4]);
                hashMap.put("rate", rate);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(webtoonList[4]).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
        star6.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rate = String.valueOf(v); //별점 값 받아옴
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("webtoonId", webtoonList[5]);
                hashMap.put("title", titleList[5]);
                hashMap.put("rate", rate);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Ratings").child(webtoonList[5]).updateChildren(hashMap); // Ratings 안에 id안에 값 넣음
            }
        });
    }
}