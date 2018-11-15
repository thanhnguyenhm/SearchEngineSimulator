package pa2;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Web Crawler retrieved on JournalDev
 * Original Link: https://www.journaldev.com/7207/google-search-from-java-program-example
 * Use JSoup to fetch data from Google Search
 * @author Pankaj from Journal Dev
 * @author thanhnguyen: Modified and added Quicksort, BST, updateScore algorithms
 */
public class WebCrawler {

    //initial min and max score for random score generator
    //each score has max 100 and min 0, therefore totalScore has min 0 and
    // max 400
    final static int MIN = 0;
    final static int MAX = 100;

    //source for web crawler
    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

    //declare ArrayList to save all urls
    private static ArrayList<Link> urls;

    public static ArrayList<Link> getUrls() { return urls; }

    //crawl
    /**
     * use google to websites related to keyword
     * @param keyword
     * @param NUM_RESULT
     * @throws IOException
     */
    public static void crawl(String keyword, final int NUM_RESULT) throws IOException {

        String searchURL =
                GOOGLE_SEARCH_URL + "?q="+keyword+"&num="+NUM_RESULT;
        //without proper User-Agent, we will get 403 error
        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();

        //below will print HTML data, save it to a file and open in browser to compare
        //System.out.println(doc.html());

        //If google search results HTML change the <h3 class="r" to <h3 class="r1"
        //we need to change below accordingly
        Elements results = doc.select("h3.r > a");

        //number ordering URLs
        int resultOrder = 1;

        urls = new ArrayList<>(NUM_RESULT);

        //generate each url and save it to an ArrayList
        for (Element result : results) {
            String linkHref = result.attr("href");
            String linkText = result.text();
            String resultLink = linkText + ", URL::" + linkHref.substring(6,
                    linkHref.indexOf("&"));

            //generate random score
            int score1 = (int)(Math.random()*((MAX - MIN)+1))+ MIN;
            int score2 = (int)(Math.random()*((MAX - MIN)+1))+ MIN;
            int score3 = (int)(Math.random()*((MAX - MIN)+1))+ MIN;
            int score4 = (int)(Math.random()*((MAX - MIN)+1))+ MIN;

            //initiate a Link object
            Link myLink = new Link(resultOrder, score1, score2, score3, score4, resultLink);

            //add a link to ArrayList<Link>
            urls.add(myLink);

            resultOrder++;
        }
    }

    /**
     * Use Quick sort to sort 30 links by total scores and set rank for each URL
     */
    public static ArrayList<Link> sortByQuickSort() {
        //extract total scores of each List object to array of integers
        int[] array = getTotalScoreArray();

        //call quick sort reverse method to sort in descending order
        QuickSort.quickSortReverse(array, 0, array.length - 1);

        //put sorted total score back to ArrayList of Link and add PageRank for each Link object
        ArrayList<Link> sorted = new ArrayList<>();
        for (int j = 0; j < array.length; j++) {
            Link o = new Link(array[j]);
            o.setPageRank(j + 1);
            sorted.add(o);
        }

        //put other elements back to ArrayList of Link according to their scores
        ArrayList<Link> copiedURL = (ArrayList<Link>) urls.clone();

        for(Link elem : sorted) {
            for(Link elem2: copiedURL) {
                if(elem.getTotalScore() == elem2.getTotalScore()){
                    elem.setId(elem2.getId());
                    elem.setName(elem2.getName());
                    copiedURL.remove(elem2);
                    break;
                }
            }
        }
        return sorted;
    }
    /**
     * This method using Priority Queue algorithm will update score based on
     * user input
     * @param scoreToUpdate: negative or positive integer
     * @param factor: factor 1 to 4 that need to be updated
     * @param linkId: id of link need to be updated
     */
    public static void updateScore(int scoreToUpdate, int factor, int linkId) {
    if (factor == 1) {
        urls.set(linkId - 1,
                new Link(urls.get(linkId -1).getId(),
                        urls.get(linkId - 1).getScore1() + scoreToUpdate,
                        urls.get(linkId - 1).getScore2(),
                        urls.get(linkId - 1).getScore3(),
                        urls.get(linkId - 1).getScore4(),
                        urls.get(linkId - 1).getName()));
    } else if (factor == 2) {
        urls.set(linkId - 1,
                new Link(urls.get(linkId -1).getId(),
                        urls.get(linkId - 1).getScore1(),
                        urls.get(linkId - 1).getScore2() + scoreToUpdate,
                        urls.get(linkId - 1).getScore3(),
                        urls.get(linkId - 1).getScore4(),
                        urls.get(linkId - 1).getName()));
    } else if (factor == 3) {
        urls.set(linkId - 1,
                new Link(urls.get(linkId -1).getId(),
                        urls.get(linkId - 1).getScore1(),
                        urls.get(linkId - 1).getScore2(),
                        urls.get(linkId - 1).getScore3() + scoreToUpdate,
                        urls.get(linkId - 1).getScore4(),
                        urls.get(linkId - 1).getName()));
    } else if (factor == 4) {
        urls.set(linkId - 1,
                new Link(urls.get(linkId -1).getId(),
                        urls.get(linkId - 1).getScore1(),
                        urls.get(linkId - 1).getScore2(),
                        urls.get(linkId - 1).getScore3(),
                        urls.get(linkId - 1).getScore4() + scoreToUpdate,
                        urls.get(linkId - 1).getName()));
    } else
        System.out.println("Error factor input. Please enter number 1 to 4");
    }

    /**
     * Build a Binary Search Tree based on total score
     */
    public static void buildBST() {
        // extract total scores of each List object to array of integers
        int[] array = getTotalScoreArray();

        // build a BST of total scores
        for (int i = 0; i < array.length; i++) {
            BinarySearchTree.treeInsert(new Node(array[i], urls.get(i)));
        }

        // for test, will print BST in console
        BinarySearchTree.inOrderTreeWalk(BinarySearchTree.root);
    }

    /**
     * Use binary search to get the URL that has the specific page rank
     * @param rank
     * @return Link object
     */
    public static Link getURLfromPageRank(int rank) {
        // convert user input's page rank of a Link object into total score of itself because BST was built based on total score, not page rank
        int tScore = convertRankToScore(rank);

        return BinarySearchTree.treeSearch(BinarySearchTree.root, tScore).getUrl();
    }

    /**
     * A method to find total score of a Link object by its rank
     * @param rank
     * @return total score
     */
    private static int convertRankToScore(int rank) {
        for (Link elem : urls) {
            if (elem.getPageRank() == rank) return elem.getTotalScore();
        }
        return 0;
    }

    /**
     * Get total scores from array of Link objects
     * @return an integer array of total scores
     */
    public static int[] getTotalScoreArray() {
        int[] array = new int[urls.size()];
        int i = 0;
        for(Link elem : urls) {
            array[i] = elem.getTotalScore();
            i++;
        }
        return array;
    }
}
