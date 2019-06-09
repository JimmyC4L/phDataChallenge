package phdatachallenge.kafka_prod_cons.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class AttackerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    private int hits;


    @OneToMany(mappedBy = "attackerEntity", cascade = CascadeType.ALL)
    private List<ApacheLogEntity> apacheLogs;

    public AttackerEntity(){

    }

    public AttackerEntity(String address, int hits, List<ApacheLogEntity> apacheLogs) {
        this.address = address;
        this.hits = hits;
        this.apacheLogs = apacheLogs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
