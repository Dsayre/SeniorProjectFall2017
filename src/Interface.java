
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import twitter4j.TwitterException;

//Note: Typically the main method will be in a
//separate class. As this is a simple one class
//example it's all in the one class.
public class Interface extends JFrame {

    String query = "";
    String[] results = {"", "", "", ""};

    public static void main(String[] args) {

        Interface i = new Interface();
    }

    public Interface() {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("TwitterPoll");
        frame.setPreferredSize(new Dimension(450, 500));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        final JPanel pane = new JPanel();

        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(300, 30));
        JButton search = new JButton("Search");
        search.setPreferredSize(new Dimension(100, 30));
        JLabel nps = new JLabel("");
        nps.setFont(new Font(nps.getFont().getFontName(), Font.PLAIN, 80));
        nps.setPreferredSize(new Dimension(300, 200));
        JLabel word1 = new JLabel("Enter");
        word1.setPreferredSize(new Dimension(300, 50));
        word1.setFont(new Font(nps.getFont().getFontName(), Font.PLAIN, 30));
        JLabel word2 = new JLabel("Keyword");
        word2.setPreferredSize(new Dimension(300, 50));
        word2.setFont(new Font(nps.getFont().getFontName(), Font.PLAIN, 30));
        JLabel word3 = new JLabel("Above");
        word3.setPreferredSize(new Dimension(300, 50));
        word3.setFont(new Font(nps.getFont().getFontName(), Font.PLAIN, 30));

        pane.add(text);
        pane.add(search);
        pane.add(nps);
        pane.add(word1);
        pane.add(word2);
        pane.add(word3);

        frame.setLayout(new GridBagLayout());
        frame.add(pane);
        frame.setContentPane(pane);

        frame.pack();

//  frame.add(pane); 
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nps.setText("loading");
                word1.setText("");
                word2.setText("");
                word3.setText("");
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        query = text.getText();
                        try {
                            results = DataCollection.collectData(query);
//        System.out.println(results[1]); 
                            nps.setText(results[0]);
                            int value = Integer.parseInt(results[0]);
                            if (value > 600) {
                                nps.setForeground(Color.green);
                            } else if (value > 300) {
                                nps.setForeground(Color.yellow);
                            } else {
                                nps.setForeground(Color.red);
                            }
                            word1.setText(results[1]);
                            word2.setText(results[2]);
                            word3.setText(results[3]);
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                    }

                };
                Thread t = new Thread(r);
                t.start(); 
            }

        }
        );
    }
}

    /*
/* FrameDemo.java requires no other files. 
public class Interface {
    private static JTextField pollText;
    private static JPanel panel;
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TwitterPoll");
        frame.setSize(500, 400);
//        frame.setState(Frame.MAXIMIZED_BOTH);
        frame.setIconImage(new ImageIcon("http://icons.iconarchive.com/icons/uiconstock/socialmedia/512/Twitter-icon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        
        JLabel emptyLabel = new JLabel("TwitterPoll");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        
        frame.add(panel);
		placeComponents(panel);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void placeComponents(JPanel panel) {
    	panel.setLayout(null);

		JLabel pollLabel = new JLabel("Poll");
		pollLabel.setBounds(10, 10, 80, 25);
		pollLabel.setPreferredSize(new Dimension(50, 75));
		panel.add(pollLabel);

		pollText = new JTextField(100);
		pollText.setBounds(100, 10, 160, 25);
		panel.add(pollText);

		JButton searchButton = new JButton("Search");
		searchButton.setBounds(10, 40, 80, 25);
		panel.add(searchButton);
		
		ActionListener searchButtonListener = new SearchButtonListener();
		searchButton.addActionListener(searchButtonListener);
	}

    public static String getPoll() {
    	System.out.println(pollText.getText());
           return pollText.getText().trim();
    }
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	public static void output() throws TwitterException, IOException {
		String[] output = DataCollection.collectData(getPoll());
		JLabel netPositivityScore = new JLabel("Net Positivity Score: ");
		netPositivityScore.setBounds(10, 80, 80, 25);
		netPositivityScore.setFont(new Font(netPositivityScore.getFont().getName(), netPositivityScore.getFont().getStyle(), 20));
		panel.add(netPositivityScore);		
	}
}
     */
