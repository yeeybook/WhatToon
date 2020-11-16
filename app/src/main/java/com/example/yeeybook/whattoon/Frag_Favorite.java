package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yeeybook.whattoon.Model.RatingModel;
import com.example.yeeybook.whattoon.naver.Tab_Frag2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.bind.TreeTypeAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Frag_Favorite extends Fragment {

    private View view;
    final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 로그인 중인 uid

    private RecyclerView mFavoriteRecyclerView;
    private FavoriteAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_favorite,container,false);

        mFavoriteRecyclerView = view.findViewById(R.id.FavoriteRecyclerView); // 띄울 리사이클러뷰 바인딩

        mGridLayoutManager = new GridLayoutManager(getContext(), 3);

        mFavoriteRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new FavoriteAdapter();

        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Favorites").addValueEventListener(new ValueEventListener() { // 평가한 항목들 가져오기
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<FavoriteData> data = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    RatingModel ratingModel = snapshot1.getValue(RatingModel.class);
                    data.add(new FavoriteData(ratingModel.webtoonId, ratingModel.title));
                    mAdapter.setData(data);
                    mFavoriteRecyclerView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        return view;
    }

}
