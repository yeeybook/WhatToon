package com.example.yeeybook.whattoon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Frag_My extends Fragment {

    private View view;
    private MyAPI myAPI;
    private final String BASE_URL = "https://f71142c0cfab.ngrok.io/"; // Django 서버에서 runserver하고 ngrok.exe에서 ngrok http 8000하고 얻은 주소 넣어야 함
    private final String TAG = getClass().getSimpleName();
    private Button RatingBtn, RecommenderBtn, BackBtn;
    private TextView ResultTv0, ResultTv1, ResultTv2, ResultTv3, ResultTv4, ResultTv5, ResultTv6, LogoutTv;
    private ImageView ResultImg1, ResultImg2, ResultImg3, ResultImg4, ResultImg5;
    private LinearLayout RecommenderLayout, ResultLayout;
    private FirebaseAuth firebaseAuth;
    public int i;
    public String[] titleList = {"", "", "", "", ""};
    public String[] authorList = {"", "", "", "", ""};
    public String[] platformList = {"", "", "", "", ""};
    public String userName = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_my, container, false);
        RatingBtn = view.findViewById(R.id.RatingBtn);
        RecommenderBtn = view.findViewById(R.id.RecommenderBtn);
        BackBtn = view.findViewById(R.id.BackBtn);
        RecommenderLayout = view.findViewById(R.id.RecommenderLayout);
        ResultTv0 = view.findViewById(R.id.ResultTv0);
        ResultTv1 = view.findViewById(R.id.ResultTv1);
        ResultTv2 = view.findViewById(R.id.ResultTv2);
        ResultTv3 = view.findViewById(R.id.ResultTv3);
        ResultTv4 = view.findViewById(R.id.ResultTv4);
        ResultTv5 = view.findViewById(R.id.ResultTv5);
        LogoutTv = view.findViewById(R.id.LogoutTv);
//        ResultTv6 = view.findViewById(R.id.ResultTv6);
        ResultImg1 = view.findViewById(R.id.ResultImg1);
        ResultImg2 = view.findViewById(R.id.ResultImg2);
        ResultImg3 = view.findViewById(R.id.ResultImg3);
        ResultImg4 = view.findViewById(R.id.ResultImg4);
        ResultImg5 = view.findViewById(R.id.ResultImg5);
        ResultLayout = view.findViewById(R.id.ResultLayout);

        RatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), RatingActivity.class);
                startActivity(intent);
            }
        });

        LogoutTv.setPaintFlags(LogoutTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //밑줄 표시
        LogoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut(); //로그아웃 코드
                Activity activity = (Activity) getContext();
                activity.finish();
                Intent a = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(a);
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultLayout.setVisibility(View.GONE);
                RecommenderLayout.setVisibility(View.VISIBLE);
            }
        });

        RecommenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 로그인 중인 uid
                Call<RecommenderItem> postCall = myAPI.post_recommenders(currentUid); // uid란 이름으로 request 보낼 내용
                postCall.enqueue(new Callback<RecommenderItem>() { // API로 POST 요청 보냄
                    @Override
                    public void onResponse(Call<RecommenderItem> call, Response<RecommenderItem> response) { // 응답 받음
                        if (response.isSuccessful()) {
                            final String[] idList = {response.body().getTitle1(), response.body().getTitle2(), response.body().getTitle3(), response.body().getTitle4(), response.body().getTitle5()}; // String 형태로 id값 받아옴
                            final double[] valList = {response.body().getVal1(), response.body().getVal2(), response.body().getVal3(), response.body().getVal4(), response.body().getVal5()}; // double 형태로 val값 받아옴

                            FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                    for (DataSnapshot snapshot : datasnapshot.getChildren()){
                                        UserModel userModel = snapshot.getValue(UserModel.class);
                                        if(currentUid.equals(userModel.uid)) userName = userModel.name; // 현재 로그인 중인 사용자의 uid가 일치하면 이름 가져옴
                                    }
                                    ResultTv0.setText(userName+"님께서 좋아하실 만한 웹툰은");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            FirebaseDatabase.getInstance().getReference().child("Webtoons").addValueEventListener(new ValueEventListener() { // 파이어베이스 Webtoons항목에서 해당 id를 가진 제목, 작가, 플랫폼 가져오기 위해
                                @Override
                                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                        for (i = 0; i < 5; i++) {
                                            WebtoonModel webtoonModel = snapshot.getValue(WebtoonModel.class);
                                            if (idList[i].equals(String.valueOf(webtoonModel.webtoonId))) {
                                                titleList[i] = webtoonModel.title;
                                                authorList[i] = webtoonModel.author;
                                                platformList[i] = webtoonModel.platform;
                                            }
                                        }
                                    }
                                    ResultLayout.setVisibility(View.VISIBLE);
                                    RecommenderLayout.setVisibility(View.GONE);
                                    ResultTv1.setText(valList[0] + "%의 확률로\n" + authorList[0] + " 작가의\n'" + titleList[0] + "'\n[" + platformList[0] + "]");
                                    ResultTv2.setText(valList[1] + "%의 확률로\n" + authorList[1] + " 작가의\n'" + titleList[1] + "'\n[" + platformList[1] + "]");
                                    ResultTv3.setText(valList[2] + "%의 확률로\n" + authorList[2] + " 작가의\n'" + titleList[2] + "'\n[" + platformList[2] + "]");
                                    ResultTv4.setText(valList[3] + "%의 확률로\n" + authorList[3] + " 작가의\n'" + titleList[3] + "'\n[" + platformList[3] + "]");
                                    ResultTv5.setText(valList[4] + "%의 확률로\n" + authorList[4] + " 작가의\n'" + titleList[4] + "'\n[" + platformList[4] + "]");
                                    ResultImg1.setImageResource(getResources().getIdentifier("img" + idList[0], "drawable", getActivity().getPackageName()));
                                    ResultImg2.setImageResource(getResources().getIdentifier("img" + idList[1], "drawable", getActivity().getPackageName()));
                                    ResultImg3.setImageResource(getResources().getIdentifier("img" + idList[2], "drawable", getActivity().getPackageName()));
                                    ResultImg4.setImageResource(getResources().getIdentifier("img" + idList[3], "drawable", getActivity().getPackageName()));
                                    ResultImg5.setImageResource(getResources().getIdentifier("img" + idList[4], "drawable", getActivity().getPackageName()));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else Log.d(TAG, "Status Code : " + response.code());
                    }

                    @Override
                    public void onFailure(Call<RecommenderItem> call, Throwable t) {
                        Log.d(TAG, "Fail msg : " + t.getMessage());
                    }
                });
            }
        });

        initMyAPI(BASE_URL);
        return view;
    }

    private void initMyAPI(String baseUrl) { // Retrofit 객체를 생성하고 이걸 이용하여 API service를 create함
        Log.d(TAG, "initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPI = retrofit.create(MyAPI.class);

    }
}

