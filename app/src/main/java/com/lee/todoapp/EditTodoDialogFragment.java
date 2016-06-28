package com.lee.todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


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
    int position;
    boolean completed;

    public interface EditTodoDialogListener {
        void onFinishEditDialog(String inputText, boolean completed);
    }

    public EditTodoDialogFragment() {
    }

    public static EditTodoDialogFragment newInstance(int position, String title, boolean completed) {
        EditTodoDialogFragment frag = new EditTodoDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("position", position);
        args.putBoolean("completed", completed);
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
        editText.setText(title);
        editText.setOnEditorActionListener(this);

        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();
            listener.onFinishEditDialog(editText.getText().toString(), completed);
            dismiss();
            return true;
        }
        return false;
    }

}