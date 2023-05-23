package com.kenzie.unit.two.ATATestHelpers;

import java.util.List;

/**
 * Read lines from a file.
 */
public interface FileReader {
    /**
     * Reads a file line by line.
     * @return all lines in the file
     */
    List<String> readLines();

    /**
     * Reads a file character by character.
     * @return all characters in the file
     */
    List<Character> readCharacters();
}