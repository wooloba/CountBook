/*
 *Class Name: MainActivity
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
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 *where user can edit count's information
 *
 * @author Yaozhi
 * @version 1.0
 * @see Count
 * @see MainActivity
 * @since 1.0
 */
public class editActivity extends AppCompatActivity {
    public static final String FILENAME = "file.sav";
    private ArrayList<Count> counters;
    private Count count;                    //count object
    private int position;                   //count's position

    /**
     * check user's input and update information
     */
    public void checkEdit(){

        /*
        relate Id in xml file to java file
         */
        final EditText nameText = (EditText) findViewById(R.id.name_edit);                  //count's name
        final EditText curValueText = (EditText) findViewById(R.id.curValue_edit);          //count's current value
        final EditText iniValueText = (EditText) findViewById(R.id.iniValue_edit);          //count's initial value
        final EditText commentText = (EditText) findViewById(R.id.comment_edit);            //count's comment

        /*
        get count's information
         */
        final String name = nameText.getText().toString();                                  //name
        final int curValue = Integer.parseInt(curValueText.getText().toString());           //current value
        final int iniValue = Integer.parseInt(iniValueText.getText().toString());           //initial value
        final String comment = commentText.getText().toString();                            //comment

        /*
        validation check
        and set new information
         */
        if(!name.isEmpty()&& curValue>=0&& iniValue>=0){
            count.setName(name);                //set name
            count.setCurValue(curValue);        //set current value
            count.setInitialValue(iniValue);    //set initial value
            count.setComment(comment);          //set comment
            count.setDate();                    //update date too
        } else {
            finish();
        }
    }

    /** Called when the activity is first created.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        loadFromFile();
        position = intent.getIntExtra("position", 0);
        count = counters.get(position);

        //get button ID
        final Button confirmButton = (Button) findViewById(R.id.confirmButton);

        /*
        wait for user's tap
        and update information
        go to main page
         */
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkEdit();
                saveInFile();
                finish();
            }
        });
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
        }
    }
    /**
     *saev information form file
     * edit based on lonelytwitter
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

