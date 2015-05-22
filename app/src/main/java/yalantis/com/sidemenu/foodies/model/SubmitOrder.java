package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Android on 28-04-2015.
 */
public class SubmitOrder {

//    @SerializedName("CustomerFirstName")
    public String CustomerFirstName;

//    @SerializedName("CustomerLastName")
    public String CustomerLastName;

//    @SerializedName("DeliveryArea")
    public String DeliveryArea;

//    @SerializedName("DeliveryCity")
    public String DeliveryCity;

//    @SerializedName("DeliveryCountry")
    public String DeliveryCountry="1";

//    @SerializedName("DeliveryState")
    public String DeliveryState="1";

//    @SerializedName("Userid")
    public String Userid;

//    @SerializedName("CreatedOn")
//    public String CreatedOn;

//    @SerializedName("DiscountPercent")
//    public String DiscountPercent;

//    @SerializedName("DiscountPrice")
//    public String DiscountPrice;

//    @SerializedName("HotelId")
    public String HotelId;

    public String HotelName;


//    @SerializedName("OrderBy")
    public String OrderBy;

//    @SerializedName("OrderDesc")
    public String OrderDesc="test";

//    @SerializedName("OrderId")
//    public String OrderId;

//    @SerializedName("OrderStatus")
    public String OrderStatus;

//    @SerializedName("PaymentTypeId")
    public String PaymentTypeId;

//    @SerializedName("PriceToPay")
    public String PriceToPay;

//    @SerializedName("Tax")
    public String Tax;

//    @SerializedName("TotalPrice")
    public String TotalPrice;

//    @SerializedName("UpdatedOn")
//    public String UpdatedOn;

//    @SerializedName("lstOrderitem")
    public ArrayList<OrderItem> orderItemArrayList;

}
