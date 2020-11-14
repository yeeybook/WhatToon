package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yeeybook.whattoon.Model.RatingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Frag_Rated extends Fragment {

    private TextView backTv;
    private View view;
    final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 로그인 중인 uid

    private RecyclerView mRatedRecyclerView;
    private RatedAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_rated, container, false);
        backTv = view.findViewById(R.id.backTv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomActivity activity = (BottomActivity)getActivity();
                activity.setFrag(3); // Frag_MY로 다시 바꾸는 함수 호출
            }
        });

        mRatedRecyclerView = view.findViewById(R.id.RatedRecyclerView); // 띄울 리사이클러뷰 바인딩

        mGridLayoutManager = new GridLayoutManager(getContext(), 3);

        mRatedRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new RatedAdapter();

        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Ratings").addValueEventListener(new ValueEventListener() { // 평가한 항목들 가져오기
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<RatedData> data = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    RatingModel ratingModel = snapshot1.getValue(RatingModel.class);
                    if(ratingModel.rate > 0) { // 평가한 데이터만 보여줌
                        data.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            mAdapter.setData(data);
                            mRatedRecyclerView.setAdapter(mAdapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        return view;
    }

}
