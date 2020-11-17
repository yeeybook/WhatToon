package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yeeybook.whattoon.Model.FavoriteModel;
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

    private RecyclerView mFavoriteRecyclerView, mFavoriteRecyclerView2;
    private FavoriteAdapter mAdapter, mAdapter2;
    private GridLayoutManager mGridLayoutManager, mGridLayoutManager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_favorite,container,false);

        mFavoriteRecyclerView = view.findViewById(R.id.FavoriteRecyclerView); // 띄울 리사이클러뷰 바인딩
        mFavoriteRecyclerView2 = view.findViewById(R.id.FavoriteRecyclerView2); // 띄울 리사이클러뷰 바인딩

        mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mGridLayoutManager2 = new GridLayoutManager(getContext(), 3);

        mFavoriteRecyclerView.setLayoutManager(mGridLayoutManager);
        mFavoriteRecyclerView2.setLayoutManager(mGridLayoutManager2);
        mAdapter = new FavoriteAdapter();
        mAdapter2 = new FavoriteAdapter();

        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Favorites").addValueEventListener(new ValueEventListener() { // 평가한 항목들 가져오기
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<FavoriteData> data = new ArrayList<>();
                ArrayList<FavoriteData> data2 = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    FavoriteModel favoriteModel = snapshot1.getValue(FavoriteModel.class);
                    if(favoriteModel.platform.equals("네이버 웹툰")){
                        data.add(new FavoriteData(favoriteModel.webtoonId, favoriteModel.title));
                        mAdapter.setData(data);
                        mFavoriteRecyclerView.setAdapter(mAdapter);
                    }else{
                        data2.add(new FavoriteData(favoriteModel.webtoonId, favoriteModel.title));
                        mAdapter2.setData(data2);
                        mFavoriteRecyclerView2.setAdapter(mAdapter2);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        return view;
    }

}
