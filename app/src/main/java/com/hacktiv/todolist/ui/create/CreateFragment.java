package com.hacktiv.todolist.ui.create;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hacktiv.todolist.R;
import com.hacktiv.todolist.database.SQLiteDatabaseHandler;
import com.hacktiv.todolist.databinding.FragmentCreateBinding;
import com.hacktiv.todolist.models.TodoModel;


public class CreateFragment extends Fragment {

    private FragmentCreateBinding binding;
    private EditText todoValue;
    private Button button;
    private SQLiteDatabaseHandler db;
    private TextView title;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        button = binding.btnSubmit;
        title = binding.createTitle;
        todoValue = binding.todoField;
        db = new SQLiteDatabaseHandler(getContext());

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            title.setTextColor(Color.WHITE);
            todoValue.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TodoModel todo = new TodoModel(todoValue.getText().toString());
                db.addTodo(todo);

                todoValue.setText("");

                Toast.makeText(getContext(), "Task has been created", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}