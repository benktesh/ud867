package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.util.Pair;

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
    public void jokeRetrievalTest() {

        try {
            EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
            //Forcing to use dev server in test.
            endpointsAsyncTask.setRootURL(Common.DevServer);
            endpointsAsyncTask.execute(activityTestRule.getActivity());

            Thread.sleep(5000);
            String result = endpointsAsyncTask.get();
            Log.d(TAG, "Joke is " + result);

            Assert.assertFalse("The retrived joke is not an error text", result.equalsIgnoreCase(Common.AsyncError));
            Assert.assertTrue("The retrieved joke must not be empty", result != null
                    && !result.isEmpty() && result.length() > 0);

        } catch (Exception ex) {
            //Log.d(TAG, "Test Failed");
            Assert.fail("Test Failed due to exceptions ");
        }

    }

}