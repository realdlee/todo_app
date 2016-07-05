package com.lee.todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.util.Log;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditTodoDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditTodoDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class EditTodoDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText editText;
    private Switch switch1;
    private Spinner spinner;
    int position;
    boolean completed;
    int priority;

    public interface EditTodoDialogListener {
        void onFinishEditDialog(String inputText, boolean completed, int priority);
    }

    public EditTodoDialogFragment() {
    }

    public static EditTodoDialogFragment newInstance(int position, String title, boolean completed, int priority) {
        EditTodoDialogFragment frag = new EditTodoDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("position", position);
        args.putBoolean("completed", completed);
        args.putInt("priority", priority);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_todo, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.textTitle);
        switch1 = (Switch) view.findViewById(R.id.switch1);
        completed = getArguments().getBoolean("completed");
        switch1.setChecked(completed);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                completed = isChecked;
            }
        });


        String title = getArguments().getString("title");
        position = getArguments().getInt("position");
        priority = getArguments().getInt("priority");

        editText.setText(title);
        editText.setOnEditorActionListener(this);
        editText.requestFocus();

        setup_spinner(view);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void setup_spinner(View view) {
        spinner = (Spinner)view.findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priorities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.e("priority", String.valueOf(priority));
        spinner.setSelection(priority);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                priority = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();
            listener.onFinishEditDialog(editText.getText().toString(), completed, priority);
            dismiss();
            return true;
        }
        return false;
    }

}