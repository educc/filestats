package com.edu.filestats.contentreader;

public interface ContentReader {

    String getName();

    /**
     * Read all the content from. You should use the
     * 'canRead' to verify if the resourceUrl can be read
     * This can throw exceptions depending on the implementation
     * NOTE: NOT GOOD implementation for large files.
     *
     * @return String
     */
    String read(String resourceUrl) throws RuntimeException;

    /**
     * TRUE if the reader can read this type of content
     *
     * @return Boolean
     */
    boolean canRead(String resourceUrl);
}
