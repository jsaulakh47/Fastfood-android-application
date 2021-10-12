package com.example.samsung.fastfood;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.fastfood.Pojo.Feed_Details;
import com.example.samsung.fastfood.Pojo.OrderDetail;

import java.util.ArrayList;

import static com.example.samsung.fastfood.AppStatus.context;

/**
 * Created by SAMSUNG on 12/1/2017.
 */

public class All_Feed extends Activity {
    TextView feed_t;
    ListView list_t;

    DataBaseOpenHelper openHelper=new DataBaseOpenHelper(this);
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Feed_Details> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_feed);
        feed_t=(TextView)findViewById(R.id.feed_f);
        list_t=(ListView) findViewById(R.id.list_t);

        sqLiteDatabase = openHelper.getWritableDatabase();
        String q = "select * from " + openHelper.DATABASE_TABLE_3;
        Cursor cursor=sqLiteDatabase.rawQuery(q,null);




        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Feedback",Toast.LENGTH_SHORT).show();

        }else {
            while (cursor.moveToNext()) {


                arrayList.add(new Feed_Details(cursor.getString(1),cursor.getString(2),cursor.getString(3)));


            }
        }
        list_t.setAdapter(new BaseAdapter() {

            LayoutInflater layoutInflater = LayoutInflater.from(All_Feed.this);

            @Override
            public int getCount() {
                return arrayList.size();
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
                final View view = layoutInflater.inflate(R.layout.single_feedback_row, null);
                TextView user=(TextView)view.findViewById(R.id.user_f);
                TextView comment=(TextView)view.findViewById(R.id.feed_f);
                TextView email=(TextView)view.findViewById(R.id.email);

                user.setText(arrayList.get(position).getUser());
                comment.setText(arrayList.get(position).getComment());
               email.setText(arrayList.get(position).getEmail());
                return view;
            }
        });

    }
}
