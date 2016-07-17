package com.virat.lanternspellchecker.dictionary.trie;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrieDictionaryTest {

    @Test
    public void findMatch_shouldReturn_bestCorrection_ofIncorrectWord() throws Exception {
        // GIVEN
        TrieDictionary trieDictionary = new TrieDictionaryBuilder().buildTrieDictionary(createMockWordList1());
        String word1 = "dOOOOassd";
        String word2 = "doggas";

        // WHEN
        String match1 = trieDictionary.findMatch(word1);
        String match2 = trieDictionary.findMatch(word2);

        // THEN
        assertThat(match1).isEqualTo("doberman");
        assertThat(match2).isEqualTo("dog");
    }

    @Test
    public void findMatch_shouldCorrect_vowels_repeatedWords_capitalization() throws Exception {
        // GIVEN
        TrieDictionary trieDictionary = new TrieDictionaryBuilder().buildTrieDictionary(createMockWordList2());
        String word1 = "inSIDE";
        String word2 = "weke";
        String word3 = "jjoobbb";
        String word4 = "CUNsperrICY";

        // WHEN
        String match1 = trieDictionary.findMatch(word1);
        String match2 = trieDictionary.findMatch(word2);
        String match3 = trieDictionary.findMatch(word3);
        String match4 = trieDictionary.findMatch(word4);

        // THEN
        assertThat(match1).isEqualTo("inside");
        assertThat(match2).isEqualTo("wake");
        assertThat(match3).isEqualTo("job");
        assertThat(match4).isEqualTo("conspiracy");
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
                add("wake");
                add("job");
                add("conspiracy");
                add("inside");
            }
        };
    }
}