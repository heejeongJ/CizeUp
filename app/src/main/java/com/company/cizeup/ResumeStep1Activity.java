package com.company.cizeup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ResumeStep1Activity extends BaseActivity {

    private EditText desiredJobEditText;                // 희망 직무
    private EditText contactEditText;                   // 연락처
    private EditText educationEditText;                 // 학력
    private Button nextButton;                          // 다음 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_resume_step1);
        setupBottomNavigation();

        // View 초기화
        desiredJobEditText = findViewById(R.id.desired_job);
        contactEditText = findViewById(R.id.contact);
        educationEditText = findViewById(R.id.education);
        nextButton = findViewById(R.id.next_button);

        // 이전 화면에서 사용자의 name 받아옴
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");

        // 다음 버튼 클릭 리스너 설정
        // 이력서 작성 화면 이동 버튼
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력된 데이터 가져오기
                String desiredJob = desiredJobEditText.getText().toString().trim();
                String contact = contactEditText.getText().toString().trim();
                String education = educationEditText.getText().toString().trim();

                // 다음 페이지에 정보 전달
                Intent intent = new Intent(ResumeStep1Activity.this, ResumeStep2Activity.class);
                intent.putExtra("USER_NAME", userName);  // 사용자 이름 전달
                intent.putExtra("DESIRED_JOB", desiredJob);
                intent.putExtra("CONTACT", contact);
                intent.putExtra("EDUCATION", education);

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
                    startActivity(new Intent(ResumeStep1Activity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.nav_interview) {
                    startActivity(new Intent(ResumeStep1Activity.this, InterviewActivity.class));
                    return true;
                } else if (itemId == R.id.nav_resume) {
                    startActivity(new Intent(ResumeStep1Activity.this, ApplicationActivity.class));
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
