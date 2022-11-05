package thirdlaw.arkka.vyapar;

public class notificationmodel {

    String message,admin_message,madate;

    public notificationmodel() {
    }

    public notificationmodel(String message, String admin_message, String madate) {
        this.message = message;
        this.admin_message = admin_message;
        this.madate = madate;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdmin_message() {
        return admin_message;
    }

    public void setAdmin_message(String admin_message) {
        this.admin_message = admin_message;
    }

    public String getMadate() {
        return madate;
    }

    public void setMadate(String madate) {
        this.madate = madate;
    }
}
