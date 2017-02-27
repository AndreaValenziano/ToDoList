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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.andreavalenziano.todolist.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class AddActivity extends AppCompatActivity {


    EditText titleET, textBodyET, dateExpET;
    TextView dateExpTV;
    ImageButton calendar_button;
    boolean isSpecial;
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
        dateExpTV = (TextView) findViewById(R.id.expiration_date_tv);
        textBodyET = (EditText) findViewById(R.id.text_body_et);
        calendar_button=(ImageButton) findViewById(R.id.calendar_button);



        if (intent.getBooleanExtra(MainActivity.EDIT, true)) {
            setTitle("Edit note");
            titleET.setText(intent.getStringExtra(MainActivity.TITLE));
            dateExpTV.setText(intent.getStringExtra(MainActivity.DATE_EXP));
            textBodyET.setText(intent.getStringExtra(MainActivity.TEXT_BODY));
        }
        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int startYear = c.get(Calendar.YEAR);
                int startMonth = c.get(Calendar.MONTH);
                int startDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date=dayOfMonth+"-"+(month+1)+"-"+year;
                        System.out.println(date);

                        dateExpTV.setText(date);
                    }
                },startYear, startMonth, startDay);

                datePickerDialog.setTitle("Calendar");
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
            case R.id.star_off_add:
                setSpecial(item);

                return true;
            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setSpecial(MenuItem item) {
        isSpecial=!isSpecial;
        if(isSpecial){
            item.setIcon(R.drawable.ic_star_on);
        }
        else
        {
            item.setIcon(R.drawable.ic_star_off);
        }

    }

    private void deleteNote() {


        intent.putExtra(MainActivity.DELETED,true);
        setResult(Activity.RESULT_OK,intent);

    }

    public void newNote(){

        String title= titleET.getText().toString();
        String dateExp= dateExpTV.getText().toString();
        String textBody= textBodyET.getText().toString();

        intent.putExtra(MainActivity.TITLE,title);
        intent.putExtra(MainActivity.DATE_EXP,dateExp);
        intent.putExtra(MainActivity.TEXT_BODY,textBody);
        intent.putExtra(MainActivity.SPECIAL,isSpecial);
        if(intent.getBooleanExtra(MainActivity.EDIT,true)){
            intent.putExtra(MainActivity.EDIT,true);
        }
        setResult(Activity.RESULT_OK,intent);
        finish();
    }



}
