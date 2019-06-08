package phdatachallenge.kafka_prod_cons.Models;

import phdatachallenge.kafka_prod_cons.Entities.ApacheLogEntity;

import java.util.List;

public class Attacker {

    private String address;

    private int hits;

    private List<ApacheLogEntity> apacheLogs;

    public Attacker(String address, int hits, List<ApacheLogEntity> apacheLogs) {
        this.address = address;
        this.hits = hits;
        this.apacheLogs = apacheLogs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public List<ApacheLogEntity> getApacheLogs() {
        return apacheLogs;
    }

    public void setApacheLogs(List<ApacheLogEntity> apacheLogs) {
        this.apacheLogs = apacheLogs;
    }
}
