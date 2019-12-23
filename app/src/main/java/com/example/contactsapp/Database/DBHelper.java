package com.example.contactsapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.contactsapp.ContactList;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "db_Contact";
    private static int DB_VERSION = 1;
//    private Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
  //      this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_contact_user = "CREATE TABLE IF NOT EXISTS " + DBContract.TABLE_CONTACT_USER + "("
                + DBContract.CONTACT_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBContract.CONTACT_FNAME + " TEXT,"
                + DBContract.CONTACT_LNAME + " TEXT,"
                + DBContract.CONTACT_NUM + " TEXT,"
                + DBContract.CONTACT_EMAIL + " TEXT,"
                + DBContract.CONTACT_WORK + " TEXT,"
                + DBContract.CONTACT_STREET + " TEXT,"
                + DBContract.CONTACT_CITY + " TEXT,"
                + DBContract.CONTACT_STATE+ " TEXT,"
                + DBContract.CONTACT_POSTAL + " TEXT,"
                + DBContract.CONTACT_COUNTRY + " TEXT,"
                + DBContract.CONTACT_IMAGE + " TEXT,"
                + DBContract.CONTACT_WEBSITE + " TEXT" +
                ")";


        db.execSQL(TABLE_contact_user);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public boolean insertContact(String fname, String lname, String num, String email, String work,String street,String city,String state,String postal,String country,String website,byte[] imageInByte) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBContract.CONTACT_FNAME, fname);
        cv.put(DBContract.CONTACT_LNAME, lname);
        cv.put(DBContract.CONTACT_NUM, num);
        cv.put(DBContract.CONTACT_EMAIL, email);
        cv.put(DBContract.CONTACT_WORK, work);
        cv.put(DBContract.CONTACT_STREET, street);
        cv.put(DBContract.CONTACT_CITY, city);
        cv.put(DBContract.CONTACT_STATE, state);
        cv.put(DBContract.CONTACT_POSTAL, postal);
        cv.put(DBContract.CONTACT_COUNTRY, country);
        cv.put(DBContract.CONTACT_WEBSITE, website);
        cv.put(DBContract.CONTACT_IMAGE, imageInByte);

        return (int) database.insert(DBContract.TABLE_CONTACT_USER, null, cv) > 0;

    }

    public ArrayList<ContactList> getContact(){

        ArrayList<ContactList> contactLists = new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_CONTACT_USER;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                ContactList contactList=new ContactList();
                contactList.setId(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_USER_ID)));
                contactList.setFname(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_FNAME)));
                contactList.setLname(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_LNAME)));
                contactList.setNum(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_NUM)));
                contactList.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_EMAIL)));
                contactList.setWork(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_WORK)));

                contactList.setStreet(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_STREET)));
                contactList.setCity(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_CITY)));
                contactList.setState(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_STATE)));
                contactList.setPostal(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_POSTAL)));
                contactList.setCountry(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_COUNTRY)));
                contactList.setWebsite(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_WEBSITE)));
                contactList.setImageInByte(cursor.getBlob(cursor.getColumnIndex(DBContract.CONTACT_IMAGE)));


                contactLists.add(contactList);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return contactLists;
    }


    public boolean updateContact(String id, String strFNAME, String strLName, String strNUM, String strEmail, String strWork, String strCity, String strState, String strPostal, String strCountry, String strWebsite,byte[] imageInByte) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBContract.CONTACT_FNAME,strFNAME);
        cv.put(DBContract.CONTACT_LNAME,strLName);
        cv.put(DBContract.CONTACT_NUM,strNUM);
        cv.put(DBContract.CONTACT_EMAIL,strEmail);
        cv.put(DBContract.CONTACT_WORK,strWork);
        cv.put(DBContract.CONTACT_CITY,strCity);
        cv.put(DBContract.CONTACT_STATE,strState);
        cv.put(DBContract.CONTACT_POSTAL,strPostal);
        cv.put(DBContract.CONTACT_COUNTRY,strCountry);
        cv.put(DBContract.CONTACT_WEBSITE,strWebsite);
        cv.put(DBContract.CONTACT_IMAGE,imageInByte);

        String whereby = DBContract.CONTACT_USER_ID+"=?";
        String[] whereArg =new String[] {String.valueOf(id)};

        return database.update(DBContract.TABLE_CONTACT_USER,cv,whereby,whereArg)>0;
    }





  /*  public ArrayList<ContactList> getFirstWord(){


        ArrayList<ContactList> contactLists = new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_CONTACT_USER+" where "+DBContract.CONTACT_FNAME+" = '"+"fname"+"'";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                ContactList contactList =new ContactList();

                contactList.setId(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_USER_ID)));
                contactList.setFname(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_FNAME)));
                contactList.setLname(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_LNAME)));
                contactList.setNum(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_NUM)));
                contactList.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_EMAIL)));
                contactList.setWork(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_WORK)));

                contactList.setStreet(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_STREET)));
                contactList.setCity(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_CITY)));
                contactList.setState(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_STATE)));
                contactList.setPostal(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_POSTAL)));
                contactList.setCountry(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_COUNTRY)));
                contactList.setWebsite(cursor.getString(cursor.getColumnIndex(DBContract.CONTACT_WEBSITE)));
                contactList.setImageInByte(cursor.getBlob(cursor.getColumnIndex(DBContract.CONTACT_IMAGE)));

                contactLists.add(contactList);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return contactLists;
    }*/




}