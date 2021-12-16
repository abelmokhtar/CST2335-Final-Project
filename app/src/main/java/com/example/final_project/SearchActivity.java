package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SearchActivity extends AppCompatActivity {

    DatePicker simpleDatePicker;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // initiate the date picker and a button
        simpleDatePicker = (DatePicker) findViewById(R.id.searchDate);
        submit = (Button) findViewById(R.id.submitButton);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // perform click event on submit button
        submit.setOnClickListener(v -> {
            // get the values for day of month , month and year from a date picker
            String day = "Day = " + simpleDatePicker.getDayOfMonth();
            String month = "Month = " + (simpleDatePicker.getMonth() + 1);
            String year = "Year = " + simpleDatePicker.getYear();
            // display the values by using a toast
            Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //creating an object of the MenuInflater to inflate the menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_menu, menu);
        //  menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Using a switch to determine which item was clicked and then perfomrming a desired action
        switch (item.getItemId()) {

            //handle item1 click event
            case R.id.search:
                Toast.makeText(this, "you clicked on Search", Toast.LENGTH_SHORT).show();
                Intent searchIntent = new Intent(SearchActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                return true;

            case R.id.settings:
                Toast.makeText(this, "you clicked on Settings", Toast.LENGTH_SHORT).show();
                Intent settingsIntent = new Intent(SearchActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            //handle item2 click event
            case R.id.logout:
                setResult(500);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}