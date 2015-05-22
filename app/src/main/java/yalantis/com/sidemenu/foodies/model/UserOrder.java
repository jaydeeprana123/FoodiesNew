package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Android on 01-05-2015.
 */
public class UserOrder {

    @SerializedName("OrderId")
    public String OrderId;
    @SerializedName("OrderStatus")
    public String OrderStatus;
    @SerializedName("PriceToPay")
    public String PriceToPay;
    @SerializedName("Tax")
    public String Tax;
    @SerializedName("TotalPrice")
    public String TotalPrice;



}
