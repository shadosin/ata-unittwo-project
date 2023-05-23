package com.kenzie.unit.two.ATATestHelpers;

import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.ArrayUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Takes in a file path in the constructor, and provides utility methods for that file.
 */
public class ATAFileReader implements FileReader {

    private final File file;

    /**
     * Constructs a new ATAFileReader object. This object will provide utility methods on the file provided.
     * @param filePath a path to a valid file
     */
    public ATAFileReader(String filePath) {
        file = new File(filePath);
        if (!file.isFile()) {
            throw new IllegalArgumentException("Invalid file path");
        }
    }

    /**
     * Reads all lines in the instances file, and returns as a list of Strings.
     * @return a list of Strings read from the file. If an error occurs while reading the file, an empty list will be
     *         returned.
     */
    public List<String> readLines() {
        final List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()))) {
            final LineIterator it = new LineIterator(br);
            while (it.hasNext()) {
                lines.add(it.nextLine());
            }
            //CHECKSTYLE:OFF:IllegalCatch
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        //CHECKSTYLE:ON:IllegalCatch
        return lines;
    }

    @Override
    public List<Character> readCharacters() {
        final List<Character> chars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()))) {
            final LineIterator it = new LineIterator(br);
            while (it.hasNext()) {
                chars.addAll(Arrays.asList(ArrayUtils.toObject(it.nextLine().toCharArray())));
            }
            //CHECKSTYLE:OFF:IllegalCatch
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        //CHECKSTYLE:ON:IllegalCatch
        return chars;
    }
}
