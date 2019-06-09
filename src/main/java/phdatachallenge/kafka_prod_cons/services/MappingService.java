package phdatachallenge.kafka_prod_cons.services;

import org.springframework.stereotype.Service;
import phdatachallenge.kafka_prod_cons.Entities.AttackerEntity;
import phdatachallenge.kafka_prod_cons.Models.ApacheLog;
import phdatachallenge.kafka_prod_cons.Entities.ApacheLogEntity;
import phdatachallenge.kafka_prod_cons.Models.Attacker;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class MappingService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ApacheLog> convertAll(Iterable<ApacheLogEntity> apacheLogEntities) {
        List<ApacheLog> results = new ArrayList<>();
        for (ApacheLogEntity apacheLogEntity : apacheLogEntities) {
            results.add(convertOne(apacheLogEntity));
        }
        return results;
    }

    public ApacheLog convertOne(ApacheLogEntity apacheLogEntity) {
        return new ApacheLog(apacheLogEntity.getAddress(), apacheLogEntity.getDateAndTime(), apacheLogEntity.getRequestType(), apacheLogEntity.getResponseCode(), apacheLogEntity.getBrowserInfo());
    }

    public ApacheLogEntity convertOne(ApacheLog apacheLog) {
        return new ApacheLogEntity(apacheLog.getAddress(),apacheLog.getDateAndTime(), apacheLog.getRequestType(), apacheLog.getResponseCode(), apacheLog.getBrowserInfo(),  null);
    }

    public List<AttackerEntity> convertAll(List<Attacker> attackers) {
        List<AttackerEntity> attackerEntities = new ArrayList<>();
        for (Attacker attacker : attackers) {
            AttackerEntity attackerEntity = convertOne(attacker);
            attackerEntities.add(attackerEntity);
        }
        return attackerEntities;
    }

    public AttackerEntity convertOne(Attacker attacker) {
        List<ApacheLogEntity> apacheLogEntities = new ArrayList<>();
        for (ApacheLog apacheLog : attacker.getApacheLogs()){
            ApacheLogEntity apacheLogEntity = convertOne(apacheLog);
            apacheLogEntities.add(apacheLogEntity);
        }
        return new AttackerEntity(attacker.getAddress(), attacker.getHits(), apacheLogEntities);
    }

    public List<AttackerEntity> fillAttackerId(List<AttackerEntity> attackerEntities) {
        for (AttackerEntity attackerEntity : attackerEntities) {
            for (ApacheLogEntity apacheLogEntity : attackerEntity.getApacheLogs()) {
                apacheLogEntity.setAttackerEntity(entityManager.find(AttackerEntity.class, attackerEntity.getId()));
            }
        }
        return  attackerEntities;
    }
}
