package com.edu.filestats.contentreader;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ContentReaderFactory {

    private List<ContentReader> readerList;

    public ContentReaderFactory(List<ContentReader> readerList) {
        this.readerList = readerList;
    }

    /**
     * To be able to set which content are allowed to read
     * using this factory class
     *
     * @param readerList
     */
    public void setReaderList(List<ContentReader> readerList) {
        this.readerList = readerList;
    }

    public List<ContentReader> getReaderList() {
        return readerList;
    }

    /**
     * Get the right ContentReader to be able
     * to read the content.
     *
     * @param resource to be read.
     * @return Optional.empty if not found
     */
    public Optional<ContentReader> parse(String resource) {
        return readerList.stream()
                .filter(it -> it.canRead(resource))
                .findFirst();
    }
}
