package com.example.yeeybook.whattoon.daum;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView tabAll, tabAction, tabRomance, tabDrama, tabThriller, tabComic, tabFantasy, tabEtc;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private List<WebtoonSample> webtoonSamples= new ArrayList<>();
    private List<ItemObject> items = new ArrayList<>();
    private List<ItemObject> items2 = new ArrayList<>();
    private List<ItemObject> items3 = new ArrayList<>();
    private List<ItemObject> items4 = new ArrayList<>();
    private List<ItemObject> items5 = new ArrayList<>();
    private List<ItemObject> items6 = new ArrayList<>();
    private List<ItemObject> items7 = new ArrayList<>();
    private List<ItemObject> items8 = new ArrayList<>();

    //프래그먼트 상태 저장
    public static Tab_Frag11 newInstance() {
        Tab_Frag11 tab_frag11=new Tab_Frag11();
        return tab_frag11;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_frag1,container,false);
        tabAll = view.findViewById(R.id.tabAll);tabAction = view.findViewById(R.id.tabAction);tabRomance = view.findViewById(R.id.tabRomance);tabDrama = view.findViewById(R.id.tabDrama);
        tabThriller = view.findViewById(R.id.tabThriller);tabComic = view.findViewById(R.id.tabComic);tabFantasy = view.findViewById(R.id.tabFantasy);tabEtc = view.findViewById(R.id.tabEtc);
        gv = view.findViewById(R.id.gridview1);

        tabAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabAll.setTextColor(Color.parseColor("#1F7AE2"));tabAction.setTextColor(Color.parseColor("#808080"));
                tabRomance.setTextColor(Color.parseColor("#808080"));tabDrama.setTextColor(Color.parseColor("#808080"));
                tabThriller.setTextColor(Color.parseColor("#808080"));tabComic.setTextColor(Color.parseColor("#808080"));
                tabFantasy.setTextColor(Color.parseColor("#808080"));tabEtc.setTextColor(Color.parseColor("#808080"));
                if(getActivity()!=null) {
                    gv.setAdapter(new CustomAdapter(getActivity(), items));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                            a.putExtra("id", items.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
                            startActivity(a);
                        }
                    });
                }
            }
        });

        tabAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabAll.setTextColor(Color.parseColor("#808080"));tabAction.setTextColor(Color.parseColor("#1F7AE2"));
                tabRomance.setTextColor(Color.parseColor("#808080"));tabDrama.setTextColor(Color.parseColor("#808080"));
                tabThriller.setTextColor(Color.parseColor("#808080"));tabComic.setTextColor(Color.parseColor("#808080"));
                tabFantasy.setTextColor(Color.parseColor("#808080"));tabEtc.setTextColor(Color.parseColor("#808080"));
                if(getActivity()!=null) {
                    gv.setAdapter(new CustomAdapter(getActivity(), items2));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                            a.putExtra("id", items2.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
                            startActivity(a);
                        }
                    });
                }
            }
        });
        tabRomance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabAll.setTextColor(Color.parseColor("#808080"));tabAction.setTextColor(Color.parseColor("#808080"));
                tabRomance.setTextColor(Color.parseColor("#1F7AE2"));tabDrama.setTextColor(Color.parseColor("#808080"));
                tabThriller.setTextColor(Color.parseColor("#808080"));tabComic.setTextColor(Color.parseColor("#808080"));
                tabFantasy.setTextColor(Color.parseColor("#808080"));tabEtc.setTextColor(Color.parseColor("#808080"));
                if(getActivity()!=null) {
                    gv.setAdapter(new CustomAdapter(getActivity(), items3));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                            a.putExtra("id", items3.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
                            startActivity(a);
                        }
                    });
                }
            }
        });
        tabDrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabAll.setTextColor(Color.parseColor("#808080"));tabAction.setTextColor(Color.parseColor("#808080"));
                tabRomance.setTextColor(Color.parseColor("#808080"));tabDrama.setTextColor(Color.parseColor("#1F7AE2"));
                tabThriller.setTextColor(Color.parseColor("#808080"));tabComic.setTextColor(Color.parseColor("#808080"));
                tabFantasy.setTextColor(Color.parseColor("#808080"));tabEtc.setTextColor(Color.parseColor("#808080"));
                if(getActivity()!=null) {
                    gv.setAdapter(new CustomAdapter(getActivity(), items4));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                            a.putExtra("id", items4.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
                            startActivity(a);
                        }
                    });
                }
            }
        });
        tabThriller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabAll.setTextColor(Color.parseColor("#808080"));tabAction.setTextColor(Color.parseColor("#808080"));
                tabRomance.setTextColor(Color.parseColor("#808080"));tabDrama.setTextColor(Color.parseColor("#808080"));
                tabThriller.setTextColor(Color.parseColor("#1F7AE2"));tabComic.setTextColor(Color.parseColor("#808080"));
                tabFantasy.setTextColor(Color.parseColor("#808080"));tabEtc.setTextColor(Color.parseColor("#808080"));
                if(getActivity()!=null) {
                    gv.setAdapter(new CustomAdapter(getActivity(), items5));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                            a.putExtra("id", items5.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
                            startActivity(a);
                        }
                    });
                }
            }
        });
        tabComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabAll.setTextColor(Color.parseColor("#808080"));tabAction.setTextColor(Color.parseColor("#808080"));
                tabRomance.setTextColor(Color.parseColor("#808080"));tabDrama.setTextColor(Color.parseColor("#808080"));
                tabThriller.setTextColor(Color.parseColor("#808080"));tabComic.setTextColor(Color.parseColor("#1F7AE2"));
                tabFantasy.setTextColor(Color.parseColor("#808080"));tabEtc.setTextColor(Color.parseColor("#808080"));
                if(getActivity()!=null) {
                    gv.setAdapter(new CustomAdapter(getActivity(), items6));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                            a.putExtra("id", items6.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
                            startActivity(a);
                        }
                    });
                }
            }
        });
        tabFantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabAll.setTextColor(Color.parseColor("#808080"));tabAction.setTextColor(Color.parseColor("#808080"));
                tabRomance.setTextColor(Color.parseColor("#808080"));tabDrama.setTextColor(Color.parseColor("#808080"));
                tabThriller.setTextColor(Color.parseColor("#808080"));tabComic.setTextColor(Color.parseColor("#808080"));
                tabFantasy.setTextColor(Color.parseColor("#1F7AE2"));tabEtc.setTextColor(Color.parseColor("#808080"));
                if(getActivity()!=null) {
                    gv.setAdapter(new CustomAdapter(getActivity(), items7));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                            a.putExtra("id", items7.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
                            startActivity(a);
                        }
                    });
                }
            }
        });
        tabEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabAll.setTextColor(Color.parseColor("#808080"));tabAction.setTextColor(Color.parseColor("#808080"));
                tabRomance.setTextColor(Color.parseColor("#808080"));tabDrama.setTextColor(Color.parseColor("#808080"));
                tabThriller.setTextColor(Color.parseColor("#808080"));tabComic.setTextColor(Color.parseColor("#808080"));
                tabFantasy.setTextColor(Color.parseColor("#808080"));tabEtc.setTextColor(Color.parseColor("#1F7AE2"));
                if(getActivity()!=null) {
                    gv.setAdapter(new CustomAdapter(getActivity(), items8));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                            a.putExtra("id", items8.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
                            startActivity(a);
                        }
                    });
                }
            }
        });
        databaseReference.child("Webtoons").addValueEventListener(new ValueEventListener() { // 계산하는 코드
            JSONArray WebSort = new JSONArray(); // 웹툰 인기순 정렬하기위한 jsonarray
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
                            Websample.put("keyword",snapshot.child("keyword").getValue(String.class));
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

                    for(int i=list_cnt-1;i>=0;i--){
                        JSONObject jsonObject = WebSort.getJSONObject(i);
                        sample.setId(jsonObject.getInt("id"));
                        sample.setTitle(jsonObject.getString("title"));
                        sample.setAuthor(jsonObject.getString("author"));

                        webtoonSamples.add(sample);
                        items.add(new ItemObject(sample.getTitle(),sample.getId())); // 전체 항목
                        if(jsonObject.getString("keyword").equals("액션")) items2.add(new ItemObject(sample.getTitle(),sample.getId())); // 액션 항목
                        else if(jsonObject.getString("keyword").equals("로맨스")) items3.add(new ItemObject(sample.getTitle(),sample.getId())); // 로맨스 항목
                        else if(jsonObject.getString("keyword").equals("드라마")) items4.add(new ItemObject(sample.getTitle(),sample.getId())); // 드라마 항목
                        else if(jsonObject.getString("keyword").equals("스릴러")) items5.add(new ItemObject(sample.getTitle(),sample.getId())); // 스릴러 항목
                        else if(jsonObject.getString("keyword").equals("코믹")) items6.add(new ItemObject(sample.getTitle(),sample.getId())); // 코믹 항목
                        else if(jsonObject.getString("keyword").equals("판타지")) items7.add(new ItemObject(sample.getTitle(),sample.getId())); // 판타지 항목
                        else if(jsonObject.getString("keyword").equals("기타")) items8.add(new ItemObject(sample.getTitle(),sample.getId())); // 기타 항목
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //gridview reference
                if(getActivity()!=null) { // 디폴트로 전체 항목 보여줌
                    CustomAdapter customAdapter = new CustomAdapter(getActivity(), items);
                    gv.setAdapter(customAdapter);
                }
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent a = new Intent(getActivity().getApplicationContext(), WebtoonProfileActivity.class);
                        a.putExtra("id", items.get(i).getImageId()); // 페이지 넘길 때 id값도 전달
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

}