package com.example.benktesh.libjavajoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Joker {
    //from http://pun.me/pages/dad-jokes.php


    List<String> jokes = Arrays.asList(
            "Did you hear about the restaurant on the moon? Great food, no atmosphere.",
            "What do you call a fake noodle? An Impasta.",
            "How many apples grow on a tree? All of them.",
            "Want to hear a joke about paper? Nevermind it's tearable.",
            "I just watched a program about beavers. It was the best dam program I've ever seen.",
            "Why did the coffee file a police report? It got mugged.");
    private int max = 0;

    public Joker() {
        max = jokes.size();
    }

    public String getJoke() {
        Random rand = new Random();
        int  n = rand.nextInt(max) + 1;
        String joke = jokes.get(n);
        return joke;
    }
}
