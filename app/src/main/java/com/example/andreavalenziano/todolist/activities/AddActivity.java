package com.example.andreavalenziano.todolist.activities;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andreavalenziano.todolist.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class AddActivity extends AppCompatActivity {


    EditText titleET, textBodyET, dateExpET;

    Intent intent;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //toolbar
        setTitle("Create a note");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);


        titleET = (EditText) findViewById(R.id.title_et);
        dateExpET = (EditText) findViewById(R.id.expiration_date_et);
        textBodyET = (EditText) findViewById(R.id.text_body_et);


        if (intent.getBooleanExtra(MainActivity.EDIT, true)) {
            setTitle("Edit note");
            titleET.setText(intent.getStringExtra(MainActivity.TITLE));
            dateExpET.setText(intent.getStringExtra(MainActivity.DATE_EXP));
            textBodyET.setText(intent.getStringExtra(MainActivity.TEXT_BODY));
        }
        dateExpET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int startYear = c.get(Calendar.YEAR);
                int startMonth = c.get(Calendar.MONTH);
                int startDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date=dayOfMonth+"-"+month+"-"+year;
                        System.out.println(date);
                        dateExpET.setText(date);
                    }
                },startYear, startMonth, startDay);

                datePickerDialog.setTitle("calendar");
                datePickerDialog.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.check_add:
                newNote();
                return true;
            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteNote() {


        intent.putExtra(MainActivity.DELETED,true);
        setResult(Activity.RESULT_OK,intent);

    }

    public void newNote(){

        String title= titleET.getText().toString();
        String dateExp= dateExpET.getText().toString();
        String textBody= textBodyET.getText().toString();

        intent.putExtra(MainActivity.TITLE,title);
        intent.putExtra(MainActivity.DATE_EXP,dateExp);
        intent.putExtra(MainActivity.TEXT_BODY,textBody);
        if(intent.getBooleanExtra(MainActivity.EDIT,true)){
            intent.putExtra(MainActivity.EDIT,true);
        }
        setResult(Activity.RESULT_OK,intent);
        finish();
    }



}
