package thirdlaw.arkka.vyapar;

public class model {

   String uid,buyer_seller,uname,uphone, uaddress1,u_img;

    public model() {
    }

    public model(String uid, String buyer_seller, String uname, String uphone, String uaddress1, String u_img) {
        this.uid = uid;
        this.buyer_seller = buyer_seller;
        this.uname = uname;
        this.uphone = uphone;
        this.uaddress1 = uaddress1;
        this.u_img = u_img;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBuyer_seller() {
        return buyer_seller;
    }

    public void setBuyer_seller(String buyer_seller) {
        this.buyer_seller = buyer_seller;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUaddress1() {
        return uaddress1;
    }

    public void setUaddress1(String uaddress1) {
        this.uaddress1 = uaddress1;
    }

    public String getU_img() {
        return u_img;
    }

    public void setU_img(String u_img) {
        this.u_img = u_img;
    }
}
