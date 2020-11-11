package com.example.yeeybook.whattoon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar LoginPb;
    private Button join, login;
    private EditText email_login, pwd_login;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener; // 로그인이 됐는지 안됐는지 체크해주는 부분(로그인 유지 위해)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginPb = findViewById(R.id.LoginPb); // 로그인 작동중임을 알려주는 프로그레스 바
        join = (Button) findViewById(R.id.JoinBtn);
        login = (Button) findViewById(R.id.LoginBtn);
        email_login = (EditText) findViewById(R.id.EmailTxt);
        pwd_login = (EditText) findViewById(R.id.PasswordTxt);
        firebaseAuth = firebaseAuth.getInstance(); // firebaseAuth의 인스턴스를 가져옴

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPb.setVisibility(View.VISIBLE); // 로그인 작동중임을 알려주는 프로그레스 바 나타냄
                loginEvent();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() { // 로그인 인터페이스 리스너(로그인 됐는지를 확인해주는 부분)
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){ // 유저가 있을 때(로그인)
                    Intent intent = new Intent(LoginActivity.this, BottomActivity.class);
                    startActivity(intent);
                    finish(); // 로그인 창 닫음
                    LoginPb.setVisibility(View.GONE); // 로그인 성공하면 프로그레스 바 숨김
                }
            }
        };
    }//sdklflsd

    void loginEvent(){
        String email = email_login.getText().toString().trim(); // String형 변수 email.pwd(edittext에서 받오는 값)으로 로그인하는것
        String pwd = pwd_login.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){ //로그인이 실패했을때만 작동
                    LoginPb.setVisibility(View.GONE); // 로그인 실패하면 프로그레스 바 숨김
                    Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치하지 않습니다.\n다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() { // onCreate() 다음에 실행됨
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() { // 뒤로가기 누르면 실행됨
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
