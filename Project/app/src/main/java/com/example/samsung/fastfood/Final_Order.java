package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samsung.fastfood.Pojo.OrderDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 12/7/2017.
 */

public class Final_Order extends Activity {
    TextView o_final;
    ListView listView;
    int Order_pos;
    Cursor data;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    String url;
   public static ArrayList<OrderDetail> detailArrayList = new ArrayList<>();
    DataBaseOpenHelper openHelper = new DataBaseOpenHelper(this);
    SQLiteDatabase sqLiteDatabase;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        detailArrayList.clear();
        Intent intent=new Intent(Final_Order.this,Main_Menu.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_order);
        o_final=(TextView)findViewById(R.id.o_final);
        listView=(ListView)findViewById(R.id.list);
        sqLiteDatabase=openHelper.getWritableDatabase();
        Intent intent = getIntent();
        Order_pos = intent.getIntExtra("Orederpos", -1);

        url="http://92.42.108.26:8080/myProject11-war/img/";

        imageLoader= ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(Final_Order.this));

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.fast)
                .showImageOnFail(R.drawable.fast)
                .showImageOnLoading(R.drawable.fast).build();



        String q = "select * from " + openHelper.DATABASE_TABLE_1 + " where s_id==" + (Order_pos+1);
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
                            Intent intent = new Intent(Final_Order.this, Main_Menu.class);
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
            listView.setAdapter(new BaseAdapter() {

                LayoutInflater layoutInflater = LayoutInflater.from(Final_Order.this);
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
                public View getView(int position, View convertView, ViewGroup parent) {
                    final View view = layoutInflater.inflate(R.layout.single_row_final_order, null);
                    TextView item = (TextView) view.findViewById(R.id.add_item_f);
                    TextView quantity = (TextView) view.findViewById(R.id.add_quantity_f);
                    TextView price = (TextView) view.findViewById(R.id.add_price_f);
                    TextView variant = (TextView) view.findViewById(R.id.add_variant_f);
                    TextView total = (TextView) view.findViewById(R.id.total_f);
                    ImageView imageView=(ImageView)view.findViewById(R.id.img);

                    item.setText(detailArrayList.get(position).getProductName());
                    quantity.setText("" + detailArrayList.get(position).getQuantity());
                    price.setText("" + detailArrayList.get(position).getPrice());
                    variant.setText(detailArrayList.get(position).getVariant());
                    total.setText("" + detailArrayList.get(position).getTotal());

                    imageLoader.displayImage(detailArrayList.get(position).getImage(), imageView ,options);

                    notifyDataSetChanged();

                    return view;
                }
            });
        }
    }
}
