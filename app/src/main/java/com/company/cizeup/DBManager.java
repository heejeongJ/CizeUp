package com.company.cizeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cizeUpDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MEMBER = "member";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";



    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void  onCreate(SQLiteDatabase db){
        // member 테이블 생성 (사용자 이메일, 이름, 비밀번호)
        try {
            String createTable = "CREATE TABLE " + TABLE_MEMBER + " (" +
                    COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT);";
            db.execSQL(createTable);
            Log.d("DBManager", "Table 'member' created successfully.");
        }catch (SQLiteException e){
            Log.d("DBManager", "Error creating table: ", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        onCreate(db);
    }

    // 사용자 추가
    public void addUser(String email, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);

        db.insert(TABLE_MEMBER, null, values);
        db.close();
    }

    // 사용자 조회
    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBER + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        return cursor;
    }

    // 열 이름 갖고 오기 위한 getter
    public static String getColumnEmail() {
        return COLUMN_EMAIL;
    }

    public static String getColumnName() {
        return COLUMN_NAME;
    }

    public static String getColumnPassword() {
        return COLUMN_PASSWORD;
    }
}

