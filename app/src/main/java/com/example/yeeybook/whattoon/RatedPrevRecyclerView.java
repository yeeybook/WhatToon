package com.example.yeeybook.whattoon;

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
        RatedPrevData data = ratedPrevData.get(position);

        holder.RatedImg.setImageResource(holder.itemView.getContext().getResources().getIdentifier("img"+data.getImgId(), "drawable", holder.itemView.getContext().getPackageName()));

    }

    @Override
    public int getItemCount() {
        return ratedPrevData.size();
    }
}

class RatedPrevViewHolder extends RecyclerView.ViewHolder { // ViewHolder를 상속받는 클래스
    public ImageView RatedImg;

    public RatedPrevViewHolder(View itemView){
        super(itemView);
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
