package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.example.benktesh.libandroidjoker.Common;
import com.example.benktesh.libandroidjoker.JokerActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private final String TAG = EndpointsAsyncTask.class.getSimpleName();

    private MyApi myApiService = null;
    private Context context;
    private String rootURL;

    public void setRootURL(String rootURL) {
        this.rootURL = rootURL;
    }


    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        Log.d(TAG, "DoInBackground");

        if (myApiService == null) {  // Only do this once
            //By default, remote is used. For testing, we will set the server to local
            if (rootURL == null) {
                rootURL = Common.RemoteServer;
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

        context = params[0].first;
        String name = params[0].second;

        try {
            String result = myApiService.sayHi(name).execute().getData();
            return result;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent myIntent = new Intent(context, JokerActivity.class);
        myIntent.putExtra(Common.JOKE, result);
        context.startActivity(myIntent);
    }
}
