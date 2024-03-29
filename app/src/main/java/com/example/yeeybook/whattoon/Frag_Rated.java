package com.example.yeeybook.whattoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

    private TextView backTv, cntTv5_0, cntTv4_5, cntTv4_0, cntTv3_5, cntTv3_0, cntTv2_5, cntTv2_0, cntTv1_5,cntTv1_0, cntTv0_5;
    private RelativeLayout Layout5_0, Layout4_5, Layout4_0, Layout3_5, Layout3_0, Layout2_5, Layout2_0, Layout1_5, Layout1_0, Layout0_5;
    private View view;
    final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 로그인 중인 uid

    private RecyclerView Recycler5_0, Recycler4_5, Recycler4_0, Recycler3_5, Recycler3_0, Recycler2_5, Recycler2_0, Recycler1_5, Recycler1_0, Recycler0_5;
    private RatedAdapter Adapter5_0, Adapter4_5, Adapter4_0, Adapter3_5, Adapter3_0, Adapter2_5, Adapter2_0, Adapter1_5, Adapter1_0, Adapter0_5;
    private GridLayoutManager Grid5_0, Grid4_5, Grid4_0, Grid3_5, Grid3_0, Grid2_5, Grid2_0, Grid1_5, Grid1_0, Grid0_5;

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

        cntTv5_0 = view.findViewById(R.id.cntTv5_0);cntTv4_5 = view.findViewById(R.id.cntTv4_5);
        cntTv4_0 = view.findViewById(R.id.cntTv4_0);cntTv3_5 = view.findViewById(R.id.cntTv3_5);
        cntTv3_0 = view.findViewById(R.id.cntTv3_0);cntTv2_5 = view.findViewById(R.id.cntTv2_5);
        cntTv2_0 = view.findViewById(R.id.cntTv2_0);cntTv1_5 = view.findViewById(R.id.cntTv1_5);
        cntTv1_0 = view.findViewById(R.id.cntTv1_0);cntTv0_5 = view.findViewById(R.id.cntTv0_5);

        Recycler5_0 = view.findViewById(R.id.RatedRecyclerView5_0);Recycler4_5 = view.findViewById(R.id.RatedRecyclerView4_5);
        Recycler4_0 = view.findViewById(R.id.RatedRecyclerView4_0);Recycler3_5 = view.findViewById(R.id.RatedRecyclerView3_5);
        Recycler3_0 = view.findViewById(R.id.RatedRecyclerView3_0);Recycler2_5 = view.findViewById(R.id.RatedRecyclerView2_5);
        Recycler2_0 = view.findViewById(R.id.RatedRecyclerView2_0);Recycler1_5 = view.findViewById(R.id.RatedRecyclerView1_5);
        Recycler1_0 = view.findViewById(R.id.RatedRecyclerView1_0);Recycler0_5 = view.findViewById(R.id.RatedRecyclerView0_5);

        Layout5_0 = view.findViewById(R.id.relativeLayout5_0);Layout4_5 = view.findViewById(R.id.relativeLayout4_5);
        Layout4_0 = view.findViewById(R.id.relativeLayout4_0);Layout3_5 = view.findViewById(R.id.relativeLayout3_5);
        Layout3_0 = view.findViewById(R.id.relativeLayout3_0);Layout2_5 = view.findViewById(R.id.relativeLayout2_5);
        Layout2_0 = view.findViewById(R.id.relativeLayout2_0);Layout1_5 = view.findViewById(R.id.relativeLayout1_5);
        Layout1_0 = view.findViewById(R.id.relativeLayout1_0);Layout0_5 = view.findViewById(R.id.relativeLayout0_5);

        Grid5_0 = new GridLayoutManager(getContext(), 3);Grid4_5 = new GridLayoutManager(getContext(), 3);
        Grid4_0 = new GridLayoutManager(getContext(), 3);Grid3_5 = new GridLayoutManager(getContext(), 3);
        Grid3_0 = new GridLayoutManager(getContext(), 3);Grid2_5 = new GridLayoutManager(getContext(), 3);
        Grid2_0 = new GridLayoutManager(getContext(), 3);Grid1_5 = new GridLayoutManager(getContext(), 3);
        Grid1_0 = new GridLayoutManager(getContext(), 3);Grid0_5 = new GridLayoutManager(getContext(), 3);

        Recycler5_0.setLayoutManager(Grid5_0);Recycler4_5.setLayoutManager(Grid4_5);
        Recycler4_0.setLayoutManager(Grid4_0);Recycler3_5.setLayoutManager(Grid3_5);
        Recycler3_0.setLayoutManager(Grid3_0);Recycler2_5.setLayoutManager(Grid2_5);
        Recycler2_0.setLayoutManager(Grid2_0);Recycler1_5.setLayoutManager(Grid1_5);
        Recycler1_0.setLayoutManager(Grid1_0);Recycler0_5.setLayoutManager(Grid0_5);

        Adapter5_0 = new RatedAdapter();Adapter4_5 = new RatedAdapter();
        Adapter4_0 = new RatedAdapter();Adapter3_5 = new RatedAdapter();
        Adapter3_0 = new RatedAdapter();Adapter2_5 = new RatedAdapter();
        Adapter2_0 = new RatedAdapter();Adapter1_5 = new RatedAdapter();
        Adapter1_0 = new RatedAdapter();Adapter0_5 = new RatedAdapter();

        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Ratings").addValueEventListener(new ValueEventListener() { // 평가한 항목들 가져오기
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int cnt5_0=0, cnt4_5=0, cnt4_0=0, cnt3_5=0, cnt3_0=0, cnt2_5=0, cnt2_0=0, cnt1_5=0, cnt1_0=0, cnt0_5=0; // 평가 안 한 애들은 안 보이게 하기 위해 체크
                ArrayList<RatedData> data5_0 = new ArrayList<>();ArrayList<RatedData> data4_5 = new ArrayList<>();
                ArrayList<RatedData> data4_0 = new ArrayList<>();ArrayList<RatedData> data3_5 = new ArrayList<>();
                ArrayList<RatedData> data3_0 = new ArrayList<>();ArrayList<RatedData> data2_5 = new ArrayList<>();
                ArrayList<RatedData> data2_0 = new ArrayList<>();ArrayList<RatedData> data1_5 = new ArrayList<>();
                ArrayList<RatedData> data1_0 = new ArrayList<>();ArrayList<RatedData> data0_5 = new ArrayList<>();

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    RatingModel ratingModel = snapshot1.getValue(RatingModel.class);
                    if(ratingModel.rate == 0) continue; // 평가 안 한 애들은 넘어가
                    switch (String.valueOf(ratingModel.rate)){ // 별점 순으로 동작 다름
                        case "5.0":
                            data5_0.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter5_0.setData(data5_0);
                            Recycler5_0.setAdapter(Adapter5_0);
                            cnt5_0++;
                            break;
                        case "4.5":
                            data4_5.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter4_5.setData(data4_5);
                            Recycler4_5.setAdapter(Adapter4_5);
                            cnt4_5++;
                            break;
                        case "4.0":
                            data4_0.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter4_0.setData(data4_0);
                            Recycler4_0.setAdapter(Adapter4_0);
                            cnt4_0++;
                            break;
                        case "3.5":
                            data3_5.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter3_5.setData(data3_5);
                            Recycler3_5.setAdapter(Adapter3_5);
                            cnt3_5++;
                            break;
                        case "3.0":
                            data3_0.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter3_0.setData(data3_0);
                            Recycler3_0.setAdapter(Adapter3_0);
                            cnt3_0++;
                            break;
                        case "2.5":
                            data2_5.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter2_5.setData(data2_5);
                            Recycler2_5.setAdapter(Adapter2_5);
                            cnt2_5++;
                            break;
                        case "2.0":
                            data2_0.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter2_0.setData(data2_0);
                            Recycler2_0.setAdapter(Adapter2_0);
                            cnt2_0++;
                            break;
                        case "1.5":
                            data1_5.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter1_5.setData(data1_5);
                            Recycler1_5.setAdapter(Adapter1_5);
                            cnt1_5++;
                            break;
                        case "1.0":
                            data1_0.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter1_0.setData(data1_0);
                            Recycler1_0.setAdapter(Adapter1_0);
                            cnt1_0++;
                            break;
                        case "0.5":
                            data0_5.add(new RatedData(ratingModel.webtoonId, ratingModel.rate, ratingModel.title));
                            Adapter0_5.setData(data0_5);
                            Recycler0_5.setAdapter(Adapter0_5);
                            cnt0_5++;
                            break;
                    }
                }

                if(cnt5_0==0) Layout5_0.setVisibility(View.GONE);else cntTv5_0.setText("("+String.valueOf(cnt5_0)+")");
                if(cnt4_5==0) Layout4_5.setVisibility(View.GONE);else cntTv4_5.setText("("+String.valueOf(cnt4_5)+")");
                if(cnt4_0==0) Layout4_0.setVisibility(View.GONE);else cntTv4_0.setText("("+String.valueOf(cnt4_0)+")");
                if(cnt3_5==0) Layout3_5.setVisibility(View.GONE);else cntTv3_5.setText("("+String.valueOf(cnt3_5)+")");
                if(cnt3_0==0) Layout3_0.setVisibility(View.GONE);else cntTv3_0.setText("("+String.valueOf(cnt3_0)+")");
                if(cnt2_5==0) Layout2_5.setVisibility(View.GONE);else cntTv2_5.setText("("+String.valueOf(cnt2_5)+")");
                if(cnt2_0==0) Layout2_0.setVisibility(View.GONE);else cntTv2_0.setText("("+String.valueOf(cnt2_0)+")");
                if(cnt1_5==0) Layout1_5.setVisibility(View.GONE);else cntTv1_5.setText("("+String.valueOf(cnt1_5)+")");
                if(cnt1_0==0) Layout1_0.setVisibility(View.GONE);else cntTv1_0.setText("("+String.valueOf(cnt1_0)+")"); // 평점이 있으면 개수 나타내게
                if(cnt0_5==0) Layout0_5.setVisibility(View.GONE);else cntTv0_5.setText("("+String.valueOf(cnt0_5)+")"); // 평점이 없는 레이아웃은 안 보이게
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        return view;
    }

}

