package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.PendingIntent.getActivity;
import static com.example.samsung.fastfood.R.id.b_date;

/**
 * Created by SAMSUNG on 10/26/2017.
 */

public class SignUp extends Activity {
    TextView text_create;
    Spinner spinner;
    Button button;
    String spin,res;
    String url;

     int day,month,year;
    List<NameValuePair> nameValuePairsList;
    EditText firstname,lastname,email_add,address,city,b_date,phn_no,answer,s_user,s_pass,s_conpass;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.submit);
        text_create = (TextView) findViewById(R.id.text_create);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        spinner = (Spinner) findViewById(R.id.spinner);

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email_add = (EditText) findViewById(R.id.email_add);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        b_date=(EditText)findViewById(R.id.b_date);

        phn_no = (EditText) findViewById(R.id.phn_no);
        answer = (EditText) findViewById(R.id.answer);
        s_user = (EditText) findViewById(R.id.s_user);
        s_pass = (EditText) findViewById(R.id.s_pass);
        s_conpass = (EditText) findViewById(R.id.s_conpass);



        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.questions, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignUp.this, "Select Value", Toast.LENGTH_SHORT).show();

            }
        });


        firstname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        lastname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        b_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);

                }
            }
        });

        phn_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        s_user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    String pass = s_user.getText().toString();
                    if(TextUtils.isEmpty(pass) || pass.length() < (7))
                    {
                        s_user.setError("You must have 7 characters in your username");
                        return;
                    }
                }
            }
        });
        s_conpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        s_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    String pass = s_pass.getText().toString();
                    if(TextUtils.isEmpty(pass) || pass.length() < (5))
                    {
                        s_pass.setError("You must have 5 characters in your password");
                        return;
                    }
                }
            }
        });
        email_add.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);

                }
            }
        });
        answer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
           // if(phone.length() < 6 || phone.length() > 13) {
                if(phone.length() != 10) {
                check = false;
              //  phn_no.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }

    public static boolean validateEmail(String email)
    {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches())
        {
            isValid = true;
        }

        return isValid;
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public void onButtonSignUp(final View view) {

        if (view.getId() == R.id.submit) {

            String First_NameStr = firstname.getText().toString();
            String Last_NameStr = lastname.getText().toString();
            String AnswerStr = answer.getText().toString();
            String D_BirthStr = b_date.getText().toString();
            String PhoneStr = phn_no.getText().toString();
            String CityStr = city.getText().toString();
            String EmailStr = email_add.getText().toString();
            String AddressStr = address.getText().toString();
            String S_userStr = s_user.getText().toString();
            String S_passStr = s_pass.getText().toString();

            if(TextUtils.isEmpty(S_passStr) || S_passStr.length() < (5))
            {
                s_pass.setError("You must have 5 characters in your password");

            }
            String S_conpassStr = s_conpass.getText().toString();





            if(isStrName(First_NameStr) && isStrCity(CityStr) && isStrEmail(EmailStr) && isStrAddress(AddressStr) && isStrS_user(S_userStr) && isStrS_pass(S_passStr) && isStrS_conpass(S_conpassStr)) {


                if (!S_passStr.equals(S_conpassStr)) {

                    Toast.makeText(this, "Password not match !", Toast.LENGTH_SHORT).show();
                } else {
                    if(validateEmail(EmailStr))
                    {
                        if (isValidMobile(PhoneStr)){



                            if(TextUtils.isEmpty(S_userStr) || S_userStr.length() < (7))
                            {
                                        Toast.makeText(this,"Please Correct Username",Toast.LENGTH_SHORT).show();
                            }else {
                                if(TextUtils.isEmpty(S_passStr) || S_passStr.length() < (5))
                                {
                                    Toast.makeText(this,"Please Correct Password",Toast.LENGTH_SHORT).show();

                                }else {
                                    JSONObject jsonobject = new JSONObject();
                                    JSONArray registerArray = new JSONArray();
                                    try {
                                        JSONObject registerObject = new JSONObject();
                                        registerObject.put("firstname", First_NameStr);
                                        registerObject.put("lastname", Last_NameStr);
                                        registerObject.put("email_add", EmailStr);
                                        registerObject.put("username", S_userStr);
                                        registerObject.put("password", S_passStr);
                                        registerObject.put("phn_no", PhoneStr);
                                        registerObject.put("address", AddressStr);
                                        registerObject.put("city", CityStr);
                                        registerObject.put("b_date", D_BirthStr);
                                        registerObject.put("question", spin);
                                        registerObject.put("answer", AnswerStr);

                                        registerArray.put(registerObject);

                                        jsonobject.put("register", registerArray);
                                        Log.e("Tag", "reg" + jsonobject.toString());


                                        url = "http://92.42.108.26:8080/myProject11-war/HomeDeliveryServices";
                                        nameValuePairsList = new ArrayList<NameValuePair>();
                                        nameValuePairsList.add(new BasicNameValuePair("btn", "CreateAccount"));
                                        nameValuePairsList.add(new BasicNameValuePair("registerStr", jsonobject.toString()));

                                        if (AppStatus.getInstance(this).isOnline()) {

                                            new SignUpAsync().execute();

                                        } else {

                                            Toast.makeText(SignUp.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception ex){
                                        ex.printStackTrace();
                                        Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                                    }


                                }

                            }
                        }else {
                            Toast.makeText(this,"Enter Valid No.",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
                    }
                }

            }else {
                Toast.makeText(this,"Enter Values",Toast.LENGTH_SHORT).show();

            }


        }

    }


    class SignUpAsync extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandler serviceHandler=new ServiceHandler();
            res = serviceHandler.makeServiceCall(url, ServiceHandler.GET, nameValuePairsList);
            Log.e("TESTING","******************"+res);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (res.length()>0){

                if (res.contains("successful")) {


                    Toast.makeText(SignUp.this, "Account created Successfully", Toast.LENGTH_SHORT).show();
                    Log.e("Tag", "reg" + res);

                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else if (res.contains("user already exist")){
                    Toast.makeText(SignUp.this, "This user name is not available.", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SignUp.this, "Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isStrS_conpass(String s_conpassStr) {
        if(s_conpassStr != null && !s_conpassStr.equals("") && s_conpassStr.length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean isStrS_pass(String s_passStr) {
        if(s_passStr != null && !s_passStr.equals("") && s_passStr.length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean isStrS_user(String s_userStr) {
        if(s_userStr != null && !s_userStr.equals("") && s_userStr.length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean isStrAddress(String addressStr) {
        if(addressStr != null && !addressStr.equals("") && addressStr.length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean isStrEmail(String emailStr) {
        if(emailStr != null && !emailStr.equals("") && emailStr.length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean isStrCity(String cityStr) {
        if(cityStr != null && !cityStr.equals("") && cityStr.length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean isStrName(String nameStr) {
        if(nameStr != null && !nameStr.equals("") && nameStr.length()>0){
            return true;
        }else {
            return false;
        }
    }

}

