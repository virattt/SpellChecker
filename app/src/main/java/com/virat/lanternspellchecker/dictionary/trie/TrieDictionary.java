package com.virat.lanternspellchecker.dictionary.trie;

import com.virat.lanternspellchecker.dictionary.api.Dictionary;

public class TrieDictionary implements Dictionary {

    private final TrieNode root;

    public TrieDictionary(TrieNode root) {
        this.root = root;
    }

    @Override
    public String findMatch(String word) {
        if (root == null
                || root.children == null
                || root.children.isEmpty()
                || word.isEmpty()
                || !root.children.containsKey(word.toLowerCase().charAt(0))) // make sure first char of word exists in dictionary
        {
            return "";
        }

        // Search through entire word for matches
        TrieNode current = this.root;
        StringBuilder sb = new StringBuilder();
        String lowerCaseWord = word.toLowerCase();
        for (int i = 0; i < lowerCaseWord.length(); i++) {
            char character = lowerCaseWord.charAt(i);

            if (current.children.containsKey(character)) {
                current = current.children.get(character);
                sb.append(character);
            }
            else {
                // Find a suffix from the rest of the dictionary, to make a valid English word
                sb.append(findMatch(current, new StringBuilder()));
                break;
            }
        }

        return sb.toString();
    }

    String findMatch(TrieNode node, StringBuilder sb) {
        if (node.isEndOfWord || node.children.isEmpty()) {
            return sb.toString();
        }

        // Traverse a random child node
        for (Character character : node.children.keySet()) {
            sb.append(character);
            return findMatch(node.children.get(character), sb);
        }

        return sb.toString();
    }

    TrieNode getRoot() {
        return root;
    }
}
