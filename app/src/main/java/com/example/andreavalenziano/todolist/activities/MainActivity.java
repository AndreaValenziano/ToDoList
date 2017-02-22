package com.example.andreavalenziano.todolist.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.andreavalenziano.todolist.R;
import com.example.andreavalenziano.todolist.adapters.NoteAdapter;
import com.example.andreavalenziano.todolist.database.DatabaseHandler;
import com.example.andreavalenziano.todolist.models.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.content.ContentValues.TAG;
import static com.example.andreavalenziano.todolist.models.StateType.TODO;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button toDoBtn;
    Button completeBtn;
    FloatingActionButton addBtn;
    private RecyclerView noteRV;
    private RecyclerView.LayoutManager layoutManager;
    private NoteAdapter adapter;

    DatabaseHandler dbHandler;


    //constants
    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    public static final String TITLE = "title";
    public static final String DATE_EXP = "dataExp";
    public static final String DATE_CR = "dataCr";
    public static final String DATE_LAST_EDIT = "dataLastEdit";
    public static final String TEXT_BODY = "textBody";
    public static final String ID = "id";
    public static final String EDIT = "iseditable";
    public static final String DELETED = "isdeleted";

    //flag
    public boolean isEditable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        setTitle("Home");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Home");

        toDoBtn = (Button) findViewById(R.id.to_do_button);
        completeBtn = (Button) findViewById(R.id.complete_button);
        addBtn = (FloatingActionButton) findViewById(R.id.create_button);

        noteRV = (RecyclerView) findViewById(R.id.to_do_list_rv);
        adapter = new NoteAdapter();
        layoutManager = new LinearLayoutManager(this);
        noteRV.setLayoutManager(layoutManager);
        noteRV.setAdapter(adapter);
        adapter.setDataSet(getNoteRV());

        toDoBtn.setOnClickListener(this);
        completeBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

       dbHandler = new DatabaseHandler(this);
        adapter.setDataSet(dbHandler.getAllNotes());


    }

    private ArrayList<Note> getNoteRV() {
        ArrayList<Note> notes = new ArrayList<>();
        Note firstNote = new Note("TITLE",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean mollis massa quis elit tincidunt dignissim. Vestibulum mollis sapien eu justo venenatis mattis. Pellentesque sed justo enim. Donec et arcu quis felis molestie volutpat sit amet non est. Praesent vehicula facilisis velit, ut aliquam ex pulvinar eget. Nulla vestibulum eros ac.\n" +
                        "\n",
                "20-02-2017",
                "",
                "23-02-2017",
                TODO
        );
        Note secondNote = new Note("SECOND TITLE",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean mollis massa quis elit tincidunt dignissim. Vestibulum mollis sapien eu justo venenatis mattis. Pellentesque sed justo enim. Donec et arcu quis felis molestie volutpat sit amet non est. Praesent vehicula facilisis velit, ut aliquam ex pulvinar eget. Nulla vestibulum eros ac.\n" +
                        "\n",
                "20-02-2017",
                "",
                "23-02-2017",
                TODO
        );

        notes.add(firstNote);
        notes.add(secondNote);

        return notes;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.to_do_button) {

        } else if (v.getId() == R.id.complete_button) {

        } else if (v.getId() == R.id.create_button) {

            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            isEditable = false;
            intent.putExtra(EDIT, isEditable);

            Activity context = (Activity) v.getContext();
            context.startActivityForResult(intent, MainActivity.ADD_REQUEST_CODE);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Note note;
        if (requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK) { //modificare if con codice EDIT_REQUEST_CODE

            String title = data.getStringExtra(TITLE);
            String dateExp = data.getStringExtra(DATE_EXP);
            String textBody = data.getStringExtra(TEXT_BODY);
            Calendar dateCr = new GregorianCalendar();
            String dateCreation = dateCr.toString();
            note = new Note(title, textBody, dateExp, dateCreation, null, TODO);
            adapter.addNote(note);
            dbHandler.addNote(note);
        }
        if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String title = data.getStringExtra(TITLE);
            String dateExp = data.getStringExtra(DATE_EXP);
            String textBody = data.getStringExtra(TEXT_BODY);
            Calendar dateCr = new GregorianCalendar();
            String dateCreation = dateCr.toString();
            int index=data.getIntExtra(ID,0);
            note = new Note(title, textBody, dateExp, dateCreation, null, TODO);
            adapter.modifyNote(note, index);
            dbHandler.updateNote(note);
        }
        noteRV.scrollToPosition(0);


        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }

    }








    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }
}
