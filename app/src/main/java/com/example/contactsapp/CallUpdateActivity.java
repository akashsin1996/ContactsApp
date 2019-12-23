package com.example.contactsapp;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactsapp.Database.DBHelper;

public class CallUpdateActivity extends AppCompatActivity {

    private EditText et_type;
    private Button btn_call;
    private static final int MY_PERMISSIONS_REQUEST_SEND_CALL =0 ;
    private DBHelper dbHelper;

    private ContactList contactList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_update);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBackPressed();
            }});
        toolbar.setTitle("Call");



        et_type = findViewById(R.id.et_type);
        btn_call = findViewById(R.id.btn_call);


        dbHelper = new DBHelper(this);

        Intent intent1 = getIntent();
        contactList = (ContactList) intent1.getSerializableExtra("contactList");


        if (contactList != null) {
            et_type.setText(contactList.getNum());

        }




        btn_call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isPermissionGranted()) {
                    call_action();


                }


              }

        });

    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    private void call_action () {
        String number = et_type.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        // Toast.makeText(CallActivity.this, "12" + number, Toast.LENGTH_SHORT).show();
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }
    }
}
