package twitterproject;

import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Query; 
import twitter4j.QueryResult; 
import java.util.*; 
import java.util.Map.Entry;

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
    
    
    
    public static void main(String[] args) throws TwitterException {
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
  .setOAuthConsumerKey("")
  .setOAuthConsumerSecret("")
  .setOAuthAccessToken("")
  .setOAuthAccessTokenSecret("");
TwitterFactory tf = new TwitterFactory(cb.build());
Twitter mytwitter = tf.getInstance();
        
// Stores count of all words from a query
        Map<String, Integer> words = new HashMap<String, Integer>();
        String buffer = "";
        String[] tw;
        int iterations = 10;
        int batchsize = 100;
        long lastId = Long.MAX_VALUE;

        Query query = new Query("#taketheknee");
        /*response.setContentType("text/html"); //sets query equal to input
        PrintWriter out = response.getWriter();
        String input = request.getParameter("keywordTextbox");
        Query query = new Query(input);
        */
        query.setCount(batchsize);      
        QueryResult result;

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
            }
            query.setMaxId(lastId - 1);
            //System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }

        words = sortDescending(words);

        for (Entry i : words.entrySet()) {
            System.out.println(i.getKey() + " : " + i.getValue());
        }
//    System.out.println(words); 

    }
}
