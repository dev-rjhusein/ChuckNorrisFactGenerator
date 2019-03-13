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
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class GetAJoke implements Runnable {

    private Logger logger = Logger.getLogger("GetAJoke Logger");

    private JSONParser parser = new JSONParser();

    private int errorCount = 1;

    private CountDownLatch cnt;

    Thread thread;

    GetAJoke(CountDownLatch cnt) {
        this.cnt = cnt;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {

        // Set chucknorris.io URL to random
        URL randomJokeURL = setURL();

        //Connect to API
        HttpURLConnection connection = connectToApi(randomJokeURL);

        //Convert return JSON to a JSON object
        JSONObject response = jsonResponse(connection);

        //Display the return value
        System.out.println(response.get("value"));

        //Decrement the CountDownLatch
        cnt.countDown();

    }

    private URL setURL(){
        //REST api URL ATTRIBUTION: CHUCKNORRIS.IO
        URL url;
        try {
            url = new URL("https://api.chucknorris.io/jokes/random");
        }catch(MalformedURLException err){
            logger.warning(">>> URL is malformed");
            throw new RuntimeException("Program exiting -- URL could not reach server");
        }
        return url;
    }

    private HttpURLConnection connectToApi(URL url){
        HttpURLConnection connection;
        try {
            //Set connection from JSON format
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            //If the site doesn't connect three times, terminate
            if (connection.getResponseCode() != 200) {
                if (++errorCount > 3) {
                    logger.warning(">>> Too many failures -- Exiting.");
                    throw new RuntimeException("Program Shutdown Unexpectedly");
                }
                logger.warning(">>> Couldn't connect to server. Try again later");
                throw new RuntimeException("Program Shutdown Unexpectedly");
            }
        }catch(IOException err){
            logger.warning(">>> Corrupt Connection");
            throw new RuntimeException("The connection couldn't be made. Are you connected to the internet?");
        }

        return connection;
    }

    private JSONObject jsonResponse(HttpURLConnection connection){
        //Put JSON response into BufferedReader
        BufferedReader restResponse;
        try {
            restResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }catch(IOException err){
            logger.warning(">>> Data format refused proper conversion");
            throw new RuntimeException("Program Shutdown Unexpectedly");
        }

        //Convert to JSON Object [GOOGLE SIMPLE JSON]
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(restResponse);
        }catch(IOException | ParseException err){
            logger.warning(">>> Data format refused proper conversion");
            throw new RuntimeException("Program Shutdown Unexpectedly");
        }

        return obj;
    }
}
