package com.example.final_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    String[] mobileArray = {"About this Application","Check for Updates","Support","Help"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        ListView listView = (ListView) findViewById(R.id.settings);
        int[] colors = {0, Color.rgb(99,94,94), 0}; // red for the example
        listView.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        listView.setDividerHeight(1);
        listView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //creating an object of the MenuInflater to inflate the menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Using a switch to determine which item was clicked and then perfomrming a desired action
        switch (item.getItemId()) {

            //handle item1 click event
            case R.id.search:
                Toast.makeText(this, "you clicked on Search", Toast.LENGTH_SHORT).show();
                Intent searchIntent = new Intent(SettingsActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                return true;

            case R.id.settings:
                Toast.makeText(this, "you clicked on Settings", Toast.LENGTH_SHORT).show();
                Intent settingsIntent = new Intent(SettingsActivity.this, SettingsActivity.class);
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
