package com.edu.filestats.statistics.internal;

import com.edu.filestats.contentreader.ContentReader;
import com.edu.filestats.statistics.ContentStatistic;
import com.edu.filestats.statistics.StatisticStrategy;
import org.springframework.stereotype.Component;

@Component
public class NumberOfDotsStrategy implements StatisticStrategy {


    @Override
    public String getName() {
        return "countDots";
    }

    @Override
    public ContentStatistic process(String contentUrl, ContentReader reader) throws RuntimeException {
        String content = reader.read(contentUrl);
        long count = 0;
        for (char c : content.toCharArray()) {
            if ('.' == c) count++;
        }
        return new ContentStatistic(getName(), count);
    }
}
