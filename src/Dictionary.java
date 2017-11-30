import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    ArrayList<String> positiveList = new ArrayList<String>();
    ArrayList<String> negativeList = new ArrayList<String>();
    int posCount = 0;
    int negCount = 0;

    public Dictionary() throws IOException {
        populateLists();
    }

    public Dictionary(ArrayList<String> posList, ArrayList<String> negList) {
        this.positiveList = posList;
        this.negativeList = negList;
    }

    private void populateLists() throws IOException {
        FileInputStream inPosStream = null;
        FileInputStream inNegStream = null;


        try {
            inPosStream = new FileInputStream("\src\positive.txt");
            inNegStream = new FileInputStream("\src\negative.txt");
            Scanner posScanner = new Scanner(inPosStream);
            Scanner negScanner = new Scanner(inNegStream);

            while(posScanner.hasNextLine()) {
                String entry = posScanner.nextLine();
                positiveList.add(entry);
            }
            while(negScanner.hasNextLine()) {
                String entry = negScanner.nextLine();
                negativeList.add(entry);
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
        }
    }

    public void feedInTweets(String word) {
        int score = 0; 
        for (int i = 0; i < positiveList.size(); i++) {
            if(word.equals(positiveList.get(i))) {
                score++;
            }
            else if(word.equals(negativeList.get(i))) {
                score--;
            }
        }
        if (score > 0)
            posCount++;
        else if (score < 0)
            negCount++; 
    }

    public void calculateSentiment() {
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
