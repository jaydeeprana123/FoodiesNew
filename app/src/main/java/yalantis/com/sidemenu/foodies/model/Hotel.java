package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 27-04-2015.
 */
public class Hotel {

    @SerializedName("AreaName")
    public String AreaName;
    @SerializedName("City")
    public int City;
    @SerializedName("CityName")
    public String CityName;
    @SerializedName("Description")
    public String Description;
    @SerializedName("EmailId")
    public String EmailId;
    @SerializedName("HotelId")
    public int HotelId;
    @SerializedName("HotelName")
    public String HotelName;
    @SerializedName("Logo")
    public String Logo;
    @SerializedName("LogoPath")
    public String LogoPath;
    @SerializedName("PhoneNumber")
    public String PhoneNumber;
    @SerializedName("State")
    public String State;
    @SerializedName("StreetAddress")
    public String StreetAddress;
}
