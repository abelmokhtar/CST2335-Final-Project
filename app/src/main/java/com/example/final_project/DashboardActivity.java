package com.example.final_project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private NavigationView nv;
    Toolbar tb;
    ProgressDialog pd;
    TextView txtJson;
    AlertDialog.Builder builder;
    ProgressBar progressBar;
    RelativeLayout dashboardLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txtJson = (TextView) findViewById(R.id.jsonView);
        dashboardLayout = (RelativeLayout) findViewById(R.id.dashboardLayout);
        progressBar = findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);

        // assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        new JsonTask().execute("https://api.nasa.gov/planetary/apod?api_key=YgIlSyaTQTUuBWLPxzkFj3Yi3neBFz9cJScGZhkL");

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
            case R.id.search: {
                Toast.makeText(this, "you clicked on Search", Toast.LENGTH_SHORT).show();
                Intent searchIntent = new Intent(DashboardActivity.this, SearchActivity.class);
                startActivityForResult(searchIntent,1);
                return true;
            }
            case R.id.settings: {
                Toast.makeText(this, "you clicked on Settings", Toast.LENGTH_SHORT).show();
                Intent settingsIntent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            }
            case R.id.favourites: {
                Toast.makeText(this, "you clicked on Favourites", Toast.LENGTH_SHORT).show();
                Intent favouritesIntent = new Intent(DashboardActivity.this, FavouritesActivity.class);
                startActivity(favouritesIntent);
                return true;
            }
            case R.id.help:
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dashboardMessage).setTitle(R.string.dashboardTitle)
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

            //handle item2 click event
            case R.id.logout:
                setResult(500);
                Intent logout = new Intent(DashboardActivity.this,MainActivity.class);
                startActivity(logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
    protected class JsonTask extends AsyncTask<String, String, String> {

        private Context context;
        private String url;

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(DashboardActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            //pd.show();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                    progressBar.setProgress(50);
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            JSONObject json = null;
            try {
                json = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String imgUrl = null;
            try {
                imgUrl = json.getString("url");
                new ImageTask().execute(imgUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String imageDesc = null;
            try {
                imageDesc = json.getString("explanation");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            txtJson.setText(imageDesc);

        }

        public void setImage(String url){

        }
    }

    protected class ImageTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            Bitmap b = downloadBitmap(strings[0]);
            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] bytes = baos.toByteArray();
            String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
            return temp;
        }
        public Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    progressBar.setProgress(100);
                    return bitmap;
                }
            } catch (Exception e) {
                Log.d("URLCONNECTIONERROR", e.toString());
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                Log.w("ImageDownloader", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();

                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                byte [] encodeByte=Base64.decode(result,Base64.DEFAULT);
                Bitmap b=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                ImageView iv = findViewById(R.id.nasaImage);
                if(b!=null)
                    iv.setImageBitmap(b);
                else
                    Snackbar.make(dashboardLayout, "Error displaying image...", Snackbar.LENGTH_SHORT).show();
                ;
                progressBar.setVisibility(View.GONE);

            } catch(Exception e) {
                e.getMessage();

            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == 1) {
                int year = data.getIntExtra("year",2021);
                int month = data.getIntExtra("month",12);
                int day = data.getIntExtra("day",12);
                new JsonTask().execute("https://api.nasa.gov/planetary/apod?api_key=YgIlSyaTQTUuBWLPxzkFj3Yi3neBFz9cJScGZhkL&date="+year+"-"+month+"-"+day);
            }
        }
    }
}



