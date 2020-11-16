package com.example.yeeybook.whattoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.yeeybook.whattoon.Model.RatingModel;
import com.example.yeeybook.whattoon.Model.WebtoonModel;
import com.example.yeeybook.whattoon.naver.Tab_Frag2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class WebtoonProfileActivity extends AppCompatActivity {
    private ToggleButton favoriteBtn;
    private TextView myRateTv;
    private RatingBar star;
    private int webtoonId;
    private String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 로그인 중인 uid
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_profile);
        webtoonId = getIntent().getIntExtra("id", 1); // 이전 페이지에서 받아온 웹툰 id값

        favoriteBtn = findViewById(R.id.favoriteBtn);
        myRateTv = findViewById(R.id.myRateTv);
        star = findViewById(R.id.star);

        //좋아요 눌렀을 때 애니메이션 효과
        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteBtn.startAnimation(scaleAnimation);
                if(favoriteBtn.isChecked()){ // 좋아요 누르면 Webtoons의 favorite에 +1된다
                    FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).addListenerForSingleValueEvent(new ValueEventListener() { // 타이틀 찾으려고 임시로 넣어둠
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            WebtoonModel webtoonModel = datasnapshot.getValue(WebtoonModel.class);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("favorite", webtoonModel.favorite+1);
                            FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).updateChildren(hashMap); // Webtoons 값에 +1
                            hashMap.clear();

                            hashMap.put("webtoonId", webtoonId);
                            hashMap.put("title", webtoonModel.title);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Favorites").child(String.valueOf(webtoonId)).updateChildren(hashMap); // User favorite항목에 추가
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else{ // 좋아요 해제하면 Webtoons의 favorite에 -1된다
                    FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).addListenerForSingleValueEvent(new ValueEventListener() { // Webtoons 값에 -1
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            WebtoonModel webtoonModel = datasnapshot.getValue(WebtoonModel.class);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("favorite", webtoonModel.favorite-1);
                            FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).updateChildren(hashMap);

                            FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Favorites").child(String.valueOf(webtoonId)).removeValue(); // User favorite항목에서 지움
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Ratings").addValueEventListener(new ValueEventListener() { // 별점 미리 채워놓는 코드
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    RatingModel ratingModel = snapshot.getValue(RatingModel.class);
                    if (ratingModel.webtoonId == webtoonId && ratingModel.rate > 0) { // 평가한 데이터만 가져올거다
                        star.setRating(ratingModel.rate);
                        myRateTv.setText("★" + ratingModel.rate);
                        myRateTv.setTextColor(Color.parseColor("#1F7AE2"));

                        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Favorites").addValueEventListener(new ValueEventListener() { // 하트 미리 채워넣는 코드
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                    RatingModel ratingModel = snapshot.getValue(RatingModel.class); // Favorites지만 RatingModel 갖다 쓰기
                                    if (ratingModel.webtoonId == webtoonId) { // 좋아요 한 작품이면
                                        favoriteBtn.setChecked(true);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() { // 평점 남길 때마다 발생하는 이벤트
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float v, final boolean b) {
                FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).addValueEventListener(new ValueEventListener() { // 타이틀 찾으려고 임시로 넣어둠
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        WebtoonModel webtoonModel = datasnapshot.getValue(WebtoonModel.class);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("webtoonId", webtoonId);
                        hashMap.put("title", webtoonModel.title);
                        hashMap.put("rate", v); //별점 값 받아옴
                        star.setRating(v);
                        myRateTv.setText("★" + v);
                        myRateTv.setTextColor(Color.parseColor("#1F7AE2"));
                        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Ratings").child(String.valueOf(webtoonId)).updateChildren(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

}