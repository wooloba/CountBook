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
import android.widget.ArrayAdapter;
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
 *add count activity where user can add count
 *
 * @author Yaozhi
 * @version 1.0
 * @see Count
 * @see MainActivity
 * @since 1.0
 */

public class addCountActivity extends AppCompatActivity {
    public static final String FILENAME = "file.sav";
    private ArrayList<Count> counters = new ArrayList<Count>();


    /**
     * check user's input and add information
     */
    private void checkInput(){

        /*
        relate Id in xml file to java file
         */
        final EditText nameText = (EditText) findViewById(R.id.name_edit);          //name
        final EditText valueText = (EditText) findViewById(R.id.curValue_edit);     //current value and initial value
        final EditText commentText = (EditText) findViewById(R.id.comment_edit);    //comment

        /*
        get count's information
         */
        final String name = nameText.getText().toString();                          //name
        final int value = Integer.parseInt(valueText.getText().toString());         //current value and initial value
        final String comment = commentText.getText().toString();                    //comment


        /*
        validation check
         */

        if(!name.isEmpty()&& value>=0&&!comment.isEmpty()){
            counters.add(new Count(name, value, comment));          //add new count with comment

        } else if(!name.isEmpty()&& value>=0&& comment.isEmpty()){
            counters.add(new Count(name, value, ""));               //add new count without comment

        } else {
            finish();

        }
    }
    /** Called when the activity is first created.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_count);

        Intent intent = getIntent();
        loadFromFile();

        final Button okButton = (Button) findViewById(R.id.okButton);   //get ok button

        /*
        wait when user tap ok
        then check user's input
        and save info
         */
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkInput();
                saveInFile();
                finish();
                }
            }
        );
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
     *save information into file
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
