package com.virat.lanternspellchecker.dictionary.api;

import java.util.List;

public interface DictionaryBuilder {

    Dictionary build(List<String> words);
}
