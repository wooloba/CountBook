/*
 * Class Name: MainActivity
 *
 *  Version: 1.0
 *
 *  Date: October, 2017
 *
 * Copyright(c) 2017 Team X. CMPUT301, University of alberta - All Rights Reserved
 * You may use distribute or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact contact@abc.ca
 */
package com.example.yaozhilu.yaozhi_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *Represent a count book
 *
 * @author Yaozhi
 * @version 1.0
 * @see Count
 * @see checkInfoActivity
 * @see addCountActivity
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ListView countList;

    private ArrayList<Count> counters = new ArrayList();
    private ArrayAdapter<Count> adapter;


    /** Called when the activity is first created.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button addCount = (Button) findViewById(R.id.addCount);
        countList = (ListView) findViewById(R.id.countList);
        countList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, checkInfoActivity.class);      //create new page
                intent.putExtra("position",position);                                       //get user tap position on which counter
                startActivity(intent);                                                  //go to counter's information page
            }
        });

        //check when user tap OK
        addCount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addCountActivity.class);
                startActivity(intent);                                                  //go to page where user can input info for new counter
            }
        });

    }

    /**
     * runs when activity starts
     */
    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Count>(this, android.R.layout.simple_list_item_1, counters);
        countList.setAdapter(adapter);

        TextView generalInfo = (TextView) findViewById(R.id.generalInfo);
        generalInfo.setText("You have: " +counters.size()+" Counter(s).");  //show counter number on page
    }


    /**
     *load information form file
     * edit based on lonelytwitter
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Taken from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 2015-09-22
            Type listType = new TypeToken<ArrayList<Count>>() {}.getType();
            counters = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            counters = new ArrayList<Count>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
