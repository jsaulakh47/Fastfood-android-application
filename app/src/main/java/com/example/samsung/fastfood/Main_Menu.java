package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.samsung.fastfood.AppStatus.context;

/**
 * Created by SAMSUNG on 12/12/2017.
 */

public class Main_Menu extends Activity {
    DataBaseOpenHelper helper=new DataBaseOpenHelper(this);
    ImageView img_feed,img_oh,img_order,img_my_order,img_pro;
    TextView txt_feed,txt_oh,txt_order,txt_my_order,txt_pro,hi,name;
    LinearLayout linear_pro,linear_make,linear_order,linear_feed,linear_history;
    Button add;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        img_feed=(ImageView)findViewById(R.id.img_feed);
        img_oh=(ImageView)findViewById(R.id.img_oh);
        img_my_order=(ImageView)findViewById(R.id.img_my_order);
        img_order=(ImageView)findViewById(R.id.img_order);
        img_pro=(ImageView)findViewById(R.id.img_pro);

        txt_feed=(TextView)findViewById(R.id.txt_feed);
        txt_oh=(TextView)findViewById(R.id.txt_oh);
        txt_my_order=(TextView)findViewById(R.id.txt_my_order);
        txt_order=(TextView)findViewById(R.id.txt_order);
        txt_pro=(TextView)findViewById(R.id.txt_pro);
        hi=(TextView)findViewById(R.id.hi);
        name=(TextView)findViewById(R.id.name);

        linear_order=(LinearLayout)findViewById(R.id.linear_order);
        linear_history=(LinearLayout)findViewById(R.id.linear_history);
        linear_pro=(LinearLayout)findViewById(R.id.linear_pro);
        linear_make=(LinearLayout)findViewById(R.id.linear_make);
        linear_feed=(LinearLayout)findViewById(R.id.linear_feed);



        add=(Button)findViewById(R.id.add_to_cart);

        sharedPreferences = getSharedPreferences("Mydata112", context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString("firstname", "null"));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main_Menu.this);
                builder.setTitle("Logout");
                builder.setMessage("Are u Logout ?");
                builder.setCancelable(false);


                builder.setPositiveButton(
                        "yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                                sqLiteDatabase.delete(helper.DATABASE_TABLE_2,null,null);
                                sqLiteDatabase.delete(helper.DATABASE_TABLE_1,null,null);
                                OrderHistroy.detailArrayList.clear();
                                Orders.detailArrayList.clear();
                                //     OrderHistroy.list.setAdapter(null);

                                Intent intent = new Intent(Main_Menu.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                builder.setNegativeButton(
                        "no",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();

                alert.show();
            }
        });

        linear_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_Menu.this,Orders.class);
                startActivity(intent);
            }
        });

        linear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_Menu.this,OrderHistroy.class);
                startActivity(intent);
            }
        });

        linear_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_Menu.this,All_Feed.class);
                startActivity(intent);
            }
        });

        linear_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_Menu.this,Profile.class);
                startActivity(intent);
            }
        });

        linear_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_Menu.this,Home.class);
                startActivity(intent);
            }
        });

    }
}
