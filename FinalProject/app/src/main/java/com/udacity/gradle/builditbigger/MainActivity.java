package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

//imported  library
import com.example.benktesh.libjavajoker.Joker;
import com.example.benktesh.libandroidjoker.JokerActivity;
import com.example.benktesh.libandroidjoker.Common;


public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        String joke = "I do not have a joke";
        try {
            Joker joker = new Joker();
            joke = joker.getJoke();
            //Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex) {
            Log.e(TAG, "Could not get Joke" + ex.getMessage());
        }
        launchJokeActivity(joke);

    }

    private void launchJokeActivity(String joke) {
        Intent intent = new Intent(this, JokerActivity.class );
        intent.putExtra(Common.JOKE, joke);
        startActivity(intent);
    }

}
