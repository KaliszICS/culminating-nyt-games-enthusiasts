package graphics.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameDataHandler {
    //Read from file

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

    
   

    public static String[] yellowWords, greenWords, blueWords, purpleWords;
    public static String yellowCategory, greenCategory, blueCategory, purpleCategory;

    

    public GameDataHandler() {
        ArrayList<HashMap<String, HashMap<String[], String[]>>> listOfCategories = new ArrayList<>();
      //I want to initialize a bunch of maps using buildMap, and then create the categories by getting two at random from wordle, and two at random from spelling bee per category.
        listOfCategories.add(buildMap("animals.txt"));
        listOfCategories.add(buildMap("colors.txt"));
        listOfCategories.add(buildMap("food.txt"));
        listOfCategories.add(buildMap("tools.txt"));
        listOfCategories.add(buildMap("transportation.txt"));


        // Shuffle the list to randomize category selection
        Collections.shuffle(listOfCategories);

        // Pick 4 different categories and fill them into the color arrays
        String[] colors = { "yellow", "green", "blue", "purple" };
        for (int i = 0; i < 4; i++) {
            HashMap<String, HashMap<String[], String[]>> selectedCategory = listOfCategories.get(i);
            String categoryName = selectedCategory.keySet().iterator().next(); // get the key
            String[] words = fillFromMap(categoryName, selectedCategory).get(categoryName); // get the words

            switch (colors[i]) {
                case "yellow":
                    yellowWords = words;
                    yellowCategory = categoryName;
                    break;
                case "green":
                    greenWords = words;
                    greenCategory = categoryName;
                    break;
                case "blue":
                    blueWords = words;
                    blueCategory = categoryName;
                    break;
                case "purple":
                    purpleWords = words;
                    purpleCategory = categoryName;
                    break;
            }
        }
    
        

        
    }

    public String[] analyzeFile(String url) {
        String[] wordList;

        String completeString = "";
        String URL_CONSTANT = "src/main/java/graphics/utils/game_resources/";
        for(String word : readWordsFromFile(URL_CONSTANT + url)) {
            completeString+=word.toUpperCase();
        }
        wordList = completeString.split(",");
        //Filter word list for any non 5 or 7 character words.
        ArrayList<String> filteredWordList = new ArrayList<>();
        for(String word : wordList) {
            if(word.length() == 5 || word.length() == 7)
                filteredWordList.add(word.toUpperCase());
        }

        return filteredWordList.toArray(new String[0]);
    }
    //First String[] is from wordle, second String[] is from spelling bee.
    
    public HashMap<String, HashMap<String[], String[]>> buildMap(String categoryFile) {
            HashMap<String[], String[]> fileMap = new HashMap<>();
            fileMap.put(analyzeFile("wordle/" + categoryFile), analyzeFile("spelling_bee/" + categoryFile));

            HashMap<String, HashMap<String[], String[]>> completeMap = new HashMap<>();
            //Remove the .txt
            completeMap.put(categoryFile.substring(0, categoryFile.length() - 4),
                fileMap
            );
        return completeMap;
    }
    //Let first String be the category name, the second String[] be the words. First two are wordle, second two are spelling bee.
    public HashMap<String, String[]> fillFromMap(String key, HashMap<String, HashMap<String[], String[]>> map) {
        //Get first two from wordle.
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

            // Randomly pick 2 distinct wordle words
            int w1 = rand.nextInt(wordleWords.length);
            int w2;
            do {
                w2 = rand.nextInt(wordleWords.length);
            } while (w2 == w1);

            // Randomly pick 2 distinct spelling bee words
            int s1 = rand.nextInt(spellingBeeWords.length);
            int s2;
            do {
                s2 = rand.nextInt(spellingBeeWords.length);
            } while (s2 == s1);

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
