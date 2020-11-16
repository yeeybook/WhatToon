package com.example.yeeybook.whattoon;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    ArrayList<Search_Webtoon> list;
    public SearchAdapter(ArrayList<Search_Webtoon> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
       // Picasso.with(holder.image.getContext()).load(list.get(position).getImage()).into(holder.image);
      //  Picasso.with(holder.imageView.getContext())
      //          .load(myList.get(position).getSqliteImage()).into(holder.imageView);
        //Picasso.with(holder.image.getContext()).load(list.get(position).getImage()).into(holder.image);
        holder.image.setImageResource(holder.image.getContext().getResources().getIdentifier("img"+list.get(position).getWebtoonId(),
                "drawable", holder.image.getContext().getPackageName()));
        holder.title.setText(list.get(position).getTitle());
        holder.author.setText(list.get(position).getAuthor());

        holder.mView.setOnClickListener(new View.OnClickListener() { // 검색 결과 누르면 프로필 페이지로 넘어가게
            @Override
            public void onClick(View view) {
                Intent a = new Intent(view.getContext(), WebtoonProfileActivity.class);
                a.putExtra("id", list.get(position).getWebtoonId()); // 페이지 넘길 때 id값도 전달
                view.getContext().startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title,author;

        public final View mView; // 클릭 이벤트 위해

      public MyViewHolder(@NonNull final View itemView){
          super(itemView);
          mView = itemView; // 클릭 이벤트 위해
          image = itemView.findViewById(R.id.webtoon_image);
          title = itemView.findViewById(R.id.webtoon_title);
          author = itemView.findViewById(R.id.webtoon_author);

      }
  }


}
