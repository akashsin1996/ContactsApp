package com.example.contactsapp;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.contactsapp.Database.DBHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactUpdateActivity extends AppCompatActivity {
    private EditText et_fname;
    private EditText et_lname;
    private EditText et_num;
    private EditText et_email;
    private Button btn_update;

    private EditText et_work;
    private EditText et_street;
    private EditText et_city;
    private EditText et_state;
    private EditText et_postal;
    private EditText et_country;
    private EditText et_website;




    private String strFNAME;
    private String strLName;
    private String strNUM;
    private String strEmail;

    private String strWork;
    private String strStreet;
    private String strCity;
    private String strState;
    private String strPostal;
    private String strCountry;
    private String strWebsite;

    private ContactList contactList;

    private DBHelper dbHelper;
    private Pattern pattern;
    private byte[] imageInByte;
    private Button btn_upload;
    private ImageView img_view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_update);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBackPressed();
            }});
        toolbar.setTitle("Save");



        pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");

        et_fname= findViewById(R.id.et_fname);
        et_lname= findViewById(R.id.et_lname);
        et_num= findViewById(R.id.et_num);
        et_email= findViewById(R.id.et_email);

        btn_update= findViewById(R.id.btn_update);


        et_work= findViewById(R.id.et_work);
        et_street= findViewById(R.id.et_street);
        et_city= findViewById(R.id.et_city);
        et_state= findViewById(R.id.et_state);
        et_postal= findViewById(R.id.et_postal);
        et_country= findViewById(R.id.et_country);
        et_website= findViewById(R.id.et_website);

        btn_upload= findViewById(R.id.btn_upload);
        img_view= findViewById(R.id.img_view);



        dbHelper = new DBHelper(this);

        Intent intent1 =getIntent();
        contactList = (ContactList) intent1.getSerializableExtra("contactList");


        if(contactList!= null) {
        et_fname.setText(contactList.getFname());
        et_lname.setText(contactList.getLname());
        et_num.setText(contactList.getEmail());
        et_email.setText(contactList.getEmail());
        et_work.setText(contactList.getWork());
        et_street.setText(contactList.getStreet());
        et_city.setText(contactList.getCity());
        et_state.setText(contactList.getState());
        et_postal.setText(contactList.getPostal());
        et_country.setText(contactList.getCountry());
        et_website.setText(contactList.getWebsite());
            if (contactList.getImageInByte() != null) {
                img_view.setVisibility(View.VISIBLE);
                img_view.setImageBitmap(getImage(contactList.getImageInByte()));
            }
        }

        btn_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateText()) {
                    if (dbHelper.updateContact(contactList.getId(), strFNAME, strLName, strNUM, strEmail, strWork, strCity, strState, strPostal, strCountry, strWebsite,imageInByte)) {
                        Toast.makeText(ContactUpdateActivity.this, "Contact Update", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(ContactUpdateActivity.this, "failed to update", Toast.LENGTH_SHORT).show();

                }
            }
        } );



        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", "250");
                intent.putExtra("outputY", "200");
            }
        });



        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK){

            if(requestCode==10){
                assert data != null;
                Bundle extras =data.getExtras();
                if(extras!=null){
                    Bitmap yourImage =extras.getParcelable("data");
                    ByteArrayOutputStream stream= new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                    imageInByte = stream.toByteArray();
                    img_view.setImageBitmap(getImage(imageInByte));
                    img_view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private Bitmap getImage(byte[] imageInByte) {
        ByteArrayInputStream inputstream =new ByteArrayInputStream(imageInByte);
        return BitmapFactory.decodeStream(inputstream);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }


    private boolean validateText() {

        boolean isValid = false;

        strFNAME= et_fname.getText().toString();
        strLName= et_lname.getText().toString();
        strNUM= et_num.getText().toString();
        strEmail= et_email.getText().toString();


        strWork= et_work.getText().toString();
        strStreet= et_street.getText().toString();
        strCity= et_city.getText().toString();
        strState= et_state.getText().toString();
        strPostal= et_postal.getText().toString();
        strCountry= et_country.getText().toString();
        strWebsite= et_website.getText().toString();




        if(!strFNAME.isEmpty()) {
            if(!strLName.isEmpty()) {
                if (!strNUM.isEmpty()) {
                    if (!strEmail.isEmpty()) {
                        if(validateEmail(strEmail.trim())) {

                            isValid = true;
                        }else Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(this, "Email id can't be empty", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(this,"contact can't be empty",Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this,"last name no can't be empty",Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this,"first name can't be empty",Toast.LENGTH_SHORT).show();
        return isValid;
    }

    private boolean validateEmail(String trim) {
        Matcher matcher =pattern.matcher(strEmail);
        Toast.makeText(this, ""+ matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
