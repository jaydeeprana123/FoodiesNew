package yalantis.com.sidemenu.foodies.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alertdialogpro.AlertDialogPro;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import yalantis.com.sidemenu.foodies.model.AppConstants;
import yalantis.com.sidemenu.foodies.model.Area;
import yalantis.com.sidemenu.foodies.model.Balance;
import yalantis.com.sidemenu.foodies.model.City;
import yalantis.com.sidemenu.foodies.model.CityList;
import yalantis.com.sidemenu.foodies.utils.GetServiceCall;
import yalantis.com.sidemenu.foodies.utils.PrefUtils;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class CitySelectionFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "Close";
    public static final String BUILDING = "Building";
    private static final int NATIVE_THEME = Integer.MIN_VALUE;
    private int mTheme = -1;
    private  String mCheckedItem;
    private TextView points;
    private ProgressDialog progressDialog;
    private ArrayList<City> cityArrayList;
//    private ArrayList<String> cityStringList;
//    String[] cityListValue;

//    private ArrayList<Area> areaArrayList;
//    private ArrayList<String> areaStringList;
//    String[] areaListValue;


    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    private TextView btnSubmit;

    private Spinner etCity,etArea;

    public static CitySelectionFragment newInstance() {
        CitySelectionFragment contentFragment = new CitySelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), android.R.drawable.screen_background_light_transparent);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());

    }

    @Override
    public void onResume() {
        super.onResume();
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        getCityList();

    }

    private void getCityList(){
        new GetServiceCall(AppConstants.GET_CITY,GetServiceCall.TYPE_JSONOBJECT){

            @Override
            public void response(String response) {
                Log.e("response:", response + "");
                progressDialog.dismiss();
                CityList cityList = new GsonBuilder().create().fromJson(response, CityList.class);
                cityArrayList=cityList.cityArrayList;
                CitySpinnerAdapter cityAdapter=new CitySpinnerAdapter(getActivity(),cityArrayList);
                etCity.setAdapter(cityAdapter);
                etCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        AreaSpinnerAdapter areaSpinnerAdapter = new AreaSpinnerAdapter(getActivity(), cityArrayList.get(etCity.getSelectedItemPosition()).areaListArrayList);
                        etArea.setAdapter(areaSpinnerAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                etArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        PrefUtils.setArea(cityArrayList.get(etCity.getSelectedItemPosition()).areaListArrayList.get(etArea.getSelectedItemPosition()).AreaId + "", getActivity());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
//                cityStringList=new ArrayList<String>();
//                for(int i=0;i<cityArrayList.size();i++){
//                    cityStringList.add(cityArrayList.get(i).cityName);
//                }
//                cityListValue = cityStringList.toArray(new String[cityStringList.size()]);

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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        etCity= (Spinner) rootView.findViewById(R.id.etCity);
        etArea= (Spinner) rootView.findViewById(R.id.etArea);
        btnSubmit= (TextView) rootView.findViewById(R.id.btnSubmit);
        points= (TextView) rootView.findViewById(R.id.points);
        Balance balance=PrefUtils.getBalance(getActivity());
        if(balance !=null) {
            points.setText("You earn "+balance.UserBal +" points");
        } else {
            points.setText("You earn 0 points");
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new fragment and transaction
                Fragment newFragment = HotelListFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });
//        etCityetCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                final String[] list = new String[]{"Vadodara", "Surat"};
//                int checkedItemIndex = 0;
//                mCheckedItem = cityListValue[checkedItemIndex];
//
//                createAlertDialogBuilder()
//                        .setTitle("Select City")
//                        .setSingleChoiceItems(cityListValue,
//                                checkedItemIndex,
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        mCheckedItem = cityListValue[which];
////                                        showToast(mCheckedItem);
//                                    }
//                                })
//                        .setNegativeButton("Cancel", new ButtonClickedListener("Cancel"))
//                        .setPositiveButton(
//                                "Save",
//                                new ButtonClickedListener("Save")
//
//                        )
//                        .show();
//
//            }
//        });

//        etArea.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                final String[] list = new String[]{"Vadodara", "Surat"};
//                int checkedItemIndex = 0;
//                mCheckedItem = cityListValue[checkedItemIndex];
//
//                createAlertDialogBuilder()
//                        .setTitle("Select Area")
//                        .setSingleChoiceItems(cityListValue,
//                                checkedItemIndex,
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        mCheckedItem = cityListValue[which];
////                                        showToast(mCheckedItem);
//                                    }
//                                })
//                        .setNegativeButton("Cancel", new ButtonClickedListener("Cancel"))
//                        .setPositiveButton(
//                                "Save",
//                                new ButtonClickedListener("Save")
//
//                        )
//                        .show();
//
//            }
//        });
        return rootView;
    }

//    private void showToast(CharSequence toastText) {
//        Toast mToast = null;
//        if (mToast != null) {
//            mToast.cancel();
//        }
//        mToast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT);
//        mToast.show();
//    }

    private AlertDialog.Builder createAlertDialogBuilder() {
        if (mTheme == NATIVE_THEME) {
            return new AlertDialog.Builder(getActivity());
        }

        return new AlertDialogPro.Builder(getActivity(), mTheme);
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
//                CitySelectionFragment.this.bitmap = bitmap;
//            }
//        };
//
//        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

//    private class ButtonClickedListener implements DialogInterface.OnClickListener {
//        private CharSequence mShowWhenClicked;
//
//        public ButtonClickedListener(CharSequence showWhenClicked) {
//            mShowWhenClicked = showWhenClicked;
//
//
//        }
//
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
////            showToast("\"" + mShowWhenClicked + "\"" + " button clicked.");
////            etCityetCity.setText(mCheckedItem);
//
//
//        }
//    }

    public class CitySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<City> asr;

        public CitySpinnerAdapter(Context context, ArrayList<City> asr) {
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
            TextView txt = new TextView(getActivity());
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position).cityName);
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;

        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
//            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down, 0);
            txt.setText(asr.get(i).cityName);
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }
    }

    public class AreaSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<Area> asr;

        public AreaSpinnerAdapter(Context context, ArrayList<Area> asr) {
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
            TextView txt = new TextView(getActivity());
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position).AreaName);
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;

        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
//            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down, 0);
            txt.setText(asr.get(i).AreaName);
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }
    }
}

