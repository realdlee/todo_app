package com.lee.todoapp;

//import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by lee on 6/27/16.
 */
public class Todo  {
    String title;
    boolean completed;
    Date dueAt;
    int priority;


    public Todo(){
    }

    public Todo(String title){
        this.title = title;
    }
}