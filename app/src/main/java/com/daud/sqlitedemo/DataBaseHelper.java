package com.daud.sqlitedemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "db_name";
    public static int DATABASE_VERSION = 1;
    public static String TABLE_1 = "my_tb";
    public static String ID = "Id";
    public static String NAME = "Name";
    public static String PHONE = "Phone";
    public static String AGE = "Age";
    public Context context;
    private String table = "CREATE TABLE " + TABLE_1 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
            + " VARCHAR,"+ PHONE + " VARCHAR," + AGE + " VARCHAR)";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertData(String name, String phone, String age){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME,name);
        cv.put(PHONE,phone);
        cv.put(AGE,age);
        db.insert(TABLE_1,null,cv);
        Toast.makeText(context, "Insert Data Successfull",Toast.LENGTH_LONG).show();
    }

    public List<ModelClass> getData(){
        List<ModelClass> List = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_1, null);

        if (cursor != null){
            while (cursor.moveToNext()){
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(NAME));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(PHONE));
                @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(AGE));

                ModelClass model = new ModelClass(id,name,phone,age);
                List.add(model);

            }
        }
        return List;
    }
    public void updateData(int id, String name, String phone, String age){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID,id);
        cv.put(NAME,name);
        cv.put(PHONE,phone);
        cv.put(AGE,age);
        db.update(TABLE_1,cv,"Id=?",new String[]{String.valueOf(id)});
        Toast.makeText(context, "Update Data Successfully",Toast.LENGTH_LONG).show();
    }
    public void deleteData(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_1,"Id=?",new String[]{String.valueOf(id)});
    }
}
