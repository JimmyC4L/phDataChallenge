package phdatachallenge.kafka_prod_cons.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import phdatachallenge.kafka_prod_cons.Models.ApacheLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Consumer {

    private static final String SIGNAL = "CATCH_THE_ATTACKER";

    private List<ApacheLog> apacheLogs = new ArrayList<>();

    @KafkaListener(topics = "myTopic", groupId = "myGroupId")
    public void getFromKafka(String record) {

        if (record.equals(SIGNAL)) {
            Map<String, List<ApacheLog>> results = getMostFrequent();
            for (Map.Entry<String, List<ApacheLog>> result : results.entrySet()) {
                System.out.println(result.getKey());
                for (ApacheLog apacheLog : result.getValue()) {
                    System.out.println(apacheLog.toString());
                }
            }
            System.out.println(results.size());
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

    private Map<String, List<ApacheLog>> getMostFrequent() {

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

        return consolidateResults(results);
    }

    private Map<String, List<ApacheLog>> consolidateResults(List<String> results) {
        Map<String, List<ApacheLog>> consolidatedResults = new HashMap<>();
        for (String result : results) {
            List<ApacheLog> sameAddressApacheLogs = new ArrayList<>();
            for (ApacheLog apacheLog : apacheLogs) {
                if (result.equals(apacheLog.getAddress())) {
                    sameAddressApacheLogs.add(apacheLog);
                }
            }
            consolidatedResults.put(result, sameAddressApacheLogs);
        }

        return  consolidatedResults;
    }
}
