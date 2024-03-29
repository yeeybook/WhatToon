package com.example.yeeybook.whattoon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yeeybook.whattoon.Model.UserModel;
import com.example.yeeybook.whattoon.Rating.RatingActivity1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class JoinActivity extends AppCompatActivity {

    private EditText EmailJoin;
    private EditText PwdJoin;
    private Button Btn;
    private EditText NameJoin;
    private RadioGroup RadioGroup;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        EmailJoin = (EditText) findViewById(R.id.EmailTxt);
        PwdJoin = (EditText) findViewById(R.id.PasswordTxt);
        NameJoin=(EditText)findViewById(R.id.NameTxt);
        RadioGroup = (RadioGroup) findViewById(R.id.radioGroupGender);
        Btn = (Button) findViewById(R.id.JoinBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = RadioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);

                final String gender = rb.getText().toString();
                final String email = EmailJoin.getText().toString().trim();
                final String pwd = PwdJoin.getText().toString().trim();
                final String name = NameJoin.getText().toString().trim();
                //공백인 부분을 제거하고 보여주는 trim();

                firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    } catch(FirebaseAuthWeakPasswordException e) {
                                        PwdJoin.setError(getString(R.string.error_weak_password));
                                        PwdJoin.requestFocus();
                                    } catch(FirebaseAuthInvalidCredentialsException e) {
                                        EmailJoin.setError(getString(R.string.error_invalid_email));
                                        EmailJoin.requestFocus();
                                    } catch(FirebaseAuthUserCollisionException e) {
                                        EmailJoin.setError(getString(R.string.error_user_exists));
                                        EmailJoin.requestFocus();
                                    } catch(Exception e) {
                                        Log.e("TAG", e.getMessage());
                                    }
                                }else{
                                    imageUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.default_profile); // 기본 이미지 uri 저장
                                    final String uid = firebaseAuth.getCurrentUser().getUid();
                                    FirebaseStorage.getInstance().getReference("UserImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) { // storage에 이미지 업로드 성공했는지 안했는지 판단
                                            FirebaseStorage.getInstance().getReference("UserImages").child(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    UserModel userModel = new UserModel();
                                                    userModel.uid = uid;
                                                    userModel.email = email;
                                                    userModel.name = name;
                                                    userModel.gender = gender;
                                                    userModel.profileImgUrl = uri.toString();

                                                    FirebaseDatabase.getInstance().getReference("Users").child(uid).setValue(userModel);

                                                    Intent intent = new Intent(getApplicationContext(), RatingActivity1.class);
                                                    startActivity(intent);
                                                    finish();
                                                    Toast.makeText(JoinActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            });

//                                            imageUrl = task.getResult().getUploadSessionUri().toString(); // storage에 업로드 된 이미지 주소

                                        }
                                    });

//                                    //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
//                                    HashMap<Object,String> hashMap = new HashMap<>();
//
//                                    hashMap.put("email",email);
//                                    hashMap.put("uid",uid);
//                                    hashMap.put("gender",gender);
//                                    hashMap.put("name",name);
////                                    hashMap.put("profileImgUrl", )
//
//                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                    DatabaseReference reference = database.getReference("Users");
//                                    reference.child(uid).setValue(hashMap);

//                                    HashMap<Object,String> hashMap2 = new HashMap<>();
//                                    hashMap2.put("webtoonId","0");
//                                    hashMap2.put("title","0");
//                                    hashMap2.put("rate","0");
//                                    database.getReference("Users/"+uid+"/Ratings").setValue(hashMap2);

//                                    firebaseAuth.signOut(); //자동적으로 로그인 된 상태라서 로그아웃 시켜주는 코드 임시로 넣음

//                                    Intent intent = new Intent(getApplicationContext(), RatingActivity1.class);
//                                    startActivity(intent);
//                                    finish();
//                                    Toast.makeText(JoinActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}