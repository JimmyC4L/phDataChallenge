package phdatachallenge.kafka_prod_cons;

import java.io.Serializable;
import java.util.Date;

public class ApacheLog implements Serializable {

    private String address;
    private Date DateAndTime;
    private String requestType;
    private int responseCode;

    public ApacheLog(){}

    public ApacheLog(String address, Date DateAndTime, String requestType, int responseCode){
        this.address = address;
        this.DateAndTime = DateAndTime;
        this.requestType = requestType;
        this.responseCode = responseCode;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateAndTime() {
        return DateAndTime;
    }

    public String getRequestType() {
        return requestType;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateAndTime(Date dateAndTime) {
        DateAndTime = dateAndTime;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String toString(){
        return address + " " + DateAndTime + "\n";
    }
}
