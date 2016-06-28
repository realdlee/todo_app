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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


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
    int position;

    public interface EditTodoDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public EditTodoDialogFragment() {
    }

    public static EditTodoDialogFragment newInstance(int position, String title) {
        EditTodoDialogFragment frag = new EditTodoDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("position", position);
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

        String title = getArguments().getString("title");
        position = getArguments().getInt("position");
        Log.e("title", title);
        editText.setText(title);
        editText.setOnEditorActionListener(this);

//        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_id);
//        if (checkBox.isChecked()) {
//            checkBox.setChecked(false);
//        }

        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();

//            Bundle myBundle = new Bundle();
//            myBundle.putString("title", editText.getText().toString());
//            myBundle.putInt("position", 1);
            listener.onFinishEditDialog(editText.getText().toString());
            dismiss();
            return true;
        }
        return false;
    }
}