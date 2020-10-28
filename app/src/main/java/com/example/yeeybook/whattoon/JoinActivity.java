package com.example.yeeybook.whattoon;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class JoinActivity extends AppCompatActivity {

    private EditText EmailJoin;
    private EditText PwdJoin;
    private Button Btn;
    private EditText NameJoin;
    private RadioGroup RadioGroup;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


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

                                if (task.isSuccessful()) {

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String uid = user.getUid();

                                    //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                    HashMap<Object,String> hashMap = new HashMap<>();

                                    hashMap.put("email",email);
                                    hashMap.put("uid",uid);
                                    hashMap.put("gender",gender);
                                    hashMap.put("name",name);

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference reference = database.getReference("Users");
                                    reference.child(uid).setValue(hashMap);

//                                    Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                                    Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(JoinActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(JoinActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
            }
        });
    }
}