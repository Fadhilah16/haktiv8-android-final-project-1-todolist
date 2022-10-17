package com.hacktiv.todolist.ui.edit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;


import com.hacktiv.todolist.R;
import com.hacktiv.todolist.database.SQLiteDatabaseHandler;
import com.hacktiv.todolist.databinding.FragmentEditBinding;
import com.hacktiv.todolist.models.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class EditFragment extends Fragment {

    private FragmentEditBinding binding;
    private SQLiteDatabaseHandler db;
    private TextView title;
    private EditText todoValue;
    private Button updateButton;
    private ImageButton backButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentEditBinding.inflate(inflater, container, false);
        container.removeAllViews();
        View root = binding.getRoot();
        List<TodoModel> todosList = new ArrayList<>();
        Bundle args= getArguments();
        title = binding.updateTitle;
        todoValue = binding.todoField;
        updateButton = binding.btnUpdate;
        backButton = binding.backButton;

        EditFragment editFragment = new EditFragment();
        db = new SQLiteDatabaseHandler(getContext());
        ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(editFragment.getClass().getName());
        TodoModel todo = db.getTodo(Integer.parseInt(args.getString("id")));
        todoValue.setText(todo.getToDo());
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            title.setTextColor(Color.WHITE);
            todoValue.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        }else{
            title.setTextColor(Color.BLACK);
            todoValue.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        }
        backButton.getBackground().setAlpha(0);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoValueString = todoValue.getText().toString();

                todo.setToDo(todoValueString);

                db.updateTodo(todo);

                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.layout_edit, new com.hacktiv.todolist.ui.list.ListFragment()).commitNow();

                Toast.makeText(getContext(), "Task successfully updated", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.layout_edit, new com.hacktiv.todolist.ui.list.ListFragment()).commitNow();

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showDialog(String title, String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();


        alertDialog.show();
    }
}