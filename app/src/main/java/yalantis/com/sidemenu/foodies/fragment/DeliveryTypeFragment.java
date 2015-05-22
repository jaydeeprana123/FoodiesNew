package yalantis.com.sidemenu.foodies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yalantis.com.sidemenu.foodies.activity.CartActivity;
import yalantis.com.sidemenu.sample.R;

public class DeliveryTypeFragment extends Fragment {

    View convertView;
    private TextView btnUSerDetail;
    public static DeliveryTypeFragment newInstance() {
        DeliveryTypeFragment fragment = new DeliveryTypeFragment();


        return fragment;
    }


    public DeliveryTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertView= inflater.inflate(R.layout.fragment_delivery_type, container, false);
        btnUSerDetail= (TextView) convertView.findViewById(R.id.btnUSerDetail);
        btnUSerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CartActivity)getActivity()).setCurrentTab(2);
            }
        });
        return convertView;
    }






}