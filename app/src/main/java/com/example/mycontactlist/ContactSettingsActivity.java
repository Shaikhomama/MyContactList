package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ContactSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_settings);
        initListButton();
        initMapButton();
        initSettingsButton();
        initSettings();
        initSortByClick();
        initSortOrderClick();
    }
    private void initListButton(){                                   //connects to ContactListActivity
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(ContactSettingsActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMapButton(){                                   //connects to ContactMapActivity
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(ContactSettingsActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton(){                              //connects to ContactSettingsActivity
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setEnabled(false);
    }

    private void initSettings(){
        // the method determines which RadioButton was pressed and then the value associated
        // with that RadioButton is saved in SharedPreferences.
        String sortBy = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortfield","contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbName = findViewById(R.id.radioName);
        RadioButton rbCity = findViewById(R.id.radioCity);
        RadioButton rbBirthday = findViewById(R.id.radioBirthday);
        //The value retrieved for the preferred sort field is evaluated to determine which RadioButton should be set as checked.
        if(sortBy.equalsIgnoreCase("contactname")) {
            rbName.setChecked(true);
        }
        else if(sortBy.equalsIgnoreCase("city")) {
            rbCity.setChecked(true);
        }
        else {
            rbBirthday.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioAscending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);

        if(sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }
    }

    // the method determines which RadioButton was pressed and then the value associated with that
    // RadioButton is saved in SharedPreferences.
    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbName = findViewById(R.id.radioName);
                RadioButton rbCity = findViewById(R.id.radioCity);

                if(rbName.isChecked()) {
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortfield","contactname").apply();
                }
                else if (rbCity.isChecked()) {
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortfield","city").apply();
                }
                else{
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortfield","birthday").apply();
                }
            }
        });
    }

    private void initSortOrderClick(){
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbAscending = findViewById(R.id.radioAscending);
                if(rbAscending.isChecked()) {
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortorder", "ASC").apply();
                }
                else {
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortorder", "DESC").apply();
                }
            }
        });
    }
}