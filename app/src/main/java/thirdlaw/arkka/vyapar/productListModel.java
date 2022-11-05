package thirdlaw.arkka.vyapar;

public class productListModel {

    String bpid, user_name,user_phone,cname,sname,product_qty,product_date,product_image, longi, lati;

    public productListModel() {
    }

    public productListModel(String bpid, String user_name, String user_phone, String cname, String sname, String product_qty, String product_date, String product_image, String longi, String lati) {
        this.bpid = bpid;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.cname = cname;
        this.sname = sname;
        this.product_qty = product_qty;
        this.product_date = product_date;
        this.product_image = product_image;
        this.longi = longi;
        this.lati = lati;
    }

    public String getBpid() {
        return bpid;
    }

    public void setBpid(String bpid) {
        this.bpid = bpid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProduct_date() {
        return product_date;
    }

    public void setProduct_date(String product_date) {
        this.product_date = product_date;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }
}
