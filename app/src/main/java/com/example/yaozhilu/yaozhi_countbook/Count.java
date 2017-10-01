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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *Represent a count
 *
 * @author Yaozhi
 * @version 1.0
 * @see MainActivity
 * @see checkInfoActivity
 * @see editActivity
 * @see addCountActivity
 * @since 1.0
 */

public class Count {
    private String name;            //count's name
    private Date date;              //count's date
    private int value;              //count's value
    private int initialValue;       //count's initial value
    private String comment;         //count's comment

    /**
     *Construct a count object
     *
     * @param name
     * @param value
     * @param comment
     */
    public Count(String name,int value,String comment){
        this.name = name;
        this.setCurValue(value);
        this.setInitialValue(value);
        this.comment = comment;
        this.date = new Date();
    }


    /*
    get count's information
    */

    /**
     * get date and format it
     *
     * @return
     */
    public String getDate(){
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        return DATE_FORMAT.format(date);
    }

    /**
     * get name
     *
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * get comment
     *
     * @return
     */
    public String getComment(){
        return comment;
    }

    /**
     * get initial value
     *
     * @return
     */
    public int getInitialValue() { return initialValue; }

    /**
     * get current value
     *
     * @return
     */
    public int getValue(){
        return value;
    }



    /*
    set count's information
     */

    /**
     * set comment
     *
     * @param comment
     */
    public void setComment(String comment){
        this.comment = comment;
    }
    public void setCurValue(int value) {
        if (value >= 0){
            this.value = value;
        }
    }

    /**
     * set initial value
     *
     * @param value
     */
    public void setInitialValue(int value){
        if (value >= 0){
            this.initialValue = value;
        }
    }

    /**
     * set name
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * set date
     */
    public void setDate(){
        this.date = new Date();
    }


    /**
     * decrease value by 1
     */
    public void decrease(){
        if (this.value > 0) {
            this.value -= 1;
        }
    }

    /**
     * decrease value by 1
     */
    public  void increase() {
        this.value += 1;
    }

    /**
     *reset value
     */
    public void reset(){
        this.value = this.initialValue;
    }

    /**
     * what to show on each count in main page
     * @return
     */
    @Override
    public String toString(){
        return name + "   " + String.valueOf(value)+"\n" + getDate();
    }
}
