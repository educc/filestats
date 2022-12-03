package com.edu.filestats;

import com.edu.filestats.contentreader.ContentReader;
import com.edu.filestats.contentreader.ContentReaderFactory;
import com.edu.filestats.statistics.ContentStatistic;
import com.edu.filestats.statistics.StatisticStrategy;
import com.edu.filestats.statistics.StatisticStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class to get the statistics from a resource.
 */
@Component
@Slf4j
public class FileStatistic {
    private ContentReaderFactory readerFactory;
    private StatisticStrategyFactory strategyFactory;

    public FileStatistic(ContentReaderFactory readerFactory, StatisticStrategyFactory strategyFactory) {
        this.readerFactory = readerFactory;
        this.strategyFactory = strategyFactory;
    }

    /**
     * Get all the statistics available
     * for the resource provided.
     *
     * @param resourceUrl to get the statistics from
     * @return List of Statistics
     */
    public List<ContentStatistic> from(String resourceUrl) {
        return readerFactory.parse(resourceUrl)
                .map(it -> getStatistics(resourceUrl, it))
                .orElseGet(() -> {
                    log.info("No reader found for resource, resource will be ignored. resource={}", resourceUrl);
                    return new ArrayList<ContentStatistic>();
                });
    }

    private List<ContentStatistic> getStatistics(String resourceUrl, ContentReader reader) {
        return strategyFactory.getAll()
                .stream()
                .map(it -> it.process(resourceUrl, reader))
                .toList();
    }


    public void excludeReaders(List<String> readerList) {
        if (readerList.isEmpty()) return;

        Predicate<ContentReader> excludeList = (ContentReader a) -> !readerList.contains(a.getName());

        var newList = this.readerFactory.getReaderList()
                .stream().filter(excludeList)
                .toList();
        this.readerFactory.setReaderList(newList);
    }

    public void excludeStrategies(List<String> strategies) {
        if (strategies.isEmpty()) return;

        Predicate<StatisticStrategy> excludeList = (StatisticStrategy a) -> !strategies.contains(a.getName());

        var newList = this.strategyFactory.getAll()
                .stream().filter(excludeList)
                .toList();
        this.strategyFactory.setStrategies(newList);
    }


    public List<String> getReaderList() {
        return this.readerFactory.getReaderList().stream().map(ContentReader::getName).toList();
    }

    public List<String> getStrategies() {
        return this.strategyFactory.getAll().stream().map(StatisticStrategy::getName).toList();
    }


}
