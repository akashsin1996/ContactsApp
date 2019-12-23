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

public class CallActivity extends AppCompatActivity {

    private EditText et_type;
    private Button btn_1;
    private TextView tv_view;
    private Button btn_del;
    private static final int MY_PERMISSIONS_REQUEST_SEND_CALL =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        et_type=findViewById(R.id.et_type);
        btn_1=findViewById(R.id.btn_1);
        tv_view= findViewById(R.id.tv_view);
        btn_del= findViewById(R.id.btn_del);

       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBackPressed();
            }});
        toolbar.setTitle("Call");
*/

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




    }


    public void buttonClickEvent(View v) {
        String phoneNo = et_type.getText().toString();
        try {

            switch (v.getId()) {
                case R.id.btnAterisk:
                    tv_view.setText("");
                    phoneNo += "*";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnHash:
                    tv_view.setText("");
                    phoneNo += "#";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnZero:
                    tv_view.setText("");
                    phoneNo += "0";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnOne:
                    tv_view.setText("");
                    phoneNo += "1";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnTwo:
                    tv_view.setText("");
                    phoneNo += "2";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnThree:
                    tv_view.setText("");
                    phoneNo += "3";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnFour:
                    tv_view.setText("");
                    phoneNo += "4";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnFive:
                    tv_view.setText("");
                    phoneNo += "5";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnSix:
                    tv_view.setText("");
                    phoneNo += "6";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnSeven:
                    tv_view.setText("");
                    phoneNo += "7";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnEight:
                    tv_view.setText("");
                    phoneNo += "8";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btnNine:
                    tv_view.setText("");
                    phoneNo += "9";
                    et_type.setText(phoneNo);
                    break;
                case R.id.btn_del:
                    tv_view.setText("");
                    if (phoneNo != null && phoneNo.length() > 0) {
                        phoneNo = phoneNo.substring(0, phoneNo.length() - 1);
                    }

                    et_type.setText(phoneNo);
                    break;

                case R.id.btn_1:
                    if (phoneNo.trim().equals("")) {
                        tv_view.setText("Please enter a number to call on!");
                    } else {
                        Boolean isHash = false;
                        if (phoneNo.subSequence(phoneNo.length() - 1, phoneNo.length()).equals("#")) {
                            phoneNo = phoneNo.substring(0, phoneNo.length() - 1);
                            if(isPermissionGranted()) {
                                call_action();


                            }
                        } else {
                            if(isPermissionGranted()) {
                                call_action();


                            }
                        }
                    }
                    break;
            }

        } catch (Exception ex) {

        }
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
    private void call_action() {
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









