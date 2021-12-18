package com.example.final_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    AlertDialog.Builder builder;

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
            // get the values for day of month , month and year from a date picker.
            int day = simpleDatePicker.getDayOfMonth();
            int month = (simpleDatePicker.getMonth() + 1);
            int year = simpleDatePicker.getYear();
            // send values back to DashboardActivity.
            Intent intent = new Intent();
            intent.putExtra("day", day);
            intent.putExtra("month", month);
            intent.putExtra("year", year);
            setResult(1, intent);
            finish();
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
                Intent logout = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(logout);
                return true;

            case R.id.favourites: {
                Toast.makeText(this, "you clicked on Favourites", Toast.LENGTH_SHORT).show();
                Intent favouritesIntent = new Intent(SearchActivity.this, FavouritesActivity.class);
                startActivity(favouritesIntent);
                return true;
            }

            case R.id.help:
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.searchMessage).setTitle(R.string.searchTitle)
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}