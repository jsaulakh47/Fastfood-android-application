package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.fastfood.Pojo.ItemVariant;
import com.example.samsung.fastfood.Pojo.Items;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SAMSUNG on 11/13/2017.
 */

public class ItemVariantClass extends Activity {
    ArrayList<Items> a = new ArrayList<>();
    ArrayList<ItemVariant> itemVariants = new ArrayList<>();
    TextView variant, price, amount, qua, name;
    RadioGroup group;
    RadioButton s, m, l;
    Button plus, minus, add;
    int pos;
     ImageView imageView;
    SharedPreferences sharedPreferences;
    int Zahl=1;
    Context context;
    String date,  Old_user,url;
    int variantPos;
    DataBaseOpenHelper openHelper = new DataBaseOpenHelper(this);
    ImageLoader imageLoader;
    DisplayImageOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_variant);
        variant = (TextView) findViewById(R.id.variant);
        price = (TextView) findViewById(R.id.price);
        amount = (TextView) findViewById(R.id.amount);
        context=ItemVariantClass.this;
        name = (TextView) findViewById(R.id.name);
        qua = (TextView) findViewById(R.id.qua);
        qua.setText(String.valueOf(Zahl));
        group = (RadioGroup) findViewById(R.id.group);
        s = (RadioButton) findViewById(R.id.small);
        m = (RadioButton) findViewById(R.id.medium);
        l = (RadioButton) findViewById(R.id.large);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        add = (Button) findViewById(R.id.add_to_cart);
        imageView=(ImageView) findViewById(R.id.imageView);


        url="http://92.42.108.26:8080/myProject11-war/img/";

        imageLoader= ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ItemVariantClass.this));

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.fast)
                .showImageOnFail(R.drawable.fast)
                .showImageOnLoading(R.drawable.fast).build();

        sharedPreferences = getSharedPreferences("Mydata112", context.MODE_PRIVATE);
        Old_user = sharedPreferences.getString("username", "null");

        variantPos=0;

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm a");
        date = df.format(Calendar.getInstance().getTime());

        final Intent intent = getIntent();
        pos = intent.getIntExtra("itempos", -1);
        final int poscat = intent.getIntExtra("catpos", -1);


        a = ItemsClass.itemsArrayList;
        itemVariants = a.get(pos).getItem_variant();
        name.setText(a.get(pos).getItem_name());

        imageLoader.displayImage(url+a.get(pos).getImage(), imageView ,options);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.small:
                        variantPos=0;
                        amount.setText(itemVariants.get(0).getPrice());
                        break;
                    case R.id.medium:
                        variantPos=1;
                        amount.setText(itemVariants.get(1).getPrice());
                        break;
                    case R.id.large:
                        variantPos=2;
                        amount.setText(itemVariants.get(2).getPrice());
                        break;
                    default:
                        break;
                }
            }
        });


        if (poscat == 0 || poscat == 1) {
            s.setText("small");
            m.setText("medium");
            l.setText("large");
            amount.setText((itemVariants.get(0).getPrice()));

        } else if (poscat == 2 || poscat == 3) {

            s.setText("classic");
            m.setVisibility(View.INVISIBLE);
            l.setVisibility(View.INVISIBLE);
            amount.setText((itemVariants.get(0).getPrice()));

        }
    }
    public int increase(View v) {

        Zahl = Integer.parseInt(qua.getText().toString());
        switch (v.getId()) {
            case R.id.plus:

                if (Zahl >= 10) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Ten is Maximim.");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setCancelable(false);

                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    qua.setText(String.valueOf(Zahl));
                } else {
                    Zahl++;
                    qua.setText(String.valueOf(Zahl));

                }

                break;
            case R.id.minus:
                if (Zahl <= 1) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Atleast One.");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setCancelable(false);
                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    qua.setText(String.valueOf(Zahl));

                } else {
                    Zahl--;
                    qua.setText(String.valueOf(Zahl));

                    break;

                }
            default:
                break;

        }
        return Zahl;

    }

    public void add_to(View view) {

        Contacts contacts = new Contacts();
        contacts.setItem(a.get(pos).getItem_name());
       contacts.setPrice((itemVariants.get(variantPos).getPrice()));
        contacts.setQuantity(Zahl);
        if(s.isChecked()){
            contacts.setVariant("Small");
        }else if(m.isChecked()){
            contacts.setVariant("Medium");
        }else if(l.isChecked()){
            contacts.setVariant("Large");
        }
        contacts.setS_id(-1);
        contacts.setImage(url+a.get(pos).getImage());
        openHelper.insertFood(contacts);


        AlertDialog.Builder builder = new AlertDialog.Builder(ItemVariantClass.this);
        builder.setCancelable(false);
        builder.setTitle("Order Is Added.");
        builder.setMessage("Go to Menu:");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(ItemVariantClass.this, Main_Menu.class);
                startActivity(intent1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}


















