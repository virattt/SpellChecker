package com.virat.lanternspellchecker.wordgenerator;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MisspelledWordGenerator {

    private static final String WORDS_FILENAME = "/usr/share/dict/words";
    private static final String MISSPELLED_WORDS = "misspelled_words.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path of file to generate incorrect words from: ");
        String wrongWords = scanner.nextLine();

        List<String> words = readWordsFromFile(wrongWords);
        if (!words.isEmpty()) {
            System.out.println("Words loaded!");
        }

        // Generate incorrect words from Dictionary and write to file
        List<String> misspelledWords = generateMisspelledWords(words);
        writeToFile(misspelledWords);

    }

    private static void writeToFile(List<String> words) {
        FileWriter writer;
        try {
            writer = new FileWriter(MISSPELLED_WORDS);
            for(String str : words) {
                writer.write(str);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file... :(");
            e.printStackTrace();
        }
    }

    @NonNull
    private static List<String> readWordsFromFile(String filename) {
        if (filename == null || filename.isEmpty()) {
            filename = WORDS_FILENAME;
        }
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

    private static List<String> generateMisspelledWords(List<String> correctWords) {
        List<String> misspelled = new ArrayList<>();
        for (String correctWord : correctWords) {
            if (correctWord.length() <= 1) {
                // Skip any 1 letter words
                continue;
            }
            String suffix = correctWord.substring(1, correctWord.length());
            misspelled.add(correctWord.charAt(0) + scrambleWord(suffix));
        }

        return misspelled;
    }

    private static String scrambleWord(String word )
    {
        Random random = new Random();
        char a[] = word.toCharArray();
        for(int i = 0; i < a.length - 1; i++) {
            int index = random.nextInt(a.length - 1);
            char temp = a[i];
            a[i] = a[index];
            a[index] = temp;
        }

        return new String(a);
    }
}
