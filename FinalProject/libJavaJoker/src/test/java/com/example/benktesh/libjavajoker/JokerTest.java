package com.example.benktesh.libjavajoker;


import org.junit.Assert;

import static org.junit.Assert.fail;

public class JokerTest {

    @org.junit.Test
    public void getJokeTest() {
        try {
            for (int i = 0; i < 10000; i++) {
                Joker joker = new Joker();
                String joke = joker.getJoke();
                //System.out.println(i + " : " + joker.getJoke());
                Assert.assertTrue("Joke must be of none zero length",
                        joke.length() > 0);
            }
        } catch (Exception ex) {
            fail(); //any randomization or index error will be caught here.
        }
    }
}