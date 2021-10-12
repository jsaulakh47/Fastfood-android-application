package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.fastfood.Pojo.OrderDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SAMSUNG on 11/20/2017.
 */

public class Orders extends Activity {
    TextView order;
    Button button;
    ListView listView;
    String date,url;
    int zahal;
    SQLiteDatabase sqLiteDatabase;
    Cursor data;
    String name,address,city;
    Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;

   public static ArrayList<OrderDetail> detailArrayList = new ArrayList<>();
    DataBaseOpenHelper openHelper = new DataBaseOpenHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);
        order = (TextView) findViewById(R.id.order);
        button = (Button) findViewById(R.id.pro);
        context=Orders.this;
        sqLiteDatabase = openHelper.getWritableDatabase();
        String q = "select * from " + openHelper.DATABASE_TABLE_1 + " where s_id==-1";

        url="http://92.42.108.26:8080/myProject11-war/img/";

        imageLoader= ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(Orders.this));

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.fast)
                .showImageOnFail(R.drawable.fast)
                .showImageOnLoading(R.drawable.fast).build();

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm a");
        date = df.format(Calendar.getInstance().getTime());

       SharedPreferences sharedPreferences = getSharedPreferences("Mydata112", context.MODE_PRIVATE);
       name = sharedPreferences.getString("username", "null");
        city = sharedPreferences.getString("city", "null");
        address = sharedPreferences.getString("address", "null");

        data = sqLiteDatabase.rawQuery(q, null);

        if (data.getCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("Card is Empty");
            builder.setMessage("Go to Main Menu:");
            builder.setCancelable(false);


            builder.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Orders.this, Main_Menu.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        } else {
            while (data.moveToNext()) {

                detailArrayList.add(new OrderDetail(
                        data.getString(2),
                        Integer.valueOf(data.getString(4)),
                        Integer.valueOf(data.getString(3)),
                        data.getString(5),
                        data.getInt(0),data.getString(6)));

            }
        }
        listView = (ListView) findViewById(R.id.list_order);

        listView.setAdapter(new BaseAdapter() {

            TextView a;
            TextView quantity;

            LayoutInflater layoutInflater = LayoutInflater.from(Orders.this);

            @Override
            public int getCount() {
                return detailArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {

                final View view = layoutInflater.inflate(R.layout.single_row_add, null);
                TextView item = (TextView) view.findViewById(R.id.add_item);
                quantity = (TextView) view.findViewById(R.id.add_quantity);
                TextView price = (TextView) view.findViewById(R.id.add_price);
                TextView variant = (TextView) view.findViewById(R.id.add_variant);
                TextView total = (TextView) view.findViewById(R.id.total);
                final ImageView delete = (ImageView) view.findViewById(R.id.del_id);
                ImageView edit = (ImageView) view.findViewById(R.id.edit);
                ImageView image=(ImageView)view.findViewById(R.id.image);


                item.setText(detailArrayList.get(position).getProductName());
                quantity.setText("" + detailArrayList.get(position).getQuantity());
                price.setText("" + detailArrayList.get(position).getPrice());
                variant.setText(detailArrayList.get(position).getVariant());
                total.setText("" + detailArrayList.get(position).getTotal());

                imageLoader.displayImage(detailArrayList.get(position).getImage(), image ,options);

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        if (detailArrayList.size()>0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Orders.this);
                            builder.setMessage("Want to Proceed ?");
                            builder.setCancelable(false);
                            builder.setPositiveButton(
                                    "yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Contacts contacts = new Contacts();
                                            contacts.setP_user(name);
                                            contacts.setDate(date);
                                            contacts.setCity_history(city);
                                            contacts.setAddress_histroy(address);
                                            openHelper.insert_proceed(contacts);
                                            openHelper.updateSecretKey();
                                            detailArrayList.clear();
                                          //  listView.setAdapter(null);
                                            dialog.dismiss();
                                            Toast.makeText(Orders.this, " Done ", Toast.LENGTH_SHORT).show();
                                            //detailArrayList.clear();
                                            Intent intent=new Intent(Orders.this,Main_Menu.class);
                                            startActivity(intent);
                                            notifyDataSetChanged();
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
                        }else{
                            Toast.makeText(context, "Add Values", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            openHelper.deleteFromDB(detailArrayList.get(position));
                            detailArrayList.remove(position);
                            notifyDataSetChanged();
                        }
                });


                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater factory = LayoutInflater.from(Orders.this);
                        final View deleteDialogView = factory.inflate(R.layout.dialog_layout, null);
                        final AlertDialog deleteDialog = new AlertDialog.Builder(Orders.this).create();
                        deleteDialog.setView(deleteDialogView);
                        a = (TextView) deleteDialogView.findViewById(R.id.e_txt);
                        a.setText("" + detailArrayList.get(position).getQuantity());
                        zahal = Integer.parseInt(a.getText().toString());

                        deleteDialogView.findViewById(R.id.e_add).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (zahal >= 10) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Orders.this);
                                    builder.setMessage("No More than Ten.");
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
                                } else {
                                    zahal++;
                                    a.setText(String.valueOf(zahal));
                                }
                            }

                        });


                        deleteDialogView.findViewById(R.id.e_minus).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (zahal <= 1) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Orders.this);
                                    builder.setMessage("At least One.");
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

                                } else {
                                    zahal--;
                                    a.setText(String.valueOf(zahal));

                                }
                            }
                        });

                        deleteDialogView.findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                OrderDetail orderDetail = detailArrayList.get(position);
                                orderDetail.setQuantity(zahal);
                                openHelper.updateFromDB(orderDetail);
                                detailArrayList.clear();
                                updateData();
                                deleteDialog.dismiss();
                                notifyDataSetChanged();
                            }
                        });

                        deleteDialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                            }
                        });
                        deleteDialog.show();
                    }
                });
                return view;
            }
        });

    }

    private void updateData() {
        detailArrayList.clear();
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();

        String q = "select * from " + openHelper.DATABASE_TABLE_1 + " where s_id==-1";

        final Cursor data = sqLiteDatabase.rawQuery(q, null);

        if (data.getCount() == 0) {
            Toast.makeText(Orders.this, "Please enter !!", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {

                detailArrayList.add(new OrderDetail(
                        data.getString(2),
                        Integer.valueOf(data.getString(4)),
                        Integer.valueOf(data.getString(3)),
                        data.getString(5),
                        data.getInt(0),data.getString(6)));
            }
        }
        for (OrderDetail orderDetail : detailArrayList) {
            Log.e("TESTING", "----------" + orderDetail);
        }
    }
}