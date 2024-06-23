package com.company.cizeup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResumeCreatedActivity extends BaseActivity {

    private TextView nameTextView;
    private TextView desiredJobTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView githubTextView;
    private TextView blogTextView;
    private TextView aiMadeIntroduceTextView;
    private TextView workExperienceTextView;
    private TextView projectExperienceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_result);
        setupBottomNavigation();

        // View 초기화
        nameTextView = findViewById(R.id.name);
        desiredJobTextView = findViewById(R.id.desired_job);
        emailTextView = findViewById(R.id.email);
        phoneTextView = findViewById(R.id.contact);
        githubTextView = findViewById(R.id.github);
        blogTextView = findViewById(R.id.blog);
        aiMadeIntroduceTextView = findViewById(R.id.ai_made_intoduce);
        workExperienceTextView = findViewById(R.id.work_experience);
        projectExperienceTextView = findViewById(R.id.project_experience);


        // 이전 화면에서 전달된 데이터 받아오기
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        String desiredJob = intent.getStringExtra("DESIRED_JOB");
        String contact = intent.getStringExtra("CONTACT");
        String email = intent.getStringExtra("EMAIL");
        String github = intent.getStringExtra("GITHUB");
        String aiMadeIntroduce = intent.getStringExtra("AI_MADE_INTRODUCE");
        String workExperience = intent.getStringExtra("WORK_EXPERIENCE");
        String projectExperience = intent.getStringExtra("PROJECT_EXPERIENCE");

        // 데이터 설정
        nameTextView.setText(userName);
        desiredJobTextView.setText(desiredJob);
        emailTextView.setText(email);
        phoneTextView.setText(contact);
        githubTextView.setText(github);
        aiMadeIntroduceTextView.setText(aiMadeIntroduce);
        workExperienceTextView.setText(workExperience);
        projectExperienceTextView.setText(projectExperience);

        // 하단 bottom navigator bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(ResumeCreatedActivity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.nav_interview) {
                    startActivity(new Intent(ResumeCreatedActivity.this, InterviewActivity.class));
                    return true;
                } else if (itemId == R.id.nav_resume) {
                    startActivity(new Intent(ResumeCreatedActivity.this, ApplicationActivity.class));
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
