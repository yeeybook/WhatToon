package com.example.yeeybook.whattoon.naver;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yeeybook.whattoon.CustomAdapter;
import com.example.yeeybook.whattoon.ItemObject;
import com.example.yeeybook.whattoon.R;
import com.example.yeeybook.whattoon.WebtoonSample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Tab_Frag3 extends Fragment {

    private View view;

    private GridView gv;



    //프래그먼트 상태 저장
    public static Tab_Frag3 newInstance() {

        Tab_Frag3 tab_frag3=new Tab_Frag3();
        return tab_frag3    ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_frag3,container,false);
        readWebtoonData();

        //gridview reference

        gv=(GridView)view.findViewById(R.id.gridview3);

        List<ItemObject> allItems = getAllItemObject();
        CustomAdapter customAdapter = new CustomAdapter(getActivity(),allItems);
        //adapter
        //gv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,rankwebtoon));
        gv.setAdapter(customAdapter);
        //Item clicks
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(),rankwebtoon[i], Toast.LENGTH_SHORT).show();
                //클릭했을때
                Toast.makeText(getActivity(),"position: "+i,Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }
    private List<WebtoonSample> webtoonSamples= new ArrayList<>();
    private List<ItemObject> items= new ArrayList<>();

    private void readWebtoonData() {
        InputStream is;
        is = getResources().openRawResource(R.raw.naver_tuesday);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line="";


        try {
            //step over headers
            reader.readLine();
            int i=0;
            while((line = reader.readLine())!=null){
                Log.d("Myactivity","Line: "+line);
                //split by ','
                String[] tokens = line.split(",");

                //read the data
                WebtoonSample sample = new WebtoonSample();
                sample.setId(Integer.parseInt(tokens[0]));
                sample.setTitle(tokens[1]);
                sample.setAuthor(tokens[2]);
                webtoonSamples.add(sample);
                items.add(new ItemObject(sample.getTitle(),sample.getId()));
                Log.d("MyActivity","Just created: "+sample);

            }
        }
        catch (Exception e){
            Log.v("MyActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }




    }

    private List<ItemObject> getAllItemObject(){
        ItemObject itemObject=null;

        return items;
    }
}
