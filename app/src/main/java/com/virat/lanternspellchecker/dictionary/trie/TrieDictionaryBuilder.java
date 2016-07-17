package com.virat.lanternspellchecker.dictionary.trie;

import com.virat.lanternspellchecker.dictionary.api.Dictionary;
import com.virat.lanternspellchecker.dictionary.api.DictionaryBuilder;

import java.util.List;

public class TrieDictionaryBuilder implements DictionaryBuilder {


    @Override
    public Dictionary build(List<String> words) {
        return buildTrieDictionary(words);
    }

    TrieDictionary buildTrieDictionary(List<String> words) {
        if ( words == null || words.size() == 0) {
            return new TrieDictionary(new TrieNode()); // empty dictionary
        }

        TrieNode root = new TrieNode();
        TrieNode current = root;

        // For each word
        for (String w : words) {
            String word = w.toLowerCase();
            // For each character in the word, add it to the TrieDictionary
            for (int i = 0; i < word.length(); i++) {
                char character = word.charAt(i);
                if (!current.children.containsKey(character)) {
                    current.children.put(character, new TrieNode());
                }

                current = current.children.get(character);
            }
            // Restart at the top of the Trie
            current.isEndOfWord = true;
            current = root;
        }

        return new TrieDictionary(root);
    }
}
