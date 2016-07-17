package com.virat.lanternspellchecker.dictionary.trie;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrieDictionaryBuilderTest {

    private TrieDictionaryBuilder trieDictionaryBuilder;

    @Before
    public void setup() {
        trieDictionaryBuilder = new TrieDictionaryBuilder();
    }

    @Test
    public void build_shouldCreate_trieDictionary_withPrefixes() throws Exception {
        // GIVEN
        TrieDictionary dictionary = trieDictionaryBuilder.buildTrieDictionary(createMockWordList1());

        // WHEN
        TrieNode root = dictionary.getRoot();

        // THEN
        Assert.assertFalse(root.children.containsKey('x'));
        Assert.assertTrue(root.children.containsKey('d')); // dog, doberman
        Assert.assertTrue(root.children.containsKey('a')); // apple, american
        Assert.assertTrue(root.children.containsKey('f')); // fox, family
        Assert.assertTrue(root.children.containsKey('r')); // rhino
    }

    @Test
    public void build_shouldCreate_trieDictionary_withFullWord() throws Exception {
        // GIVEN


        // WHEN
        TrieDictionary dictionary = trieDictionaryBuilder.buildTrieDictionary(createMockWordList2());
        TrieNode root = dictionary.getRoot();

        // THEN, search for 'd'
        Assert.assertTrue(root.children.containsKey('d'));

        // WHEN
        TrieNode d = root.children.get('d');

        // THEN, search for 'o'
        Assert.assertTrue(d.children.containsKey('o'));

        // WHEN
        TrieNode o = d.children.get('o');

        // THEN, search for 'g'
        Assert.assertTrue(o.children.containsKey('g'));

        // WHEN
        TrieNode g = o.children.get('g');

        // THEN, 'g' has no more children
        Assert.assertTrue(g.children.isEmpty());
    }

    @Test
    public void build_shouldCreateEmptyDictionary_ifNullWordsArePassedIntoFunction() throws Exception {
        // WHEN
        TrieDictionary trieDictionary = trieDictionaryBuilder.buildTrieDictionary(null);
        TrieNode root = trieDictionary.getRoot();

        // THEN
        Assert.assertTrue(root.children.isEmpty());
    }

    List<String> createMockWordList1() {
        return new ArrayList<String>() {
            {
                add("dog");
                add("apple");
                add("fox");
                add("rhino");
                add("american");
                add("doberman");
                add("forgot");
            }
        };
    }

    List<String> createMockWordList2() {
        return new ArrayList<String>() {
            {
                add("dog");
            }
        };
    }
}