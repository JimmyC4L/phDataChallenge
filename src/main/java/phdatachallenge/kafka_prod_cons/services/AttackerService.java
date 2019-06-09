package phdatachallenge.kafka_prod_cons.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phdatachallenge.kafka_prod_cons.ApacheLogRepository.AttackerRepository;
import phdatachallenge.kafka_prod_cons.Entities.AttackerEntity;
import phdatachallenge.kafka_prod_cons.Models.Attacker;

import java.util.List;

@Service
public class AttackerService {


    private AttackerRepository attackerRepository;
    private MappingService mappingService;

    @Autowired
    public AttackerService(AttackerRepository attackerRepository, MappingService mappingService) {
        this.attackerRepository = attackerRepository;
        this.mappingService = mappingService;
    }

    public void saveAll(List<Attacker> attackers) {
        List<AttackerEntity> attackerEntities = mappingService.convertAll(attackers);
        attackerRepository.saveAll(attackerEntities);
        mappingService.fillAttackerId(attackerEntities);
        attackerRepository.saveAll(attackerEntities);
    }
}
