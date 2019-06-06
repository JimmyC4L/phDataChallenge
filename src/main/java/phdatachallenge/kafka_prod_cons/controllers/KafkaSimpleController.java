package phdatachallenge.kafka_prod_cons.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phdatachallenge.kafka_prod_cons.services.Producer;

@RestController
@RequestMapping("/api/kafka")
public class KafkaSimpleController {

    private Gson jsonConverter;
    private Producer producer;

    @Autowired
    public KafkaSimpleController(Gson jsonConverter, Producer producer){
        this.jsonConverter = jsonConverter;
        this.producer = producer;
    }

    @PostMapping
    public void post(){
        producer.readLogAndSendLines();
    }

}
