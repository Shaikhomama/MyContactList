package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {

    private Contact currentContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            initContact(extras.getInt("contactID"));
        }
        else{
            currentContact = new Contact();
        }
        setForEditing(false);  //makes the screen not on editing mode when opened
        initChangeDateButton();
        initTextChangedEvents();
        initSaveButton();

    }
    private void initListButton(){                                   //connects to ContactListActivity
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMapButton(){                                   //connects to ContactMapActivity
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ContactMapActivity.class);
                if(currentContact.getContactID() == -1){
                    Toast.makeText(getBaseContext(), "Contacts must be saved before it can be mapped", Toast.LENGTH_LONG).show();
                }
                else{
                    intent.putExtra("contactid",currentContact.getContactID());
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton(){                              //connects to ContactSettingsActivity
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initToggleButton(){
        final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setForEditing(editToggle.isChecked()); //passes as true if the button is toggled, false if not
            }
        });
    }
    private void setForEditing(boolean enabled){
        EditText editName = findViewById(R.id.editName);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editCity = findViewById(R.id.editCity);
        EditText editState = findViewById(R.id.editState);
        EditText editZipCode = findViewById(R.id.editZipcodeText);
        EditText editPhone = findViewById(R.id.editHome);
        EditText editCell = findViewById(R.id.editCell);
        EditText editEMail = findViewById(R.id.editEMail);
        Button buttonChange = findViewById(R.id.btnBirthday);
        Button buttonSave = findViewById(R.id.buttonSave);

        editName.setEnabled(enabled);
        editAddress.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editState.setEnabled(enabled);
        editZipCode.setEnabled(enabled);
        editPhone.setEnabled(enabled);
        editCell.setEnabled(enabled);
        editEMail.setEnabled(enabled);
        buttonChange.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

        if(enabled){
            editName.requestFocus();
        }
        else{
            ScrollView s = findViewById(R.id.scrollview);
            s.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {  //is the code that will handle the date that the user selected.
        TextView birthDay = findViewById(R.id.textBirthday);
        birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        currentContact.setBirthday(selectedTime);
    }

    private void initChangeDateButton(){    //makes it display the dialog
        Button changeDate = findViewById(R.id.btnBirthday);
        changeDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getSupportFragmentManager();  //A FragmentManager is a required object to manage any and all fragments displayed in an activity
                DatePickerDialog datePickerDialog = new DatePickerDialog();  //a new instance of DatePickerDialog class is created
                datePickerDialog.show(fm, "DatePick"); //.show displays the dialog. The method requires an instance of a FragmentManager and a name, which the FragmentManager uses to keep track of the dialog.
            }
        });
    }

    private void initTextChangedEvents(){
        final EditText etContactName = findViewById(R.id.editName);
        etContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setContactName(etContactName.getText().toString());
            }
        });

        final EditText etStreetAddress= findViewById(R.id.editAddress);
        etStreetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setStreetAddress(etStreetAddress.getText().toString());
            }
        });

        final EditText etCity= findViewById(R.id.editCity);
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setCity(etCity.getText().toString());
            }
        });

        final EditText etState= findViewById(R.id.editState);
        etState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setState(etState.getText().toString());
            }
        });

        final EditText etzipcode= findViewById(R.id.editZipcodeText);
        etzipcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setZipCode(etzipcode.getText().toString());
            }
        });

        final EditText etphonenumber = findViewById(R.id.editHome);
        etphonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setPhoneNumber(etphonenumber.getText().toString());
            }
        });

        final EditText etcellnumber= findViewById(R.id.editCell);
        etcellnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setCellNumber(etcellnumber.getText().toString());
            }
        });

        final EditText etemail= findViewById(R.id.editEMail);
        etemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.seteMail(etemail.getText().toString());
            }
        });

        etphonenumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        etcellnumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    //save button code
    private void initSaveButton(){
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                boolean wasSuccessful;
                ContactDataSource ds = new ContactDataSource(MainActivity.this);
                try{
                    ds.open();

                    if (currentContact.getContactID() == -1){
                        wasSuccessful = ds.insertContact(currentContact);
                        int newId = ds.getLastContactID();
                        currentContact.setContactID(newId);

                    }
                    else {
                        wasSuccessful = ds.updateContact(currentContact);
                    }
                    ds.close();
                }catch (Exception ex){
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

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editName = findViewById(R.id.editName);
        imm.hideSoftInputFromWindow(editName.getWindowToken(),0);
        EditText editAddress = findViewById(R.id.editAddress);
        imm.hideSoftInputFromWindow(editAddress.getWindowToken(),0);
        EditText editCity = findViewById(R.id.editCity);
        imm.hideSoftInputFromWindow(editCity.getWindowToken(),0);
        EditText editState = findViewById(R.id.editState);
        imm.hideSoftInputFromWindow(editState.getWindowToken(),0);
        EditText editZipCode = findViewById(R.id.editZipcodeText);
        imm.hideSoftInputFromWindow(editZipCode.getWindowToken(),0);
        EditText editPhone = findViewById(R.id.editHome);
        imm.hideSoftInputFromWindow(editPhone.getWindowToken(),0);
        EditText editCell = findViewById(R.id.editCell);
        imm.hideSoftInputFromWindow(editCell.getWindowToken(),0);
        EditText editEMail = findViewById(R.id.editEMail);
        imm.hideSoftInputFromWindow(editEMail.getWindowToken(),0);
    }

    private void initContact(int id) {
        ContactDataSource ds = new ContactDataSource(MainActivity.this);
        try{
            ds.open();
            currentContact = ds.getSpecificContact(id);
            ds.close();
        }catch(Exception ex){
            Toast.makeText(this,"Load Contact Failed", Toast.LENGTH_LONG).show();
        }
        EditText editName = findViewById(R.id.editName);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editCity = findViewById(R.id.editCity);
        EditText editState = findViewById(R.id.editState);
        EditText editZipCode = findViewById(R.id.editZipcodeText);
        EditText editPhone = findViewById(R.id.editHome);
        EditText editCell = findViewById(R.id.editCell);
        EditText editEMail = findViewById(R.id.editEMail);
        TextView birthday = findViewById(R.id.textBirthday);

        editName.setText(currentContact.getContactName());
        editAddress.setText(currentContact.getStreetAddress());
        editCity.setText(currentContact.getCity());
        editState.setText(currentContact.getState());
        editZipCode.setText(currentContact.getZipCode());
        editPhone.setText(currentContact.getPhoneNumber());
        editCell.setText(currentContact.getCellNumber());
        editEMail.setText(currentContact.geteMail());
        birthday.setText(DateFormat.format("MM/dd/yyyy",
                currentContact.getBirthday().getTimeInMillis()).toString());
    }
}