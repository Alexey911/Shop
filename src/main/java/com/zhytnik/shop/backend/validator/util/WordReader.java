package com.zhytnik.shop.backend.validator.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.io.Files.readLines;
import static java.util.Collections.addAll;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class WordReader {

    public static final String DEFAULT_DELIMITER = ";";

    private File file;
    private String delimiter = DEFAULT_DELIMITER;

    public WordReader() {
    }

    public List<String> read() {
        final List<String> data;
        try {
            data = readLines(file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final List<String> words = newArrayList();
        for (String rowWords : data) {
            addAll(words, rowWords.split(delimiter));
        }
        return words;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void setFile(String file) {
        this.file = new File(file);
    }
}
