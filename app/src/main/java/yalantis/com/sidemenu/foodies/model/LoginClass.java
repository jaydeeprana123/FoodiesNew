package yalantis.com.sidemenu.foodies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 01-05-2015.
 */
public class LoginClass {

    @SerializedName("Login")
    public Login loginObject;
    @SerializedName("FName")
    public String FName;
    @SerializedName("LName")
    public String LName;
    @SerializedName("OwnerId")
    public String OwnerId;


}
