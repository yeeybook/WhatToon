package com.example.yeeybook.whattoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yeeybook.whattoon.Model.CommentModel;
import com.example.yeeybook.whattoon.Model.FavoriteModel;
import com.example.yeeybook.whattoon.Model.RatingModel;
import com.example.yeeybook.whattoon.Model.UserModel;
import com.example.yeeybook.whattoon.Model.WebtoonModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebtoonProfileActivity extends AppCompatActivity {
    private ToggleButton favoriteBtn;
    private TextView myRateTv, leaveCommentTv, myCommentTv, num, myNameTv, editMyCommentTv, deleteMyCommentTv;
    private RatingBar star;
    private TextView Profiletitle,Profileauthor,Profilegenre,Profileday,Profilefavorite,Profiledesc;
    private ImageView Profileimg, myProfileImg;
    private Button Profileurl;
    private LinearLayout myCommentLayout;
    private EditText commentDialog;
    private int webtoonId;
    private String webtoonTitle, webtoonPlatform, userName, userProfileUrl, userComment;
    private float userRate;

    private Toolbar toolbar;

    private DatabaseReference mDatabase;

    ListView listView;
    MyAdapter adapter;
    List<ItemData> dataList;

    private String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 로그인 중인 uid
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_profile);
        if(Build.VERSION.SDK_INT >= 21){ // 버전이 21이상일 때만 상태바 색상 변경 가능함
            getWindow().setStatusBarColor(Color.parseColor("#1F7AE2"));
        }

        toolbar = findViewById(R.id.myToolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기버튼 추가

        webtoonId = getIntent().getIntExtra("id", 1); // 이전 페이지에서 받아온 웹툰 id값
        final String ProfileId = Integer.toString(webtoonId-1);

        Profileimg = findViewById(R.id.Profileimg);
        Profiletitle = findViewById(R.id.Profiletitle);
        Profileauthor = findViewById(R.id.Profileauthor);
        Profilegenre = findViewById(R.id.Profilegenre);
        Profileday = findViewById(R.id.Profileday);
        Profilefavorite=findViewById(R.id.Profilefavorite);
        Profiledesc=findViewById(R.id.Profiledesc);
        Profileurl=findViewById(R.id.Profileurl);
        favoriteBtn = findViewById(R.id.favoriteBtn);
        myRateTv = findViewById(R.id.myRateTv);
        star = findViewById(R.id.star);

        num = findViewById(R.id.num);//
        leaveCommentTv = findViewById(R.id.leaveCommentTv);//
        editMyCommentTv = findViewById(R.id.editMyCommentTv);
        deleteMyCommentTv = findViewById(R.id.deleteMyCommentTv);
        myCommentLayout = findViewById(R.id.myCommentLayout);//
        myCommentTv = findViewById(R.id.myCommentTv);//
        myNameTv = findViewById(R.id.myNameTv);
        myProfileImg = findViewById(R.id.myProfileImg);
        listView = findViewById(R.id.recomment);//

        editMyCommentTv.setOnClickListener(new View.OnClickListener() { // 내 코멘트 수정
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WebtoonProfileActivity.this);
                final View view2 = getLayoutInflater().inflate(R.layout.dialog_comment, null);//
                commentDialog = view2.findViewById(R.id.commentDialog);//
                commentDialog.setText(userComment); // 이전에 남긴 코멘트로 채워놓음
                builder.setView(view2).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("webtoonId", webtoonId);
                        hashMap.put("comment", commentDialog.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Comments").child(String.valueOf(webtoonId)).updateChildren(hashMap); // Users에 내 코멘트 업데이트

                        HashMap<String, Object> hashMap2 = new HashMap<>();
                        hashMap2.put("userId", currentUid);
                        hashMap2.put("rate", userRate);
                        hashMap2.put("comment", commentDialog.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId-1)).child("Comments").child(currentUid).updateChildren(hashMap2); // Webtoons에 내 코멘트 업데이트

                        myNameTv.setText(userName); // 내 이름 띄움
                        Glide.with(getApplicationContext()).load(userProfileUrl).apply(new RequestOptions().circleCrop()).into(myProfileImg); // 내 프로필 사진 띄움
                        myCommentTv.setText(commentDialog.getText().toString()); // 내 코멘트 띄움
                        myCommentLayout.setVisibility(View.VISIBLE); // 내 코멘트 레이아웃 띄움
                       leaveCommentTv.setVisibility(View.GONE); // 처음 코멘트 남기기 버튼 숨김
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        deleteMyCommentTv.setOnClickListener(new View.OnClickListener() { // 내 코멘트 삭제
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Comments").child(String.valueOf(webtoonId)).removeValue(); // User Comments항목에서 내 코멘트 지움
                FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId-1)).child("Comments").child(currentUid).removeValue(); // Webtoons Comments항목에서 내 코멘트 지움
                myCommentLayout.setVisibility(View.GONE); // 코멘트 띄우는 레이아웃 없애기
                leaveCommentTv.setVisibility(View.VISIBLE); // 초기 코멘트 남기는 부분 띄우기
            }
        });
        leaveCommentTv.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View view) { // 코멘트 남기기
                AlertDialog.Builder builder = new AlertDialog.Builder(WebtoonProfileActivity.this);
                final View view2 = getLayoutInflater().inflate(R.layout.dialog_comment, null);//
                commentDialog = view2.findViewById(R.id.commentDialog);//
                builder.setView(view2).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("webtoonId", webtoonId);
                        hashMap.put("comment", commentDialog.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Comments").child(String.valueOf(webtoonId)).updateChildren(hashMap); // Users에 내 코멘트 업데이트

                        HashMap<String, Object> hashMap2 = new HashMap<>();
                        hashMap2.put("userId", currentUid);
                        hashMap2.put("rate", userRate);
                        hashMap2.put("comment", commentDialog.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId-1)).child("Comments").child(currentUid).updateChildren(hashMap2); // Webtoons에 내 코멘트 업데이트

                        myNameTv.setText(userName); // 내 이름 띄움
                        Glide.with(getApplicationContext()).load(userProfileUrl).apply(new RequestOptions().circleCrop()).into(myProfileImg); // 내 프로필 사진 띄움
                        myCommentTv.setText(commentDialog.getText().toString()); // 내 코멘트 띄움
                        myCommentLayout.setVisibility(View.VISIBLE); // 내 코멘트 레이아웃 띄움
                        leaveCommentTv.setVisibility(View.GONE); // 처음 코멘트 남기기 버튼 숨김
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });//

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").child(currentUid).addValueEventListener(new ValueEventListener() { // 사용자 이름, 프로필 이미지 가져와서 변수에 저장해놓는 코드
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                userName = userModel.name;
                userProfileUrl = userModel.profileImgUrl;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        //사진,제목,작가,장르,하트수,설명 출력

        mDatabase.child("Webtoons").child(String.valueOf(webtoonId-1)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WebtoonModel webtoonModel = snapshot.getValue(WebtoonModel.class);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("favorite", Integer.toString(webtoonModel.favorite));

                Profilefavorite.setText(hashMap.get("favorite"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("Webtoons").child(String.valueOf(webtoonId-1)).addListenerForSingleValueEvent(new ValueEventListener() { // 웹툰 정보 가져옴
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                WebtoonModel webtoonModel = snapshot.getValue(WebtoonModel.class);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("title", webtoonModel.title);
                hashMap.put("author", webtoonModel.author);
                hashMap.put("desc", webtoonModel.story);
                hashMap.put("day", webtoonModel.day);
                hashMap.put("genre", webtoonModel.genre);
                hashMap.put("id", Integer.toString(webtoonModel.webtoonId));

                //hashMap.clear();

                webtoonTitle = webtoonModel.title;
                webtoonPlatform = webtoonModel.platform;

                Profiletitle.setText(hashMap.get("title"));
                Profileauthor.setText(hashMap.get("author"));
                Profileday.setText(hashMap.get("day"));
                Profiledesc.setText(hashMap.get("desc"));
                Profilegenre.setText(hashMap.get("genre"));
                Profileimg.setImageResource(getResources().getIdentifier("img"+hashMap.get("id"),"drawable",getApplicationContext().getPackageName()));

                mDatabase.child("Webtoons").child(String.valueOf(webtoonId-1)).child("Comments").addValueEventListener(new ValueEventListener() { // 다른 사람들 코멘트 가져옴
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        dataList = new ArrayList<>();
                        int cnt=0;
                        for (DataSnapshot snapshot : datasnapshot.getChildren()){
                            final CommentModel commentModel = snapshot.getValue(CommentModel.class);
                            if(commentModel.comment == null || commentModel.userId.equals(currentUid)) continue; // 코멘트를 남기지 않은 사람이거나 본인 코멘트일 땐 넘김
                            cnt++;
                            mDatabase.child("Users").child(commentModel.userId).addListenerForSingleValueEvent(new ValueEventListener() { // 코멘트 남긴 사람 정보 가져옴
                                @Override
                                public void onDataChange(@NonNull DataSnapshot datasnapshot1) {
                                    UserModel userModel = datasnapshot1.getValue(UserModel.class);
                                    dataList.add(new ItemData(userModel.name,userModel.profileImgUrl,commentModel.comment,commentModel.rate));
                                    adapter = new MyAdapter(WebtoonProfileActivity.this,dataList);
                                    listView.setAdapter(adapter);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) { }
                            });
                        }
                        num.setText("("+cnt+")");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //url이동

        Profileurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId-1)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        WebtoonModel webtoonModel = snapshot.getValue(WebtoonModel.class);

                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put("url", webtoonModel.url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(hashMap.get("url")));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



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
                    FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).addListenerForSingleValueEvent(new ValueEventListener() { // favorite 값 찾기 위해
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            WebtoonModel webtoonModel = datasnapshot.getValue(WebtoonModel.class);
                            HashMap<String, Object> hashMap = new HashMap<>();

                            hashMap.put("favorite", webtoonModel.favorite+1);

                            FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).updateChildren(hashMap); // Webtoons 값에 +1
                            hashMap.clear();

                            hashMap.put("webtoonId", webtoonId);
                            hashMap.put("title", webtoonTitle);
                            hashMap.put("platform", webtoonPlatform);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Favorites").child(String.valueOf(webtoonId)).updateChildren(hashMap); // User favorite항목에 추가
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else{ // 좋아요 해제하면 Webtoons의 favorite에 -1된다
                    FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).addListenerForSingleValueEvent(new ValueEventListener() { // favorite 값 찾기 위해
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            WebtoonModel webtoonModel = datasnapshot.getValue(WebtoonModel.class);
                            HashMap<String, Object> hashMap = new HashMap<>();

                            hashMap.put("favorite", webtoonModel.favorite-1);

                            FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId - 1)).updateChildren(hashMap); // Webtoons 값에 -1

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
                        userRate = ratingModel.rate; // 사용자가 남긴 별점 값 변수에 저장
                        star.setRating(ratingModel.rate);
                        myRateTv.setText("★ " + ratingModel.rate);
                        myRateTv.setTextColor(Color.parseColor("#1F7AE2"));
                        leaveCommentTv.setVisibility(View.VISIBLE);//
                        break;
                    }
                }
                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Comments").addValueEventListener(new ValueEventListener() { // 코멘트 남긴거 있으면 가져오는 코드
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot2) {
                        for (DataSnapshot snapshot : datasnapshot2.getChildren()) {
                            CommentModel commentModel = snapshot.getValue(CommentModel.class);
                            if (commentModel.webtoonId == webtoonId) { // 코멘트를 남긴 경우에만 가져옴
                                myNameTv.setText(userName); // 내 이름 띄움
                                Glide.with(getApplicationContext()).load(userProfileUrl).apply(new RequestOptions().circleCrop()).into(myProfileImg); // 내 프로필 사진 띄움
                                myCommentTv.setText(commentModel.comment); // 내 코멘트 띄움
                                userComment = commentModel.comment; // 코멘트 수정 기능을 위해 변수에 저장해놓음
                                myCommentLayout.setVisibility(View.VISIBLE);
                                leaveCommentTv.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Favorites").addValueEventListener(new ValueEventListener() { // 하트 미리 채워넣는 코드
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot1) {
                        for (DataSnapshot snapshot : datasnapshot1.getChildren()) {
                            FavoriteModel favoriteModel = snapshot.getValue(FavoriteModel.class);
                            if (favoriteModel.webtoonId == webtoonId) { // 좋아요 한 작품이면
                                favoriteBtn.setChecked(true);
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() { // 평점 남길 때마다 발생하는 이벤트
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float v, final boolean b) {
                userRate = v; // 사용자가 남긴 별점 값 변수에 저장
                if(v == 0) {
                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Ratings").child(String.valueOf(webtoonId)).removeValue(); // 0점은 평가 안 한거니까
                    star.setRating(v);
                    myRateTv.setText("아직 평가를 하지 않았어요");
                    myRateTv.setTextColor(Color.parseColor("#808080"));

                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Comments").child(String.valueOf(webtoonId)).removeValue(); // User Comments항목에서 내 코멘트 지움
                    FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId-1)).child("Comments").child(currentUid).removeValue(); // Webtoons Comments항목에서 내 코멘트 지움
                    myCommentLayout.setVisibility(View.GONE); // 코멘트 띄우는 레이아웃 없애기
                    leaveCommentTv.setVisibility(View.GONE); // 초기 코멘트도 못 남기게 없애기
                }
                else{
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("webtoonId", webtoonId);
                    hashMap.put("title", webtoonTitle);
                    hashMap.put("rate", v); //별점 값 받아옴
                    star.setRating(v);
                    myRateTv.setText("★" + v);
                    myRateTv.setTextColor(Color.parseColor("#1F7AE2"));
                    leaveCommentTv.setVisibility(View.VISIBLE);//
                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Ratings").child(String.valueOf(webtoonId)).updateChildren(hashMap);

                    HashMap<String, Object> hashMap2 = new HashMap<>();
                    hashMap2.put("userId", currentUid);
                    hashMap2.put("rate", v);
                    FirebaseDatabase.getInstance().getReference().child("Webtoons").child(String.valueOf(webtoonId-1)).child("Comments").child(currentUid).updateChildren(hashMap2); // Webtoons Comments에 내 평점도 업데이트
                }
            }
        });

    }


    @Override
    //뒤로가기
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}