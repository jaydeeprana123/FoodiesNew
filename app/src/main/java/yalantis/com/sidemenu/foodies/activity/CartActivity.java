package yalantis.com.sidemenu.foodies.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;
import yalantis.com.sidemenu.foodies.fragment.CartDetailFragment;
import yalantis.com.sidemenu.foodies.fragment.ContentFragment5;
import yalantis.com.sidemenu.foodies.fragment.DeliveryTypeFragment;
import yalantis.com.sidemenu.foodies.fragment.PaymentTypeFragment;
import yalantis.com.sidemenu.foodies.fragment.UserDetailsFragment;
import yalantis.com.sidemenu.foodies.model.SubmitOrder;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.sample.R;

public class CartActivity extends ActionBarActivity {

    private Toolbar toolbar;
    public  ViewPager viewPager;
    private SpringIndicator springIndicator;
    public TextView priceView;
    private MyPagerAdapter adapter;
    SubmitOrder submitOrder;

    public void setCurrentTab(int i){
        viewPager.setCurrentItem(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        priceView= (TextView) findViewById(R.id.price);
        submitOrder= PrefUtils.getCartItems(CartActivity.this);
        Log.e("hotel id",submitOrder.HotelId+"");
        setToolbar();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        // just set viewPager
        springIndicator.setViewPager(viewPager);
    }

    public void setTotalPrice(double price){
        priceView.setText(getResources().getString(R.string.rupees)+" "+price+"");
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = {"1","2","3","4"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return CartDetailFragment.newInstance();
            }else if(position == 1){
                return DeliveryTypeFragment.newInstance();
            }else if(position == 2){
                return UserDetailsFragment.newInstance();
            }else{
                return PaymentTypeFragment.newInstance();
            }
        }
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("My Cart");
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
}
