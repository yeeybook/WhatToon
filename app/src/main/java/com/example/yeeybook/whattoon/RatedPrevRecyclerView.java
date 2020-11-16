package com.example.yeeybook.whattoon;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


class RatedPrevAdapter extends RecyclerView.Adapter<RatedPrevViewHolder> {

    private ArrayList<RatedPrevData> ratedPrevData;

    public void setData(ArrayList<RatedPrevData> list){
        ratedPrevData = list;
    }

    @Override
    public RatedPrevViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rated_prev, parent, false);

        RatedPrevViewHolder holder = new RatedPrevViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RatedPrevViewHolder holder, int position) {
        final RatedPrevData data = ratedPrevData.get(position);

        holder.RatedImg.setImageResource(holder.itemView.getContext().getResources().getIdentifier("img"+data.getImgId(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.mView.setOnClickListener(new View.OnClickListener() { // 작품 누르면 프로필 페이지로 넘어가게
            @Override
            public void onClick(View view) {
                Intent a = new Intent(view.getContext(), WebtoonProfileActivity.class);
                a.putExtra("id", data.getImgId()); // 페이지 넘길 때 id값도 전달
                view.getContext().startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ratedPrevData.size();
    }
}

class RatedPrevViewHolder extends RecyclerView.ViewHolder { // ViewHolder를 상속받는 클래스
    public ImageView RatedImg;
    public final View mView; // 클릭 이벤트 위해

    public RatedPrevViewHolder(View itemView){
        super(itemView);
        mView = itemView; // 클릭 이벤트 위해
        RatedImg = itemView.findViewById(R.id.RatedImg);
    }
}

class RatedPrevData {

    private int imgId;

    public RatedPrevData(int imgId){
        this.imgId = imgId;
    }

    public int getImgId() {
        return this.imgId;
    }
}
