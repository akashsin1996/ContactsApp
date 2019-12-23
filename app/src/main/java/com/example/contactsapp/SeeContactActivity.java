package com.example.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.contactsapp.Database.DBHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class SeeContactActivity extends AppCompatActivity {

    private RecyclerView mRecylerview;
    private RecyclerView myRecylerview;
    private ArrayList<ContactList> contactLists;
//    private boolean ascending=true;
    HashMap<String, Integer> mapIndex;
    private ContactListAdapter adapter;
    private DBHelper dbHelper;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button btn_create;
    private Button btn_call;




    public SeeContactActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_contact);

        btn_create= findViewById(R.id.btn_create);
        btn_call= findViewById(R.id.btn_call);






        mRecylerview = findViewById(R.id.mRecylerview);
  //      myRecylerview = findViewById(R.id.myRecylerview);  /*use for data */

        mRecylerview.setHasFixedSize(true);
 //       myRecylerview.setHasFixedSize(true);               /*use for data */




        RecyclerView.LayoutManager firstLayoutManager = new LinearLayoutManager(this);
        mRecylerview.setLayoutManager(firstLayoutManager);



//        RecyclerView.LayoutManager secondLayoutManager = new LinearLayoutManager(this);
//        myRecylerview.setLayoutManager(secondLayoutManager);

        dbHelper = new DBHelper(this);


        contactLists = new ArrayList<>();
        contactLists = dbHelper.getContact();


        mapIndex = calculateIndexesForName(contactLists);
        mAdapter = new ContactListAdapter(contactLists, mapIndex,this);

        mRecylerview.setAdapter(mAdapter);

        FastScrollRecyclerViewItemDecoration decoration = new FastScrollRecyclerViewItemDecoration(this);
        mRecylerview.addItemDecoration(decoration);
        mRecylerview.setItemAnimator(new DefaultItemAnimator());




        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ContactSaveActivity.class);
                startActivity(intent);
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CallActivity.class);
                startActivity(intent);
            }
        });

    }



    private HashMap<String, Integer> calculateIndexesForName(ArrayList<ContactList> fname){
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i<fname.size(); i++){
            String name = fname.get(i).fname;
            String index = name.substring(0,1);
            index = index.toUpperCase();

            if (!mapIndex.containsKey(index)) {
                mapIndex.put(index, i);
                //fname.get(i).getFname().charAt(0);

            }
        }

        return mapIndex;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    private void sortData(boolean ascending) {
//        Collections.sort(contactLists);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onResume() {
        super.onResume();


        ContactListAdapter adapter = new ContactListAdapter(contactLists, mapIndex,this );
//        myRecylerview.setAdapter(adapter);



        mRecylerview.setAdapter(adapter);




    }
}


