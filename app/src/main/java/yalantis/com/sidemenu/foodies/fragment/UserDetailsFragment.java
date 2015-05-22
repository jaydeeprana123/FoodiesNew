package yalantis.com.sidemenu.foodies.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import yalantis.com.sidemenu.foodies.activity.CartActivity;
import yalantis.com.sidemenu.foodies.activity.LoginActivity;
import yalantis.com.sidemenu.foodies.model.AppConstants;
import yalantis.com.sidemenu.foodies.model.LoginClass;
import yalantis.com.sidemenu.foodies.model.SubmitOrder;
import yalantis.com.sidemenu.foodies.utils.PostServiceCall;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.sample.R;

public class UserDetailsFragment extends Fragment {

    View convertView;
    private ProgressDialog progressDialog;
    LoginClass loginClass;
    private SubmitOrder submitOrder;
    private TextView btnLogin;
    public static String firstname,lastname;
    private TextView btnPaymentType;
    private  EditText etFirstName,etLastName,etEmail;
    public static UserDetailsFragment newInstance() {
        UserDetailsFragment fragment = new UserDetailsFragment();


        return fragment;
    }


    public UserDetailsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("on Resume","called");
        loginClass=PrefUtils.getLogin(getActivity());
        if(loginClass !=null){
            etFirstName.setText(loginClass.FName);
            etLastName.setText(loginClass.LName);
            etEmail.setText(loginClass.loginObject.EmailAddress);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertView= inflater.inflate(R.layout.fragment_user_details, container, false);
        btnPaymentType= (TextView) convertView.findViewById(R.id.btnPaymentType);
        loginClass=PrefUtils.getLogin(getActivity());
        btnLogin= (TextView) convertView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginClass !=null) {

                    Toast.makeText(getActivity(),"Already Login",Toast.LENGTH_LONG).show();
                } else {
                    Intent i =new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
            }
        });
        etFirstName= (EditText) convertView.findViewById(R.id.etFirstName);
        etLastName= (EditText) convertView.findViewById(R.id.etLastName);
        etEmail= (EditText) convertView.findViewById(R.id.etEmail);


        submitOrder= PrefUtils.getCartItems(getActivity());

//        submitOrder.CustomerFirstName=etFirstName.getText().toString().trim();
//        submitOrder.CustomerLastName=etLastName.getText().toString().trim();
//        PrefUtils.AddItemToCart(submitOrder,getActivity());
        btnPaymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder = PrefUtils.getCartItems(getActivity());

                firstname = etFirstName.getText().toString().trim();
                lastname = etLastName.getText().toString().trim();

//                submitOrder.CustomerFirstName=firstname;
//                submitOrder.CustomerLastName=lastname;
//                Log.e("first name",firstname+"");
//                Log.e("last name",lastname+"");
                PrefUtils.AddItemToCart(submitOrder, getActivity());
                ((CartActivity) getActivity()).setCurrentTab(3);

            }
        });
        return convertView;
    }




}