package phdatachallenge.kafka_prod_cons.services;

import org.springframework.stereotype.Service;
import phdatachallenge.kafka_prod_cons.ApacheLog;
import phdatachallenge.kafka_prod_cons.Entities.ApacheLogEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MappingService {

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
}
