package com.edu.filestats.statistics.internal;

import org.junit.jupiter.api.Test;

class MostUsedWordStrategyTest extends BaseStrategyTest {

    public MostUsedWordStrategyTest() {
        super(new MostUsedWordStrategy());
    }

    @Test
    void empty() {
        write("");
        expected("");
    }

    @Test
    void oneWord() {
        write("hello     ");
        expected("hello");
    }

    @Test
    void helloMostUsed() {
        write("1 2 3 4 5 6 7 8 9 hello hello. HELLO");
        expected("hello");
    }

    @Test
    void twoWordsSameCount() {
        write("edu hello. Hello from edu.");
        expected("edu hello");
    }

    @Test
    void multipleLines() {
        write("hi there.\n" +
                "Edward was here.\n" +
                "Edward.");
        expected("edward");
    }

}