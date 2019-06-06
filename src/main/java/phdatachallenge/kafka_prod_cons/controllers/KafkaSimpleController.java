package phdatachallenge.kafka_prod_cons.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phdatachallenge.kafka_prod_cons.services.Producer;

@RestController
@RequestMapping("/api/kafka")
public class KafkaSimpleController {

    private Producer producer;

    @Autowired
    public KafkaSimpleController(Producer producer){
        this.producer = producer;
    }

    @PostMapping
    public void post(){
        producer.readLogAndSendLines();
    }

}
