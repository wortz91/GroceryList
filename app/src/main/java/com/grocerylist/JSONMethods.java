package com.grocerylist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Nicholas on 3/3/2016.
 */
public class JSONMethods {

    public JSONArray GetShoppingList(int userID) {
        String url = "http://w16groc.franklinpracticum.com/select_script.php?UserID=" + userID;

        HttpEntity httpEntity = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
        } catch (ClientProtocolException cpe) {
            cpe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        JSONArray jsonArray = null;
        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response :", entityResponse);
                jsonArray = new JSONArray(entityResponse);
            } catch (JSONException jsone) {
                jsone.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return jsonArray;
    }
}
