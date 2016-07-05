package com.lee.todoapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditTodoDialogFragment.EditTodoDialogListener {
    List<Todo> todoItems;
    TodosAdapter todosAdapter;
    ListView lvItems;
    EditText etEditText;
    Todo selectedTodo = new Todo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Daily Focus");
        setContentView(R.layout.activity_main);
        todoItems = new ArrayList<Todo>();
        todoItems = Todo.getAll();
        todosAdapter = new TodosAdapter(this, todoItems);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todosAdapter);

        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedTodo = todoItems.get(position);
                        showEditDialog(position, todoItems.get(position).title.toString(), selectedTodo.completed, selectedTodo.priority);
                    }
                }
        );


        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo removeItem = todoItems.remove(position);
                removeItem.delete();
                todosAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void addTodo(View view) {
        String text = etEditText.getText().toString();
        if(text == null || text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please describe your todo.",
                    Toast.LENGTH_LONG).show();
        }
        else if(todoItems.size() < 3) {
            Todo newTodo = new Todo();
            newTodo.title = etEditText.getText().toString();
            newTodo.completed = false;

            if(todoItems.size() > 0) {
                newTodo.remoteId = todoItems.get(todoItems.size() - 1).getId();
            } else {
                newTodo.remoteId = 0 ;
            }

            newTodo.save();
            todosAdapter.add(newTodo);
            etEditText.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "You can only add up to 3 items.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void showEditDialog(int position, String text, boolean completed, int priority) {
        FragmentManager fm = getSupportFragmentManager();
        EditTodoDialogFragment editTodoDialogFragment = EditTodoDialogFragment.newInstance(position, text, completed, priority);
        editTodoDialogFragment.show(fm, "fragment_edit_todo");
    }

    public void onFinishEditDialog(String text, boolean completed, int priority) {
        selectedTodo.title = text;
        boolean old_completed = selectedTodo.completed;
        selectedTodo.completed = completed;
        selectedTodo.priority = priority;
        selectedTodo.save();
        todosAdapter.notifyDataSetChanged();
        
        if((old_completed != completed) && (completed)) {
            int remaining = Todo.remainingTodos();
            String message;
            if(remaining > 1) {
                message = "Great job! You have " + String.valueOf(remaining) + " remaining todos.";
            } else if (remaining > 0) {
                message = "Great job! You have just one more todo to go!";
            } else {
                message = "Great job! You're all done!";
            }

            Toast.makeText(getApplicationContext(), message,
                    Toast.LENGTH_LONG).show();

        }

    }

}
