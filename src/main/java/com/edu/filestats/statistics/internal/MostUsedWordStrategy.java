package com.edu.filestats.statistics.internal;

import com.edu.filestats.contentreader.ContentReader;
import com.edu.filestats.statistics.ContentStatistic;
import com.edu.filestats.statistics.StatisticStrategy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MostUsedWordStrategy implements StatisticStrategy {


    Comparator<WordCount> wordCountComparatorDesc = (o1, o2) -> Integer.compare(o2.count, o1.count);

    @Override
    public String getName() {
        return "mostUsedWord";
    }

    @Override
    public ContentStatistic process(String contentUrl, ContentReader reader) throws RuntimeException {
        String content = reader.read(contentUrl).toLowerCase().replaceAll("[^\\w]", " ");
        List<WordCount> wordsCountList = Arrays.stream(content.split("\\s+"))
                .filter(it -> !it.isEmpty())
                .collect(Collectors.groupingBy(it -> it))
                .entrySet()
                .stream()
                .map(it -> new WordCount(it.getKey(), it.getValue().size()))
                .sorted(wordCountComparatorDesc)
                .toList();

        if (wordsCountList.isEmpty()) {
            return new ContentStatistic(getName(), "");
        }

        int maxCount = wordsCountList.get(0).count;
        var result = wordsCountList
                .stream()
                .takeWhile(it -> it.count == maxCount)
                .map(it -> it.word)
                .sorted()
                .toList();

        return new ContentStatistic(getName(), String.join(" ", result));
    }

    record WordCount(String word, int count) {
    }
}
