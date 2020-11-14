package com.example.yeeybook.whattoon.Rating;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yeeybook.whattoon.BottomActivity;
import com.example.yeeybook.whattoon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class RatingActivity5 extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    LinearLayout RatingLayout;
    RatingBar star1, star2, star3, star4, star5, star6;
    Button nextBtn, prevBtn, RatingBtn;
    ImageView RatingImg1, RatingImg2, RatingImg3, RatingImg4, RatingImg5, RatingImg6;
    TextView RatingTv1, RatingTv2, RatingTv3, RatingTv4, RatingTv5, RatingTv6, welcomeTv, guideTv1, guideTv2;
    int [] webtoonList = {357, 538, 525, 51, 368, 438};
    String [] titleList = {"내 ID는 강남미인", "우리 집에 왜 왔니", "취향저격 그녀", "여신강림", "죽음에 관하여", "쌍갑포차"};
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public static Activity activity;

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
        RatingLayout = findViewById(R.id.RatingLayout);
        RatingLayout.setVisibility(View.GONE);
        RatingImg1 = findViewById(R.id.RatingImg1);
        RatingImg2 = findViewById(R.id.RatingImg2);
        RatingImg3 = findViewById(R.id.RatingImg3);
        RatingImg4 = findViewById(R.id.RatingImg4);
        RatingImg5 = findViewById(R.id.RatingImg5);
        RatingImg6 = findViewById(R.id.RatingImg6);
        RatingTv1 = findViewById(R.id.RatingTv1);
        RatingTv2 = findViewById(R.id.RatingTv2);
        RatingTv3 = findViewById(R.id.RatingTv3);
        RatingTv4 = findViewById(R.id.RatingTv4);
        RatingTv5 = findViewById(R.id.RatingTv5);
        RatingTv6 = findViewById(R.id.RatingTv6);
        welcomeTv = findViewById(R.id.welcomeTv);
        guideTv1 = findViewById(R.id.guideTv1);
        guideTv2 = findViewById(R.id.guideTv2);
        nextBtn = findViewById(R.id.nextBtn);
        prevBtn = findViewById(R.id.prevBtn);
        RatingBtn = findViewById(R.id.RatingBtn);

        RatingImg1.setImageResource(getResources().getIdentifier("img"+webtoonList[0], "drawable", getPackageName()));
        RatingImg2.setImageResource(getResources().getIdentifier("img"+webtoonList[1], "drawable", getPackageName()));
        RatingImg3.setImageResource(getResources().getIdentifier("img"+webtoonList[2], "drawable", getPackageName()));
        RatingImg4.setImageResource(getResources().getIdentifier("img"+webtoonList[3], "drawable", getPackageName()));
        RatingImg5.setImageResource(getResources().getIdentifier("img"+webtoonList[4], "drawable", getPackageName()));
        RatingImg6.setImageResource(getResources().getIdentifier("img"+webtoonList[5], "drawable", getPackageName()));
        RatingTv1.setText(titleList[0]);
        RatingTv2.setText(titleList[1]);
        RatingTv3.setText(titleList[2]);
        RatingTv4.setText(titleList[3]);
        RatingTv5.setText(titleList[4]);
        RatingTv6.setText(titleList[5]);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nextBtn.setText("완료");
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingLayout.setVisibility(View.VISIBLE);
                RatingBtn.setText("왓툰 시작하기");
                welcomeTv.setText("수고하셨습니다!\n");
                guideTv1.setText("이제 마이페이지에서");
                guideTv2.setText("첫 작품을 추천 받을 수 있어요\n");
                RatingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RatingActivity1 ratingActivity1 = (RatingActivity1)RatingActivity1.activity;
                        RatingActivity2 ratingActivity2 = (RatingActivity2)RatingActivity2.activity;
                        RatingActivity3 ratingActivity3 = (RatingActivity3)RatingActivity3.activity;
                        RatingActivity4 ratingActivity4 = (RatingActivity4)RatingActivity4.activity;
                        ratingActivity1.finish();
                        ratingActivity2.finish();
                        ratingActivity3.finish();
                        ratingActivity4.finish();
                        // 이전 액티비티들 다 닫기
                        Intent intent = new Intent(getApplicationContext(), BottomActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
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