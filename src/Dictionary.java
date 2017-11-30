import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    ArrayList<String> positiveList = new ArrayList<String>();
    ArrayList<String> negativeList = new ArrayList<String>();
    ArrayList<String> neutralList = new ArrayList<String>();
    String[] topThree = new String[3];
    int topThreeIndex = 0;
    int posCount = 0;
    int negCount = 0;

    public Dictionary(String query) throws IOException {
        neutralList.add(query);
        populateLists();
    }

    public Dictionary(ArrayList<String> posList, ArrayList<String> negList, ArrayList<String> neutralList) {
        this.positiveList = posList;
        this.negativeList = negList;
        this.neutralList = neutralList;
    }

    private void populateLists() throws IOException {
        FileInputStream inPosStream = null;
        FileInputStream inNegStream = null;
        FileInputStream inNeutralStream= null;


        try {
            inPosStream = new FileInputStream("src/positive.txt");
            inNegStream = new FileInputStream("src/negative.txt");
            inNeutralStream = new FileInputStream("src/neutral.txt");
            Scanner posScanner = new Scanner(inPosStream);
            Scanner negScanner = new Scanner(inNegStream);
            Scanner neutralScanner = new Scanner(inNeutralStream);

            while(posScanner.hasNextLine()) {
                String entry = posScanner.nextLine();
                positiveList.add(entry);
            }
            while(negScanner.hasNextLine()) {
                String entry = negScanner.nextLine();
                negativeList.add(entry);
            }
            while(neutralScanner.hasNextLine()) {
                String entry = neutralScanner.nextLine();
                neutralList.add(entry);
            }

        }
        catch (IOException excpt) {
            System.out.println("Caught IOException: " + excpt.getMessage());
        }
        finally { //closes streams
            if (inPosStream != null) {
                inPosStream.close();
            }
            if (inNegStream != null) {
                inNegStream.close();
            }
            if (inNeutralStream != null) {
                inNeutralStream.close();
            }
        }
    }

    public void feedInTweets(String word, int value) {
        for (int i = 0; i < positiveList.size(); i++) {
            if(word.equalsIgnoreCase(positiveList.get(i))) {
                posCount+=value;
            }
            else if(word.equalsIgnoreCase(negativeList.get(i))) {
                negCount+=value;
            }
        }
    }

    public void findTopThreeSentiments(String word) {
        if(topThreeIndex < topThree.length) {
            boolean isNeutral = false;
            for(int i = 0; i < neutralList.size(); i++) {
                if(word.equalsIgnoreCase(neutralList.get(i))) {
                    isNeutral = true;
                }
            }
            if(!isNeutral) {
                topThree[topThreeIndex] = word;
                topThreeIndex++;
            }
        }
    }

    public void calculateSentiment() {
        for(int j = 1; j < topThree.length + 1; j++) {
            System.out.println("Number " + j + " word: " + topThree[j-1]);
        }
        System.out.println("Net positivity score is: " + (posCount-negCount));
    }

    /*
    private void printList() {
        for (int i = 0; i < positiveList.size(); i++) {
            System.out.println(positiveList.get(i));
        }
    }
    */
}
