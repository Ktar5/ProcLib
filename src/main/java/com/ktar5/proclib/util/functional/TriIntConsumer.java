package com.ktar5.proclib.util.functional;

@FunctionalInterface
public interface TriIntConsumer {
    void accept(int row, int col, int value);
}
