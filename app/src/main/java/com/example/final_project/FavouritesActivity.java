package com.example.final_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    List<String> mobileArray;
    AlertDialog.Builder builder;
    SQLiteDatabase db;
    private final static int VERSION_NO = 1;
    private FavouritesDB fdb;
    ListView listView;
    ArrayAdapter adapter;
    EditText t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        mobileArray = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        listView = (ListView) findViewById(R.id.favourites);
        int[] colors = {0, Color.rgb(99,94,94), 0}; // red for the example
        listView.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        listView.setDividerHeight(1);
        listView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fdb = new FavouritesDB(this,"favourites_db",null,VERSION_NO);
        getData("favourites");

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
                Intent searchIntent = new Intent(FavouritesActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                return true;

            case R.id.settings:
                Toast.makeText(this, "you clicked on Settings", Toast.LENGTH_SHORT).show();
                Intent settingsIntent = new Intent(FavouritesActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            //handle item2 click event
            case R.id.logout:
                setResult(500);
                Intent logout = new Intent(FavouritesActivity.this,MainActivity.class);
                startActivity(logout);
                return true;

            case R.id.help:
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.settingsMessage).setTitle(R.string.settingsTitle)
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

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveFavourite(View v){
        Date d = new Date();
        t = (EditText) findViewById(R.id.favourite);
        long key = d.getTime();
        fdb.getWritableDatabase().execSQL("INSERT INTO favourites VALUES ("+key+",'"+t.getText().toString()+"')");
        mobileArray.add(t.getText().toString());
        adapter.notifyDataSetChanged();
        t.setText("");

    }
    public void getData(String table_name){
        SQLiteDatabase db = fdb.getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT FAVOURITE FROM " + table_name, null);

        if(cur.getCount() != 0){
            cur.moveToFirst();

            do{
                String row_values = "";

                for(int i = 0 ; i < cur.getColumnCount(); i++){
                    row_values = row_values + " || " + cur.getString(i);
                    mobileArray.add(cur.getString(i));
                    adapter.notifyDataSetChanged();
                }



            }while (cur.moveToNext());
        }
    }

}
