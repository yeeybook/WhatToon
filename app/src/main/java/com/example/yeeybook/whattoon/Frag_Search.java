package com.example.yeeybook.whattoon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.ImmutableSortedMapIterator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.*;

public class Frag_Search extends Fragment {

    private View view;
    DatabaseReference ref;
    ArrayList<Search_Webtoon> list = new ArrayList<>();
    RecyclerView recyclerView;
    SearchView searchView;




    //private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_search,container,false);
        ref = FirebaseDatabase.getInstance().getReference().child("Webtoons");
        SearchAdapter searchAdapter = new SearchAdapter(list);
        recyclerView = (RecyclerView)view.findViewById(R.id.result_view);
        searchView = (SearchView)view.findViewById(R.id.searchView);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if(ref !=null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        list=new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            list.add(ds.getValue(Search_Webtoon.class));
                        }
                        SearchAdapter searchAdapter = new SearchAdapter(list);
                        recyclerView.setAdapter(searchAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(),error.getMessage(), LENGTH_SHORT).show();
                }
            });
        }

        if(searchView !=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void search(String str){

        ArrayList<Search_Webtoon> myList = new ArrayList<>();
        for(Search_Webtoon object : list){
            if(object.getTitle().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
           if(object.getAuthor().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        SearchAdapter searchAdapter = new SearchAdapter(myList);
        recyclerView.setAdapter(searchAdapter);
    }
}
