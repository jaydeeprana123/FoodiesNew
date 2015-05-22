package yalantis.com.sidemenu.foodies.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.foodies.model.AppConstants;
import yalantis.com.sidemenu.foodies.model.FoodDiatList;
import yalantis.com.sidemenu.foodies.model.HotelMenuItem;
import yalantis.com.sidemenu.foodies.model.HotelsMenu;
import yalantis.com.sidemenu.foodies.model.HotelsMenuList;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.sample.R;

public class MenuItemListActivity extends ActionBarActivity {
    private Toolbar toolbar;
    HotelsMenu hotelsMenuList;
    private ListView menuItemListView;
    private ArrayList<HotelMenuItem> hotelMenuItemArrayList;
    private MyAppAdapter myAppAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_list);
        setToolbar();
        hotelsMenuList= PrefUtils.getHotelsMenuItems(MenuItemListActivity.this);
        hotelMenuItemArrayList=hotelsMenuList.hotelMenuItemArrayList;
        menuItemListView= (ListView) findViewById(R.id.menuItenList);
        myAppAdapter=new MyAppAdapter(hotelMenuItemArrayList,MenuItemListActivity.this);
        menuItemListView.setAdapter(myAppAdapter);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.setTitle(getIntent().getStringExtra("hotel_name"));
            toolbar.setSubtitle(getIntent().getStringExtra("hotel_category"));
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public class MyAppAdapter extends BaseAdapter {

        public class ViewHolder {
            public TextView name,tagline,price;
            ImageView image;


        }

        public List<HotelMenuItem> parkingList;

        public Context context;

        private MyAppAdapter(List<HotelMenuItem> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder;

            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_menu_list, null);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) rowView.findViewById(R.id.name);
                viewHolder.tagline = (TextView) rowView.findViewById(R.id.tagline);
                viewHolder.price = (TextView) rowView.findViewById(R.id.price);
                viewHolder.image=(ImageView)rowView.findViewById(R.id.hotelImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(parkingList.get(position).ItemaName+"");
            viewHolder.tagline.setText(parkingList.get(position).TagLine+"");
            viewHolder.price.setText(getResources().getString(R.string.rupees)+" "+parkingList.get(position).Price+"");
//            viewHolder.address.setText(parkingList.get(position).StreetAddress+"");
            Glide.with(MenuItemListActivity.this)
                    .load(AppConstants.IMAGE_PATH + parkingList.get(position).MenuFolderPath + parkingList.get(position).MenuIcon)
                    .placeholder(R.drawable.ic_launcher)
                    .centerCrop()
                    .into(viewHolder.image);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefUtils.setMenuItemDetail(parkingList.get(position), MenuItemListActivity.this);
                    Intent intent = new Intent(MenuItemListActivity.this, MenuItemDetailActivity.class);
                    intent.putExtra("item_name",parkingList.get(position).ItemaName+"");
                    startActivity(intent);
                }
            });
            return rowView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            if(PrefUtils.getCartItems(MenuItemListActivity.this) != null) {
                Intent intent = new Intent(MenuItemListActivity.this, CartActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MenuItemListActivity.this, "Cart is Empty", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
