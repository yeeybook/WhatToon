package com.example.yeeybook.whattoon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yeeybook.whattoon.Model.RatingModel;
import com.example.yeeybook.whattoon.Model.UserModel;
import com.example.yeeybook.whattoon.Model.WebtoonModel;
import com.example.yeeybook.whattoon.Rating.RatingActivity1;
import com.example.yeeybook.whattoon.Rating.RatingActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class Frag_My extends Fragment {

    private View view, ResultLine1, ResultLine2, ResultLine3, ResultLine4, ResultLine5;
    private MyAPI myAPI;
    private final String BASE_URL = "https://af1292babd70.ngrok.io/"; // Django 서버에서 runserver하고 ngrok.exe에서 ngrok http 8000하고 얻은 주소 넣어야 함
    private final String TAG = getClass().getSimpleName();
    private EditText nameProfileDialog;
    private Button RecommenderBtn, BackBtn, editProfileBtn, RatingBtn;
    private TextView ResultTv0, ResultTv1, ResultTv2, ResultTv3, ResultTv4, ResultTv5, LogoutTv, nameProfileTv, showRatedTv;
    private ImageView ResultImg1, ResultImg2, ResultImg3, ResultImg4, ResultImg5, imgProfile, imgProfileDialog;
    private LinearLayout RecommenderLayout, ResultLayout, ResultLl1, ResultLl2, ResultLl3, ResultLl4, ResultLl5;
    private FirebaseAuth firebaseAuth;
    final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 로그인 중인 uid
    public int i;
    public String[] titleList = {"", "", "", "", ""};
    public String[] authorList = {"", "", "", "", ""};
    public String[] platformList = {"", "", "", "", ""};
    public String userName = "";
    private static final int PICK_FROM_ALBUM = 10;
    private Uri imageUri;

    private RecyclerView mRatedRecyclerView;
    private RatedPrevAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_my, container, false);
        RecommenderBtn = view.findViewById(R.id.RecommenderBtn);BackBtn = view.findViewById(R.id.BackBtn);editProfileBtn = view.findViewById(R.id.editProfileBtn);
//        RatingBtn = view.findViewById(R.id.RatingBtn);
        RecommenderLayout = view.findViewById(R.id.RecommenderLayout);ResultLayout = view.findViewById(R.id.ResultLayout);
        ResultLl1 = view.findViewById(R.id.ResultLl1);ResultLl2 = view.findViewById(R.id.ResultLl2);ResultLl3 = view.findViewById(R.id.ResultLl3);ResultLl4 = view.findViewById(R.id.ResultLl4);ResultLl5 = view.findViewById(R.id.ResultLl5);
        ResultLine1 = view.findViewById(R.id.ResultLine1);ResultLine2 = view.findViewById(R.id.ResultLine2);ResultLine3 = view.findViewById(R.id.ResultLine3);ResultLine4 = view.findViewById(R.id.ResultLine4);ResultLine5 = view.findViewById(R.id.ResultLine5);
        ResultTv0 = view.findViewById(R.id.ResultTv0);ResultTv1 = view.findViewById(R.id.ResultTv1);ResultTv2 = view.findViewById(R.id.ResultTv2);ResultTv3 = view.findViewById(R.id.ResultTv3);ResultTv4 = view.findViewById(R.id.ResultTv4);ResultTv5 = view.findViewById(R.id.ResultTv5);
        LogoutTv = view.findViewById(R.id.LogoutTv);nameProfileTv = view.findViewById(R.id.nameProfileTv);showRatedTv = view.findViewById(R.id.showRatedTv);
        ResultImg1 = view.findViewById(R.id.ResultImg1);ResultImg2 = view.findViewById(R.id.ResultImg2);ResultImg3 = view.findViewById(R.id.ResultImg3);ResultImg4 = view.findViewById(R.id.ResultImg4);ResultImg5 = view.findViewById(R.id.ResultImg5);imgProfile = view.findViewById(R.id.imgProfile);
        imgProfile = view.findViewById(R.id.imgProfile);

        mRatedRecyclerView = view.findViewById(R.id.RatedRecyclerView); // 띄울 리사이클러뷰 바인딩

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 수평 리사이클러뷰를 위해
        mRatedRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RatedPrevAdapter();

//        RatingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent a = new Intent(getActivity().getApplicationContext(), RatingActivity1.class);
//                startActivity(a);
//            }
//        });

        showRatedTv.setOnClickListener(new View.OnClickListener() { // 내가 평가한 작품들 텍스트 클릭 시
            @Override
            public void onClick(View view) {
                BottomActivity activity = (BottomActivity)getActivity();
                activity.setFrag(4); // 숨겨져 있던 Rated_Frag로 바꾸는 함수 호출
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() { // 프로필 사진, 이름 띄움
            @Override
            public void onDataChange(@NonNull final DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    if(currentUid.equals(userModel.uid)){ // 현재 로그인 중인 사용자의 uid가 일치할 때
                        Glide.with(getContext()).load(userModel.profileImgUrl).apply(new RequestOptions().circleCrop()).into(imgProfile); // 이미지 가져와서 띄우고
                        nameProfileTv.setText(userModel.name); // 이름 가져와서 띄움

                        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Ratings").addValueEventListener(new ValueEventListener() { // 평가한 항목들 가져오기(사진만 미리보기)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int Max = 10; // 최대 10개까지만 보여줄거임
                                ArrayList<RatedPrevData> data = new ArrayList<>();
                             for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                 RatingModel ratingModel = snapshot1.getValue(RatingModel.class);
                                 if(ratingModel.rate > 0) { // 평가한 데이터만 보여줌
                                     data.add(new RatedPrevData(ratingModel.webtoonId));
                                     mAdapter.setData(data);
                                     mRatedRecyclerView.setAdapter(mAdapter);
                                     Max--;
                                 }
                                 if(Max==0) break;
                             }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileDialog(view.getContext());
            }
        });

        LogoutTv.setPaintFlags(LogoutTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // 밑줄 표시
        LogoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut(); //로그아웃 코드
                Activity activity = (Activity) getContext();
                activity.finish();
                Intent a = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(a);
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultLayout.setVisibility(View.GONE);
                RecommenderLayout.setVisibility(View.VISIBLE);
                ResultLl1.setVisibility(View.VISIBLE);
                ResultLine1.setVisibility(View.VISIBLE);
                ResultLl2.setVisibility(View.VISIBLE);
                ResultLine2.setVisibility(View.VISIBLE);
                ResultLl3.setVisibility(View.VISIBLE);
                ResultLine3.setVisibility(View.VISIBLE);
                ResultLl4.setVisibility(View.VISIBLE);
                ResultLine4.setVisibility(View.VISIBLE);
                ResultLl5.setVisibility(View.VISIBLE);
                ResultLine5.setVisibility(View.VISIBLE);
            }
        });

        RecommenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<RecommenderItem> postCall = myAPI.post_recommenders(currentUid); // uid란 이름으로 request 보낼 내용
                postCall.enqueue(new Callback<RecommenderItem>() { // API로 POST 요청 보냄
                    @Override
                    public void onResponse(Call<RecommenderItem> call, Response<RecommenderItem> response) { // 응답 받음
                        if (response.isSuccessful()) {
                            final String[] idList = {response.body().getTitle1(), response.body().getTitle2(), response.body().getTitle3(), response.body().getTitle4(), response.body().getTitle5()}; // String 형태로 id값 받아옴
                            final double[] valList = {response.body().getVal1(), response.body().getVal2(), response.body().getVal3(), response.body().getVal4(), response.body().getVal5()}; // double 형태로 val값 받아옴
                            FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                    for (DataSnapshot snapshot : datasnapshot.getChildren()){
                                        UserModel userModel = snapshot.getValue(UserModel.class);
                                        if(currentUid.equals(userModel.uid)) userName = userModel.name; // 현재 로그인 중인 사용자의 uid가 일치하면 이름 가져옴
                                    }
                                    ResultTv0.setText(userName+"님께서 좋아하실 만한 웹툰은");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            FirebaseDatabase.getInstance().getReference().child("Webtoons").addValueEventListener(new ValueEventListener() { // 파이어베이스 Webtoons항목에서 해당 id를 가진 제목, 작가, 플랫폼 가져오기 위해
                                @Override
                                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                        for (i = 0; i < 5; i++) {
                                            WebtoonModel webtoonModel = snapshot.getValue(WebtoonModel.class);
                                            if(idList[i]==null) {
                                                switch (i){
                                                    case 0:
                                                        ResultLl1.setVisibility(View.GONE);
                                                        ResultLine1.setVisibility(View.GONE);
                                                    case 1:
                                                        ResultLl2.setVisibility(View.GONE);
                                                        ResultLine2.setVisibility(View.GONE);
                                                    case 2:
                                                        ResultLl3.setVisibility(View.GONE);
                                                        ResultLine3.setVisibility(View.GONE);
                                                    case 3:
                                                        ResultLl4.setVisibility(View.GONE);
                                                        ResultLine4.setVisibility(View.GONE);
                                                    case 4:
                                                        ResultLl5.setVisibility(View.GONE);
                                                        ResultLine5.setVisibility(View.GONE);
                                                }
                                                continue;
                                            }
                                            if (idList[i].equals(String.valueOf(webtoonModel.webtoonId))) {
                                                titleList[i] = webtoonModel.title;
                                                authorList[i] = webtoonModel.author;
                                                platformList[i] = webtoonModel.platform;
                                            }
                                        }
                                    }
                                    ResultLayout.setVisibility(View.VISIBLE);
                                    RecommenderLayout.setVisibility(View.GONE);
                                    ResultTv1.setText(valList[0] + "%의 확률로\n" + authorList[0] + " 작가의\n<" + titleList[0] + ">\n-" + platformList[0] + "-");
                                    ResultTv2.setText(valList[1] + "%의 확률로\n" + authorList[1] + " 작가의\n<" + titleList[1] + ">\n-" + platformList[1] + "-");
                                    ResultTv3.setText(valList[2] + "%의 확률로\n" + authorList[2] + " 작가의\n<" + titleList[2] + ">\n-" + platformList[2] + "-");
                                    ResultTv4.setText(valList[3] + "%의 확률로\n" + authorList[3] + " 작가의\n<" + titleList[3] + ">\n-" + platformList[3] + "-");
                                    ResultTv5.setText(valList[4] + "%의 확률로\n" + authorList[4] + " 작가의\n<" + titleList[4] + ">\n-" + platformList[4] + "-");
                                    ResultImg1.setImageResource(getResources().getIdentifier("img" + idList[0], "drawable", getActivity().getPackageName()));
                                    ResultImg2.setImageResource(getResources().getIdentifier("img" + idList[1], "drawable", getActivity().getPackageName()));
                                    ResultImg3.setImageResource(getResources().getIdentifier("img" + idList[2], "drawable", getActivity().getPackageName()));
                                    ResultImg4.setImageResource(getResources().getIdentifier("img" + idList[3], "drawable", getActivity().getPackageName()));
                                    ResultImg5.setImageResource(getResources().getIdentifier("img" + idList[4], "drawable", getActivity().getPackageName()));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else Log.d(TAG, "Status Code : " + response.code());
                    }

                    @Override
                    public void onFailure(Call<RecommenderItem> call, Throwable t) {
                        Log.d(TAG, "Fail msg : " + t.getMessage());
                    }
                });
            }
        });

        initMyAPI(BASE_URL);
        return view;
    }

    void showProfileDialog(Context context){ // 다이얼로그 띄우는 함수
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_profile, null);
        imgProfileDialog = view.findViewById(R.id.imgProfileDialog);
        nameProfileDialog = view.findViewById(R.id.nameProfileDialog);
        imgProfileDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM); //여기에 모든 이벤트가 모여야 이 request code값(10)으로 내가 원하는 이벤트로 이동
            }
        });

        builder.setView(view).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(imageUri == null){ //이미지를 변경하지 않았을 경우 기본 이미지로 넘기기 위해
                    imageUri = Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.drawable.default_profile);
                }
                FirebaseStorage.getInstance().getReference().child("UserImages").child(currentUid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) { // storage에 이미지 업로드 성고했는지 판단
                        FirebaseStorage.getInstance().getReference("UserImages").child(currentUid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                UserModel userModel = new UserModel();
                                Map<String, Object> stringObjectMap = new HashMap<>();
                                userModel.profileImgUrl = uri.toString(); // storage에 업로드 된 이미지 주소
                                if(nameProfileDialog.getText().toString().getBytes().length > 0){ // null값이 아닌 경우에만 (이름을 바꿨을 경우에만)
                                    userModel.name = nameProfileDialog.getText().toString();
                                    stringObjectMap.put("name", userModel.name);
                                }
                                stringObjectMap.put("profileImgUrl", userModel.profileImgUrl);
                                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).updateChildren(stringObjectMap);
                                Toast.makeText(getContext(),"변경이 완료되었습니다", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK){
            imgProfileDialog.setImageURI(data.getData()); // 다이얼로그 이미지 바꿈
            imageUri = data.getData(); // 이미지 원본 경로 저장
        }
    }

    private void initMyAPI(String baseUrl) { // Retrofit 객체를 생성하고 이걸 이용하여 API service를 create함
        Log.d(TAG, "initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPI = retrofit.create(MyAPI.class);

    }
}

