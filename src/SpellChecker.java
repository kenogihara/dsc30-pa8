/*
 * Name: Ken Ogihara
 * PID: A16969236
 */

import java.io.*;
import java.util.*;

/**
 * Spell Checker Implementation.
 *
 * @author Ken Ogihara
 * @since ${3/15/24}
 */

public class SpellChecker {

    public KeyedSet dictWords;


    /**
     * Method that accepts a Reader object and scans through it using a Java Scanner.
     *
     * @param reader file that we are scanning.
     * @param useHashTable whether or not we will use an object of the MyHashTable or an object
     * of the MyBloomFilter class.
     */
    public void readDictionary(Reader reader, boolean useHashTable) {
        Scanner fetch = new Scanner(reader);
        if (useHashTable) {
            dictWords = new MyHashTable();
        }
        else {
            dictWords = new MyBloomFilter();
        }
        while (fetch.hasNext()) {
            dictWords.insert(fetch.nextLine());
        }
        fetch.close();
    }

    /**
     * Returns a list of Strings where if you change one letter in the given String you can find a
     * match.
     *
     * @param word String item that we will be manipulating.
     * @return a list of strings that contain the suggestions for the given string.
     */
    private LinkedList<String> checkWrongLetter(String word) {
        LinkedList<String> suggestions = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char character = 'a'; character <= 'z'; character++) {
                if (word.charAt(i) == character) {
                    continue;
                }
                String suggestion = word.substring(0, i) + character +
                        word.substring(i + 1);
                if (dictWords.lookup(suggestion)) {
                    suggestions.add(suggestion);
                }
            }
        }
        return suggestions;
    }

    /**
     * Returns a list of strings where if you add one letter in the given string you can find a
     * match.
     *
     * @param word String item that we will be manipulating.
     * @return a list of strings that contain the suggestions for the given string.
     */
    private LinkedList<String> checkInsertedLetter(String word) {
        LinkedList<String> suggestions = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char character = 'a'; character <= 'z'; character++) {
                String suggestion = word.substring(0, i) + character + word.substring(i);
                if (dictWords.lookup(suggestion)) {
                    suggestions.add(suggestion);
                }
            }
        }
        return suggestions;
    }

    /**
     * Returns a list of Strings where if you delete one letter in the given string you can find a
     * match.
     *
     * @param word String item that we will be manipulating.
     * @return a list of strings that contain the suggestions for the given string.
     */
    private LinkedList<String> checkDeleted(String word) {
        LinkedList<String> suggestions = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            String suggestion = word.substring(0, i) + word.substring(i+1);
            if (dictWords.lookup(suggestion)) {
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }

    /**
     * Returns a list of strings where if you swap two adjacent letters in the given string item
     * you can find a match.
     *
     * @param word String item that we will be manipulating.
     * @return a list of strings that contain the suggestions for the given string.
     */
    private LinkedList<String> checkTransposedLetter(String word) {
        LinkedList<String> suggestions = new LinkedList<>();
        for (int i = 0; i < word.length() - 1; i++) {
            char[] characters = word.toCharArray();
            char temp = characters[i];
            characters[i] = characters[i+1];
            characters[i+1] = temp;
            String suggestion = String.valueOf(characters);
            if (dictWords.lookup(suggestion)) {
                suggestions.add(suggestion);
            }
        }
        return suggestions;
        // "ryan" "yran"
    }

    /**
     * Returns a list of strings where if you insert a space in the given string you can find a
     * match with both of the newly generated words.
     *
     * @param word String item that we will be manipulating.
     * @return a list of strings that contain the suggestions for the given string.
     */
    private LinkedList<String> checkInsertSpace(String word) {
        LinkedList<String> suggestions = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            String suggestionBeforeSpace = word.substring(0, i);
            String suggestionAfterSpace = word.substring(i);
            if (dictWords.lookup(suggestionBeforeSpace) && dictWords.lookup(suggestionAfterSpace)) {
                suggestions.add(suggestionBeforeSpace + " " + suggestionAfterSpace);
            }
        }
        return suggestions;
    }

    /**
     * Returns an array of potential correct words by using the methods above.
     *
     * @param word String item that will be checked.
     * @return a list of strings that contain the suggestions for the given string.
     */
    private String[] checkWord(String word) {
        Set<String> allSuggestions = new HashSet<>();
        String output = "";
        if (dictWords.lookup(word)) {
            return new String[]{"ok"};
        }
        allSuggestions.addAll(checkWrongLetter(word));
        allSuggestions.addAll(checkInsertedLetter(word));
        allSuggestions.addAll(checkDeleted(word));
        allSuggestions.addAll(checkTransposedLetter(word));
        allSuggestions.addAll(checkInsertSpace(word));

        String[] set = allSuggestions.toArray(new String[0]);
        Arrays.sort(set);
        if (allSuggestions.isEmpty()) {
            return new String[]{output + "not found"};
        }
        else {
            return set;
        }
    }

    /**
     * Main method that reads files and checks for potential correct words.
     *
     * @param args a list of strings where the first index is a boolean, second is the path to the
     *             dictionary file, and the third index is the path to the input file.
     */
    public static void main(String[] args) {
        // args[0]: 0 if we should use a MyHashTable and 1 for a MyBloomFilter
        // args[1]: path to dict file
        // args[2]: path to input file

        SpellChecker checker = new SpellChecker();

        File dictionary = new File(args[1]);
        try {
            Reader reader = new FileReader(dictionary);

            checker.readDictionary(reader, Integer.parseInt(args[0]) == 0);

        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + dictionary);
            System.exit(1);
        }

        File inputFile = new File(args[2]);
        try {
            Scanner input = new Scanner(inputFile); // Reads list of words

            while (input.hasNext()) {
                String lowerCase = input.nextLine().toLowerCase();
                String[] checked = checker.checkWord(lowerCase);
                StringBuilder result = new StringBuilder();
                for (String suggestion : checked) {
                    result.append(suggestion).append(", ");
                }
                String output = result.substring(0, result.length() - 2);
                System.out.println(lowerCase + ": " + output);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + inputFile);
            System.exit(1);
        }
    }
}
