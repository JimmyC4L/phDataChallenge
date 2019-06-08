package phdatachallenge.kafka_prod_cons.ApacheLogRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import phdatachallenge.kafka_prod_cons.Entities.ApacheLogEntity;

public interface ApacheLogRepository extends JpaRepository<ApacheLogEntity, Integer> {
}
