package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.benktesh.libandroidjoker.Common;
import com.example.benktesh.libandroidjoker.JokerActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private final String TAG = EndpointsAsyncTask.class.getSimpleName();

    private MyApi myApiService = null;
    private Context context;
    private String rootURL;
    private ProgressBar mProgressBar;

    public void setRootURL(String rootURL) {
        this.rootURL = rootURL;
    }

    public EndpointsAsyncTask(Activity activity) {
        context = activity;
        mProgressBar = activity.findViewById(R.id.progressBar);

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected String doInBackground(Void... params) {
        Log.d(TAG, "DoInBackground");


        if (myApiService == null) {  // Only do this once
            //By default, remote is used. For testing, we will set the server to local
            if (rootURL == null) {
                rootURL = Common.RemoteServer;
                //rootURL = Common.DevServer;
            }
            Log.d(TAG, "RootURL is " + rootURL);
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    //.setRootUrl("http://andnanodegree.appspot.com/_ah/api")
                    .setRootUrl(rootURL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            myApiService = builder.build();
        }

        try {
            String result = myApiService.getJoke().execute().getData();
            //Thread.sleep(5000);
            return result;

        } catch (Exception e) {
            return Common.AsyncError;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mProgressBar.setVisibility(View.GONE);
        Intent myIntent = new Intent(context, JokerActivity.class);
        myIntent.putExtra(Common.JOKE, result);
        context.startActivity(myIntent);
    }
}
