package com.zhytnik.shop.backend.validator;

import com.google.common.base.Charsets;
import com.zhytnik.shop.backend.validator.util.WordReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.io.Files.write;
import static com.zhytnik.shop.backend.validator.util.WordReader.DEFAULT_DELIMITER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class WordReaderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    static final String TEXT = "first" + DEFAULT_DELIMITER + "second";

    WordReader reader = new WordReader();

    @Before
    public void setUp() throws IOException {
        final File file = folder.newFile();
        write(TEXT, file, Charsets.UTF_8);
        reader.setFile(file.toString());
    }

    @Test
    public void readsByFile() {
        final List<String> words = reader.read();
        assertThat(words).containsOnly("first", "second");
    }
}
