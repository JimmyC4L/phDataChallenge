package phdatachallenge.kafka_prod_cons.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phdatachallenge.kafka_prod_cons.ApacheLog;
import phdatachallenge.kafka_prod_cons.ApacheLogRepository.ApacheLogRepository;

import java.util.List;

@Service
public class ApacheLogService {

    private ApacheLogRepository apacheLogRepository;
    private MappingService mappingService;

    @Autowired
    public ApacheLogService (ApacheLogRepository apacheLogRepository, MappingService mappingService) {
        this.apacheLogRepository = apacheLogRepository;
        this.mappingService = mappingService;
    }

    public List<ApacheLog> getAllApacheLogs(){
       return mappingService.convertAll(apacheLogRepository.findAll());
    }

}
