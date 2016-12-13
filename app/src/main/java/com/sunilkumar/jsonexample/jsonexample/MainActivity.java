package com.sunilkumar.jsonexample.jsonexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //UI elements
    Button retrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference to UI elements
        retrieve = (Button) findViewById(R.id.retrieve);

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RetrieveJSON().execute();
            }
        });
    }

    class RetrieveJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //Custom HTTP connection class (Ctrl+Click on HTTPHandler to see)
            HTTPHandler httpHandler = new HTTPHandler();
            //This is a public URL for testing
            String url = "https://httpbin.org/get";
            String json = httpHandler.makeServiceCall(url);
            Log.i("Response from url", json);
            if (!json.isEmpty()) {
                try {
                    JSONObject jsonObject = new JSONObject(json);

                    JSONObject innerObj = jsonObject.getJSONObject("headers");

                    final String host = jsonObject.getString("origin");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Data from httpbin.org :" + host, Toast.LENGTH_LONG).show();
                        }
                    });


                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Could not receive data from server " +
                                "check your internet connection", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }
}
