package com.example.andreavalenziano.todolist.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.andreavalenziano.todolist.R;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class AddActivity extends AppCompatActivity {


    EditText titleTV, dateExpTV, textBodyTV;
    Button datePickerButton;
    Intent intent;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //toolbar
        setTitle("Create a note");
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);


        titleTV=(EditText) findViewById(R.id.title_et);
        dateExpTV=(EditText)findViewById(R.id.expiration_date_et);
        textBodyTV=(EditText)findViewById(R.id.text_body_et);
        datePickerButton=(Button)findViewById(R.id.date_picker_button);

        if(intent.getBooleanExtra(MainActivity.EDIT,true)){
            System.out.println("true");
            titleTV.setText(intent.getStringExtra(MainActivity.TITLE));
            dateExpTV.setText(intent.getStringExtra(MainActivity.DATE_EXP));
            textBodyTV.setText(intent.getStringExtra(MainActivity.TEXT_BODY));
        }
//    datePickerButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            showDatePickerDialog();
//        }
//    });


    }


//    private void showDatePickerDialog() {
//        DatePickerDialog datePick=new DatePickerDialog(this);
//
//        datePick.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//
//            }
//        });
//        datePick.create().show();
//    }

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

        String title=titleTV.getText().toString();
        String dateExp=dateExpTV.getText().toString();
        String textBody=textBodyTV.getText().toString();

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
