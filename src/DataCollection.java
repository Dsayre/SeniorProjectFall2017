import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

public class DataCollection {

    public static Map<String, Integer> sortDescending(Map<String, Integer> unsorted)
    {
        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsorted.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> first, Entry<String, Integer> second) {
                return second.getValue().compareTo(first.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }




    public static String[] collectData(String input) throws TwitterException, IOException {
    	//JOptionPane.showMessageDialog(null, input);
        // Dictionary dic = new Dictionary();


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("CCV16sY8MoDnmGC8DhJZBPUxW")
                .setOAuthConsumerSecret("RQkMiEymiKYr41k4xGaLSmJJV2i5Z6dKPLk8YefEYLJLDdkFhO")
                .setOAuthAccessToken("909502828112052224-yuxb4ao39g3FSuuomcMw1TgJOPvN6Se")
                .setOAuthAccessTokenSecret("Pg7lFqPk7lfzRqHf7qJMhoi6j0qjesSEErpN4Z1MCeC2Q");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter mytwitter = tf.getInstance();

// Stores count of all words from a query
        Map<String, Integer> words = new HashMap<String, Integer>();
        String buffer = "";
        String[] tw;
        int iterations = 10;
        int batchsize = 100;
        long lastId = Long.MAX_VALUE;

        Query query = new Query(input);
        query.setCount(batchsize);
        QueryResult result;
        Dictionary dic = new Dictionary(input); //bans query term

        for (int j = 0; j < iterations; j++) {
            result = mytwitter.search(query);
            System.out.println("Progress: " + j * batchsize + "/" + iterations * batchsize);

            for (Status status : result.getTweets()) {
                // buffer = status.getText().replaceAll("\\p{Punct}|\\d", "").toLowerCase();
                // tw = buffer.split(" ");
                // tw = status.getText().toLowerCase().split(" ");
                if (status.getId() < lastId) {
                    lastId = status.getId();
                }
                tw = status.getText().replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase().split("\\s+");
                for (int i = 0; i < tw.length; i++) {
                    if (words.containsKey(tw[i])) {
                        words.put(tw[i], words.get(tw[i]) + 1); // Increment on hit
                    } else {
                        words.put(tw[i], 1); // Add new entry on miss
                    }
                }
           // System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
       
            }
            query.setMaxId(lastId - 1);
          //  System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }

        words = sortDescending(words);

        String[] h = new String[3]; 
        for (Entry i : words.entrySet()) {
            dic.feedInTweets(i.getKey().toString(), (int)i.getValue());
            h = dic.findTopThreeSentiments(i.getKey().toString());
            //    System.out.println(i.getKey() + " : " + i.getValue());
        }
        int netPositivityScore = dic.calculateSentiment();
//    System.out.println(words);
        String[] output = new String[4];
        output[0] = Integer.toString(netPositivityScore);
        output[1] = h[0]; 
        output[2] = h[1];
        output[3] = h[2]; 
        return output;
    }
}
