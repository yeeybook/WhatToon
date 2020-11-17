package com.example.yeeybook.whattoon.daum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yeeybook.whattoon.CustomAdapter;
import com.example.yeeybook.whattoon.ItemObject;
import com.example.yeeybook.whattoon.R;
import com.example.yeeybook.whattoon.WebtoonProfileActivity;
import com.example.yeeybook.whattoon.WebtoonSample;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tab_Frag11 extends Fragment {

    private View view;
    private GridView gv;
    private ArrayList<Integer> IdList = new ArrayList<Integer>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private List<WebtoonSample> webtoonSamples= new ArrayList<>();
    private List<ItemObject> items= new ArrayList<>();

    //프래그먼트 상태 저장
    public static Tab_Frag11 newInstance() {

        Tab_Frag11 tab_frag11=new Tab_Frag11();
        return tab_frag11;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_frag1,container,false);

        //readWebtoons()
        databaseReference.child("Webtoons").addValueEventListener(new ValueEventListener() {

            //웹툰 인기순 정렬하기위한 jsonarray
            JSONArray WebSort = new JSONArray();

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    if(snapshot.child("webtoonId").getValue(int.class)>372 && snapshot.child("favorite").getValue(int.class)>0){
                        JSONObject Websample = new JSONObject();
                        try {
                            Websample.put("id",snapshot.child("webtoonId").getValue(int.class));
                            Websample.put("title",snapshot.child("title").getValue(String.class));
                            Websample.put("author",snapshot.child("author").getValue(String.class));
                            Websample.put("favorite",snapshot.child("favorite").getValue(int.class));
                            WebSort.put(Websample);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    WebSort = sortJsonArray(WebSort);
                    //JsonArray --> JsonObject
                    WebtoonSample sample = new WebtoonSample();
                    int list_cnt = WebSort.length();
                    String[] getDescription = new String[list_cnt]; //decription 저장용
                    String[] getLink = new String[list_cnt]; //link 저장용
                    String[] getImageUrl = new String[list_cnt]; //imageUrl 저장용

                    for(int i=list_cnt-1;i>=0;i--){
                        JSONObject jsonObject = WebSort.getJSONObject(i);
                        sample.setId(jsonObject.getInt("id"));
                        sample.setTitle(jsonObject.getString("title"));
                        sample.setAuthor(jsonObject.getString("author"));

                        webtoonSamples.add(sample);
                        items.add(new ItemObject(sample.getTitle(),sample.getId()));
                        IdList.add(sample.getId()); // 그리드뷰로 나타내고 있는 아이디를 리스트에 저장

                        Log.d("Tab1","샘플 ::   "+sample);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //gridview reference

                gv=(GridView)view.findViewById(R.id.gridview1);

                List<ItemObject> allItems = getAllItemObject();
                CustomAdapter customAdapter = new CustomAdapter(getActivity(),allItems);
                //adapter
                gv.setAdapter(customAdapter);
                //Item clicks
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                        a.putExtra("id", IdList.get(i)); // 페이지 넘길 때 id값도 전달
                        startActivity(a);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }


    public void readWebtoonData(){

    }
    //인기순 정렬
    public static JSONArray sortJsonArray(JSONArray array) throws JSONException {
        List<JSONObject> jsons = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++) {
            jsons.add(array.getJSONObject(i));
        }
        Collections.sort(jsons, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                String lid = null;
                try {
                    lid = lhs.getString("favorite");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String rid = null;
                try {
                    rid = rhs.getString("favorite");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Here you could parse string id to integer and then compare.
                return lid.compareTo(rid);
            }
        });

        return new JSONArray(jsons);
    }

    private List<ItemObject> getAllItemObject(){
        ItemObject itemObject=null;

        return items;
    }


}
