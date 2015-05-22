package yalantis.com.sidemenu.foodies.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yalantis.com.sidemenu.foodies.activity.MainActivity;
import yalantis.com.sidemenu.foodies.model.AppConstants;
import yalantis.com.sidemenu.foodies.model.SubmitOrder;
import yalantis.com.sidemenu.foodies.utils.PostServiceCall;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.sample.R;

public class PaymentTypeFragment extends Fragment {

    View convertView;
    private JSONObject object;
    private JSONArray jsonArray;
    private JSONObject innerObject;
    private RadioGroup rgPaymentType;
    private SubmitOrder submitOrder;
    private ProgressDialog progressDialog;
    private TextView btnMakePayment;
    public static PaymentTypeFragment newInstance() {
        PaymentTypeFragment fragment = new PaymentTypeFragment();


        return fragment;
    }


    public PaymentTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submitOrder=PrefUtils.getCartItems(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertView= inflater.inflate(R.layout.fragment_payment_type, container, false);
        btnMakePayment= (TextView) convertView.findViewById(R.id.btnMakePayment);
        btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),"Thanks, Your Order Submitted Successfully",Toast.LENGTH_LONG).show();
                checkOutOrdered();


//                Log.e("first name.........454",submitOrder.CustomerLastName+"");
//                Log.e("last name...........54",submitOrder.CustomerLastName+"");

            }
        });
        rgPaymentType= (RadioGroup) convertView.findViewById(R.id.rgPaymentType);
        rgPaymentType.check(0);
        rgPaymentType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.cod:
                        submitOrder.PaymentTypeId= AppConstants.CASH_ON_DELIVERY+"";
                        PrefUtils.AddItemToCart(submitOrder,getActivity());
                        break;

                    case R.id.cd:
                        submitOrder.PaymentTypeId= AppConstants.CREDIT_CARD+"";
                        PrefUtils.AddItemToCart(submitOrder,getActivity());
                        break;

                    case R.id.nb:
                        submitOrder.PaymentTypeId= AppConstants.NET_BANKING+"";
                        PrefUtils.AddItemToCart(submitOrder,getActivity());
                        break;

                    case R.id.dc:
                        submitOrder.PaymentTypeId= AppConstants.DEBIT_CARD+"";
                        PrefUtils.AddItemToCart(submitOrder,getActivity());
                        break;
                }
            }
        });

        return convertView;
    }



    private void checkOutOrdered() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        object=new JSONObject();
        jsonArray=new JSONArray();
        innerObject=new JSONObject();
        try {
            for(int i=0;i<submitOrder.orderItemArrayList.size();i++){
                innerObject.put("FoodDietId",submitOrder.orderItemArrayList.get(i).FoodDietId+"");
                innerObject.put("MenuItem",submitOrder.orderItemArrayList.get(i).MenuItem+"");
                innerObject.put("MenuItemQuantity",submitOrder.orderItemArrayList.get(i).MenuItemQuantity+"");
            }

//            innerObject.put("OrderId","");
//            innerObject.put("OrderItemId","");
            jsonArray.put(innerObject);
            submitOrder.CustomerFirstName=UserDetailsFragment.firstname;
            submitOrder.CustomerLastName=UserDetailsFragment.lastname;

            object.put("CustomerFirstName",submitOrder.CustomerFirstName);
            object.put("CustomerLastName",submitOrder.CustomerLastName);

            object.put("DeliveryArea",submitOrder.DeliveryArea+"");
            object.put("DeliveryCity",submitOrder.DeliveryCity+"");
            object.put("DeliveryCountry",submitOrder.DeliveryCountry+"");
            object.put("DeliveryState",submitOrder.DeliveryState+"");
            if(PrefUtils.getLogin(getActivity()) !=null){
                object.put("Userid", PrefUtils.getLogin(getActivity()).OwnerId + "");
            }else {
                object.put("Userid", submitOrder.Userid + "");
            }
//            object.put("CreatedOn","");
//            object.put("DiscountPercent","");
//            object.put("DiscountPrice","");
            object.put("HotelId",submitOrder.HotelId+"");
            if(PrefUtils.getLogin(getActivity()) !=null) {
                object.put("OrderBy", PrefUtils.getLogin(getActivity()).OwnerId + "");
            }else {
                object.put("OrderBy",submitOrder.OrderBy+"");
            }

            object.put("OrderDesc",submitOrder.OrderDesc+"");
//            object.put("OrderId","");
            object.put("OrderStatus",submitOrder.OrderStatus+"");
            object.put("PaymentTypeId",submitOrder.PaymentTypeId+"");
            object.put("PriceToPay",submitOrder.PriceToPay+"");
            object.put("Tax",submitOrder.Tax+"");
            object.put("TotalPrice",submitOrder.TotalPrice+"");
//            object.put("UpdatedOn","");
            object.put("lstOrderitem", jsonArray);
            Log.e("object:", object + "");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error................",e+"");
        }

        new PostServiceCall(AppConstants.CHECKOUT_ORDER,object){

            @Override
            public void response(String response) {
                progressDialog.dismiss();
                Log.e("response:", response + "");

                try {
                    JSONObject object=new JSONObject(response);
                    if(object.getString("ResponseCode").equalsIgnoreCase("1")){
                        Toast.makeText(getActivity(),object.getString("ResponseMsg")+"\n"+"Your Order Id is "+object.getString("OrderConfirmId")+"",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getActivity(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        getActivity().finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(String error) {
                progressDialog.dismiss();
            }
        }.call();
    }


}