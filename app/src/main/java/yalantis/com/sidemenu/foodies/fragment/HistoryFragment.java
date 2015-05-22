package yalantis.com.sidemenu.foodies.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yalantis.com.sidemenu.foodies.model.AppConstants;
import yalantis.com.sidemenu.foodies.model.HistoryClass;
import yalantis.com.sidemenu.foodies.model.Hotel;
import yalantis.com.sidemenu.foodies.model.HotelsList;
import yalantis.com.sidemenu.foodies.model.ResponseClass;
import yalantis.com.sidemenu.foodies.model.UserOrder;
import yalantis.com.sidemenu.foodies.utils.CircularImageView;
import yalantis.com.sidemenu.foodies.utils.GetServiceCall;
import yalantis.com.sidemenu.foodies.utils.PostServiceCall;
import yalantis.com.sidemenu.foodies.utils.PostServiceCallTest;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class HistoryFragment extends Fragment implements ScreenShotable {

    public static final String PAINT = "Paint";
    private ListView historyList;
    private MyAppAdapter customAdapter;

    private View containerView;
    protected ImageView mImageView;

    private ProgressDialog progressDialog;
    private ArrayList<UserOrder> hotelArrayList;
    protected int res;
    private Bitmap bitmap;

    public static HistoryFragment newInstance() {
        HistoryFragment contentFragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(),android.R.drawable.screen_background_light_transparent);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        res = getArguments().getInt(Integer.class.getName());
    }

    private void getHotelList() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        new GetServiceCall(AppConstants.USER_HISTORY+PrefUtils.getLogin(getActivity()).OwnerId+"",GetServiceCall.TYPE_JSONOBJECT){

            @Override
            public void response(String response) {
                Log.e("response:", response + "");
                progressDialog.dismiss();
                HistoryClass hotelsList = new GsonBuilder().create().fromJson(response, HistoryClass.class);
                hotelArrayList=hotelsList.userOrderArrayList;
                Log.e("list size", hotelArrayList.size() + "");
                customAdapter=new MyAppAdapter(hotelArrayList,getActivity());
                historyList.setAdapter(customAdapter);
            }

            @Override
            public void error(VolleyError error) {
                progressDialog.dismiss();

            }
        }.call();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
//        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
        historyList= (ListView) rootView.findViewById(R.id.historyList);
        if(PrefUtils.getLogin(getActivity()) != null){
//            getHotelList();
//            getResponseValue();
            postSample();
        }

//        mImageView.setClickable(true);
//        mImageView.setFocusable(true);
//        mImageView.setImageResource(res);
//        Toast.makeText(getActivity(), "content fragment 2", Toast.LENGTH_LONG).show();
        return rootView;
    }

    private void postSample() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Map<String, String> mParams;
        mParams = new HashMap<String, String>();
        mParams.put("email", "nirav@gmail.com");
        mParams.put("fb_id", " ");
        mParams.put("password", "nirav");
        Log.e("params",mParams+"");
        new PostServiceCallTest("http://www.hackaholicit.com/parvarish/manager/users/login",mParams+""){

            @Override
            public void response(String response) {
                Log.e("post response: ",response+"");
                progressDialog.dismiss();
            }



            @Override
            public void error(String error) {
                Log.e("post response: ",error+"");
            }
        }.call();
    }


    @Override
    public void takeScreenShot() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                ContentFragment2.this.bitmap = bitmap;
//            }
//        };
//
//        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    public class MyAppAdapter extends BaseAdapter {

        public class ViewHolder {
            public TextView orderId, PriceToPay, Tax, TotalPrice;
            public TextView Status;

        }

        public List<UserOrder> parkingList;
        public Context context;

        private MyAppAdapter(List<UserOrder> apps, Context context) {
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

                LayoutInflater inflater = getActivity().getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_history, null);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.orderId = (TextView) rowView.findViewById(R.id.orderId);
//                viewHolder.PriceToPay = (TextView) rowView.findViewById(R.id.PriceToPay);
//                viewHolder.Tax = (TextView) rowView.findViewById(R.id.Tax);
                viewHolder.TotalPrice = (TextView) rowView.findViewById(R.id.TotalPrice);
                viewHolder.Status = (TextView) rowView.findViewById(R.id.Status);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.orderId.setText("Order No: "+parkingList.get(position).OrderId + "");
//            viewHolder.PriceToPay.setText("Price To Pay: "+parkingList.get(position).PriceToPay + "");
//            viewHolder.Tax.setText("Tax: "+parkingList.get(position).Tax + "");
            viewHolder.TotalPrice.setText(getResources().getString(R.string.rupees)+" "+parkingList.get(position).TotalPrice + "");
           if(parkingList.get(position).OrderStatus.equalsIgnoreCase("1")){
               viewHolder.Status.setText("Status: Pending");
           } else if(parkingList.get(position).OrderStatus.equalsIgnoreCase("2")){
               viewHolder.Status.setText("Status: Completed");
           } else {
               viewHolder.Status.setText("Status: Cancelled");
           }

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return rowView;
        }
    }
}

