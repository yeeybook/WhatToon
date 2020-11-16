package com.example.yeeybook.whattoon;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private ArrayList<FavoriteData> favoriteData;

    public void setData(ArrayList<FavoriteData> list){
        favoriteData = list;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);

        FavoriteViewHolder holder = new FavoriteViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        final FavoriteData data = favoriteData.get(position);
        holder.FavoriteImg.setImageResource(holder.itemView.getContext().getResources().getIdentifier("img"+data.getImgId(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.FavoriteTitleTv.setText(data.getName());

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
        return favoriteData.size();
    }
}

class FavoriteViewHolder extends RecyclerView.ViewHolder { // ViewHolder를 상속받는 클래스
    public ImageView FavoriteImg;
    public TextView FavoriteTitleTv;
    public final View mView; // 클릭 이벤트 위해

    public FavoriteViewHolder(View itemView){
        super(itemView);
        mView = itemView; // 클릭 이벤트 위해
        FavoriteImg = itemView.findViewById(R.id.FavoriteImg);
        FavoriteTitleTv = itemView.findViewById(R.id.FavoriteTitleTv);
    }
}

class FavoriteData {
    private int imgId;
    private String title;

    public FavoriteData(int imgId, String title){
        this.imgId = imgId;
        this.title = title;
    }

    public int getImgId() {
        return this.imgId;
    }
    public String getName(){
        return this.title;
    }
}
