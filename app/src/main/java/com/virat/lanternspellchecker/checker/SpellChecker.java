package com.virat.lanternspellchecker.checker;

import android.support.annotation.NonNull;

import com.virat.lanternspellchecker.dictionary.api.Dictionary;
import com.virat.lanternspellchecker.dictionary.trie.TrieDictionaryBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpellChecker {
    private static final String WORDS_FILENAME = "/usr/share/dict/words";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path of file to create dictionary from: ");
        String fileName = scanner.nextLine();

        List<String> words = readWordsFromFile(fileName);
        if (!words.isEmpty()) {
            System.out.println("Dictionary loaded!");
            System.out.println();
        }

        // Add the words to the dictionary
        Dictionary trieDictionary = new TrieDictionaryBuilder().build(words);

        System.out.println("Do you want to: \n" +
                "(1) enter a txt file of misspelled words \n " +
                "or \n" +
                "(2) use the interactive prompt?");
        System.out.print("> ");

        int choice = scanner.nextInt();

        if (choice == 1) {
            startAutomaticDictionaryCheck(trieDictionary, scanner);
        }
        else if (choice == 2) {
            startInteractivePrompt(trieDictionary, scanner);
        }
    }

    /**
     * This method is for the first part of the assignment
     * Requests user input for a word and then prints a spell-corrected
     * version of it.
     * @param trieDictionary - a Dictionary with valid English words
     * @param scanner - scans from System.in
     */
    private static void startInteractivePrompt(Dictionary trieDictionary, Scanner scanner) {
        scanner.nextLine(); // wait
        // Get user input
        while (true) {
            System.out.print("> ");

            String word = scanner.nextLine();
            String correction = trieDictionary.findMatch(word);
            if (correction.isEmpty()) {
                System.out.println("NO SUGGESTION");
            }
            else {
                System.out.println(correction);
            }
        }
    }

    /**
     * This method is for the "Final Step" portion of the assignment
     * Uses the English word dictionary that the user provided and a txt file that
     * contains misspelled words to check if there are any NO OCCURRENCES.
     *
     * @param trieDictionary - a Dictionary with valid English words
     * @param scanner - scans from System.in
     */
    private static void startAutomaticDictionaryCheck(Dictionary trieDictionary, Scanner scanner) {
        // Load misspelled words dictionary
        System.out.print("Enter path of txt file with misspelled words: ");
        scanner.nextLine();
        String misspelledFilename = scanner.nextLine();

        List<String> misspelledWords = readWordsFromFile(misspelledFilename);

        int count = 0;
        for (String word : misspelledWords) {
            String match = trieDictionary.findMatch(word);
            if (match.isEmpty()) {
                count++;
            }
        }
        System.out.println("There were " + count + " occurrences of NO SUGGESTION");
    }

    @NonNull
    private static List<String> readWordsFromFile(String filename) {
        List<String> words = new ArrayList<>();
        try {
            // Read the file of words
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                words.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return words;
    }
}
