package yalantis.com.sidemenu.foodies.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import yalantis.com.sidemenu.foodies.model.AppConstants;
import yalantis.com.sidemenu.foodies.model.City;
import yalantis.com.sidemenu.foodies.model.FoodDiatList;
import yalantis.com.sidemenu.foodies.model.HotelMenuItem;
import yalantis.com.sidemenu.foodies.model.OrderItem;
import yalantis.com.sidemenu.foodies.model.SubmitOrder;
import yalantis.com.sidemenu.foodies.utils.PostServiceCall;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.sample.R;

public class MenuItemDetailActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private HotelMenuItem hotelMenuItem;
    private TextView addToCart,price;
    private ProgressDialog progressDialog;
    private JSONObject object;
    private JSONArray jsonArray;
    private JSONObject innerObject;
    private Spinner spinnerFoodDietType;
    private TextView name,tagline,cuisine,quantity;
    private FoodDietSpinnerAdapter foodDietSpinnerAdapter;
    private ImageView remove,add;
    private SubmitOrder submitOrder;
    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_detail);
        hotelMenuItem=PrefUtils.getMenuItemDetail(MenuItemDetailActivity.this);
        initView();
        setToolbar();
        initData();
    }

    private void initView() {
        addToCart= (TextView) findViewById(R.id.addToCart);
        price= (TextView) findViewById(R.id.price);
        quantity= (TextView) findViewById(R.id.quantity);
        remove= (ImageView) findViewById(R.id.remove);
        add= (ImageView) findViewById(R.id.add);
        name= (TextView) findViewById(R.id.name);
        tagline= (TextView) findViewById(R.id.tagline);
        cuisine= (TextView) findViewById(R.id.cuisine);
    }

    private void initData() {
        quantity.setText("Quantity "+i);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity.setText("Quantity "+(++i));
                price.setText(getResources().getString(R.string.rupees)+" "+(Integer.parseInt(hotelMenuItem.Price)*i)+"");
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 1) {
                    quantity.setText("Quantity " + (--i));
                    price.setText(getResources().getString(R.string.rupees) + " " + (Integer.parseInt(hotelMenuItem.Price) * i) + "");
                }
            }
        });
        price.setText(getResources().getString(R.string.rupees) + " " + hotelMenuItem.Price + "");
        foodDietSpinnerAdapter=new FoodDietSpinnerAdapter(MenuItemDetailActivity.this,hotelMenuItem.foodDiatListArrayList);
        spinnerFoodDietType= (Spinner) findViewById(R.id.spinnerFoodDietType);
        spinnerFoodDietType.setAdapter(foodDietSpinnerAdapter);
        name.setText(hotelMenuItem.ItemaName);
        tagline.setText(hotelMenuItem.TagLine);
        cuisine.setText(hotelMenuItem.cuisineObject.CuisineName);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("food diete id",hotelMenuItem.foodDiatListArrayList.get(spinnerFoodDietType.getSelectedItemPosition()).DietId+"");
                Log.e("item id",hotelMenuItem.ItemId+"");
                Log.e("item quantity",i+"");
                Log.e("delivery area",PrefUtils.getArea(MenuItemDetailActivity.this)+"");
                Log.e("delivery city","1");

                Log.e("user id","2");
                Log.e("order by ","2");
                Log.e("hotel id",PrefUtils.getMenuItemDetail(MenuItemDetailActivity.this).HotelId+"");
                Log.e("price to pay", (Integer.parseInt(hotelMenuItem.Price) * i) + "");
                if(PrefUtils.getCartItems(MenuItemDetailActivity.this) !=null){
                    submitOrder=PrefUtils.getCartItems(MenuItemDetailActivity.this);
                }else {
                    submitOrder = new SubmitOrder();
                }
                ArrayList<OrderItem> orderItems=new ArrayList<OrderItem>();
                orderItems.add(new OrderItem(hotelMenuItem.foodDiatListArrayList.get(spinnerFoodDietType.getSelectedItemPosition()).DietId+"",hotelMenuItem.ItemId+"",hotelMenuItem.ItemaName+"",i+"",(Integer.parseInt(hotelMenuItem.Price)*i)+""));
               if(submitOrder.orderItemArrayList !=null) {
                   submitOrder.orderItemArrayList.add(orderItems.get(0));
               } else {
                   submitOrder.orderItemArrayList=new ArrayList<OrderItem>();
                   submitOrder.orderItemArrayList.add(orderItems.get(0));
               }
                submitOrder.DeliveryCity="1";
                submitOrder.DeliveryCountry="1";
                submitOrder.DeliveryState="1";
                submitOrder.OrderStatus="1";
                submitOrder.DeliveryArea=PrefUtils.getArea(MenuItemDetailActivity.this)+"";
                submitOrder.Userid="2";
                submitOrder.OrderBy="2";
                submitOrder.HotelId=PrefUtils.getMenuItemDetail(MenuItemDetailActivity.this).HotelId+"";
                PrefUtils.AddItemToCart(submitOrder,MenuItemDetailActivity.this);
                Intent intent = new Intent(MenuItemDetailActivity.this, CartActivity.class);

                startActivity(intent);


//                checkOutOrdered();
            }
        });
    }



    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.setTitle(getIntent().getStringExtra("item_name"));
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


    public class FoodDietSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<FoodDiatList> asr;

        public FoodDietSpinnerAdapter(Context context, ArrayList<FoodDiatList> asr) {
            this.asr = asr;
            activity = context;
        }

        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(MenuItemDetailActivity.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position).DietName);
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(MenuItemDetailActivity.this);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setText(asr.get(i).DietName);
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
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
            if(PrefUtils.getCartItems(MenuItemDetailActivity.this) != null) {
                Intent intent = new Intent(MenuItemDetailActivity.this, CartActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MenuItemDetailActivity.this, "Cart is Empty", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
