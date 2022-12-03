package com.edu.filestats.statistics.internal;

import com.edu.filestats.contentreader.ContentReader;
import com.edu.filestats.statistics.ContentStatistic;
import com.edu.filestats.statistics.StatisticStrategy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CountWordsStrategy implements StatisticStrategy {

    @Override
    public String getName() {
        return "countWords";
    }

    @Override
    public ContentStatistic process(String contentUrl, ContentReader reader) throws RuntimeException {
        String content = reader.read(contentUrl).replaceAll("[^\\w]", " ");
        ;
        long count = Arrays.stream(content.split("\\s+"))
                .filter(it -> !it.isEmpty())
                .count();
        return new ContentStatistic(getName(), count);
    }

}
