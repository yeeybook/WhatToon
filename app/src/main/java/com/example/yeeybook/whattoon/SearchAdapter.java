package com.example.yeeybook.whattoon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       // Picasso.with(holder.image.getContext()).load(list.get(position).getImage()).into(holder.image);
      //  Picasso.with(holder.imageView.getContext())
      //          .load(myList.get(position).getSqliteImage()).into(holder.imageView);
        //Picasso.with(holder.image.getContext()).load(list.get(position).getImage()).into(holder.image);
        holder.image.setImageResource(holder.image.getContext().getResources().getIdentifier("img"+list.get(position).getWebtoonId(),
                "drawable", holder.image.getContext().getPackageName()));

        holder.title.setText(list.get(position).getTitle());
        holder.author.setText(list.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title,author,id;
      public MyViewHolder(@NonNull View itemView){
          super(itemView);

          image = itemView.findViewById(R.id.webtoon_image);
          title = itemView.findViewById(R.id.webtoon_title);
          author = itemView.findViewById(R.id.webtoon_author);
      }
  }


}
