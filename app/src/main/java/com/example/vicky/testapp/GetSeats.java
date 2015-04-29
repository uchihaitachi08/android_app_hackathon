package com.example.vicky.testapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GetSeats extends ActionBarActivity implements View.OnClickListener {

    private TextView tvTrainNum,tvSeats;
    private EditText etRemainder,tvT;
    private Button bSave;
    private final static String STORETEXT="history_data.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_seats);

        tvTrainNum = (TextView) findViewById(R.id.setT);
        tvSeats = (TextView) findViewById(R.id.setAS);
        etRemainder = (EditText) findViewById(R.id.setSL);
        bSave = (Button) findViewById(R.id.saveB);
        bSave.setOnClickListener(this);
        tvT = (EditText)findViewById(R.id.etTT);

        Bundle data = getIntent().getExtras();
        String stationName = "";
        if (data != null)
        {
            tvTrainNum.setText(data.getString("TrainNo"));
            stationName = data.getString("From");
        }
        tvSeats.setText("100");
        String stationCode = retriveCode(stationName);
        tvT.setText(stationCode);

    }

    private String retriveCode(String stationName) {
        String stationCode;
        int posn = stationName.indexOf(" - ");
        stationCode = stationName.substring(posn+3);
        return stationCode;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_seats, menu);
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
        switch(v.getId()){
            case R.id.saveB:
                boolean b = validateSeats();
                if(b){
                    Toast.makeText(getApplicationContext(), "Saved!",
                            Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    private boolean validateSeats() {
        if(etRemainder.getText().length() == 0){
            //Show Toast
            Toast.makeText(getApplicationContext(), "Enter limit of seats!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(Integer.parseInt(etRemainder.getText().toString())>= Integer.parseInt(tvSeats.getText().toString())){
            //Show Toast
            Toast.makeText(getApplicationContext(), "Entered value should be less than seats available!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
