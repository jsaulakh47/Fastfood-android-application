package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.samsung.fastfood.AppStatus.context;

/**
 * Created by SAMSUNG on 11/29/2017.
 */

public class Profile extends Activity {
    TextView first,phone,city,dob,address,email;
    Context context;
    ImageView cover;
    RelativeLayout relativeLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        first=(TextView)findViewById(R.id.first);
        city=(TextView)findViewById(R.id.city);
        phone=(TextView)findViewById(R.id.phone);
        dob=(TextView)findViewById(R.id.dob);
        address=(TextView)findViewById(R.id.address);
        email=(TextView)findViewById(R.id.email_1);
        cover=(ImageView)findViewById(R.id.header_cover_image) ;
        relativeLayout=(RelativeLayout)findViewById(R.id.profile_layout);

        sharedPreferences = getSharedPreferences("Mydata112", context.MODE_PRIVATE);

        String FirstName = sharedPreferences.getString("firstname","null");
        String  Email= sharedPreferences.getString("emailAddress","null");
        String  Phone= sharedPreferences.getString("phoneNo","null");
        String  City= sharedPreferences.getString("city","null");
        String  DOB= sharedPreferences.getString("emailAddress","null");
        String  Address= sharedPreferences.getString("address","null");


        first.setText(FirstName);
        city.setText(City);
        phone.setText(Phone);
        email.setText(Email);
        dob.setText(DOB);
        address.setText(Address);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.feed:

                Intent intent=new Intent(Profile.this,Feed_Back.class);
                startActivity(intent);
                break;

            case R.id.log:

                Intent intent1=new Intent(Profile.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.Change:

                Intent intent2=new Intent(Profile.this,Change_Pass.class);
                startActivity(intent2);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
