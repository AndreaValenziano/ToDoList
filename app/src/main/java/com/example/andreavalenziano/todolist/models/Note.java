package com.example.andreavalenziano.todolist.models;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class Note {
    private static int totNote;
    private int id;
    private String title;
    private String textBody;
    private String DateCreation, DateLastEdit, DateExpired;
    private StateType state;


    public Note(String title, String textBody, String dateExpired, String dateCreation, String dateLastEdit, StateType state) {

        this.title = title;
        this.textBody = textBody;
        DateCreation = dateCreation;
        DateLastEdit = dateLastEdit;
        DateExpired = dateExpired;
        this.state = state;




    }

    public Note() {



    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(String dateCreation) {
        DateCreation = dateCreation;
    }

    public String getDateLastEdit() {
        return DateLastEdit;
    }

    public void setDateLastEdit(String dateLastEdit) {
        DateLastEdit = dateLastEdit;
    }

    public String getDateExpired() {
        return DateExpired;
    }

    public void setDateExpired(String dateExpired) {
        DateExpired = dateExpired;
    }

    public StateType getState() {

        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }



}
