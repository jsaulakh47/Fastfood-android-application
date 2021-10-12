package com.example.samsung.fastfood;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.fastfood.Pojo.OrderDetail;
import com.example.samsung.fastfood.Pojo.Proceed_Buy;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 11/29/2017.
 */

public class OrderHistroy extends Activity {
    TextView order;
   public static ListView list;
    BaseAdapter baseAdapter;
    SQLiteDatabase sqLiteDatabase;
    public  static  ArrayList<Proceed_Buy> detailArrayList = new ArrayList<>();
    DataBaseOpenHelper openHelper=new DataBaseOpenHelper(this);


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        detailArrayList.clear();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_his);
        order=(TextView)findViewById(R.id.o_his);
        list=(ListView)findViewById(R.id.list);
        sqLiteDatabase = openHelper.getWritableDatabase();

        String q = "select * from " + openHelper.DATABASE_TABLE_2;
        final Cursor data = sqLiteDatabase.rawQuery(q, null);

        baseAdapter=new BaseAdapter() {


            LayoutInflater layoutInflater = LayoutInflater.from(OrderHistroy.this);
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
                final View view = layoutInflater.inflate(R.layout.single_order_histroy, null);
                TextView User=(TextView)view.findViewById(R.id.user);
                TextView Date=(TextView)view.findViewById(R.id.date);
                TextView City=(TextView)view.findViewById(R.id.city);
                TextView Address=(TextView)view.findViewById(R.id.address);
                TextView Comma=(TextView)view.findViewById(R.id.coma);

                User.setText(detailArrayList.get(position).getUser_name());
                Date.setText(detailArrayList.get(position).getOrder_Date());
                City.setText(detailArrayList.get(position).getCity());
                Address.setText(detailArrayList.get(position).getAddress());

                return view;
            }
        };

        if(data.getCount() == 0){
            Toast.makeText(this,"Enter data",Toast.LENGTH_SHORT).show();
        }else {
            while (data.moveToNext()) {
                detailArrayList.add(new Proceed_Buy(data.getString(1),data.getString(2),data.getString(3),data.getString(4)));

            }
        }
        list.setAdapter(baseAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(OrderHistroy.this,Final_Order.class);
                intent.putExtra("Orederpos", position);
                startActivity(intent);
                detailArrayList.clear();
                baseAdapter.notifyDataSetChanged();



            }
        });
    }
}
