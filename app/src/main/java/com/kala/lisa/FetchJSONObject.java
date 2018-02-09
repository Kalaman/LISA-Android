package com.kala.lisa;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kalaman on 30.01.18.
 */

public class FetchJSONObject extends AsyncTask<Void, Void, JSONObject> {

    URL url;

    public FetchJSONObject(String strUrl) {
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        JSONObject jsonResponse = null;

        try {
            Response response;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            response = client.newCall(request).execute();

            jsonResponse = new JSONObject(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }
}