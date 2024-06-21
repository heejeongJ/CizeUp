package com.company.cizeup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Check if the BottomNavigationView is not null before setting the listener
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_home) {
                        startActivity(new Intent(BaseActivity.this, MainActivity.class));
                        return true;
                    } else if (itemId == R.id.nav_interview) {
                        startActivity(new Intent(BaseActivity.this, InterviewActivity.class));
                        return true;
                    } else if (itemId == R.id.nav_resume) {
                        startActivity(new Intent(BaseActivity.this, ApplicationActivity.class));
                        return true;
                    } else if (itemId == R.id.nav_profile) {
                        // 프로필 화면으로 이동
                        // startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
                        return true;
                    }
                    return false;
                }
            });
        } else {
            // Log or handle the error as needed
            System.out.println("BottomNavigationView is null");
        }
    }
}
