package com.edu.filestats;

import com.edu.filestats.contentreader.ContentReaderFactory;
import com.edu.filestats.contentreader.internal.UTF8PlainTextReader;
import com.edu.filestats.statistics.ContentStatistic;
import com.edu.filestats.statistics.StatisticStrategyFactory;
import com.edu.filestats.statistics.internal.CountWordsStrategy;
import com.edu.filestats.statistics.internal.MostUsedWordStrategy;
import com.edu.filestats.statistics.internal.NumberOfDotsStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

class FileStatisticTest {


    Comparator<ContentStatistic> asc = (o1, o2) -> CharSequence.compare(o1.name(), o2.name());

    final String filename = "test.txt";

    @Test
    void allStrategiesTest() {
        var readerFactory = new ContentReaderFactory(List.of(new UTF8PlainTextReader()));
        var stgFactory = new StatisticStrategyFactory(List.of(
                new CountWordsStrategy(),
                new MostUsedWordStrategy(),
                new NumberOfDotsStrategy()
        ));

        var fileStats = new FileStatistic(readerFactory, stgFactory);

        write("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem");
        var result = fileStats.from(filename).stream().sorted(asc).toList();

        Assertions.assertEquals(result.get(0).value().toString(), "1");
        Assertions.assertEquals(result.get(1).value().toString(), "13");
        Assertions.assertEquals(result.get(2).value().toString(), "lorem");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(Paths.get(filename));
    }


    void write(String content) {
        try (var writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}