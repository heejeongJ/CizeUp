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

public class MainActivity extends BaseActivity {

    private TextView welcomeTextView, iconTextView;
    private Button btn_Interview, btn_Resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getLayoutInflater().inflate(R.layout.activity_main, findViewById(R.id.container));          // setContentView를 먼저 호출하여 레이아웃을 설정
        setupBottomNavigation();

        welcomeTextView = findViewById(R.id.welcomeTextView);                                       // setContentView 다음에 findViewById를 호출하여 뷰를 초기화
        iconTextView = findViewById(R.id.iconTextView);
        btn_Interview = findViewById(R.id.btn_practice_interview);
        btn_Resume = findViewById(R.id.btn_prepare_resume);

        if (welcomeTextView == null || iconTextView == null || btn_Interview == null || btn_Resume == null) {
            throw new NullPointerException("One or more view references are null. Check the layout file for the correct ID.");
        }


        // NullPointerException 방지를 위한 null 체크
        /*if (welcomeTextView == null) {
            throw new NullPointerException("welcomeTextView is null. Check the layout file for the correct ID.");
        }

        if (iconTextView == null) {
            throw new NullPointerException("iconTextView is null. Check the layout file for the correct ID.");
        }*/


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
            practiceIntent.putExtra("USER_NAME", userName);  // 사용자 이름 전달
            startActivity(practiceIntent);
        });


        // 지원서 작성 화면 이동
        btn_Resume.setOnClickListener(view -> {
            Intent resumeIntent = new Intent(MainActivity.this, ApplicationActivity.class);
            resumeIntent.putExtra("USER_NAME", userName);  // 사용자 이름 전달
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
                    startActivity(new Intent(MainActivity.this, ApplicationActivity.class));
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
