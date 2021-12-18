package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        user.setText("");
        pass.setText("");
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.toggle);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (rb!=null){
                    switch(checkedId) {
                        case R.id.english:
                            // do operations specific to this selection
                            Locale locale = new Locale("en");
                            rb.setChecked(true);
                            Locale.setDefault(locale);
                            Configuration configuration = new Configuration();
                            configuration.locale = locale;
                            getBaseContext().getResources().updateConfiguration(configuration,
                                    getBaseContext().getResources().getDisplayMetrics());

                            break;
                        case R.id.french:
                            locale = new Locale("fr");
                            rb.setChecked(true);
                            Locale.setDefault(locale);
                            configuration = new Configuration();
                            configuration.locale = locale;
                            getBaseContext().getResources().updateConfiguration(configuration,
                                    getBaseContext().getResources().getDisplayMetrics());
                            break;

                        default:
                            locale = new Locale("en");
                            rb.setChecked(true);
                            Locale.setDefault(locale);
                            configuration = new Configuration();
                            configuration.locale = locale;
                            getBaseContext().getResources().updateConfiguration(configuration,
                                    getBaseContext().getResources().getDisplayMetrics());
                    }

                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("id",user.getText().toString() );
        Ed.putString("password",pass.getText().toString() );
        Ed.apply();


    }

    public void startProfile(View v) {
        if(user.getText().toString().equals("")|| pass.getText().toString().equals(""))
            Snackbar.make(getWindow().getDecorView().getRootView(), "Missing username and/or password.", Snackbar.LENGTH_LONG).show();
        else{
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
        }

    }
}