package com.example.shoto.passwordmanager;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String PASSWORDS_TABLE_NAME = "passwords";
    public static final String PASSWORDS_COLUMN_ID = "id";
    public static final String PASSWORDS_COLUMN_ACCOUNT = "account";
    public static final String PASSWORDS_COLUMN_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table passwords " +
                        "(id integer primary key, account text,password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS passwords");
        onCreate(db);
    }

    public boolean insertPassword (String account, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", account);
        contentValues.put("password", password);
        db.insert("passwords", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from passwords where id="+id+"", null );
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, PASSWORDS_TABLE_NAME);
    }

    public boolean updatePassword (String account, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", account);
        contentValues.put("password", password);
//        db.update("passwords", contentValues, "id = ? ", new String[] { Integer.toString(id) } );

        Cursor res =  db.rawQuery( "update passwords set password ='"+password+"' where account ='"+account+"'", null );
        res.moveToFirst();
        res.close();

        return true;
    }

    public void deleteAccount (String account) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("delete from passwords where account ='"+account+"'", null);
        res.moveToFirst();
        res.close();
    }

    public ArrayList<String> getAllAccounts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from passwords", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(PASSWORDS_COLUMN_ACCOUNT)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public String getPassword(String account){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select password from passwords where account = '"+account+"'", null);
        String s;
        c.moveToNext();
        s = c.getString(c.getColumnIndex(PASSWORDS_COLUMN_PASSWORD));

        c.close();
        System.out.println(s);
        return s;
    }
}
