package phdatachallenge.kafka_prod_cons.Models;

import phdatachallenge.kafka_prod_cons.Entities.ApacheLogEntity;

import java.util.List;

public class Attacker {

    private String address;

    private int hits;

    private List<ApacheLog> apacheLogs;

    public Attacker(String address, int hits, List<ApacheLog> apacheLogs) {
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

    public List<ApacheLog> getApacheLogs() {
        return apacheLogs;
    }

    public void setApacheLogs(List<ApacheLog> apacheLogs) {
        this.apacheLogs = apacheLogs;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(address + " " + hits + ": \n");
        for (ApacheLog apacheLog : apacheLogs) {
            stringBuilder.append("     "+ apacheLog.toString() + "\n");
        }
        return stringBuilder.toString();
    }
}
