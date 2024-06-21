package com.company.cizeup;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kotlin.experimental.ExperimentalObjCName;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEd,passwordEd;
    private Button loginButton;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        emailEd = findViewById(R.id.email);
        passwordEd = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        dbManager = new DBManager(this);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginUser();
            }
        });

        findViewById(R.id.signup_moveTo).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }


    private void loginUser() {
        String email = emailEd.getText().toString().trim();
        String password = passwordEd.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "모든 정보를 작성해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dbManager.getUser(email);
        if (cursor != null && cursor.moveToFirst()) {
            int passwordColumnIndex = cursor.getColumnIndex(DBManager.getColumnPassword());
            if (passwordColumnIndex >= 0) {  // Ensure column index is valid
                String storedPassword = cursor.getString(passwordColumnIndex);

                if (storedPassword.equals(password)) {
                    Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                    // 로그인 성공 후 메인 화면으로 이동
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "타당하지 않은 이메일 또는 패스워드입니다.", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            } else {
                Toast.makeText(LoginActivity.this, "존재하지 않는 사용자입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

