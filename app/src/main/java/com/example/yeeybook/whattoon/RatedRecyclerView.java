package com.example.yeeybook.whattoon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


class RatedAdapter extends RecyclerView.Adapter<RatedViewHolder> {

    private ArrayList<RatedData> ratedData;

    public void setData(ArrayList<RatedData> list){
        ratedData = list;
    }

    @Override
    public RatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rated, parent, false);

        RatedViewHolder holder = new RatedViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RatedViewHolder holder, int position) {
        RatedData data = ratedData.get(position);
        holder.RatedImg.setImageResource(holder.itemView.getContext().getResources().getIdentifier("img"+data.getImgId(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.RatedTitleTv.setText(data.getName());
        holder.RatedValTv.setText("★ "+data.getRateVal());
    }

    @Override
    public int getItemCount() {
        return ratedData.size();
    }
}

class RatedViewHolder extends RecyclerView.ViewHolder { // ViewHolder를 상속받는 클래스
    public ImageView RatedImg;
    public TextView RatedTitleTv, RatedValTv;

    public RatedViewHolder(View itemView){
        super(itemView);
        RatedImg = itemView.findViewById(R.id.RatedImg);
        RatedTitleTv = itemView.findViewById(R.id.RatedTitleTv);
        RatedValTv = itemView.findViewById(R.id.RatedValTv);
    }
}

class RatedData {
    private int imgId;
    private float rateVal;
    private String title;

    public RatedData(int imgId, float rateVal, String title){
        this.imgId = imgId;
        this.rateVal = rateVal;
        this.title = title;
    }

    public int getImgId() {
        return this.imgId;
    }
    public float getRateVal(){
        return this.rateVal;
    }
    public String getName(){
        return this.title;
    }
}
