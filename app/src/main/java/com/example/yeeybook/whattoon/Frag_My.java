package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private final String BASE_URL = "https://9e83161576a6.ngrok.io/";
    private Button RecommenderBtn;
    private TextView RecommenderTv;
    private final String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_my,container,false);
        RecommenderBtn = view.findViewById(R.id.RecommenderBtn);
        RecommenderTv = view.findViewById(R.id.RecommenderTv);

        RecommenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "POST");
                RecommenderItem item = new RecommenderItem();
                item.setTitle("ㅇㅇ");
                item.setSim((float)20);

                Call<RecommenderItem> postCall = myAPI.post_recommenders(item);
                postCall.enqueue(new Callback<RecommenderItem>() {
                    @Override
                    public void onResponse(Call<RecommenderItem> call, Response<RecommenderItem> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "등록 완료");
                        } else{
                            Log.d(TAG, "Status Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<RecommenderItem> call, Throwable t) {
                        Log.d(TAG, "Fail msg : " + t.getMessage());
                    }
                });

                Log.d(TAG, "GET");
                Call<List<RecommenderItem>> getCall = myAPI.get_recommenders();
                getCall.enqueue(new Callback<List<RecommenderItem>>() {
                    @Override
                    public void onResponse(Call<List<RecommenderItem>> call, Response<List<RecommenderItem>> response) {
                        if(response.isSuccessful()){
                            List<RecommenderItem> list = response.body();
                            String result = "";
                            for(RecommenderItem item : list){
                                result += "유사도 : " + item.getSim() + "제목 : " + item.getTitle() + "\n";
                            }
                            RecommenderTv.setText(result);
                        } else{
                            Log.d(TAG, "Status Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RecommenderItem>> call, Throwable t) {
                        Log.d(TAG, "Fail msg : " + t.getMessage());
                    }
                });

            }
        });

//        RecommenderBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "GET");
//                Call<List<RecommenderItem>> getCall = myAPI.get_recommenders();
//                getCall.enqueue(new Callback<List<RecommenderItem>>() {
//                    @Override
//                    public void onResponse(Call<List<RecommenderItem>> call, Response<List<RecommenderItem>> response) {
//                        if(response.isSuccessful()){
//                            List<RecommenderItem> list = response.body();
//                            String result = "";
//                            for(RecommenderItem item : list){
//                                result += "유사도 : " + item.getSim() + "제목 : " + item.getTitle() + "\n";
//                            }
//                            RecommenderTv.setText(result);
//                        } else{
//                            Log.d(TAG, "Status Code : " + response.code());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<RecommenderItem>> call, Throwable t) {
//                        Log.d(TAG, "Fail msg : " + t.getMessage());
//                    }
//                });
//            }
//        });
        initMyAPI(BASE_URL);
        return view;
    }

    private void initMyAPI(String baseUrl){
        Log.d(TAG, "initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPI = retrofit.create(MyAPI.class);

    }
}
