package phdatachallenge.kafka_prod_cons.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ApacheLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;
    private Date DateAndTime;
    private String requestType;
    private int responseCode;
    private String browserInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attackerEntity_id", nullable = false)
    private AttackerEntity attackerEntity;

    public ApacheLogEntity(String address, Date dateAndTime, String requestType, int responseCode, String browserInfo, AttackerEntity attackerEntity) {
        this.address = address;
        DateAndTime = dateAndTime;
        this.requestType = requestType;
        this.responseCode = responseCode;
        this.browserInfo = browserInfo;
        this.attackerEntity = attackerEntity;
    }

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
