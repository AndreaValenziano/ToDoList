package com.example.andreavalenziano.todolist.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import com.example.andreavalenziano.todolist.activities.MainActivity;
import com.example.andreavalenziano.todolist.models.Note;
import com.example.andreavalenziano.todolist.models.StateType;

import java.util.ArrayList;

/**
 * Created by AndreaValenziano on 22/02/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Notes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATA_EXP = "data_exp";
    private static final String KEY_DATA_CR = "data_cr";
    private static final String KEY_DATA_EDIT = "data_edit";
    private static final String KEY_STATE = "state";


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "notes";

    // Notes table name
    private static final String TABLE_NOTES = "table_notes";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_BODY + " TEXT, " + KEY_DATA_EXP + " TEXT, " + KEY_DATA_CR + " TEXT, "
                + KEY_DATA_EDIT + " TEXT, " + KEY_STATE + " TEXT" + ")";
        db.execSQL(CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        // Create tables again
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    /**
     * CREATE
     */
    public int addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_BODY, note.getTextBody());
        values.put(KEY_DATA_EXP, note.getDateExpired());
        values.put(KEY_DATA_CR, note.getDateCreation());
        values.put(KEY_DATA_EDIT, note.getDateLastEdit());
        values.put(KEY_STATE, note.getState().toString());

        // Inserting Row
        note.setId((int)db.insert(TABLE_NOTES, null, values));
        db.close(); // Closing database connection
        return note.getId();
    }

    /**
     * READ
     */
    // Getting All Notes
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;
        Log.d(ContentValues.TAG, "GET ALL NOTES");

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setTextBody(cursor.getString(2));
                note.setDateExpired(cursor.getString(3));
                note.setDateCreation(cursor.getString(4));
                note.setDateLastEdit(cursor.getString(5));
                Log.d(ContentValues.TAG,"CREATION: "+note.getDateCreation());
                Log.d(ContentValues.TAG,"LAST EDIT: "+note.getDateLastEdit());
                note.setState(StateType.TODO); //???how can I convert String to ENUM????
                // Adding note to list
                notesList.add(0,note);
            } while (cursor.moveToNext());
        }
        db.close();

        // return notes list
        return notesList;
    }

    /**
     * UPDATE
     */
    // Updating single note
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_BODY, note.getTextBody());
        values.put(KEY_DATA_EXP,note.getDateExpired());
        values.put(KEY_DATA_EDIT,note.getDateLastEdit());
        // updating row
        int up= db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        Log.d(ContentValues.TAG,"ID TO UPDATE: "+note.getId());
        db.close();
        return up;
    }


    /**
     * DELETE
     */
    // Deleting single note
    public int deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(ContentValues.TAG,"ID TO DELETE: "+note.getId());

        int del=db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();


        return del;
    }

    public void dropDatabase(){
        System.out.println("DROPPING");
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
    }
}
