package thirdlaw.arkka.vyapar;

public class tpricemodel {

    String tname, tprice;

    public tpricemodel(String tname, String tprice) {
        this.tname = tname;
        this.tprice = tprice;
    }


    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }
}
