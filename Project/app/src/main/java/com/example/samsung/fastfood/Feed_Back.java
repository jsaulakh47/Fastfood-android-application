package com.example.samsung.fastfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 11/24/2017.
 */

public class Feed_Back extends Activity {
    String commentfeed;
    String userfeedback,email;
    String resfeedback;
    DataBaseOpenHelper helper=new DataBaseOpenHelper(this);
    SharedPreferences sharedPreferences;

    ServiceHandler serviceHandler;
    List<NameValuePair> nameValuePairsList;
    String url;
    EditText cmt;
    Context context;
    TextView des;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        context = this;
        cmt = (EditText) findViewById(R.id.cmt);
        des=(TextView)findViewById(R.id.des);
        sharedPreferences = getSharedPreferences("Mydata112", context.MODE_PRIVATE);
        userfeedback = sharedPreferences.getString("username", "null");
        email = sharedPreferences.getString("emailAddress", "null");

    }

    public void sub(View view) {

        commentfeed = cmt.getText().toString();

        Feed feed=new Feed();
        feed.setUser(userfeedback);
        feed.setComment(commentfeed);
        feed.setEmail(email);
        helper.insert_feed(feed);
        if(isStringFeed(commentfeed)){
            if (AppStatus.getInstance(this).isOnline()) {

                new FeedAsync().execute();

            } else {
                Toast.makeText(Feed_Back.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(Feed_Back.this, "Enter Feed back", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isStringFeed(String commentfeed) {
        if(commentfeed != null && !commentfeed.equals("") && commentfeed.length()>0){
            return true;
        }else {
            return false;
        }
    }

    class FeedAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            serviceHandler = new ServiceHandler();
            url = "http://92.42.108.26:8080/myProject11-war/HomeDeliveryServices";
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("username", userfeedback);
                jObj.put("comment", commentfeed);

                Log.e("Log", "json data--------" + jObj.toString());

                nameValuePairsList = new ArrayList<NameValuePair>();
                nameValuePairsList.add(new BasicNameValuePair("btn", "Feedback"));
                nameValuePairsList.add(new BasicNameValuePair("feedbackStr", jObj.toString()));


            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(Feed_Back.this, "Error", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {

            resfeedback = serviceHandler.makeServiceCall(url, ServiceHandler.GET, nameValuePairsList);
            Log.e("Log", "res : " + resfeedback);
            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String valuefeedback;

            if (resfeedback != null) {
                try {
                   JSONObject jobj = new JSONObject(resfeedback);
                    valuefeedback = jobj.getString("result");
                    Toast.makeText(Feed_Back.this, "Done"+valuefeedback, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Feed_Back.this,Main_Menu.class);
                    startActivity(intent);
                    Log.e("Log", "Value--------------" + valuefeedback);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(Feed_Back.this, "Not Done", Toast.LENGTH_SHORT).show();
            }
        }
    }
}




