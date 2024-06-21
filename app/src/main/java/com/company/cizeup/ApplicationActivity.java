package com.company.cizeup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ApplicationActivity extends BaseActivity {

    private Button btnCreateResume, btnCreateCoverLetter;
    private TextView iconTextView;                      // Cizz 말풍선 Text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        getLayoutInflater().inflate(R.layout.application_layout, findViewById(R.id.container));
        setupBottomNavigation();*/
        setContentView(R.layout.application_layout); // setContentView로 변경
        setupBottomNavigation();

        iconTextView = findViewById(R.id.iconTextView);
        // 이동 버튼 (이력서 작성, 자기소개서 작성)
        btnCreateResume = findViewById(R.id.btn_create_resume);
        btnCreateCoverLetter = findViewById(R.id.btn_create_cover_letter);

        if (iconTextView == null || btnCreateResume == null || btnCreateCoverLetter == null) {
            throw new NullPointerException("One or more view references are null. Check the layout file for the correct ID.");
        }

        // 사용자 환영 멘트 & Cizz 멘트
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");

        if (userName != null && !userName.isEmpty()) {
            String icontext = "🧙 반가워요! 저는 " + userName + "님을 취업으로 인도할 Cizz예요!";
            iconTextView.setText(icontext);
        }




/*        // NullPointerException 방지를 위한 null 체크
        if (iconTextView == null) {
            throw new NullPointerException("iconTextView is null. Check the layout file for the correct ID.");
        }

        if (btnCreateResume == null) {
            throw new NullPointerException("btnCreateResume is null. Check the layout file for the correct ID.");
        }

        if (btnCreateCoverLetter == null) {
            throw new NullPointerException("btnCreateCoverLetter is null. Check the layout file for the correct ID.");
        }*/


        // 이력서 작성 화면 이동 버튼
        btnCreateResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplicationActivity.this, ResumeActivity.class);
                intent.putExtra("USER_NAME", userName);  // 사용자 이름 전달
                startActivity(intent);
            }
        });

        // 자기소개서 작성 화면 이동 버튼
        btnCreateCoverLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplicationActivity.this, CoverLetterActivity.class);
                intent.putExtra("USER_NAME", userName);  // 사용자 이름 전달
                startActivity(intent);
            }
        });


        // 하단 bottom navigator bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(ApplicationActivity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.nav_interview) {
                    startActivity(new Intent(ApplicationActivity.this, InterviewActivity.class));
                    return true;
                } else if (itemId == R.id.nav_resume) {
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

