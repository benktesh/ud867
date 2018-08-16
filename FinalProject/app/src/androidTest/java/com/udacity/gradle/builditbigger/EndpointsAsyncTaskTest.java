package com.udacity.gradle.builditbigger;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.benktesh.libandroidjoker.Common;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    private final String TAG = EndpointsAsyncTaskTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    @UiThreadTest
    public void jokeRetrievalTest() {

        try {

            //ProgressBar progressBar  = MainActivity.gfindViewById(R.id.progressBar);

            EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(activityTestRule.getActivity());
            //Forcing to use dev server in test.
            endpointsAsyncTask.setRootURL(Common.DevServer);
            endpointsAsyncTask.execute();

            Thread.sleep(5000);
            String result = endpointsAsyncTask.get();
            Log.d(TAG, "Joke is " + result);

            Assert.assertFalse("The retrived joke is not an error text", result.equalsIgnoreCase(Common.AsyncError));
            Assert.assertTrue("The retrieved joke must not be empty", result != null
                    && !result.isEmpty() && result.length() > 0);

        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Assert.fail("Test Failed due to exceptions ");
        }

    }

}