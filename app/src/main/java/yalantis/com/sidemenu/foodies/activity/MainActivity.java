package yalantis.com.sidemenu.foodies.activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.foodies.fragment.HotelListFragment;
import yalantis.com.sidemenu.foodies.fragment.FragmentRatings;
import yalantis.com.sidemenu.foodies.model.AppConstants;
import yalantis.com.sidemenu.foodies.model.Balance;
import yalantis.com.sidemenu.foodies.utils.GetServiceCall;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.foodies.fragment.CitySelectionFragment;
import yalantis.com.sidemenu.foodies.fragment.HistoryFragment;
import yalantis.com.sidemenu.foodies.fragment.FragmentContactUs;
import yalantis.com.sidemenu.foodies.fragment.ContentFragment5;
import yalantis.com.sidemenu.foodies.fragment.ContentFragment6;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private CitySelectionFragment contentFragment;
    private ViewAnimator viewAnimator;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentFragment = CitySelectionFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
        getBalance();
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(CitySelectionFragment.CLOSE, R.drawable.icon_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(CitySelectionFragment.BUILDING, R.drawable.address);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(HotelListFragment.BOOK, R.drawable.hotels);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(HistoryFragment.PAINT, R.drawable.history);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(FragmentContactUs.CASE, R.drawable.contactus);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(FragmentRatings.SHOP, R.drawable.rate_us);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment5.PARTY, R.drawable.icn_6);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment6.MOVIE, R.drawable.icn_7);
        list.add(menuItem7);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
//        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
//
        switch (topPosition){

            case 2:
                HotelListFragment contentFragment = HotelListFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
                return contentFragment;

            case 3:
                HistoryFragment contentFragment1 = HistoryFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment1).commit();
                return contentFragment1;

            case 4:
                FragmentContactUs contentFragment2 = FragmentContactUs.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment2).commit();
                return contentFragment2;

            case 5:
                FragmentRatings contentFragment3 = FragmentRatings.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment3).commit();
                return contentFragment3;

            case 6:
                ContentFragment5 contentFragment4 = ContentFragment5.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment4).commit();
                return contentFragment4;

            case 7:
                ContentFragment6 contentFragment5 = ContentFragment6.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment5).commit();
                return contentFragment5;

            case 1:
                CitySelectionFragment contentFragment7 = CitySelectionFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment7).commit();
                return contentFragment7;
        }

        HotelListFragment contentFragment = HotelListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case CitySelectionFragment.CLOSE:
                return screenShotable;

            case CitySelectionFragment.BUILDING:
                return replaceFragment(screenShotable, 1);
            case HotelListFragment.BOOK:
                return replaceFragment(screenShotable, 2);
            case HistoryFragment.PAINT:
                return replaceFragment(screenShotable, 3);
            case FragmentContactUs.CASE:
                return replaceFragment(screenShotable, 4);
            case FragmentRatings.SHOP:
                return replaceFragment(screenShotable, 5);
            case ContentFragment5.PARTY:
                return replaceFragment(screenShotable, 6);
            case ContentFragment6.MOVIE:
                return replaceFragment(screenShotable, 7);

            default:
                return screenShotable;
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }


    private void getBalance(){
      if(  PrefUtils.getLogin(MainActivity.this) !=null) {
          progressDialog=new ProgressDialog(MainActivity.this);
          progressDialog.setMessage("Loading...");
          progressDialog.show();
          new GetServiceCall(AppConstants.GET_BALANCE+PrefUtils.getLogin(MainActivity.this).OwnerId+"",GetServiceCall.TYPE_JSONOBJECT){

              @Override
              public void response(String response) {
                  Log.e("response:", response + "");
                  progressDialog.dismiss();
                  Balance balance = new GsonBuilder().create().fromJson(response, Balance.class);
                    PrefUtils.setBalance(balance,MainActivity.this);
                  Log.e("user balance", balance.UserBal + "");

              }

              @Override
              public void error(VolleyError error) {
                  progressDialog.dismiss();
                  ;
              }
          }.call();
      }
    }

    private void getHotelList() {

    }

}
