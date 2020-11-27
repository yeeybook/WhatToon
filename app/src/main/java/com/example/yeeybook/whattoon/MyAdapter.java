package com.example.yeeybook.whattoon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<ItemData> dataList;

    public MyAdapter(Context context, List<ItemData> dataList) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.dataList = dataList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View view1 = view;
        if(view1 ==null){
            view1 = layoutInflater.inflate(R.layout.item_comment,null);
        }

        ImageView ivProfile = (ImageView)view1.findViewById(R.id.pimg);
        TextView tvNickname=(TextView)view1.findViewById(R.id.nickname);
        RatingBar rbStar = (RatingBar)view1.findViewById(R.id.star);
        TextView tvDesc = (TextView)view1.findViewById(R.id.desc);

        ItemData itemData = dataList.get(i);
        Glide.with(context).load(itemData.getImgid()).apply(new RequestOptions().circleCrop()).into(ivProfile); // 내 프로필 사진 띄움
        tvNickname.setText(itemData.getNn());
        if(itemData.getSn() != (int)itemData.getSn()) rbStar.setNumStars((int)itemData.getSn() + 1); // 0.5단위의 평점은 별을 하나 더 늘린다
        else rbStar.setNumStars((int)itemData.getSn());
        rbStar.setRating(itemData.getSn());
        tvDesc.setText(itemData.getDesc());

        return view1;
    }
}
