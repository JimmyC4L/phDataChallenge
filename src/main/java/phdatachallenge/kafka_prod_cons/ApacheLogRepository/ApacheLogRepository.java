package phdatachallenge.kafka_prod_cons.ApacheLogRepository;

import org.springframework.data.repository.CrudRepository;
import phdatachallenge.kafka_prod_cons.Entities.ApacheLogEntity;

public interface ApacheLogRepository extends CrudRepository<ApacheLogEntity, Integer> {
}
