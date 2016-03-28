package com.grocerylist;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Michael on 3/9/2016.
 */
public class RequestHandler {

    public static final int CONNECTION_TIMEOUT = 1000 * 15;


    //Method to send httpPostRequest
    //This method is taking two arguments
    //First argument is the URL of the script to which we will send the request
    //Other is an HashMap with name value pairs containing the data to be send with the request
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {

        Log.d("(33)requestURL#2", requestURL);
        URL url;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //Create and configure
            url = new URL(requestURL);
            Log.d("(39)url=", url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(CONNECTION_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            //Write parameters
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String writeThis = getPostDataString(postDataParams);
            Log.d("(52)about to write", writeThis);
            writer.write(writeThis);

            Log.d("(55)ordered string", writeThis);
            writer.flush();
            writer.close();
            Log.d("(59)After writer", "close");
            os.close();
            int responseCode = conn.getResponseCode();
            Log.d("(62)responseCode=", conn.getResponseCode() + "");

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.d("(65)Inside if stmt", "ReqHandler");

                InputStream inputStream = new BufferedInputStream(conn.getInputStream());

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(inputStream));
                Log.d("(68)br should be next", "!");

                Log.d("(70)bufferedreader", br.toString());
                stringBuilder = new StringBuilder();
                String response;

/*
                response = br.readLine();
*/
                try {
                    while ((response = br.readLine()) != null) {
                        stringBuilder.append(response);
                        Log.d("Response = ", stringBuilder.toString());
                       // Log.d("(76)response=null", "ERROR"); //////////THIS IS HAPPENING!!! 3/16 9:46pm
                    }
                }
                catch (IOException e) {
                    Log.d("IOException", "e");
                    e.printStackTrace();
                }
                /*else {
                    while (response != null) {
                        stringBuilder.append(response);
                        Log.d("(81)stringBldr", stringBuilder.toString());
                        response = br.readLine();
                    }

                }*/




                //Reading response
                /*while ((response = br.readLine()) != null) {
                    Log.d("(71)in while loop", response);
                    stringBuilder.append(response);
                }
                Log.d("(74)stringBuilder", stringBuilder.toString());*/
            }

        } catch (Exception e) {
            Log.d("(78)Error here", "ERROR");
            e.printStackTrace();
        }
        Log.d("(81)stringBuilder", stringBuilder.toString());
        return stringBuilder.toString();
    }

    private String getPostDataString(HashMap<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                result.append("?");
                Log.d("(92)added ?", result.toString());
                first = false;
            } else {
                result.append("&");
                Log.d("(96)added &", result.toString());

            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.d("(103)Result string", result.toString());
        return result.toString();
        //TEMPORARY CODE!!!
        //return "ItemID=3&ItemName=Bread&ItemUnitType=unit&ItemDescription=Bread+Yo%21&ItemPrice=3.79&ItemCount=7&ItemCategory=Bakery";
    }

    public String sendGetRequest (String requestURL) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public String sendGetRequestParam (String requestURL, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(requestURL + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}