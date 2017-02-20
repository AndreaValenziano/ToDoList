package com.example.andreavalenziano.todolist.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.andreavalenziano.todolist.R;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class AddActivity extends AppCompatActivity {

    Button createBtn;
    EditText titleTV, dateExpTV, textBodyTV;
    Intent intent;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Create a note");
        createBtn=(Button)findViewById(R.id.submit_button);
        titleTV=(EditText) findViewById(R.id.title_et);
        dateExpTV=(EditText)findViewById(R.id.expiration_date_et);
        textBodyTV=(EditText)findViewById(R.id.text_body_et);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=titleTV.getText().toString();
                String dateExp=dateExpTV.getText().toString();
                String textBody=textBodyTV.getText().toString();

                intent.putExtra(MainActivity.TITLE,title);
                intent.putExtra(MainActivity.DATE_EXP,dateExp);
                intent.putExtra(MainActivity.TEXT_BODY,textBody);

                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });



    }



}
