package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Android on 28-04-2015.
 */
public class HotelsMenuList {

    @SerializedName("ResponseCode")
    public String ResponseCode;

    @SerializedName("ResponseMsg")
    public String ResponseMsg;

    @SerializedName("lstMenuItem")
    public ArrayList<HotelsMenu> hotelsMenuArrayList;




}
