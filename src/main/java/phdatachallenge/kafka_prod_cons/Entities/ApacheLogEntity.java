package phdatachallenge.kafka_prod_cons.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ApacheLogEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String address;
    private Date DateAndTime;
    private String requestType;
    private int responseCode;
    private String browserInfo;

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
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
}
