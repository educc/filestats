package com.edu.filestats.statistics.internal;

import org.junit.jupiter.api.Test;

class NumberOfDotsStrategyTest extends BaseStrategyTest {

    public NumberOfDotsStrategyTest() {
        super(new NumberOfDotsStrategy());
    }

    @Test
    void zeroPlainText() {
        write("");
        expected(0L);
    }

    @Test
    void onePlainText() {
        write(" .     ");
        expected(1L);
    }

    @Test
    void twoPlainText() {
        write("hello there. What's up.");
        expected(2L);
    }

    @Test
    void multipleLines() {
        write("hi there.\n" +
                "Edward was here.\n" +
                "Edward.");
        expected(3L);
    }

}