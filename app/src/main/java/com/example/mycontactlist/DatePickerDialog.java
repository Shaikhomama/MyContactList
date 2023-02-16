package com.example.mycontactlist;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DatePickerDialog extends DialogFragment {
    Calendar selectedDate;
    public interface SaveDateListener {
        void didFinishDatePickerDialog(Calendar selectedTime);
            }
            public DatePickerDialog(){
                //Need an empty constructor for DialogFragment
            }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.select_date, container); // gets references to the widgets on the layout and sets up listeners for the widgets in the layout so that they can respond to user action
    getDialog().setTitle("Select Date");
    selectedDate = Calendar.getInstance(); //The selectedDate Calendar object is instantiated. By default, it is set to the current date.

    //A listener is set up to capture the selected date anytime the user changes the date.
    final CalendarView cv = view.findViewById(R.id.calendarView2);
    cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
            selectedDate.set(year, month, day);
        }
    });

    //A button-click listener is set up on the Save button. When the user clicks the OK button, it sends the last selected date to the saveItem method to report the selection to the main activity.
    Button saveButton = view.findViewById(R.id.buttonSelect);
    saveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            saveItem(selectedDate);
        }
    });

    Button cancelButton = view.findViewById(R.id.buttonCancel);
    cancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDialog().dismiss();
        }
    });
    return view;
}
private void saveItem(Calendar selectedTime) {    //The saveItem method gets a reference to the listener and calls its method to report the results of the dialog
    SaveDateListener activity = (SaveDateListener) getActivity(); //getActivity is the main activity
    activity.didFinishDatePickerDialog(selectedTime);
    getDialog().dismiss();
}
}
