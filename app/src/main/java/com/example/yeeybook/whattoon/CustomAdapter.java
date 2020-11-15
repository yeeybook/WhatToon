package com.example.yeeybook.whattoon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<ItemObject> listStorage;
    private Context context;

    public CustomAdapter(Context context, List<ItemObject> customizedListView){
        this.context = context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder listViewHolder;
        if(view ==null){
            listViewHolder=new ViewHolder();
            view = layoutInflater.inflate(R.layout.image_toon,parent,false);
            listViewHolder.textInListView = (TextView)view.findViewById(R.id.textView);
            listViewHolder.imageInListView = (ImageView)view.findViewById(R.id.imageView);
            view.setTag(listViewHolder);
        }
        else{
            listViewHolder = (ViewHolder)view.getTag();
        }

        listViewHolder.textInListView.setText(listStorage.get(position).getContent());

       int imageResourceId = this.context.getResources().getIdentifier("img"+listStorage.get(position).getImageId(), "drawable", this.context.getPackageName()); //가져온 id에 따라 이미지 이름 다름
        listViewHolder.imageInListView.setImageResource(imageResourceId);
        return view;
        }

    static class ViewHolder {

        TextView textInListView;
        ImageView imageInListView;

    }
}