package com.hacktiv.todolist.models;

public class TodoModel {
    private int id;
    private String toDo;


    public TodoModel(int id, String toDo) {
        super();
        this.id = id;
        this.toDo = toDo;
    }

    public TodoModel(String toDo) {
        super();
        this.toDo = toDo;
    }

    public TodoModel() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }
}
