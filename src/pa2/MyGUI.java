package pa2;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

//import java.awt.List;

public class MyGUI extends JFrame{

    // Define required number of URLs that need to be saved
    private static final int NUM_RESULT = 30;
    private JPanel topPanel;
    private JPanel searchPanel;
    private JPanel bottomPanel;
    private JButton searchButton;
    private JButton findButton;
    private JButton updateButton;
    private JButton topKeywordButton;
    private JButton topResultButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JLabel label;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel empty;
    private JLabel empty2;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JTextField input;
    private JTextField input2;
    private JTextField input3;
    private JTextField input4;
    private JTextField input5;
    private JTextField scoreIncrease;
    private JTextField linkIncrease;
    private JTextField factor;
    private JTextArea result;
    private JScrollPane scroller;

    public MyGUI() {
        //keywords: an array of keywords from user input
        HashMap<String, Integer> keywords = new HashMap<>();
        Color blue1 = new Color(237, 243, 250);

        //declare layout with three panels
        topPanel = new JPanel();
        searchPanel = new JPanel();
        bottomPanel = new JPanel();

        //declare GUI components in top panel
        topPanel.setLayout(new GridLayout(0, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchButton = new JButton("Search");
        label = new JLabel("Enter a keyword: ");
        input = new JTextField();
        topResultButton = new JButton("Top Results");
        topKeywordButton = new JButton("Top Keywords");
        insertButton = new JButton("Insert");
        label5 = new JLabel("Enter a Page Rank: ");
        input2 = new JTextField();
        input3 = new JTextField();
        input4 = new JTextField();
        input5 = new JTextField();
        findButton = new JButton("Find URL");
        empty = new JLabel("");
        empty2 = new JLabel("");
        label6 = new JLabel("URL to be inserted: ");
        label7 = new JLabel("   with Total Score: ");
        label8 = new JLabel("Delete URL with score: ");
        deleteButton = new JButton("Delete");

        //declare GUI components in center panel
        searchPanel.setLayout(new GridLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        result = new JTextArea(37, 65);
        result.setEditable(false);
        result.setBackground(blue1);
        scroller = new JScrollPane(result);

        //declare GUI components in bottom panel
        label2 = new JLabel("Adjust score (+/- int): ");
        scoreIncrease = new JTextField(4);
        label4 = new JLabel("for factor number (1-4): ");
        factor = new JTextField(4);
        label3 = new JLabel("from URL number (1-~30): ");
        linkIncrease = new JTextField(4);
        updateButton = new JButton("Update");

        //add components to panel
        topPanel.add(label);
        topPanel.add(input);
        topPanel.add(searchButton);
        topPanel.add(topResultButton);
        topPanel.add(topKeywordButton);
        topPanel.add(label5);
        topPanel.add(input2);
        topPanel.add(findButton);
        topPanel.add(empty);
        topPanel.add(empty2);
        topPanel.add(label6);
        topPanel.add(input3);
        topPanel.add(label7);
        topPanel.add(input4);
        topPanel.add(insertButton);
        topPanel.add(label8);
        topPanel.add(input5);
        topPanel.add(deleteButton);
        searchPanel.add(scroller);
        bottomPanel.add(label2);
        bottomPanel.add(scoreIncrease);
        bottomPanel.add(label4);
        bottomPanel.add(factor);
        bottomPanel.add(label3);
        bottomPanel.add(linkIncrease);
        bottomPanel.add(updateButton);

        //setup frame, layout
        JFrame frame = new JFrame("Google Search Simulator");
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(searchPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getRootPane().setDefaultButton(searchButton); //enter as
        // default button
        //-------------------SearchButton---------------------------------
        //searchButton: add listener for Search button
        searchButton.addActionListener(e -> {
            // get keyword from user and put it in an ArrayList
            String userKeyword = input.getText();

            // add userKeyword to HashMap, if exist, increase value by 1, if
            // not, set value as 1
            keywords.put(userKeyword,
                    keywords.getOrDefault(userKeyword, 0) + 1);

            // run web crawler for that keyword
            try {
                WebCrawler.crawl(userKeyword, NUM_RESULT);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            String output = "";

            for (Link s : WebCrawler.getUrls()) {
                output += s.toString() + "\n";
            }
            result.setText(output);

            //build BST from 30 URLS
            WebCrawler.buildBST();
        });
        //-------------------------Update------------------------------------
        //updateButton: add action listener for update button
        //todo DO NOT LEAVE 3 fields blank before click Update
        updateButton.addActionListener(x -> {
            String updateStr = "";

            //call method updateScore to increase key
            WebCrawler.updateScore(
                    Integer.parseInt(scoreIncrease.getText()),
                    Integer.parseInt(factor.getText()),
                    Integer.parseInt(linkIncrease.getText()));

            for (Link elem : WebCrawler.getUrls()) {
                updateStr += elem.toString() + "\n";
            }

            result.setText(updateStr);
        });
        //------------------------TopKeyword-----------------------------
        //topKeywordButton: Add listener to Top Keyword Button
        topKeywordButton.addActionListener(e -> {
            String out = "";

            // Sort HashMap by value to find top 10 search
            HashMap<String, Integer> keywordSorted =
                    keywords.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            int counter = 0;

            for (Map.Entry<String, Integer> element :
                    keywordSorted.entrySet()) {
                if (counter <= 10) {
                    out += "Keyword: " + element.getKey() +
                            "\nNumber of search: " + element.getValue() + "\n\n";
                    counter++;
                } else break;
            }

            result.setText(out);
        });
        //--------------------------TopResult------------------------------
        //topResultButton: add action listener to topResult button
        //first time user clicks, program will show top urls after quickSort
        topResultButton.addActionListener(e -> {
            String output = "";

            //save top urls with highest scores and print it
            WebCrawler.sortByQuickSort();
            for (Link elem : WebCrawler.getSortedUrls()) {
                output += elem.toString(elem.getPageRank()) + "\n";
            }

            result.setText(output);

            //findButton: add action listener for find button
            findButton.addActionListener(x -> {
                //get page rank from user input
                int rank = Integer.parseInt(input2.getText());
                String foundURL = "Page Rank cannot be found";

                // Search URL from Page Rank using for loop
                //                for (Link elem : sortedLink) {
                //                    if (elem.getPageRank() == rank)
                //                        foundURL = elem.toString(rank);
                //                }

                // Search URL from Page Rank using Binary Search Tree
                foundURL = WebCrawler.getURLfromPageRank(rank).toString(rank);
                result.setText(foundURL);
            });
        });

    }
//----------------------------------------------------------------
    public static void main(String[] args) {

        Instant start = Instant.now();

        new MyGUI();

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toNanos();
        System.out.println("Time to run the program in nanoseconds: " + timeElapsed);
    }
}

