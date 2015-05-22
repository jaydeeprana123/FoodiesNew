package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Android on 28-04-2015.
 */
public class HotelsMenu {

    @SerializedName("CategoryFolderPath")
    public String CategoryFolderPath;
    @SerializedName("CategoryIcon")
    public String CategoryIcon;
    @SerializedName("CategoryId")
    public int CategoryId;
    @SerializedName("CategoryName")
    public String CategoryName;
    @SerializedName("IsActive")
    public boolean IsActive ;
    @SerializedName("Lstcategory")
    public ArrayList<HotelMenuItem> hotelMenuItemArrayList;



}
