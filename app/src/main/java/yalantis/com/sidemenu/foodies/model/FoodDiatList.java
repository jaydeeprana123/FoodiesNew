package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 28-04-2015.
 */
public class FoodDiatList {


    @SerializedName("DietId")
    public int DietId;
    @SerializedName("DietName")
    public String DietName;
    @SerializedName("ISActive")
    public boolean ISActive;
}
