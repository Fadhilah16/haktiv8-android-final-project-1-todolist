package com.hacktiv.todolist.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.hacktiv.todolist.R;
import com.hacktiv.todolist.database.SQLiteDatabaseHandler;
import com.hacktiv.todolist.models.TodoModel;
import com.hacktiv.todolist.ui.edit.EditFragment;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private List<TodoModel> listTodo = new ArrayList<>();
    private Context context;
    private SQLiteDatabaseHandler db;
    public TodoListAdapter(Context context, List<TodoModel> listTodo) {
        this.context = context;
        this.listTodo = listTodo;
        db = new SQLiteDatabaseHandler(this.context);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);

        TodoViewHolder todoViewHolder = new TodoViewHolder(view);
        return todoViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.todo_item;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoModel todo = listTodo.get(position);
        holder.todoValue.setText(todo.getToDo());
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            holder.todoValue.setTextColor(Color.WHITE);
            holder.deleteButton.setTextColor(Color.WHITE);
//            holder.cardItem.setCardBackgroundColor(new ColorDrawable(context.getResources().getColor(R.color.charcoal)));
        holder.cardItem.setCardBackgroundColor(context.getColor(R.color.dark_item));
        }


        holder.deleteButton.setTag(todo.getId());
        holder.editButton.setTag(todo.getId());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataId = new Bundle();
                EditFragment editFragment = new EditFragment();
                dataId.putString("id",Integer.toString(todo.getId()));
                editFragment.setArguments(dataId);
                ListFragment listFragment = new ListFragment();


                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.layout_list, editFragment).commit();
            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                db.deleteTodo(db.getTodo(todo.getId()));
                NavController navController = Navigation.findNavController((FragmentActivity) context, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_list);

            }
        });

    }



    @Override
    public int getItemCount() {
        return (listTodo != null) ? listTodo.size() : 0 ;
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder{

        public TextView todoValue;

        public Button deleteButton;
        public LinearLayout editButton;
        public CardView cardItem;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoValue = itemView.findViewById(R.id.todo_value);
            deleteButton = itemView.findViewById(R.id.delete_btn);
            editButton = itemView.findViewById(R.id.todo_item);
            cardItem = itemView.findViewById(R.id.card_item);

        }
    }
}
