/*
 * Name: Ken Ogihara
 * PID: A16969236
 */

import java.io.*;
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

            }
        }
    }

    private LinkedList<String> checkDeleted(String word) {
        // TODO
        return null;
    }

    private LinkedList<String> checkTransposedLetter(String word) {
        // TODO
        return null;
    }

    private LinkedList<String> checkInsertSpace(String word) {
        // TODO
        return null;
    }

    private String[] checkWord(String word) {
        // TODO
        return null;
    }

    public static void main(String[] args) {
        // args[0]: 0 if we should use a MyHashTable and 1 for a MyBloomFilter
        // args[1]: path to dict file
        // args[2]: path to input file

        SpellChecker checker = new SpellChecker();

        File dictionary = new File(args[1]);
        try {
            Reader reader = new FileReader(dictionary);

            //TODO - call readDictionary with the given reader on the correct data structure.


        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + dictionary);
            System.exit(1);
        }

        File inputFile = new File(args[2]);
        try {
            Scanner input = new Scanner(inputFile); // Reads list of words

            //TODO - go through each word from Scanner

        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + inputFile);
            System.exit(1);
        }
    }
}
