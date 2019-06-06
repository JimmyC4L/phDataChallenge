package phdatachallenge.kafka_prod_cons.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import phdatachallenge.kafka_prod_cons.ApacheLog;

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
            List<String> results = mostFrequent();
            for (String result : results) {
                System.out.println(result);
            }
            System.out.println(results.size());
        } else {
            parseStringToApacheLogClass(record);
//            System.out.println(record);

        }


//        ApacheLog apacheLog1 = (ApacheLog) jsonConverter.fromJson(simpleModel, ApacheLog.class);

//        System.out.println(apacheLog1.toString());
//        logParserServices.readLogAndParseToClass("chicken");
    }

    private void parseStringToApacheLogClass(String record) {
        final String regex = "^(\\S+) (\\S+) (\\S+) " +
                "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)" +
                " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+)";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(record);

        while (matcher.find()) {
            ApacheLog apacheLog = new ApacheLog(
                    matcher.group(1),
                    parseStringToDateAndTime(matcher.group(4)),
                    matcher.group(5),
                    Integer.parseInt(matcher.group(8)));
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

    private List<String> mostFrequent() {

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

        for (Map.Entry<String, Integer> val : occurrenceMap.entrySet()) {
            if (maxCount < val.getValue()) {
                maxCount = val.getValue();
            }
        }

        for (Map.Entry<String, Integer> val : occurrenceMap.entrySet()) {
            if (maxCount == val.getValue()) {
                results.add(val.getKey());
            }
        }

        System.out.println(maxCount);

        return results;
    }
}
