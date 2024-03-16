/*
 * Name: Ken Ogihara
 * PID: A16969236
 */

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

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
     * @param reader
     * @param useHashTable
     */
    public void readDictionary(Reader reader, boolean useHashTable) {
        Scanner fetch = new Scanner(reader);
        if (useHashTable) {
            dictWords = new MyHashTable();
        }
        else {
            dictWords = new MyBloomFilter();
        }
        while (fetch.hasNextLine()) {
            dictWords.insert(fetch.nextLine());
        }
        fetch.close();
    }

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

    private LinkedList<String> checkInsertedLetter(String word) {
        LinkedList<String> suggestions = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char character = 'a'; character <= 'z'; character++) {
                if (word.charAt(i) == character) {
                    continue;
                }
                String suggestion = word.substring(0, i) + character + word.substring(i);
                if (dictWords.lookup(suggestion)) {
                    suggestions.add(suggestion);
                }
            }
        }
        return suggestions;
    }

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

    private String[] checkWord(String word) {
        LinkedList<String> allSuggestions = new LinkedList<>();
        String output = "";
        if (dictWords.lookup(word)) {
            return new String[]{"ok"};
        }
        allSuggestions.addAll(checkWrongLetter(word));
        allSuggestions.addAll(checkInsertedLetter(word));
        allSuggestions.addAll(checkInsertSpace(word));
        allSuggestions.addAll(checkTransposedLetter(word));
        allSuggestions.addAll(checkDeleted(word));

        if (allSuggestions.isEmpty()) {
            return new String[]{output + "not found"};
        }
        else {
            return allSuggestions.toArray(new String[0]);
        }
    }

    public static void main(String[] args) {
        // args[0]: 0 if we should use a MyHashTable and 1 for a MyBloomFilter
        // args[1]: path to dict file
        // args[2]: path to input file

        SpellChecker checker = new SpellChecker();

//        args = new String[]{"0", "./src/Samples/tiny.dict.txt", "./src/Samples/Input1.txt"};
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

            while (input.hasNextLine()) {
                String lowerCase = input.nextLine().toLowerCase();
                String checked = Arrays.toString(checker.checkWord(lowerCase));
                System.out.println(lowerCase + ": " + checked.substring(1, checked.length() - 1));
            }

        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + inputFile);
            System.exit(1);
        }
    }
}
