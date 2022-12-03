package com.edu.filestats.statistics.internal;

import org.junit.jupiter.api.Test;

class CountWordsStrategyTest extends BaseStrategyTest {

    public CountWordsStrategyTest() {
        super(new CountWordsStrategy());
    }

    @Test
    void zeroWordsPlainText() {
        write("");
        expected(0L);
    }

    @Test
    void oneWordPlainText() {
        write("hello     ");
        expected(1L);
    }

    @Test
    void tenWordPlainText() {
        write("1 2 3 4 5 6 7 8 9 diez");
        expected(10L);
    }

    @Test
    void multipleLines() {
        write("hi there.\n" +
                "Edward was here.\n" +
                "Edward.");
        expected(6L);
    }

}