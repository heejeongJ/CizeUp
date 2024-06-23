package com.company.cizeup;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResumeStep4Activity extends BaseActivity {

    private EditText emphasisContentEditText;         // 어필/강조하고 싶은 내용
    private Button createResumeBtn;                   // 다음 버튼
    private String userName;                          // 사용자 이름
    private String email;                             // 사용자 이메일

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_resume_step4);
        setupBottomNavigation();

        // View 초기화
        emphasisContentEditText = findViewById(R.id.emphasisContent);
        createResumeBtn = findViewById(R.id.btn_resume);

        // 이전 화면에서 전달된 데이터 받아오기
        Intent intent = getIntent();
        userName = intent.getStringExtra("USER_NAME");
        String desiredJob = intent.getStringExtra("DESIRED_JOB");
        String contact = intent.getStringExtra("CONTACT");      // 전화번호
        String education = intent.getStringExtra("EDUCATION");
        String techStack = intent.getStringExtra("TECH_STACK");
        String github = intent.getStringExtra("GITHUB");
        String certificate = intent.getStringExtra("CERTIFICATE");
        String career = intent.getStringExtra("CAREER");
        String project = intent.getStringExtra("PROJECT");

        // 이메일을 데이터베이스에서 가져오기
        email = getEmailFromDatabase(userName);

        // 다음 버튼 클릭 리스너 설정
        createResumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력된 데이터 가져오기
                String emphasisContent = emphasisContentEditText.getText().toString().trim();

                // GPT-3 API 요청 생성 및 전송
                sendRequestToGPT(userName, desiredJob, email, contact, education, techStack, github, certificate, career, project, emphasisContent);
            }
        });

        // 하단 bottom navigator bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(ResumeStep4Activity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.nav_interview) {
                    startActivity(new Intent(ResumeStep4Activity.this, InterviewActivity.class));
                    return true;
                } else if (itemId == R.id.nav_resume) {
                    startActivity(new Intent(ResumeStep4Activity.this, ApplicationActivity.class));
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    // 프로필 화면으로 이동
                    // startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });

        // 네트워크 작업을 메인 스레드에서 허용 (임시, 나중에 별도의 스레드로 이동 예정)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    // 이메일 가져오는 메서드
    private String getEmailFromDatabase(String userName) {
        String email = null;
        DBManager dbManager = new DBManager(this);
        Cursor cursor = dbManager.getUser(userName);

        if (cursor != null && cursor.moveToFirst()) {
            int emailColumnIndex = cursor.getColumnIndex(DBManager.getColumnEmail());
            if (emailColumnIndex != -1) {
                email = cursor.getString(emailColumnIndex);
            }
            cursor.close();
        }
        return email;
    }

    // GPT 모델에 요청 전달
    private void sendRequestToGPT(String userName, String desiredJob, String email, String contact, String education, String techStack, String github, String certificate, String career, String project, String emphasisContent) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃 시간
                .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 타임아웃 시간
                .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃 시간
                .build();
        String apiKey = BuildConfig.OPENAI_API_KEY;

        String prompt = "사용자 이름: " + userName + "\n" +
                "희망 직무: " + desiredJob + "\n" +
                "이메일: " + email + "\n" +
                "전화번호: " + contact + "\n" +
                "학력 사항: " + education + "\n" +
                "기술 스택: " + techStack + "\n" +
                "깃허브 링크: " + github + "\n" +
                "자격증 및 수료: " + certificate + "\n" +
                "이력/경력: " + career + "\n" +
                "프로젝트 경험: " + project + "\n" +
                "어필/강조 내용: " + emphasisContent + "\n\n" +
                "이 정보를 바탕으로 전문적인 이력서를 작성해주세요. " +
                "희망 직무와 프로젝트 경험, 이력/경력에 포커스하여 이력서를 작성해주십시오. " +
                "이력서 양식은 다음과 같습니다: " +
                "0. AI가 생성한 소개글\n(AI가 생성한 맞춤형 소개글 내용)\n\n" +
                "1. 경력/이력\n(경력 이력 내용)\n\n" +
                "2. 프로젝트 경험\n(프로젝트 경험 내용)";

        // messages 배열을 생성합니다.
        GPTMessage[] messages = new GPTMessage[]{
                new GPTMessage("user", prompt)
        };

        String json = new Gson().toJson(new GPTChatRequest("gpt-3.5-turbo", messages, 1500));

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ResumeStep4Activity.this, "Failed to generate resume", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("API Response", responseBody); // 응답 로그 출력
                    GPTChatResponse gptResponse = new Gson().fromJson(responseBody, GPTChatResponse.class);
                    String generatedResume = gptResponse.getChoices().get(0).getMessage().getContent().trim();

                    String[] resumeParts = generatedResume.split("\n\n");
                    String aiMadeIntroduce = "";
                    String workExperience = "";
                    String projectExperience = "";

                    for (String part : resumeParts) {
                        if (part.startsWith("0. AI가 생성한 소개글")) {
                            aiMadeIntroduce = part.substring(part.indexOf('\n') + 1).trim();
                        } else if (part.startsWith("1. 경력/이력")) {
                            workExperience = part.substring(part.indexOf('\n') + 1).trim();
                        } else if (part.startsWith("2. 프로젝트 경험")) {
                            projectExperience = part.substring(part.indexOf('\n') + 1).trim();
                        }
                    }

                    saveResumeData(userName, desiredJob, email, contact, education, techStack, github, certificate, career, project, emphasisContent, aiMadeIntroduce, workExperience, projectExperience);

                    Intent intent = new Intent(ResumeStep4Activity.this, ResumeCreatedActivity.class);
                    intent.putExtra("USER_NAME", userName);
                    intent.putExtra("DESIRED_JOB", desiredJob);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("CONTACT", contact);
                    intent.putExtra("GITHUB", github);
                    intent.putExtra("AI_MADE_INTRODUCE", aiMadeIntroduce);
                    intent.putExtra("WORK_EXPERIENCE", workExperience);
                    intent.putExtra("PROJECT_EXPERIENCE", projectExperience);
                    startActivity(intent);
                } else {
                    String responseBody = response.body().string();
                    Log.e("API Error", "Response Code: " + response.code() + ", Response Body: " + responseBody); // 오류 로그 출력
                    runOnUiThread(() -> Toast.makeText(ResumeStep4Activity.this, "Failed to generate resume", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }


    // 생성된 이력서를 데이터베이스에 저장
    private void saveResumeData(String userName, String desiredJob, String email, String contact, String education, String techStack, String github, String certificate, String career, String project, String emphasisContent, String aiMadeIntroduce, String workExperience, String projectExperience) {
        DBManager dbManager = new DBManager(this);
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", userName);
        values.put("desiredJob", desiredJob);
        values.put("email", email);
        values.put("contact", contact);
        values.put("education", education);
        values.put("techStack", techStack);
        values.put("github", github);
        values.put("certificate", certificate);
        values.put("career", career);
        values.put("project", project);
        values.put("emphasisContent", emphasisContent);
        values.put("aiMadeIntroduce", aiMadeIntroduce);
        values.put("workExperience", workExperience);
        values.put("projectExperience", projectExperience);

        db.insert("resumes", null, values);
        db.close();
    }

    private static class GPTMessage {
        private String role;
        private String content;

        public GPTMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    private static class GPTChatRequest {
        private String model;
        private GPTMessage[] messages;
        private int max_tokens;

        public GPTChatRequest(String model, GPTMessage[] messages, int max_tokens) {
            this.model = model;
            this.messages = messages;
            this.max_tokens = max_tokens;
        }
    }

    private static class GPTChatResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() {
            return choices;
        }

        private static class Choice {
            private GPTChatResponse.GPTMessage message;

            public GPTChatResponse.GPTMessage getMessage() {
                return message;
            }
        }

        private static class GPTMessage {
            private String content;

            public String getContent() {
                return content;
            }
        }
    }
}
