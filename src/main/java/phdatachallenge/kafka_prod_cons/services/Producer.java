package phdatachallenge.kafka_prod_cons.services;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class Producer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void readLogAndSendLines() {
        try {
            File f = new File("/Users/jimmy/src/kafka_prod_cons/src/main/resources/logs/log.txt");
            List<String> lines = FileUtils.readLines(f, "UTF-8");

            for (String line : lines) {
                kafkaTemplate.send("myTopic", line);
            }
            kafkaTemplate.send("myTopic", "CATCH_THE_ATTACKER");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
