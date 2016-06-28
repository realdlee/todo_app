package com.lee.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lee on 6/27/16.
 */

public class TodosAdapter extends ArrayAdapter<Todo> {
    public TodosAdapter(Context context, List<Todo> todos) {
        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Todo todo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
//        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.title);
//        // Populate the data into the template view using the data object
        title.setText(todo.title);

        // Return the completed view to render on screen
        return convertView;
    }
}