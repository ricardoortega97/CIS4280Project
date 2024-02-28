package com.example.cis4280project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MemoSettings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_memo_settings);
        initMemoListButton();
        initMemoMain();
        initSettings();
        initSortByClick();
        initSortOrderClick();

    }

    private void initMemoListButton(){
        Button allMemos = findViewById(R.id.toggleButtonAll);
        allMemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoSettings.this, MemoList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //returns to the main activity
    private void initMemoMain() {
        Button mainMemo = findViewById(R.id.mainMemo);
        mainMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoSettings.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettings() {
        String sortBy = getSharedPreferences("MemoPreferences", Context.MODE_PRIVATE).getString("sortfield","subject");
        String sortOrder = getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbSubject = findViewById(R.id.radioButtonSubject);
        RadioButton rbDate = findViewById(R.id.radioButtonDate);
        RadioButton rbCriticality = findViewById(R.id.radioButtonCriticality);

        if (sortBy.equalsIgnoreCase("subject")) {
            rbSubject.setChecked(true);
        } else if (sortBy.equalsIgnoreCase("date")) {
            rbDate.setChecked(true);
        } else {
            rbCriticality.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.rbAscending);
        RadioButton rbDescending = findViewById(R.id.rbDescending);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.rgSortMemos);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbSubject = findViewById(R.id.radioButtonSubject);
                RadioButton rbCriticality = findViewById(R.id.radioButtonCriticality);
                if (rbSubject.isChecked()) {
                    getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).edit().putString("sortfield","subject").apply();
                } else if (rbCriticality.isChecked()) {
                    getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).edit().putString("sortfield","level").apply();

                }
                else {
                    getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).edit().putString("sortfield","date").apply();
                }
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.rgAscDes);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbAscending = findViewById(R.id.rbAscending);
                if (rbAscending.isChecked()) {
                    getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).edit().putString("sortorder","ASC").apply();
                }
                else {
                    getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).edit().putString("sortorder","DESC").apply();
                }
            }
        });
    }





}
