package phdatachallenge.kafka_prod_cons.services;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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
            URL res = getClass().getClassLoader().getResource("logs/log.txt");
            File file = Paths.get(res.toURI()).toFile();
            List<String> lines = FileUtils.readLines(file, "UTF-8");

            for (String line : lines) {
                kafkaTemplate.send("myTopic", line);
            }
            kafkaTemplate.send("myTopic", "CATCH_THE_ATTACKER");

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }



}
