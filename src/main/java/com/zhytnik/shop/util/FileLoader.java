package com.zhytnik.shop.util;

import java.io.File;
import java.net.URL;

/**
 * @author Alexey Zhytnik
 * @since 08.06.2016
 */
public class FileLoader {

    private FileLoader() {
    }

    public static File[] loadInnerDirectoryFiles(String directory) {
        final URL root = FileLoader.class.getResource(directory);
        if (root == null) return new File[0];
        final File rootDirectory = new File(root.getFile());
        return rootDirectory.listFiles();
    }
}
