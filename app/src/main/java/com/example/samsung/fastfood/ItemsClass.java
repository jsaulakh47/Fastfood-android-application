package com.example.samsung.fastfood;

import android.app.Activity;
import android.content.Intent;
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

import com.example.samsung.fastfood.Pojo.Items;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * Created by SAMSUNG on 11/9/2017.
 */

public class ItemsClass extends Activity {
    String url;

    public static ArrayList<Items> itemsArrayList = new ArrayList<>();
    ImageLoader imageLoader;
    DisplayImageOptions options;


    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);
        listView = (ListView) findViewById(R.id.list_category);
        TextView textView = (TextView) findViewById(R.id.category);

        url="http://92.42.108.26:8080/myProject11-war/img/";

        imageLoader= ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ItemsClass.this));

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.fast)
                .showImageOnFail(R.drawable.fast)
                .showImageOnLoading(R.drawable.fast).build();


        Intent intent = getIntent();
        final int pos = intent.getIntExtra("catpos", -1);


        itemsArrayList = Home.arrayList.get(pos).getItems();

        if (pos > -1) {
            listView.setAdapter(new BaseAdapter() {



                LayoutInflater layoutInflater = LayoutInflater.from(ItemsClass.this);

                @Override
                public int getCount() {
                    return itemsArrayList.size();
                }

                @Override
                public Object getItem(int position) {
                    return 0;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = layoutInflater.inflate(R.layout.single_rowitems, null);
                    TextView text = (TextView) view.findViewById(R.id.txt_category);
                    ImageView imageView=(ImageView)view.findViewById(R.id.img);

                    imageLoader.displayImage(url+itemsArrayList.get(position).getImage(), imageView ,options);

                    text.setText(itemsArrayList.get(position).getItem_name());
                    return view;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  Intent intent = new Intent(ItemsClass.this,ItemVariantClass.class);
                    intent.putExtra("itempos", position);
                    intent.putExtra("catpos", pos);
                    startActivity(intent);

                }
            });

        } else {

            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();

        }
    }
}
