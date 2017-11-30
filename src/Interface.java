import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import twitter4j.TwitterException;
 
/* FrameDemo.java requires no other files. */
public class Interface {
    private static JTextField pollText;
    private static JPanel panel;
    private static void createAndShowGUI() {
        //Create and set up the window.
    	JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame("TwitterPoll");
        frame.setSize(500, 400);
        frame.setState(Frame.MAXIMIZED_BOTH);
        frame.setIconImage(new ImageIcon("http://icons.iconarchive.com/icons/uiconstock/socialmedia/512/Twitter-icon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JLabel emptyLabel = new JLabel("TwitterPoll");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        panel = new JPanel();
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
