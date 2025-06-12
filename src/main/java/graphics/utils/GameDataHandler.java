package graphics.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Handles reading word data from files and selecting random words
 * grouped into categories and color-coded sets for the game.
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class GameDataHandler {

    private static final String GAME_RESOURCES_PATH = "src/main/java/graphics/utils/game_resources/";
    private static final int WORD_LENGTH_FIVE = 5;
    private static final int WORD_LENGTH_SEVEN = 7;
    private static final int NUM_CATEGORIES_TO_SELECT = 4;
    private static final int NUM_WORDLE_WORDS_TO_PICK = 2;
    private static final int NUM_SPELLING_BEE_WORDS_TO_PICK = 2;
    private static final String[] COLOR_ORDER = { "yellow", "green", "blue", "purple" };

    /**
     * Words assigned to each color category.
     */
    public static String[] yellowWords;
    public static String[] greenWords;
    public static String[] blueWords;
    public static String[] purpleWords;

    /**
     * Category names assigned to each color.
     */
    public static String yellowCategory;
    public static String greenCategory;
    public static String blueCategory;
    public static String purpleCategory;

    /**
     * Reads words from a given file path, returning each line as a trimmed string.
     *
     * @param filepath The path to the file containing words.
     * @return An ArrayList of trimmed words.
     */
    public static ArrayList<String> readWordsFromFile(String filepath) {
        ArrayList<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = br.readLine()) != null) {
                words.add(line.trim()); // Trim to remove any leading/trailing spaces
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return words;
    }

    /**
     * Constructor initializes game categories and word lists by reading and selecting words
     * from resource files. Picks 4 random categories and assigns words to color-coded arrays.
     */
    public GameDataHandler() {
        ArrayList<HashMap<String, HashMap<String[], String[]>>> listOfCategories = new ArrayList<>();

        // Initialize category maps from file data
        listOfCategories.add(buildMap("animals.txt"));
        listOfCategories.add(buildMap("colors.txt"));
        listOfCategories.add(buildMap("food.txt"));
        listOfCategories.add(buildMap("tools.txt"));
        listOfCategories.add(buildMap("transportation.txt"));
        listOfCategories.add(buildMap("things found in a school.txt"));
        listOfCategories.add(buildMap("writing.txt"));
        listOfCategories.add(buildMap("stress.txt"));
        listOfCategories.add(buildMap("pet names.txt"));
        listOfCategories.add(buildMap("parts of a car.txt"));
        listOfCategories.add(buildMap("music.txt"));
        listOfCategories.add(buildMap("electricity terms.txt"));
        listOfCategories.add(buildMap("computer science.txt"));
        listOfCategories.add(buildMap("coins.txt"));
        listOfCategories.add(buildMap("hide or reveals.txt"));

        // Shuffle the list to randomize category selection
        Collections.shuffle(listOfCategories);

        // Pick 4 different categories and fill color-coded arrays
        for (int i = 0; i < NUM_CATEGORIES_TO_SELECT; i++) {
            HashMap<String, HashMap<String[], String[]>> selectedCategory = listOfCategories.get(i);

            // Each map has one key: the category name
            String categoryName = selectedCategory.keySet().iterator().next();

            // Get the combined words for that category
            String[] combinedWords = fillFromMap(categoryName, selectedCategory).get(categoryName);

            // Assign words and category name to the appropriate color variables
            String currentColor = COLOR_ORDER[i];
            if ("yellow".equals(currentColor)) {
                yellowWords = combinedWords;
                yellowCategory = categoryName;
            } else if ("green".equals(currentColor)) {
                greenWords = combinedWords;
                greenCategory = categoryName;
            } else if ("blue".equals(currentColor)) {
                blueWords = combinedWords;
                blueCategory = categoryName;
            } else if ("purple".equals(currentColor)) {
                purpleWords = combinedWords;
                purpleCategory = categoryName;
            }
        }
    }

    /**
     * Reads, combines, filters, and returns words from a category file,
     * converting all words to uppercase and filtering by length (5 or 7).
     *
     * @param fileName The category file to analyze.
     * @return Array of filtered uppercase words of length 5 or 7.
     */
    public String[] analyzeFile(String fileName) {
        String completeString = "";

        // Read all words from file and concatenate uppercase words (assumed comma-separated)
        for (String word : readWordsFromFile(GAME_RESOURCES_PATH + fileName)) {
            completeString += word.toUpperCase();
        }

        // Split on commas
        String[] wordList = completeString.split(",");

        // Filter word list to only include words with length 5 or 7
        ArrayList<String> filteredWordList = new ArrayList<>();

        for (String word : wordList) {
            if (word.length() == WORD_LENGTH_FIVE || word.length() == WORD_LENGTH_SEVEN) {
                filteredWordList.add(word.toUpperCase());
            }
        }

        return filteredWordList.toArray(new String[0]);
    }

    /**
     * Builds a map from a category file,
     * linking the category name to word arrays for Wordle and Spelling Bee.
     *
     * @param categoryFile The filename of the category.
     * @return A map with the category name as key, and word arrays as value.
     */
    public HashMap<String, HashMap<String[], String[]>> buildMap(String categoryFile) {
        HashMap<String[], String[]> fileMap = new HashMap<>();

        fileMap.put(
            analyzeFile("wordle/" + categoryFile),
            analyzeFile("spelling_bee/" + categoryFile)
        );

        HashMap<String, HashMap<String[], String[]>> completeMap = new HashMap<>();

        // Remove the .txt extension to get category name
        String categoryName = categoryFile.substring(0, categoryFile.length() - 4);

        completeMap.put(categoryName, fileMap);

        return completeMap;
    }

    /**
     * Selects two distinct random words from Wordle and two from Spelling Bee
     * for the given category from the provided map.
     *
     * @param key The category name.
     * @param map The map containing word arrays for the category.
     * @return A map linking the category name to the selected combined word array.
     */
    public HashMap<String, String[]> fillFromMap(String key, HashMap<String, HashMap<String[], String[]>> map) {
        HashMap<String, String[]> result = new HashMap<>();
        Random rand = new Random();

        if (!map.containsKey(key)) {
            System.out.println("Category not found in map: " + key);
            return result;
        }

        HashMap<String[], String[]> wordleToSpellingMap = map.get(key);

        // Extract the only entry from the inner map
        Map.Entry<String[], String[]> entry = wordleToSpellingMap.entrySet().iterator().next();

        String[] wordleWords = entry.getKey();
        String[] spellingBeeWords = entry.getValue();

        // Randomly pick 2 distinct Wordle words
        int w1 = rand.nextInt(wordleWords.length);
        int w2;
        do {
            w2 = rand.nextInt(wordleWords.length);
        } while (w2 == w1);

        // Randomly pick 2 distinct Spelling Bee words
        int s1 = rand.nextInt(spellingBeeWords.length);
        int s2;
        do {
            s2 = rand.nextInt(spellingBeeWords.length);
        } while (s2 == s1);

        // Combine selected words
        String[] combined = new String[] {
            wordleWords[w1],
            wordleWords[w2],
            spellingBeeWords[s1],
            spellingBeeWords[s2]
        };

        result.put(key, combined);

        return result;
    }
}
