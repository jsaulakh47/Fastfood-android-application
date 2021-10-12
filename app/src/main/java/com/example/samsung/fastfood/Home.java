package com.example.samsung.fastfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.fastfood.Pojo.Category;
import com.example.samsung.fastfood.Pojo.ItemVariant;
import com.example.samsung.fastfood.Pojo.Items;
import com.example.samsung.fastfood.Pojo.MainData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by SAMSUNG on 10/26/2017.
 */

public class Home extends Activity {
    DataBaseOpenHelper helper = new DataBaseOpenHelper(this);
    public static ArrayList<Category> arrayList=new ArrayList<>();
      String url;
    TextView category;
    ListView listView;
    String name;
    ProgressDialog progressDialog;
     ImageLoader imageLoader;
    DisplayImageOptions options;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        category = (TextView) findViewById(R.id.category);
        listView = (ListView) findViewById(R.id.list_category);
        progressDialog = new ProgressDialog(this);
         name = getIntent().getStringExtra("username");

        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(Home.this));

       options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.fast)
                .showImageOnFail(R.drawable.fast)
                .showImageOnLoading(R.drawable.fast).build();

        if (AppStatus.getInstance(this).isOnline()) {

            new Quick().execute();

        } else {

            Toast.makeText(Home.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }


    }

    class Quick extends AsyncTask {
        String s;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Waiting...");
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                s=Util.getHttpMethod("http://92.42.108.26:8080/myProject11-war/HomeDeliveryServices?btn=Menu");
                //  s = "{\"category\":[{\"category_name\":\"Pizza\",\"items\":[{\"item_variant\":[{\"price\":\"120\",\"variant\":\"Small\"},{\"price\":\"150\",\"variant\":\"Medium\"},{\"price\":\"200\",\"variant\":\"Large\"}],\"item_name\":\"Crown Pizza\",\"image\":\"Crown Pizza.jpg\"},{\"item_variant\":[{\"price\":\"120\",\"variant\":\"Small\"},{\"price\":\"200\",\"variant\":\"Medium\"},{\"price\":\"350\",\"variant\":\"Large\"}],\"item_name\":\"Cheese Pizza\",\"image\":\"Cheese Pizza.jpeg\"},{\"item_variant\":[{\"price\":\"150\",\"variant\":\"Small\"},{\"price\":\"300\",\"variant\":\"Medium\"},{\"price\":\"500\",\"variant\":\"Large\"}],\"item_name\":\"Magic Pan \",\"image\":\"Magic Pan.jpeg\"},{\"item_variant\":[{\"price\":\"150\",\"variant\":\"Small\"},{\"price\":\"250\",\"variant\":\"Medium\"},{\"price\":\"450\",\"variant\":\"Large\"}],\"item_name\":\"Non Veg Magic Pan\",\"image\":\"Non Veg Magic Pan.jpeg\"},{\"item_variant\":[{\"price\":\"200\",\"variant\":\"Small\"},{\"price\":\"350\",\"variant\":\"Medium\"},{\"price\":\"500\",\"variant\":\"Large\"}],\"item_name\":\"Classic Cheese Pizza\",\"image\":\"Classic Cheese Pizza.jpeg\"},{\"item_variant\":[{\"price\":\"130\",\"variant\":\"Small\"},{\"price\":\"250\",\"variant\":\"Medium\"},{\"price\":\"350\",\"variant\":\"Large\"}],\"item_name\":\"Italian Treat\",\"image\":\"Italian Treat.jpeg\"},{\"item_variant\":[{\"price\":\"140\",\"variant\":\"Small\"},{\"price\":\"260\",\"variant\":\"Medium\"},{\"price\":\"500\",\"variant\":\"Large\"}],\"item_name\":\"Veg Treat\",\"image\":\"Veg Treat.jpeg\"},{\"item_variant\":[{\"price\":\"200\",\"variant\":\"Small\"},{\"price\":\"400\",\"variant\":\"Medium\"},{\"price\":\"550\",\"variant\":\"Large\"}],\"item_name\":\"Super Chicken Twist\",\"image\":\"Super Chicken Twist.jpeg\"},{\"item_variant\":[{\"price\":\"230\",\"variant\":\"Small\"},{\"price\":\"400\",\"variant\":\"Medium\"},{\"price\":\"700\",\"variant\":\"Large\"}],\"item_name\":\"Double Chicken Feast\",\"image\":\"Double Chicken Feast.jpeg\"},{\"item_variant\":[{\"price\":\"150\",\"variant\":\"Small\"},{\"price\":\"300\",\"variant\":\"Medium\"},{\"price\":\"450\",\"variant\":\"Large\"}],\"item_name\":\"Veggie Fortune\",\"image\":\"Veggie Fortune.jpeg\"},{\"item_variant\":[{\"price\":\"180\",\"variant\":\"Small\"},{\"price\":\"350\",\"variant\":\"Medium\"},{\"price\":\"650\",\"variant\":\"Large\"}],\"item_name\":\"Veggie Grand\",\"image\":\"Veggie Grand.jpeg\"}],\"image\":\"Pizza.jpg\"},{\"category_name\":\"Burgur\",\"items\":[{\"item_variant\":[{\"price\":\"60\",\"variant\":\"small\"},{\"price\":\"100\",\"variant\":\"medium\"},{\"price\":\"150\",\"variant\":\"large\"}],\"item_name\":\"Big Mac\",\"image\":\"Big Mac.jpeg\"},{\"item_variant\":[{\"price\":\"100\",\"variant\":\"small\"},{\"price\":\"130\",\"variant\":\"medium\"},{\"price\":\"150\",\"variant\":\"large\"}],\"item_name\":\"Quarter Pounder with Cheese\",\"image\":\"Quarter Pounder with Cheese.jpeg\"},{\"item_variant\":[{\"price\":\"60\",\"variant\":\"small\"},{\"price\":\"80\",\"variant\":\"medium\"},{\"price\":\"100\",\"variant\":\"large\"}],\"item_name\":\"Hamburger\",\"image\":\"Hamburger.jpeg\"},{\"item_variant\":[{\"price\":\"80\",\"variant\":\"small\"},{\"price\":\"120\",\"variant\":\"medium\"},{\"price\":\"150\",\"variant\":\"large\"}],\"item_name\":\"Cheeseburger\",\"image\":\"Cheeseburger.jpeg\"},{\"item_variant\":[{\"price\":\"100\",\"variant\":\"small\"},{\"price\":\"150\",\"variant\":\"medium\"},{\"price\":\"180\",\"variant\":\"large\"}],\"item_name\":\"McDouble\",\"image\":\"McDouble.jpeg\"},{\"item_variant\":[{\"price\":\"70\",\"variant\":\"small\"},{\"price\":\"120\",\"variant\":\"medium\"},{\"price\":\"160\",\"variant\":\"large\"}],\"item_name\":\"Daily Double\",\"image\":\"Daily Double.jpeg\"},{\"item_variant\":[{\"price\":\"110\",\"variant\":\"small\"},{\"price\":\"170\",\"variant\":\"medium\"},{\"price\":\"250\",\"variant\":\"large\"}],\"item_name\":\"McRib\",\"image\":\"McRib.jpeg\"},{\"item_variant\":[{\"price\":\"120\",\"variant\":\"small\"},{\"price\":\"200\",\"variant\":\"medium\"},{\"price\":\"300\",\"variant\":\"large\"}],\"item_name\":\"Filet-O-Fish\",\"image\":\"Filet-O-Fish.jpeg\"},{\"item_variant\":[{\"price\":\"80\",\"variant\":\"small\"},{\"price\":\"130\",\"variant\":\"medium\"},{\"price\":\"160\",\"variant\":\"large\"}],\"item_name\":\"Bacon McDouble\",\"image\":\"Bacon McDouble.jpeg\"},{\"item_variant\":[{\"price\":\"80\",\"variant\":\"small\"},{\"price\":\"120\",\"variant\":\"medium\"},{\"price\":\"180\",\"variant\":\"large\"}],\"item_name\":\"McChicken\",\"image\":\"McChicken.jpeg\"},{\"item_variant\":[{\"price\":\"100\",\"variant\":\"small\"},{\"price\":\"150\",\"variant\":\"medium\"},{\"price\":\"200\",\"variant\":\"large\"}],\"item_name\":\"BBQ Ranch Burger\",\"image\":\"BBQ Ranch Burger.jpeg\"}],\"image\":\"Burger.jpg\"},{\"category_name\":\"Dessert\",\"items\":[{\"item_variant\":[{\"price\":\"150\",\"variant\":\"Classic\"}],\"item_name\":\"Banoffee Pie\",\"image\":\"Banoffee Pie.jpeg\"},{\"item_variant\":[{\"price\":\"200\",\"variant\":\"Classic\"}],\"item_name\":\"Choco Chip Cookie Sundae\",\"image\":\"Choco Chip Cookie Sundae.jpeg\"},{\"item_variant\":[{\"price\":\"250\",\"variant\":\"Classic\"}],\"item_name\":\"Classic Tiramisu\",\"image\":\"Classic Tiramisu.jpeg\"},{\"item_variant\":[{\"price\":\"180\",\"variant\":\"Classic\"}],\"item_name\":\"Hut Velvet Cake\",\"image\":\"Hut Velvet Cake.jpeg\"},{\"item_variant\":[{\"price\":\"200\",\"variant\":\"Classic\"}],\"item_name\":\"Classic Vanilla Romance\",\"image\":\"Classic Vanilla Romance.jpeg\"},{\"item_variant\":[{\"price\":\"250\",\"variant\":\"Classic\"}],\"item_name\":\"Chocoholic Symphony\",\"image\":\"Chocoholic Symphony.jpeg\"}],\"image\":\"Dessert.jpeg\"},{\"category_name\":\"SoftDrink\",\"items\":[],\"image\":\"SoftDrink.jpeg\"}]}";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            arrayList=new ArrayList<>();

            url="http://92.42.108.26:8080/myProject11-war/img/";



            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                JSONObject object = new JSONObject(s);
                JSONArray category = object.getJSONArray("category");
                final MainData mainData = new MainData();

                final ArrayList<Category> categoryArrayList = new ArrayList<>();

                for (int i = 0; i <category.length(); i++) {
                    JSONObject Object_category = category.getJSONObject(i);

                    Category category1 = new Category();

                    category1.setCategory_name(Object_category.getString("category_name"));
                    category1.setImage(Object_category.getString("image"));
                    JSONArray items = Object_category.getJSONArray("items");



                    ArrayList<Items> itemsArrayList = new ArrayList<>();

                    for (int j = 0; j < items.length(); j++) {
                        JSONObject Object_items = items.getJSONObject(j);

                        Items items1 = new Items();
                        items1.setItem_name(Object_items.getString("item_name"));
                        items1.setImage(Object_items.getString("image"));
                        JSONArray items_variant = Object_items.getJSONArray("item_variant");



                        ArrayList<ItemVariant> itemVariantArrayList = new ArrayList<>();


                        for (int k = 0; k < items_variant.length(); k++) {
                            JSONObject Object_itemsvariant = items_variant.getJSONObject(k);
                            ItemVariant itemVariant = new ItemVariant();

                            itemVariant.setPrice(Object_itemsvariant.getString("price"));
                            itemVariant.setVariant(Object_itemsvariant.getString("variant"));
                            itemVariantArrayList.add(itemVariant);

                        }
                        items1.setItem_variant(itemVariantArrayList);
                        itemsArrayList.add(items1);
                    }
                    category1.setItems(itemsArrayList);
                      categoryArrayList.add(category1);


                    arrayList=categoryArrayList;


                  listView.setAdapter(new BaseAdapter() {


                        LayoutInflater layoutInflater = LayoutInflater.from(Home.this);

                        @Override
                        public int getCount() {
                            return arrayList.size();
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


                            View view = layoutInflater.inflate(R.layout.single_rowcategory, null);

                            TextView text = (TextView) view.findViewById(R.id.txt_category);

                           ImageView imageView=(ImageView)view.findViewById(R.id.img);

                            imageLoader.displayImage(url+arrayList.get(position).getImage(), imageView ,options);

                            text.setText(arrayList.get(position).getCategory_name());

                            return view;
                        }
                    });

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                      Intent intent=new Intent(Home.this,ItemsClass.class);
                            intent.putExtra("catpos",position);
                            startActivity(intent);

                        }
                    });


                }
             mainData.setCategory(categoryArrayList);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
