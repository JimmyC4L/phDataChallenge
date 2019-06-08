package phdatachallenge.kafka_prod_cons.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phdatachallenge.kafka_prod_cons.ApacheLog;
import phdatachallenge.kafka_prod_cons.services.ApacheLogService;
import phdatachallenge.kafka_prod_cons.services.Producer;

import java.util.List;

@RestController
@RequestMapping("/api/kafka")
public class KafkaSimpleController {

    private Producer producer;
    private ApacheLogService apacheLogService;


    @Autowired
    public KafkaSimpleController(Producer producer, ApacheLogService apacheLogService){
        this.producer = producer;
        this.apacheLogService = apacheLogService;
    }

    @PostMapping
    public void readLogAndSendLines(){
        producer.readLogAndSendLines();
    }

    @GetMapping(path="/all")
    public List<ApacheLog> getAllApacheLog() {
        return apacheLogService.getAllApacheLogs();
    }

}
