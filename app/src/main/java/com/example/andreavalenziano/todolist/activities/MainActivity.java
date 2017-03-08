package com.example.andreavalenziano.todolist.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.andreavalenziano.todolist.R;
import com.example.andreavalenziano.todolist.adapters.NoteAdapter;
import com.example.andreavalenziano.todolist.database.DatabaseHandler;
import com.example.andreavalenziano.todolist.models.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.content.ContentValues.TAG;
import static com.example.andreavalenziano.todolist.models.StateType.TODO;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button toDoBtn;
    Button completeBtn;
    Button createSupportBtn;
    FloatingActionButton addBtn;
    Note editingNote;
    ImageView starIcon;
    private RecyclerView noteRV;
    private RecyclerView.LayoutManager layoutManager;

    private static RelativeLayout relativeLayout;
    private NoteAdapter adapter;

    DatabaseHandler dbHandler;


    private int STAGGERED_LAYOUT = 20;
    private int LINEAR_LAYOUT = 21;
    private int layoutManagerType = LINEAR_LAYOUT;


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
    public static final String SPECIAL = "special";
    private static final String LAYOUT_MANAGER_KEY = "LAYOUT_MANAGER_KEY";
    public Snackbar snackbar;

    //flag
    public boolean isEditable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.home);


        toDoBtn = (Button) findViewById(R.id.to_do_button);
        completeBtn = (Button) findViewById(R.id.complete_button);
        addBtn = (FloatingActionButton) findViewById(R.id.create_button);
        createSupportBtn = (Button) findViewById(R.id.create_support_btn);

        starIcon = (ImageView) findViewById(R.id.star_icon_main);

        noteRV = (RecyclerView) findViewById(R.id.to_do_list_rv);
        adapter = new NoteAdapter(this);
        // layoutManager = new LinearLayoutManager(this);
        layoutManager = getSavedLayoutManager();

        noteRV.setLayoutManager(layoutManager);
        noteRV.setAdapter(adapter);
        registerForContextMenu(noteRV);

        relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);

        toDoBtn.setOnClickListener(this);
        completeBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        createSupportBtn.setOnClickListener(this);


        dbHandler = new DatabaseHandler(this);
        adapter.setDataSet(dbHandler.getAllNotes());


    }

    private RecyclerView.LayoutManager getSavedLayoutManager() {
        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int layoutManager = sharedPrefs.getInt(LAYOUT_MANAGER_KEY, -1);
        if (layoutManager == STAGGERED_LAYOUT) {
            setLayoutManagerType(layoutManager);
            return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        }
        if (layoutManager == LINEAR_LAYOUT) {
            setLayoutManagerType(layoutManager);
            return new LinearLayoutManager(this);
        }
        return new LinearLayoutManager(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Log.d("menu", "getLayoutManagerType " + getLayoutManagerType());
            if (getLayoutManagerType() == STAGGERED_LAYOUT) {
                setLayoutManagerType(LINEAR_LAYOUT);
                noteRV.setLayoutManager(new LinearLayoutManager(this));
                // item.setIcon(getDrawable(R.drawable.view_quilt));


            } else {
                setLayoutManagerType(STAGGERED_LAYOUT);
                noteRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                // item.setIcon(getDrawable(R.drawable.view_list));

            }

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.to_do_button) {

        } else if (v.getId() == R.id.complete_button) {


        } else if (v.getId() == R.id.create_button) {

            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            isEditable = false;
            intent.putExtra(EDIT, isEditable);


            //Activity context = (Activity) v.getContext();
            // context.startActivityForResult(intent, MainActivity.ADD_REQUEST_CODE);
            startActivityForResult(intent, MainActivity.ADD_REQUEST_CODE);

        } else if (v.getId() == R.id.create_support_btn) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            isEditable = false;
            intent.putExtra(EDIT, isEditable);


            //Activity context = (Activity) v.getContext();
            // context.startActivityForResult(intent, MainActivity.ADD_REQUEST_CODE);
            startActivityForResult(intent, MainActivity.ADD_REQUEST_CODE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Note note;
        if (requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK) { //modificare if con codice EDIT_REQUEST_CODE

            String title = data.getStringExtra(TITLE);
            String dateExp = data.getStringExtra(DATE_EXP);
            String textBody = data.getStringExtra(TEXT_BODY);
            Boolean special = data.getBooleanExtra(SPECIAL, false);
            Calendar dateCr = new GregorianCalendar();
            SimpleDateFormat sDF = new SimpleDateFormat();
            String dateCreation = sDF.format(dateCr.getTime());
            note = new Note(title, textBody, dateExp, dateCreation, dateCreation, TODO, special);
            adapter.addNote(note);

            dbHandler.addNote(note);
            if (note.getId() > -1) {
                snackbar = Snackbar.make(relativeLayout, R.string.adding_success, Snackbar.LENGTH_SHORT);
            } else {
                snackbar = Snackbar.make(relativeLayout, R.string.database_error, Snackbar.LENGTH_SHORT);
            }
            snackbar.show();
        }
        if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            String title = data.getStringExtra(TITLE);
            String dateExp = data.getStringExtra(DATE_EXP);
            String textBody = data.getStringExtra(TEXT_BODY);
            Boolean isSpecial = data.getBooleanExtra(SPECIAL, false);
            Calendar dateLE = new GregorianCalendar();
            SimpleDateFormat sDF = new SimpleDateFormat();
            String dateLastEdit = sDF.format(dateLE.getTime());

            int index = data.getIntExtra(ID, 0);
            editingNote.setTitle(title);
            editingNote.setDateLastEdit(dateLastEdit);
            editingNote.setTextBody(textBody);
            editingNote.setDateExpired(dateExp);
            editingNote.setSpecial(isSpecial);

            if (dbHandler.updateNote(editingNote) > 0) {
                snackbar = Snackbar.make(relativeLayout, R.string.updating_success, Snackbar.LENGTH_SHORT);
                adapter.updateNote(editingNote, index);
            } else {
                snackbar = Snackbar.make(relativeLayout, R.string.database_error, Snackbar.LENGTH_SHORT);
            }
            snackbar.show();

        }
        noteRV.scrollToPosition(0);


        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();
        Note deletingNote = adapter.getNote(adapter.getPosition());
        switch (id) {
            case R.id.action_delete:
                //remove record

                if (dbHandler.deleteNote(deletingNote) > 0) {
                    snackbar = Snackbar.make(relativeLayout, R.string.deleting_success, Snackbar.LENGTH_SHORT);
                    adapter.deleteNote(adapter.getPosition());
                } else {
                    snackbar = Snackbar.make(relativeLayout, R.string.database_error, Snackbar.LENGTH_SHORT);
                }
                snackbar.show();
                // remove from adapter

                break;

            case R.id.action_edit:

                editingNote = adapter.getNote(adapter.getPosition());
                Intent i = new Intent(this, AddActivity.class);
                i.putExtra(EDIT, true);
                i.putExtra(TITLE, editingNote.getTitle());
                i.putExtra(TEXT_BODY, editingNote.getTextBody());
                i.putExtra(DATE_EXP, editingNote.getDateExpired());
                i.putExtra(SPECIAL, editingNote.isSpecial());
                startActivityForResult(i, EDIT_REQUEST_CODE);
                break;

        }

        return super.onContextItemSelected(item);
    }


    public int getLayoutManagerType() {

        return layoutManagerType;
    }

    public void setLayoutManagerType(int layoutManagerType) {
        this.layoutManagerType = layoutManagerType;
        Log.d("setLayoutManagerType", "type " + this.layoutManagerType);
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
        SharedPreferences layoutPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = layoutPreferences.edit();
        editor.putInt(LAYOUT_MANAGER_KEY, getLayoutManagerType());
        editor.apply();

    }
}
