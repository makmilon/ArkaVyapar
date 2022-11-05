package thirdlaw.arkka.vyapar;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


class Mapdatum {

    @SerializedName("uid")
    @Expose
    public String uid;
    @SerializedName("buyer_seller")
    @Expose
    public String buyerSeller;
    @SerializedName("uname")
    @Expose
    public String uname;
    @SerializedName("uphone")
    @Expose
    public String uphone;
    @SerializedName("uaddress1")
    @Expose
    public String uaddress1;
    @SerializedName("u_img")
    @Expose
    public String uImg;
    @SerializedName("llong")
    @Expose
    public String llong;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("km")
    @Expose
    public Integer km;

}