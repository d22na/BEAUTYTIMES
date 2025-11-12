package com.example.beautytimes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "salon.db";
    private static final int DB_VERSION = 3;

    public DBHelper(Context ctx) { super(ctx, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "email TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL)");

        db.execSQL("CREATE TABLE services (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "minutes INTEGER," +
                "price REAL)");

        db.execSQL("CREATE TABLE appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "serviceId INTEGER," +
                "date TEXT," +
                "time TEXT," +
                "notes TEXT," +
                "FOREIGN KEY(userId) REFERENCES users(id)," +
                "FOREIGN KEY(serviceId) REFERENCES services(id))");

        db.execSQL("INSERT INTO services(name,minutes,price) VALUES " +
                "('Haircut',45,60.0)," +
                "('Nail Art',20,30.0)," +
                "('Blowout',40,80.0)," +
                "('Color',90,250.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS appointments");
        db.execSQL("DROP TABLE IF EXISTS services");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public long register(String name, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password", password);
        return db.insert("users", null, cv);
    }

    public long login(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT id FROM users WHERE email=? AND password=?",
                new String[]{email, password});
        long id = -1;
        if (c.moveToFirst()) id = c.getLong(0);
        c.close();
        return id;
    }

    public ArrayList<String> getServiceRows() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name||'  •  '||minutes||' min  •  SAR '||price FROM services", null);
        while (c.moveToNext()) list.add(c.getString(0));
        c.close();
        return list;
    }
    // Get service id by its name
    public long getServiceIdByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM services WHERE name = ?", new String[]{name});
        long id = -1;
        if (c.moveToFirst()) id = c.getLong(0);
        c.close();
        return id;
    }

    // Insert appointment
    public long insertAppointment(long userId, long serviceId, String date, String time, String notes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userId", userId);
        cv.put("serviceId", serviceId);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("notes", notes);
        return db.insert("appointments", null, cv);
    }
    public java.util.ArrayList<String> getBookingsForUser(long userId) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT s.name, a.date, a.time " +
                "FROM appointments a JOIN services s ON a.serviceId = s.id " +
                "WHERE a.userId = ? ORDER BY a.date, a.time";
        android.database.Cursor c = db.rawQuery(sql, new String[]{String.valueOf(userId)});
        while (c.moveToNext()) {
            String name = c.getString(0);
            String date = c.getString(1);
            String time = c.getString(2);
            list.add(name + " • " + date + " • " + time);
        }
        c.close();
        return list;
    }

}
