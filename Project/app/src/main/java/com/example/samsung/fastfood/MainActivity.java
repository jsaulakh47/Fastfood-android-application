package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.WindowManager;
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

public class MainActivity extends Activity {
    EditText pass, user;
    TextView  text2, text3,txtView;
    Button b_login, b_signUp;
    Context context;
    String res;
    String url, userStr, passStr;
    List<NameValuePair> nameValuePairsList;
    SharedPreferences sharedPreferences;
    ServiceHandler serviceHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pass = (EditText) findViewById(R.id.pass);
        user = (EditText) findViewById(R.id.user);
        b_signUp = (Button) findViewById(R.id.b_SignUp);
        b_login = (Button) findViewById(R.id.b_login);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);

        context=MainActivity.this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

      txtView = (TextView) findViewById(R.id.forgot);

        Linkify.addLinks(txtView, Linkify.ALL);

        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Forgot_Pass.class);
                startActivity(intent);
            }
        });


        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.b_SignUp) {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);

        }
    }


    class LoginAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            serviceHandler = new ServiceHandler();
            nameValuePairsList = new ArrayList<NameValuePair>();
            nameValuePairsList.add(new BasicNameValuePair("btn",
                    "LOGIN"));
            nameValuePairsList.add(new BasicNameValuePair("username", userStr));

            nameValuePairsList.add(new BasicNameValuePair("password", passStr));
            url = "http://92.42.108.26:8080/myProject11-war/HomeDeliveryServices";
        }

        @Override
        protected Void doInBackground(Void... voids) {

            res = serviceHandler.makeServiceCall(url, ServiceHandler.GET, nameValuePairsList);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (res.length() > 0) {
                if (res.contains("Customer not exist")) {
                    Toast.makeText(context, "Invalid details entered, please try again", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        JSONObject jobj = new JSONObject(res);

                        if (jobj != null) {

                          String firstName=  jobj.getString("FirstName");
                         String lastName=   jobj.getString("LastName");
                           String birthDate= jobj.getString("Birth_Date");
                           String phoneNo= jobj.getString("Phone_No");
                           String emailAddress= jobj.getString("Email_Address");
                           String address= jobj.getString("Address");
                          String city=  jobj.getString("City");
                          String question=  jobj.getString("Question");
                         String answer=  jobj.getString("Answer");
                         String username=   jobj.getString("Username");
                         String password=   jobj.getString("Password");

                            sharedPreferences = getSharedPreferences("Mydata112", context.MODE_PRIVATE);


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("firstname",firstName);
                            editor.putString("lastname",lastName);
                            editor.putString("birthDate",birthDate);
                            editor.putString("phoneNo",phoneNo);
                            editor.putString("emailAddress",emailAddress);
                            editor.putString("address",address);
                            editor.putString("city",city);
                            editor.putString("question",question);
                            editor.putString("answer",answer);
                            editor.putString("username",username);
                            editor.putString("password",password);

                            editor.commit();


                            // STORE THE DETAILS IN SHARED PREFRENCES

                            Intent intent = new Intent(MainActivity.this,Main_Menu.class);
                            startActivity(intent);
                            finish();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }


        }
    }


    public void login(View view) {


        userStr = user.getText().toString();
        passStr = pass.getText().toString();


        if (view.getId() == R.id.b_login) {
            if (isStringUser(userStr) && isStringPass(passStr)) {

                if (AppStatus.getInstance(this).isOnline()) {

                    new LoginAsync().execute();

                } else {

                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please Enter Username & password.");
                builder.setCancelable(false);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.show();


            }

        }
    }


    private boolean isStringUser(String userStr) {
        if (userStr != null && !userStr.equals("") && userStr.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isStringPass(String passStr) {
        if (passStr != null && !passStr.equals("") && passStr.length() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
