package com.example.cis4280project;

import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MemoSettings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_memo_settings);
        initSettings();
        initSortByClick();
        initSortOrderClick();

    }

    private void initSettings() {
        String sortBy = getSharedPreferences("MemoPreferences", Context.MODE_PRIVATE).getString("sortfield","memosubject");
        String sortOrder = getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbSubject = findViewById(R.id.radioButtonSubject);
        RadioButton rbDate = findViewById(R.id.radioButtonDate);
        RadioButton rbCriticality = findViewById(R.id.radioButtonCriticality);

        if (sortBy.equalsIgnoreCase("memosubject")) {
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
                    getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).edit().putString("sortfield","memosubject").apply();
                } else if (rbCriticality.isChecked()) {
                    getSharedPreferences("MemoPreferences",Context.MODE_PRIVATE).edit().putString("sortfield","criticality").apply();

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
