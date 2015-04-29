package com.example.vicky.testapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class Add_new extends ActionBarActivity implements View.OnClickListener {

    private EditText etTrain;
    private Spinner sClass;
    private DatePicker datePicker;
    private int year, month, day;
    private Calendar calendar;
    private TextView tvDate;
    private ImageButton ibCal;
    private Button bCheck;
    private long today,limitDay;
    private AutoCompleteTextView etFrom,etTo;
    StringArray station_names = new StringArray();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        // Reference to views
        etTrain = (EditText) findViewById(R.id.editText);
        etFrom = (AutoCompleteTextView) findViewById(R.id.editText2);
        etTo = (AutoCompleteTextView) findViewById(R.id.editText3);
        tvDate = (TextView) findViewById(R.id.tv_date);
        ibCal = (ImageButton) findViewById(R.id.imageButton);
        sClass = (Spinner) findViewById(R.id.spinner);
        bCheck = (Button) findViewById(R.id.bCheck);

        station_names.theArray = getResources().getStringArray(R.array.station_names);
        ArrayAdapter<String> autoFill = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, station_names.theArray);
        etTo.setAdapter(autoFill);
        etFrom.setAdapter(autoFill);


        //Spinner Item for class
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classNames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sClass.setAdapter(adapter);

        // Date for calender
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //setCurrentDate(day, month + 1, year);

        ibCal.setOnClickListener(this);
        bCheck.setOnClickListener(this);

    }

    private void setCurrentDate(int day, int month, int year) {
        if(month<10)
            tvDate.setText(new StringBuilder().append(day).append("/").append("0")
                .append(month).append("/").append(year));
        else
            tvDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));

    }

    private void chooseDate()
    {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            long today;
            DatePickerDialog dialog =  new DatePickerDialog(this, myDateListener, year, month, day);
            dialog.getDatePicker().setCalendarViewShown(true);
            dialog.getDatePicker().setSpinnersShown(false);
            today = new Date().getTime();
            dialog.getDatePicker().setMinDate(today-1000);
            dialog.getDatePicker().setMaxDate(today+5184000000l);

            return dialog;

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener
            = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            setCurrentDate(arg3, arg2 + 1, arg1);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        boolean fill;
        switch(v.getId())
        {
            case R.id.imageButton:
                chooseDate();
                break;
            case R.id.bCheck:
                fill = check_all_fields();
                if(fill == true)
                {
                    Intent intent = new Intent(this,GetSeats.class);
                    intent.putExtra("TrainNo",etTrain.getText().toString());
                    intent.putExtra("From",etFrom.getText().toString());
                    intent.putExtra("To",etTo.getText().toString());
                    intent.putExtra("Date",tvDate.getText().toString());
                    intent.putExtra("Class",sClass.getSelectedItemPosition());
                    startActivity(intent);
                }
                break;
        }
    }

    // Check if all filled
    private boolean check_all_fields() {

        if(etTrain.getText().length() < 5){
        //Show Toast
            Toast.makeText(getApplicationContext(), "Enter 5 Digit Train Number!",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        Arrays.sort(station_names.theArray);

        if (Arrays.binarySearch(station_names.theArray, etFrom.getText().toString()) <= 0) {
            Toast.makeText(getApplicationContext(), "Enter proper originating station !",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (Arrays.binarySearch(station_names.theArray, etTo.getText().toString()) <= 0) {
            Toast.makeText(getApplicationContext(), "Enter proper destination station !",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(sClass.getSelectedItemPosition()==0){
            Toast.makeText(getApplicationContext(), "Select Class!",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if(tvDate.getText().length() == 0){
            //Show Toast
            Toast.makeText(getApplicationContext(), "Enter Date of Journey!",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
