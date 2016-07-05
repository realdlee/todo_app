package com.lee.todoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Todos")
public class Todo extends Model {
    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;

    @Column(name = "Title")
    public String title;
    @Column(name = "Completed")
    public boolean completed;


    // Make sure to have a default constructor for every ActiveAndroid model
    public Todo(){
        super();
    }

    public Todo(int remoteId, String title, boolean completed){
        super();
        this.remoteId = remoteId;
        this.title = title;
        this.completed = completed;
    }

    public static List<Todo> getAll() {
        return new Select()
                .from(Todo.class)
                .orderBy("Title ASC")
                .execute();
    }

}