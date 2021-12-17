package com.example.final_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    String[] mobileArray = {"About this Application","Check for Updates","Support the Developer"};
    AlertDialog.Builder builder;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                TextView v = (TextView) view.findViewById(R.id.label);

                if(v.getText().equals("About this Application")){
                    builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage("This application fetches NASA's daily image, as well as the explanation for said image. Enjoy!").setTitle(R.string.settingsTitle)
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                else if(v.getText().equals("Check for Updates")){
                    builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage("You are up to date! Try again in the future.").setTitle(R.string.settingsTitle)
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                else if(v.getText().equals("Support the Developer")){
                    builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage("Thanks for choosing to support this project, your bonus marks are highly appreciated!").setTitle(R.string.settingsTitle)
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

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

            case R.id.help:
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.settingsMessage).setTitle(R.string.settingsTitle)
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
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
