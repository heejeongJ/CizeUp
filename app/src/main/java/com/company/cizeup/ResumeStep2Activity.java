package com.company.cizeup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;

public class ResumeStep2Activity extends BaseActivity {

    private EditText techStackEditText;                 // 기술 스택
    private EditText githubEditText;                    // 깃허브 링크
    private EditText certificateEditText;               // 자격증 및 수료
    private Button beforeButton,nextButton;                          // 다음 버튼
    private String userName;                            // 사용자 이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_resume_step2);
        setupBottomNavigation();

        // View 초기화
        techStackEditText = findViewById(R.id.tech_stack);
        githubEditText = findViewById(R.id.github);
        certificateEditText = findViewById(R.id.certificate);
        beforeButton = findViewById(R.id.before_button);
        nextButton = findViewById(R.id.next_button);

        // 이전 화면에서 전달된 데이터 받아오기
        Intent intent = getIntent();
        userName = intent.getStringExtra("USER_NAME");
        String desiredJob = intent.getStringExtra("DESIRED_JOB");
        String contact = intent.getStringExtra("CONTACT");
        String education = intent.getStringExtra("EDUCATION");


        // 이력서 작성 화면 이동 버튼
        // 이전 버튼 클릭 리스너 설정
        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력된 데이터 가져오기
                String techStack = techStackEditText.getText().toString().trim();
                String github = githubEditText.getText().toString().trim();
                String certificate = certificateEditText.getText().toString().trim();

                // 이전 페이지에 정보 전달
                Intent intent = new Intent(ResumeStep2Activity.this, ResumeStep1Activity.class);
                intent.putExtra("USER_NAME", userName);  // 사용자 이름 전달
                intent.putExtra("DESIRED_JOB", desiredJob);
                intent.putExtra("CONTACT", contact);
                intent.putExtra("EDUCATION", education);
                intent.putExtra("TECH_STACK", techStack);
                intent.putExtra("GITHUB", github);
                intent.putExtra("CERTIFICATE", certificate);

                startActivity(intent);
            }
        });
        // 다음 버튼 클릭 리스너 설정
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력된 데이터 가져오기
                String techStack = techStackEditText.getText().toString().trim();
                String github = githubEditText.getText().toString().trim();
                String certificate = certificateEditText.getText().toString().trim();

                // 다음 페이지에 정보 전달
                Intent intent = new Intent(ResumeStep2Activity.this, ResumeStep3Activity.class);
                intent.putExtra("USER_NAME", userName);  // 사용자 이름 전달
                intent.putExtra("DESIRED_JOB", desiredJob);
                intent.putExtra("CONTACT", contact);
                intent.putExtra("EDUCATION", education);
                intent.putExtra("TECH_STACK", techStack);
                intent.putExtra("GITHUB", github);
                intent.putExtra("CERTIFICATE", certificate);

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
                    startActivity(new Intent(ResumeStep2Activity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.nav_interview) {
                    startActivity(new Intent(ResumeStep2Activity.this, InterviewActivity.class));
                    return true;
                } else if (itemId == R.id.nav_resume) {
                    startActivity(new Intent(ResumeStep2Activity.this, ApplicationActivity.class));
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
