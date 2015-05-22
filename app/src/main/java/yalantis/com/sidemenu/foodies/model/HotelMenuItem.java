package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Android on 28-04-2015.
 */
public class HotelMenuItem {

    @SerializedName("HotelId")
    public int HotelId;
    @SerializedName("IsActive")
    public boolean IsActive;
    @SerializedName("IsDeleted")
    public boolean IsDeleted;
    @SerializedName("ItemId")
    public int ItemId;
    @SerializedName("ItemaName")
    public String ItemaName;
    @SerializedName("MenuFolderPath")
    public String MenuFolderPath;
    @SerializedName("MenuIcon")
    public String MenuIcon;
    @SerializedName("Price")
    public String Price;
    @SerializedName("TagLine")
    public String TagLine;
    @SerializedName("CuisineObj")
    public CuisineObject cuisineObject;
    @SerializedName("lstFoodDiate")
    public ArrayList<FoodDiatList> foodDiatListArrayList;

}
