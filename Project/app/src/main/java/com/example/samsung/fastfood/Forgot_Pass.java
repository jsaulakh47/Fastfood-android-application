package com.example.samsung.fastfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 12/5/2017.
 */

public class Forgot_Pass extends Activity {
    Spinner spinner;
    String spin, User, url, resfeedback, sign_ques;
    EditText user;
    List<NameValuePair> nameValuePairsList;
    ServiceHandler serviceHandler;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
        spinner = (Spinner) findViewById(R.id.spinner);
        user = (EditText) findViewById(R.id.user);
        context = Forgot_Pass.this;
        sharedPreferences = getSharedPreferences("Mydata112", context.MODE_PRIVATE);
        sign_ques = sharedPreferences.getString("question", "null");


        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.questions, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Forgot_Pass.this, "Select Value", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void set(View view) {
        User = user.getText().toString();

        if (isStringUser(User)) {
            if (AppStatus.getInstance(this).isOnline()) {

                new ForgotAsync().execute();
            } else {

                Toast.makeText(Forgot_Pass.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Forgot_Pass.this, "Enter Values", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isStringUser(String user) {
        if (user != null && !user.equals("") && user.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    class ForgotAsync extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            serviceHandler = new ServiceHandler();
            url = "http://92.42.108.26:8080/myProject11-war/HomeDeliveryServices";
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("username", User);
                jObj.put("question", spin);


                Log.e("Log", "json data--------" + jObj.toString());

                nameValuePairsList = new ArrayList<NameValuePair>();
                nameValuePairsList.add(new BasicNameValuePair("btn", "Forgot Password"));
                nameValuePairsList.add(new BasicNameValuePair("forgotStr", jObj.toString()));


            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(Forgot_Pass.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            resfeedback = serviceHandler.makeServiceCall(url, ServiceHandler.GET, nameValuePairsList);
            Log.e("Log", "res : " + resfeedback);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {


                if (resfeedback.length() > 0) {

                    JSONObject jobj = new JSONObject(resfeedback);
                    String data = jobj.getString("question");

                    if (data.equals(spin)) {

                        Toast.makeText(Forgot_Pass.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Forgot_Pass.this, Forgot_Pass2.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(Forgot_Pass.this, "Invalid question section", Toast.LENGTH_SHORT).show();

                    }
                } else {

                    Toast.makeText(Forgot_Pass.this, "Un-Successful", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
