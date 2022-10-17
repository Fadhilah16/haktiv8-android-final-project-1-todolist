package com.hacktiv.todolist.ui.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hacktiv.todolist.R;
import com.hacktiv.todolist.adapter.TodoListAdapter;
import com.hacktiv.todolist.database.SQLiteDatabaseHandler;
import com.hacktiv.todolist.databinding.FragmentEditBinding;
import com.hacktiv.todolist.databinding.FragmentListBinding;
import com.hacktiv.todolist.models.TodoModel;
import com.hacktiv.todolist.ui.edit.EditFragment;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private FragmentEditBinding editBinding;
    private SQLiteDatabaseHandler db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        container.removeAllViews();
        binding = FragmentListBinding.inflate(inflater, container, false);
        editBinding = FragmentEditBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        RecyclerView countryListView = binding.countriesList;
        Button deleteAllButton = binding.btnDeleteAll;

        countryListView.setHasFixedSize(true);
        ListFragment listFragment = new ListFragment();
        EditFragment editFragment = new EditFragment();
        db = new SQLiteDatabaseHandler(getContext());
        List<TodoModel> todosList = new ArrayList<>();
        todosList =   db.getAllTodos();
        TodoListAdapter countryListAdapter = new TodoListAdapter(getActivity(), todosList);
        countryListView.setAdapter(countryListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        countryListView.setLayoutManager(layoutManager);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warningDeleteAllDialog("Warning", "Are you sure to delete all tasks?");
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

    private void warningDeleteAllDialog(String title, String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = new SQLiteDatabaseHandler(getContext());
                db.deleteAllTodos();
                NavController navController = Navigation.findNavController((FragmentActivity) getContext(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_list);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();


        alertDialog.show();
    }

}