package yalantis.com.sidemenu.foodies.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import yalantis.com.sidemenu.foodies.model.Hotel;
import yalantis.com.sidemenu.foodies.model.HotelsMenu;
import yalantis.com.sidemenu.foodies.model.HotelsMenuList;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.sample.R;

public class MenuListActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ArrayList<HotelsMenu> hotelsMenuArrayList;
    HotelsMenuList hotelsMenuList;
    private ListView menuListView;
    private MyAppAdapter myAppAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        setToolbar();

        hotelsMenuList=PrefUtils.getHotelsMenu(MenuListActivity.this);


        hotelsMenuArrayList=hotelsMenuList.hotelsMenuArrayList;
        menuListView= (ListView) findViewById(R.id.menuList);
        myAppAdapter=new MyAppAdapter(hotelsMenuArrayList,MenuListActivity.this);
        menuListView.setAdapter(myAppAdapter);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getIntent().getStringExtra("hotel_name"));
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuListActivity.this);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            PrefUtils.clearCart(MenuListActivity.this);
                            dialog.dismiss();
                            finish();

                        }
                    });

                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();


                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setMessage("Are you sure you'd like to change restaurants? Your current order will be lost.");
                    dialog.show();
                }
            });
        }
    }

    public class MyAppAdapter extends BaseAdapter {

        public class ViewHolder {
            public TextView name,email,mobile,address;
            public ImageView image;

        }

        public List<HotelsMenu> parkingList;
        public Context context;

        private MyAppAdapter(List<HotelsMenu> apps, Context context) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder;

            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_menu_name, null);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) rowView.findViewById(R.id.name);
                viewHolder.image= (ImageView) rowView.findViewById(R.id.hotelImage);
                Glide.with(MenuListActivity.this)
                        .load(AppConstants.IMAGE_PATH + parkingList.get(position).CategoryFolderPath + parkingList.get(position).CategoryIcon)
                        .placeholder(R.drawable.ic_launcher)
                        .centerCrop()

                        .into(viewHolder.image);

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(parkingList.get(position).CategoryName+"");
//            viewHolder.email.setText(parkingList.get(position).EmailId+"");
//            viewHolder.mobile.setText(parkingList.get(position).PhoneNumber+"");
//            viewHolder.address.setText(parkingList.get(position).StreetAddress+"");

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    PrefUtils.setHotelsMenuItems(parkingList.get(position), MenuListActivity.this);
                Intent intent=new Intent(MenuListActivity.this,MenuItemListActivity.class);
                    intent.putExtra("hotel_name",getIntent().getStringExtra("hotel_name"));
                    intent.putExtra("hotel_category",parkingList.get(position).CategoryName+"");
                    startActivity(intent);

                }
            });
            return rowView;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuListActivity.this);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
                finish();

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();


            }
        });
        AlertDialog dialog = builder.create();
        dialog.setMessage("Are you sure you'd like to change restaurants? Your current order will be lost.");
        dialog.show();
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
            if(PrefUtils.getCartItems(MenuListActivity.this) != null) {
                Intent intent = new Intent(MenuListActivity.this, CartActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MenuListActivity.this,"Cart is Empty",Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
