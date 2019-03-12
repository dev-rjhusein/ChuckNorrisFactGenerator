package org.devrj;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class GetAJoke implements Runnable {

    Logger logger = Logger.getLogger("My Logger");

    Thread thread;

    GetAJoke() {
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        //REST api URL ATTRIBUTION: CHUCKNORRIS.IO
        URL url;
        try {
            url = new URL("https://api.chucknorris.io/jokes/random");
        }catch(MalformedURLException err){
            err.printStackTrace();
            throw new RuntimeException("DANG! The URL was messed up...");
        }


        BufferedReader restResponse;
        JSONParser parser = new JSONParser();
        Integer intNumberOfFacts = 1;

        int errorCount = 1;


        HttpURLConnection connection;
        try {
            //Set connection from JSON format
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            //If the site doesn't connect three times, terminate
            if (connection.getResponseCode() != 200) {
                if (++errorCount > 3) {
                    System.out.println("Too many failures -- Exiting.");
                    return;
                }
                System.out.println(connection.getResponseCode());
                System.out.println("Ehh, something went wrong. Let's try again...");
                return;
            }
        }catch(IOException err){
            err.printStackTrace();
            throw new RuntimeException("The connection couldn't be made. Are you connected to the internet?");
        }

        //Put JSON response into BufferedReader
        try {
            restResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }catch(IOException err){
            err.printStackTrace();
            throw new RuntimeException("The data didn't convert well. We done messed up, bro...");
        }

        //Convert to JSON Object [GOOGLE SIMPLE JSON]
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(restResponse);
        }catch(IOException | ParseException err){
            err.printStackTrace();
            throw new RuntimeException("The response wasn't quite what we expected... Sorry.");
        }

        System.out.println(obj.get("value"));


        //Merge thread with the main thread;
        try {
            this.thread.join();
        }catch(InterruptedException err){
            System.out.println("Couldn't join " + this.thread.getName() + " to main thread");
        }
    }
}
