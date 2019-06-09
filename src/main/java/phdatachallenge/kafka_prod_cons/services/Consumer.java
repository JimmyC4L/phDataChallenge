package phdatachallenge.kafka_prod_cons.services;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import phdatachallenge.kafka_prod_cons.Models.ApacheLog;
import phdatachallenge.kafka_prod_cons.Models.Attacker;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.Charset.forName;

@Service
public class Consumer {

    private Gson gson;
    private AmazonService amazonService;
    private AttackerService attackerService;

    private static final String SIGNAL = "CATCH_THE_ATTACKER";

    private List<ApacheLog> apacheLogs = new ArrayList<>();

    @Autowired
    public Consumer(Gson gson, AmazonService amazonService, AttackerService attackerService) {
        this.gson = gson;
        this.amazonService = amazonService;
        this.attackerService = attackerService;
    }

    @KafkaListener(topics = "myTopic", groupId = "myGroupId")
    public void getFromKafka(String record) {

        if (record.equals(SIGNAL)) {
            List<Attacker> attackers = getMostFrequent();
            generateJsonFileFromResultsAndUploadeToAmazonS3(attackers);
            saveToDatabase(attackers);
            for (Attacker attacker : attackers) {
                System.out.println(attacker.toString());
            }
            System.out.println(attackers.size());
        } else {
            parseStringToApacheLogClass(record);
        }
    }

    private void parseStringToApacheLogClass(String record) {
        final String regex = "^(\\S+) (\\S+) (\\S+) " +
                "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)" +
                " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+) (\\S+) (.*)";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(record);

        while (matcher.find()) {
            ApacheLog apacheLog = new ApacheLog(
                    matcher.group(1),
                    parseStringToDateAndTime(matcher.group(4)),
                    matcher.group(5),
                    Integer.parseInt(matcher.group(8)),
                    matcher.group(11));
            apacheLogs.add(apacheLog);
        }
    }

    private Date parseStringToDateAndTime(String dateAndTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(dateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private List<Attacker> getMostFrequent() {

        // Insert all elements in hash
        Map<String, Integer> occurrenceMap = new HashMap<>();

        for (ApacheLog apacheLog : apacheLogs) {
            String key = apacheLog.getAddress();
            if (occurrenceMap.containsKey(key)) {
                int freq = occurrenceMap.get(key);
                freq++;
                occurrenceMap.put(key, freq);
            } else {
                occurrenceMap.put(key, 1);
            }
        }

        // find max frequency.
        int maxCount = 0;
        List<String> results = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : occurrenceMap.entrySet()) {
            if (maxCount < entry.getValue()) {
                maxCount = entry.getValue();
                results = new ArrayList<>();
            } else if (maxCount == entry.getValue()) {
                results.add(entry.getKey());
            }
        }

        return consolidateResults(results, maxCount);
    }

    private List<Attacker> consolidateResults(List<String> results, int hits) {
        List<Attacker> consolidatedResults = new ArrayList<>();
        for (String result : results) {
            Attacker attacker = new Attacker(result, hits, null);
            List<ApacheLog> sameAddressApacheLogs = new ArrayList<>();
            for (ApacheLog apacheLog : apacheLogs) {
                if (result.equals(apacheLog.getAddress())) {
                    sameAddressApacheLogs.add(apacheLog);
                }
                attacker.setApacheLogs(sameAddressApacheLogs);
            }
            consolidatedResults.add(attacker);
        }

        return  consolidatedResults;
    }

    private void generateJsonFileFromResultsAndUploadeToAmazonS3(List<Attacker> attackers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Attacker attacker : attackers) {
            stringBuilder.append(gson.toJson(attacker));
        }
        try {
            File tempFile = File.createTempFile("attackers", ".tmp");
            FileUtils.writeStringToFile(tempFile, stringBuilder.toString(), forName("UTF-8"));
            amazonService.uploadFile(tempFile);
            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(List<Attacker> attackers) {
        attackerService.saveAll(attackers);
    }

}
