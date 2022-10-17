package com.hacktiv.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.hacktiv.todolist.models.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoData";
    private static final String TABLE_TODO = "Todo";

    private static final String KEY_ID = "id";
    private static final String TODO = "ToDo";


    public SQLiteDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String CREATE_COUNTRY_TABLE = "CREATE TABLE "+ TABLE_TODO+"("+KEY_ID+" INTEGER PRIMARY KEY, "+ TODO + " TEXT" +")";
    sqLiteDatabase.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_TODO);
    onCreate(sqLiteDatabase);
    }

    public void addTodo(TodoModel todo){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODO, todo.getToDo());
        db.insert(TABLE_TODO,null, values);
        db.close();
    }

    public TodoModel getTodo(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODO, new String[] {
                KEY_ID,TODO
        }, KEY_ID+"=?",new String[]{
                String.valueOf(id)
        }, null, null, null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        TodoModel todo = new TodoModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        return todo;
    };

    public List<TodoModel> getAllTodos(){
        List<TodoModel> todoList = new ArrayList<TodoModel>();

        String selectQuery = "SELECT * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                TodoModel todo = new TodoModel();
                todo.setId(Integer.parseInt(cursor.getString(0)));
                todo.setToDo(cursor.getString(1));


                todoList.add(todo);

            }while (cursor.moveToNext());
        }

        return todoList;
    }

    public int updateTodo(TodoModel todo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TODO, todo.getToDo());


        return db.update(TABLE_TODO, values, KEY_ID+"=?", new String[]{String.valueOf(todo.getId())});

    }

    public void deleteTodo(TodoModel todo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID+"=?", new String[]{
                String.valueOf(todo.getId())
        });
        db.close();
    }

    public void deleteAllTodos(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, null,null);
        db.close();
    }

    public int getTodosCount(){
        String countQuery = "SELECT * FROM "+TABLE_TODO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}
