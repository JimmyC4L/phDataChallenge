package phdatachallenge.kafka_prod_cons.services;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import phdatachallenge.kafka_prod_cons.ApacheLog;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Producer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate =kafkaTemplate;
    }

    public void readLogAndSendLines() {
        try {
            File f = new File("/Users/jimmy/src/kafka_prod_cons/src/main/resources/logs/log.txt");
            List<String> lines = FileUtils.readLines(f, "UTF-8");

            for (String line : lines) {
                kafkaTemplate.send("myTopic", line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void parseToClass(String record) {
        final String regex = "^(\\S+) (\\S+) (\\S+) " +
                "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)" +
                " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+)";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(record);

        // Creating a Hashmap containing string as
        // the key and integer as the value.
        HashMap<String, Integer> countIP = new HashMap<String, Integer>();
        while (matcher.find()) {
            ApacheLog apacheLog = new ApacheLog(
                    matcher.group(1),
                    parseDateAndTime(matcher.group(4)),
                    matcher.group(5),
                    Integer.parseInt(matcher.group(8)));
        }

    }

    public Date parseDateAndTime(String dateAndTime){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
        Date date = null;
        try {
           date = formatter.parse(dateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
