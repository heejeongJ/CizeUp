package com.company.cizeup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeTextView, iconTextView;
    private Button btn_Interview, btn_Resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        iconTextView = findViewById(R.id.iconTextView);
        btn_Interview = findViewById(R.id.btn_practice_interview);
        btn_Resume = findViewById(R.id.btn_prepare_resume);

        // 사용자 환영 멘트 & Cizz 멘트
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");

        if (userName != null && !userName.isEmpty()) {
            String welcomeMessage = "환영합니다, " + userName + "님.";
            welcomeTextView.setText(welcomeMessage);

            String icontext = "🧙 반가워요! 저는 " + userName + "님을 취업으로 인도할 Cizz예요!";
            iconTextView.setText(icontext);
        }

        // 버튼 별 화면 이동
        // 음성 연습 화면 이동
        btn_Interview.setOnClickListener(view -> {
            Intent practiceIntent = new Intent(MainActivity.this, InterviewActivity.class);
            startActivity(practiceIntent);
        });

        // 지원서 작성 화면 이동
        btn_Resume.setOnClickListener(view -> {
            Intent resumeIntent = new Intent(MainActivity.this, ResumeActivity.class);
            startActivity(resumeIntent);
        });

        // 하단 bottom navigator bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_interview) {
                    startActivity(new Intent(MainActivity.this, InterviewActivity.class));
                    return true;
                } else if (itemId == R.id.nav_resume) {
                    startActivity(new Intent(MainActivity.this, ResumeActivity.class));
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    // 프로필 화면으로 이동
                    // startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
