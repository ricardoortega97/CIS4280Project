package com.example.cis4280project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {

    private Memo currentMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMemoListButton();
        initSettings();
        //calls the current object
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            initMemo(extras.getInt("memoID"));
        }
        else {
            currentMemo = new Memo();
        }
    }

    //method to go to the next activity (memoList)
    private void initMemoListButton(){
        Button allMemos = findViewById(R.id.toggleButtonAll);
        allMemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettings(){
        Button settingsButton = findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chhange to the settings activity
                Intent intent = new Intent(MainActivity.this, MemoSettings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMemo(int id) {

        MemoDataSource ds = new MemoDataSource(MainActivity.this);
        try {
            ds.open();
            currentMemo = ds.getSpecificMemo(id);
            ds.close();
        }catch (Exception e) {
            Toast.makeText(this, "Load failed!", Toast.LENGTH_LONG).show();
        }
        EditText editSubject = findViewById(R.id.editSubject);
        EditText editMemo = findViewById(R.id.editInput);
        TextView textDate = findViewById(R.id.textDate);
        //radio button saved in a database?
        RadioGroup rgLevel = findViewById(R.id.rbLevel);

        editSubject.setText(currentMemo.getSubject());
        editMemo.setText(currentMemo.getMemoText());
        //The format method must be added inorder to set this.
       // textDate.setText(DateFormat.format("MM/dd/yy",
               // currentMemo.getDate().getTimeInMillis()));


    }


}