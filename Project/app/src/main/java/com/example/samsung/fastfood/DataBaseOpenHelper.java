package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samsung.fastfood.Pojo.OrderDetail;

import java.io.ByteArrayOutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by SAMSUNG on 10/26/2017.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contact";
    private static final String DATABASE_TABLE = "contacts";
    public static final String DATABASE_TABLE_1 = "food";
    public static final String DATABASE_TABLE_2 = "buy";
    public static final String DATABASE_TABLE_3 = "feed";
    private static final int DATABASE_VERSION = 16;
    private String UID = "_id";
    private String S_UID = "s_id";

    static final String FIRST_NAME = "f_name";
    static final String LAST_NAME = "l_name";
    static final String ITEMS = "items";
    static final String PRICE = "price";
    static final String QUANTITY = "quantity";
    static final String VARIANT = "variant";
    static final String CITY = "city";
    static final String ADDRESS = "address";
    static final String PHONE = "phone";
    static final String EMAIL = "email";
    static final String D_BIRTH = "d_birth";
    static final String QUESTION = "question";
    static final String ANSWER = "answer";
    static final String PASSWORD = "password";
    static final String IMAGE = "image";
    static final String DATE = "date";
    static final String USERNAME = "username";
    static final String COMMENT = "cmt";
    static final String CREATE_TABLE = "create table contacts(_id integer primary key not null ," + " f_name text , l_name text , phone integer , d_birth integer , city text , address text , email text , password text , username text , question text , answer text);";

    static final String CREATE_TABLE_1 = "create table food(_id integer primary key not null , s_id integer ," +
            "items text,price integer,quantity integer,variant text,image text);";

    static final String CREATE_TABLE_3 = "create table feed(_id integer primary key not null , " +
            "username text,cmt text,email text);";


    static final String CREATE_TABLE_2 = "create table buy(_id integer primary key not null, username text ,date text,address text,city text);";

    private static final String DROP_TABLE = "drop table if exists contacts";
    private static final String DROP_TABLE_1 = "drop table if exists food";
    private static final String DROP_TABLE_2 = "drop table if exists buy";
    private static final String DROP_TABLE_3 = "drop table if exists feed";

    Context context;


    public DataBaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_1);
        db.execSQL(CREATE_TABLE_2);
        db.execSQL(CREATE_TABLE_3);

    }

    public void insert_feed(Feed feed){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME,feed.getUser());
        contentValues.put(COMMENT,feed.getComment());
        contentValues.put(EMAIL,feed.getEmail());
        sqLiteDatabase.insert(DATABASE_TABLE_3,null,contentValues);
    }

 /*   public void insertContacts(Contacts c) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "select * from contacts ";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int count = cursor.getCount();


        contentValues.put(UID, count);
        contentValues.put(FIRST_NAME, c.getFirstname());
        contentValues.put(LAST_NAME, c.getLastname());
        contentValues.put(PHONE, c.getPone());
        contentValues.put(CITY, c.getCity());
        contentValues.put(D_BIRTH, c.getD_birth());
        contentValues.put(QUESTION, c.getQuestion());
        contentValues.put(ANSWER, c.getAnswer());
        contentValues.put(ADDRESS, c.getAddress());
        contentValues.put(EMAIL, c.getEmail());
        contentValues.put(PASSWORD, c.getPass());
        contentValues.put(USERNAME, c.getUser());

        sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);

    }*/


    public void insertFood(Contacts c) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "select * from food";
        sqLiteDatabase.rawQuery(query, null);
        contentValues.put(ITEMS, c.getItem());
        contentValues.put(PRICE, c.getPrice());
        contentValues.put(QUANTITY, c.getQuantity());
        contentValues.put(VARIANT, c.getVariant());
        contentValues.put(S_UID, c.getS_id());
        contentValues.put(IMAGE,c.getImage());
        sqLiteDatabase.insert(DATABASE_TABLE_1, null, contentValues);
        logsOfTable(DATABASE_TABLE_1);
    }

    public void insert_proceed(Contacts contacts) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, contacts.getDate());
        contentValues.put(USERNAME,contacts.getP_user());
        contentValues.put(ADDRESS,contacts.getAddress_histroy());
        contentValues.put(CITY,contacts.getCity_history());
        sqLiteDatabase.insert(DATABASE_TABLE_2, null, contentValues);

    }

    public void updateSecretKey() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String q = "select * from " + DATABASE_TABLE_2;
        Cursor cursor = sqLiteDatabase.rawQuery(q, null);
        int last = -1;
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            last = cursor.getInt(0);
        }

        logsOfTable(DATABASE_TABLE_1);
        logsOfTable(DATABASE_TABLE_2);

        q = "select * from " + DATABASE_TABLE_1 + " WHERE s_id == -1";
        cursor = sqLiteDatabase.rawQuery(q, null);

        if (cursor.getCount() > 0) {
            q = "update " + DATABASE_TABLE_1 + " set s_id = " + last + " WHERE s_id == -1";
            sqLiteDatabase.execSQL(q);
        }

        logsOfTable(DATABASE_TABLE_1);
        logsOfTable(DATABASE_TABLE_2);



    }

    private void logsOfTable(String databaseTable1) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String q = "select * from " + databaseTable1;
        Log.e("TESTING ", databaseTable1);
        Cursor cursor = sqLiteDatabase.rawQuery(q, null);
        if (cursor.moveToFirst()) {
            int i = cursor.getColumnCount();
            do {
                for (int j = 0; j < i; j++) {
                    Log.e("TESTING",cursor.getColumnName(j)+" -----: "+cursor.getString(j));
                }
            } while (cursor.moveToNext());
        }
    }

  /*  public String check_login(String u_name) {

        SQLiteDatabase db = this.getReadableDatabase();
        String select = "select  username, password from " + DATABASE_TABLE;

        Cursor c = db.rawQuery(select, null);
        String a, b;
        b = "not found";

        if (c.moveToFirst()) {
            do {
                a = c.getString(0);

               if (a.equals(u_name)) {
                    b = c.getString(1);
                    break;
                }

            } while (c.moveToNext());
        }
        return b;
    }*/

    public void deleteFromDB(OrderDetail orderDetail) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DATABASE_TABLE_1, "_id" + " = " + orderDetail.getSecretId(), null);
    }

    public void updateFromDB(OrderDetail orderDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUANTITY, orderDetail.getQuantity());
        db.update(DATABASE_TABLE_1, contentValues, "_id" + " = " + orderDetail.getSecretId(), null);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_TABLE_1);
        db.execSQL(DROP_TABLE_2);
        db.execSQL(DROP_TABLE_3);
        onCreate(db);


    }
}
