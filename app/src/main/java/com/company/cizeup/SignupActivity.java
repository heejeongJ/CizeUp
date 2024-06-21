package com.company.cizeup;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText nameEd, emailEd, passwordEd;
    private Button signButton;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        nameEd = findViewById(R.id.name);
        emailEd = findViewById(R.id.email);
        passwordEd = findViewById(R.id.password);
        signButton = findViewById(R.id.signup_button);
        dbManager = new DBManager(this);


        // 회원 가입 버튼 누를 경우, 회원 정보 등록
        signButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                registerUser();
            }
        });


        // [Login] text 누르면, 로그인 화면으로 이동
        findViewById(R.id.login_moveTo).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    public void registerUser(){
        String name = nameEd.getText().toString().trim();
        String email = emailEd.getText().toString().trim();
        String password = passwordEd.getText().toString().trim();

        // 사용자 정보 모두 기입하지 않으면 toast message
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(SignupActivity.this, "모든 정보를 작성해 주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        //이메일 중복 체크 (이메일 primary key)
        Cursor cursor = dbManager.getUser(email);
        if (cursor != null && cursor.getCount() > 0){
            Toast.makeText(SignupActivity.this, "해당 이메일은 이미 회원 정보가 존재합니다.", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        // DB에 회원 정보 ADD
        dbManager.addUser(email, name, password);
        Toast.makeText(SignupActivity.this, "회원 가입이 성공적으로 완료되었습니다!", Toast.LENGTH_SHORT).show();

        // 회원가입 후 로그인 화면으로 이동
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }
}
