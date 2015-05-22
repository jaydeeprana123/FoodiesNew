package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Android on 27-04-2015.
 */
public class CityList {


    @SerializedName("ResponseCode")
    public int ResponseCode;
    @SerializedName("ResponseMsg")
    public String ResponseMsg;
    @SerializedName("lstcity")
    public ArrayList<City> cityArrayList;
}
