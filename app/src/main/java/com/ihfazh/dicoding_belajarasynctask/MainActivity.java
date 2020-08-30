package com.ihfazh.dicoding_belajarasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements MyAsyncCallback {

    private TextView tvStatus, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvStatus = findViewById(R.id.status);
        tvDescription = findViewById(R.id.desc);

        DemoAsync demoAsync = new DemoAsync(this);
        demoAsync.execute("Hello world... hahaha");

    }

    @Override
    public void onPreExecute() {
        tvStatus.setText("Pre Status.....");
        tvDescription.setText("Hello world... hahaha");
    }

    @Override
    public void onPostExecute(String text) {
        tvStatus.setText("Post status.....");

        if (text!= null){
            tvDescription.setText(text);
        }

    }

    private static class DemoAsync extends AsyncTask<String, Void, String> {
        static final String LOG_ASYNC = "DemoAsync";
        WeakReference<MyAsyncCallback> myListener;
        DemoAsync(MyAsyncCallback myListener){
            this.myListener = new WeakReference<>(myListener);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(LOG_ASYNC, "Status: on Running Job");
            String output = null;

            try {
                String input = strings[0];
                output = input + " Selamat belajar";
                Thread.sleep(2000);

            } catch (Exception e) {
                Log.d(LOG_ASYNC, e.getMessage());
            }

            return output;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "Status: on PreExecute");

            MyAsyncCallback listener = this.myListener.get();
            if (listener != null){
                listener.onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(LOG_ASYNC, "Status: on PostExecute");

            MyAsyncCallback listener = this.myListener.get();
            if (listener != null){
                listener.onPostExecute(s);
            }
        }
    }
}

interface MyAsyncCallback {
    void onPreExecute();
    void onPostExecute(String text);
}
