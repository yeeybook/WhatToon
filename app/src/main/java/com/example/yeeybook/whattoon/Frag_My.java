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

import java.awt.font.TextAttribute;
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
    private final String BASE_URL = "https://4eade4cad20c.ngrok.io/";
    private final String TAG = getClass().getSimpleName();
    private Button RecommenderBtn, BackBtn;
    private TextView ResultTv0, ResultTv1, ResultTv2, ResultTv3, ResultTv4, ResultTv5, ResultTv6, LogoutTv;
    private ImageView ResultImg1, ResultImg2, ResultImg3, ResultImg4, ResultImg5;
    private LinearLayout RecommenderLayout, ResultLayout;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_my,container,false);
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

        LogoutTv.setPaintFlags(LogoutTv.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG); //밑줄 표시
        LogoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),"로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut(); //로그아웃 코드
                Activity activity = (Activity)getContext();
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
                Call<RecommenderItem> postCall = myAPI.post_recommenders("whereismyavocado"); // uid란 이름으로 request 보낼 내용
                postCall.enqueue(new Callback<RecommenderItem>() {
                    @Override
                    public void onResponse(Call<RecommenderItem> call, Response<RecommenderItem> response) {
                        if(response.isSuccessful()){
                            ResultLayout.setVisibility(View.VISIBLE);
                            RecommenderLayout.setVisibility(View.GONE);
                            String result = "";
                            Log.d(TAG, "성공했다리");
                            String tit1 = response.body().getTitle1(), tit2 = response.body().getTitle2(), tit3 = response.body().getTitle3(), tit4 = response.body().getTitle4(), tit5 = response.body().getTitle5();
                            ResultTv1.setText(response.body().getVal1()+"%의 확률로\n"+tit1);
                            ResultTv2.setText(response.body().getVal2()+"%의 확률로\n"+tit2);
                            ResultTv3.setText(response.body().getVal3()+"%의 확률로\n"+tit3);
                            ResultTv4.setText(response.body().getVal4()+"%의 확률로\n"+tit4);
                            ResultTv5.setText(response.body().getVal5()+"%의 확률로\n"+tit5);
//                            ResultImg1.setImageResource(getResources().getIdentifier("img123", "drawable", getActivity().getPackageName()));
//                            ResultImg2.setImageResource(getResources().getIdentifier(tit1, "drawable", getActivity().getPackageName()));
//                            ResultImg3.setImageResource(getResources().getIdentifier(tit1, "drawable", getActivity().getPackageName()));
//                            ResultImg4.setImageResource(getResources().getIdentifier(tit1, "drawable", getActivity().getPackageName()));
//                            ResultImg5.setImageResource(getResources().getIdentifier(tit1, "drawable", getActivity().getPackageName()));
                        } else Log.d(TAG, "Status Code : " + response.code());
                    }

                    @Override
                    public void onFailure(Call<RecommenderItem> call, Throwable t) {
                        Log.d(TAG, "Fail msg : " + t.getMessage());
                    }
                });
//                Call<List<RecommenderItem>> postCall = myAPI.post_recommenders("whereismyavocado");
//                postCall.enqueue(new Callback<List<RecommenderItem>>() {
//                    @Override
//                    public void onResponse(Call<List<RecommenderItem>> call, Response<List<RecommenderItem>> response) {
//                        if(response.isSuccessful()){
//                            List<RecommenderItem> list = response.body();
//                            String result = "";
//                            Log.d(TAG, "성공했다리");
//                            for(RecommenderItem item : list){
//                                result += "제목 : " + item.getTitle1() + " 유사도 : " + item.getVal1() + "\n";
//                            }
//                            RecommenderTv.setText(result);
//                        } else Log.d(TAG, "Status Code : " + response.code());
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<RecommenderItem>> call, Throwable t) {
//                        Log.d(TAG, "Fail msg : " + t.getMessage());
//                    }
//                });

            }
        });

        initMyAPI(BASE_URL);
        return view;
    }

    private void initMyAPI(String baseUrl){ // Retrofit 객체를 생성하고 이걸 이용하여 API service를 create함
        Log.d(TAG, "initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPI = retrofit.create(MyAPI.class);

    }
}
