package phdatachallenge.kafka_prod_cons;

import java.io.Serializable;
import java.util.Date;

public class ApacheLog implements Serializable {

    private String address;
    private Date DateAndTime;
    private String requestType;
    private int responseCode;
    private String browserInfo;

    public ApacheLog() {
    }

    public ApacheLog(String address, Date DateAndTime, String requestType, int responseCode, String browserInfo) {
        this.address = address;
        this.DateAndTime = DateAndTime;
        this.requestType = requestType;
        this.responseCode = responseCode;
        this.browserInfo = browserInfo;
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

    public String getBrowserInfo() {
        return browserInfo;
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

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }

    public String toString() {
        return address + " " + DateAndTime + " " + requestType + " " + responseCode + " " + browserInfo + "\n";
    }
}
