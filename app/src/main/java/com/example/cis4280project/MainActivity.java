package com.example.cis4280project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener{

    private Memo currentMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMemoListButton();
        initSettings();
        initChangeDateButton();
        initSaveButton();
        initTextChangedEvents();
        initToggleButton();
        //calls the current object
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            initMemo(extras.getInt("memoID"));
        }
        else {
            currentMemo = new Memo();
        }
        setForEditing(false);

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

        editSubject.setText(currentMemo.getSubject());
        editMemo.setText(currentMemo.getMemoText());
        textDate.setText(DateFormat.format("MM/dd/yy",
               currentMemo.getDate().getTimeInMillis()));

    }

    private void setForEditing(boolean enabled) {

        EditText editSubject = findViewById(R.id.editSubject);
        EditText editInput = findViewById(R.id.editInput);
        Button buttonCalendar = findViewById(R.id.buttonCalendar);
        RadioButton rbLow = findViewById(R.id.rbLow);
        RadioButton rbMedium = findViewById(R.id.rbMedium);
        RadioButton rbHigh = findViewById(R.id.rbHigh);
        Button buttonSave = findViewById(R.id.buttonSave);

        editSubject.setEnabled(enabled);
        editInput.setEnabled(enabled);
        buttonCalendar.setEnabled(enabled);
        rbLow.setEnabled(enabled);
        rbMedium.setEnabled(enabled);
        rbHigh.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

    }

    //calls a dialog Fragment calendar to set the date of the memo
    private void initChangeDateButton(){
        Button changeDate = findViewById(R.id.buttonCalendar);
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(fm, "DatePick");
            }
        });
    }



    private void initToggleButton() {
        final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setForEditing(editToggle.isChecked());
            }
        });
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RadioGroup rgLevel = findViewById(R.id.rbLevel);
                int selectedID = rgLevel.getCheckedRadioButtonId();
                RadioButton rbLevel = findViewById(selectedID);
                String selectedLevel = rbLevel.getText().toString();

                // Set the "level" property in the currentMemo object
                currentMemo.setLevel(selectedLevel);


                // hideKeyboard();
                boolean wasSuccessful;
                MemoDataSource ds = new MemoDataSource(MainActivity.this);
                try {
                    ds.open();

                    if (currentMemo.getMemoID() == -1) {
                        wasSuccessful = ds.insertMemo(currentMemo);
                        if (wasSuccessful) {
                            int newId = ds.getLastMemoID();
                            currentMemo.setMemoID(newId);
                        }
                    } else {
                        wasSuccessful = ds.updateMemo(currentMemo);
                    }
                    ds.close();

                } catch (Exception e) {
                    wasSuccessful = false;
                }
                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }

    private void initTextChangedEvents() {

        final EditText etMemoSubject = findViewById(R.id.editSubject);
        etMemoSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentMemo.setSubject(etMemoSubject.getText().toString());

            }
        });

        final EditText etMemoInput = findViewById(R.id.editInput);
        etMemoInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentMemo.setMemoText(etMemoInput.getText().toString());

            }
        });


    }

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView date = findViewById(R.id.textDate);
        date.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        currentMemo.setDate(selectedTime);
    }



}