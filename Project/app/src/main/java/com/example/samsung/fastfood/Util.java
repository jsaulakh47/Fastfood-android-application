package com.example.samsung.fastfood;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by SAMSUNG on 11/6/2017.
 */

public class Util {

    public static String getHttpMethod(String url) throws Exception {
        String jsonResponse = "";

        Log.e(TAG, "XML response getHttpMethod: URL===== " + url);

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            final HttpParams httpParameters = httpClient.getParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 60 * 1000);
            HttpConnectionParams.setSoTimeout(httpParameters, 60 * 1000);


            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            HttpGet httpGet = new HttpGet(url);

            httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();

            jsonResponse = EntityUtils.toString(httpEntity);

        } catch (Exception e) {
            Log.e(TAG, "getHttpMethod: EXCEPTION CAUGHT", e);
        }
        Log.e(TAG, "XML response getHttpMethod: response===== " + jsonResponse);
        return jsonResponse;
    }



}
