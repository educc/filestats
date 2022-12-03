package com.edu.filestats.statistics;

import com.edu.filestats.contentreader.ContentReader;

public interface StatisticStrategy {

    /**
     * Name of the strategy
     *
     * @return
     */
    String getName();

    /**
     * Get the statistic using the content reader
     *
     * @param contentUrl where is the content
     * @param reader     to read the content
     * @return ContentStatistic
     * @throws RuntimeException
     */
    ContentStatistic process(String contentUrl, ContentReader reader) throws RuntimeException;
}
