package com.edu.filestats.statistics.internal;

import com.edu.filestats.contentreader.internal.UTF8PlainTextReader;
import com.edu.filestats.statistics.ContentStatistic;
import com.edu.filestats.statistics.StatisticStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

abstract class BaseStrategyTest {

    StatisticStrategy strategy;
    UTF8PlainTextReader reader = new UTF8PlainTextReader();
    final String filename = "test.txt";

    public BaseStrategyTest(StatisticStrategy strategy) {
        this.strategy = strategy;
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


    void expected(Object expected) {
        ContentStatistic result = strategy.process(filename, reader);
        Assertions.assertEquals(expected, result.value());
    }
}
