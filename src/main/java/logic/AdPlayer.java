/**
 * A class to handle ads.
 * Reads ads.txt and maps all video link/website link pairs to a hashmap.
 * Contains one method to return a String array of some random ad's video link and website link.
 * 
 * @author @FranklinZhu1
 */

package logic;

import java.io.*; // file i/o
import java.util.Scanner;
import java.util.HashMap;
import java.util.Random;

public class AdPlayer {

    private HashMap<String, String> videoToURL; // hashmap relating video links to their website links

    /**
     * Default constructor that reads ads.txt and maps each video link/website link pair to the videoToURL hashmap.
     */

    public AdPlayer() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("ads.txt")));
            while (scanner.hasNext()) videoToURL.put(scanner.next(), scanner.next());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) scanner.close();
        }
    }

    /**
     * Returns a random ad.
     * Returns an array containing the random ad's video link and website link.
     * 
     * @return {video link, website link}
     */

    public String[] getAd() {
        Object[] videoArray = this.videoToURL.keySet().toArray(); // put all video links in object array
        Random random = new Random();
        int randomIndex = random.nextInt(videoArray.length);
        return new String[]{(String) videoArray[randomIndex], this.videoToURL.get(videoArray[randomIndex])}; // take random key and cast it to string
    }

}