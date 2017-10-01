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
import android.widget.TextView;

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
import java.util.Date;


/**
 *
 * where user can see details of a count
 *
 * @author Yaozhi
 * @version 1.0
 * @see Count
 * @see checkInfoActivity
 * @see editActivity
 * @see MainActivity
 * @since 1.0
 */
public class checkInfoActivity extends AppCompatActivity {
    public static final String FILENAME = "file.sav";
    private ArrayList<Count> counters = new ArrayList<Count>();
    private Count count;                //count object
    private int position;               //count position

    /** Called when the activity is first created.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info);
        loadFromFile();

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        count = counters.get(position);


        /*
        get this count's information
         */
        String name = count.getName();                  //get count's name
        int initial_V = count.getInitialValue();        //get count's initial value
        final int current_V = count.getValue();         //get count's current value
        String comment = count.getComment();            //get count's comment

        /*
        relate Id in xml file to java file
         */
        TextView name_info = (TextView) findViewById(R.id.name_info);               //name
        TextView initialValue_info = (TextView) findViewById(R.id.iniValue_info);   //initial value
        final TextView curV_info = (TextView) findViewById(R.id.curValue_info);     //current value
        TextView comment_info = (TextView) findViewById(R.id.comment_info);         //comment

        /*
        relate buttons' ID in xml file to java file
         */

        Button reset = (Button) findViewById(R.id.reset);                   //reset button
        Button minus = (Button) findViewById(R.id.minus);                   //minus button
        Button plus = (Button) findViewById(R.id.plus);                     //plus button
        Button edit = (Button) findViewById(R.id.edit_info);                //edit button
        Button del = (Button) findViewById(R.id.del_info);                  //delete button

        /*
        input values into textView area
         */
        name_info.setText(name);
        initialValue_info.setText(Integer.toString(initial_V));
        curV_info.setText(Integer.toString(current_V));
        comment_info.setText(comment);

        /*
        wait when user tap plus
         */
        plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                count.increase();                                       //increase value by 1
                count.setDate();                                        //update date also
                curV_info.setText(Integer.toString(count.getValue()));  //change shown value
                counters.set(position, count);
                saveInFile();
            }
        });

        /*
        wait when user tap minus
        then decrease value by 1 and update count's information
        save in file
         */
        minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                count.decrease();                                        //decrease value by 1
                count.setDate();                                        //update date also
                curV_info.setText(Integer.toString(count.getValue()));  //change shown value
                counters.set(position, count);
                saveInFile();
            }
        });


        /*
        wait when user tap plus
        the increase value by 1 and update count's information
        save in file
         */
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                count.reset();                                             //reset value
                count.setDate();                                           //change date also
                curV_info.setText(Integer.toString(count.getInitialValue()));//change shown value
                counters.set(position, count);
                saveInFile();
            }
        });

        /*
        wait when user tap edit
        then go to edit page
         */
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(checkInfoActivity.this, editActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);                                      //go to edit page
                finish();
            }
        });

        /*
        wait when user tap delete
        then delete this count and save
        go to main page
         */
        del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                counters.remove(position);                      //remove count from list
                saveInFile();
                finish();
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
            Type listType = new TypeToken<ArrayList<Count>>() {
            }.getType();
            counters = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            counters = new ArrayList<Count>();
        }
    }

    /**
     *load information form file
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
