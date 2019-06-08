package phdatachallenge.kafka_prod_cons.ApacheLogRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import phdatachallenge.kafka_prod_cons.Entities.AttackerEntity;

public interface AttackerRepository  extends JpaRepository<AttackerEntity, Integer> {
}
