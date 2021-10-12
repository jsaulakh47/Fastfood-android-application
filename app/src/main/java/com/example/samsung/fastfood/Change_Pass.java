package com.example.samsung.fastfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.samsung.fastfood.R.id.s_pass;

/**
 * Created by SAMSUNG on 12/1/2017.
 */

public class Change_Pass extends Activity {
    EditText old_pass,new_pass,user;
    TextView change;
    String url,Old,New,User,resfeedback,Old_user,Old_pass;
    ServiceHandler serviceHandler;
    List<NameValuePair> nameValuePairsList;
    Context context;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass);
        old_pass=(EditText) findViewById(R.id.old_pass);
        new_pass=(EditText) findViewById(R.id.new_pass);
        user=(EditText) findViewById(R.id.user);
        context=this;

        change=(TextView)findViewById(R.id.change);
        sharedPreferences = getSharedPreferences("Mydata112", context.MODE_PRIVATE);
        Old_user = sharedPreferences.getString("username", "null");
        Old_pass = sharedPreferences.getString("password", "null");


      user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        old_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    String pass = old_pass.getText().toString();
                    if(TextUtils.isEmpty(pass) || pass.length() < (7))
                    {
                        old_pass.setError("You must have 7 characters in your password");
                        return;
                    }
                }
            }
        });
        new_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    String pass = new_pass.getText().toString();
                    if(TextUtils.isEmpty(pass) || pass.length() < (7))
                    {
                       new_pass.setError("You must have 7 characters in your password");
                        return;
                    }
                }
            }
        });


    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void set(View view){
        User=user.getText().toString();
        Old= old_pass.getText().toString();
        New= new_pass.getText().toString();

        if(isStringUser(User) && isStringOld(Old) && isStringNew(New)){

            if(TextUtils.isEmpty(Old) || Old.length() < (7))
            {
                Toast.makeText(this,"Please Correct Old Password",Toast.LENGTH_SHORT).show();
            }else {
                if(TextUtils.isEmpty(New) || New.length() < (7))
                {
                    Toast.makeText(this,"Please Correct New Password",Toast.LENGTH_SHORT).show();
                }else {
                    if (AppStatus.getInstance(this).isOnline()) {

                        new ChangeAsync().execute();

                    } else {

                        Toast.makeText(Change_Pass.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }else {
            Toast.makeText(Change_Pass.this,"Enter Values",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isStringNew(String aNew) {
        if(aNew != null && !aNew.equals("") && aNew.length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean isStringOld(String old) {
        if(old != null && !old.equals("") && old.length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean isStringUser(String user) {
        if(user != null && !user.equals("") && user.length()>0){
            return true;
        }else {
            return false;
        }
    }

    class ChangeAsync extends AsyncTask{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            serviceHandler = new ServiceHandler();
            url = "http://92.42.108.26:8080/myProject11-war/HomeDeliveryServices";
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("username", User);
                jObj.put("old password", Old);
                jObj.put("new password", New);

                Log.e("Log", "json data--------" + jObj.toString());

                nameValuePairsList = new ArrayList<NameValuePair>();
                nameValuePairsList.add(new BasicNameValuePair("btn", "Change Password"));
                nameValuePairsList.add(new BasicNameValuePair("changeStr", jObj.toString()));


            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(Change_Pass.this, "Error", Toast.LENGTH_SHORT).show();
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
            if(Old_user.equals(User)){
                if(Old_pass.equals(Old)){
                    if(resfeedback.contains("successful")){

                       Toast.makeText(Change_Pass.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Change_Pass.this,Main_Menu.class);
                        startActivity(intent);
                   }else {
                       Toast.makeText(Change_Pass.this, "Un-Successful", Toast.LENGTH_SHORT).show();
                   }


                }else {
                    Toast.makeText(Change_Pass.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(Change_Pass.this, "Invalid Username", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
