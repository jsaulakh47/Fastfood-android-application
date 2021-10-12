package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 12/5/2017.
 */

public class Forgot_Pass2 extends Activity {
    EditText user,ans;
    Context context;
    String sign_ans,User,url,Ans, resfeedback,  sign_pass ;
    ServiceHandler serviceHandler;
    SharedPreferences sharedPreferences;
    List<NameValuePair> nameValuePairsList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass2);
        user=(EditText)findViewById(R.id.user);
        ans=(EditText)findViewById(R.id.ans);
        context=Forgot_Pass2.this;
        sharedPreferences = getSharedPreferences("Mydata111", context.MODE_PRIVATE);
        sign_ans = sharedPreferences.getString("answer", "null");
        sign_pass = sharedPreferences.getString("password", "null");
    }

    public void set(View view){
        User=user.getText().toString();
        Ans=ans.getText().toString();

        if(isStringUser(User) && isStringAns(Ans)){
            if (AppStatus.getInstance(this).isOnline()) {

                new Forgot_pass2_A().execute();
            } else {

                Toast.makeText(Forgot_Pass2.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(Forgot_Pass2.this,"Enter Values",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isStringAns(String ans) {
        if(ans != null && !ans.equals("") && ans.length()>0){
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


    class Forgot_pass2_A extends AsyncTask{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            serviceHandler = new ServiceHandler();
            url = "http://92.42.108.26:8080/myProject11-war/HomeDeliveryServices";
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("username", User);
                jObj.put("answer", Ans);


                Log.e("Log", "json data--------" + jObj.toString());

                nameValuePairsList = new ArrayList<NameValuePair>();
                nameValuePairsList.add(new BasicNameValuePair("btn", "confirm"));
                nameValuePairsList.add(new BasicNameValuePair("confirmStr", jObj.toString()));


            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(Forgot_Pass2.this, "Error", Toast.LENGTH_SHORT).show();
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
                        if(resfeedback.contains("successful")){

                            JSONObject jsonObject =new JSONObject(resfeedback);
                          String data=  jsonObject.getString("password");

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Your Password");
                            builder.setMessage("your password is: "+data);
                            builder.setCancelable(false);
                            builder.setPositiveButton(
                                    "Go to Login",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(Forgot_Pass2.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                            AlertDialog alert = builder.create();

                            alert.show();


                    }else {
                        Toast.makeText(Forgot_Pass2.this, "Invalid answer", Toast.LENGTH_SHORT).show();
                    }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

        }
    }
}
