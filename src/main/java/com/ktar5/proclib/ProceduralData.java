package com.ktar5.proclib;

import com.ktar5.proclib.util.functional.BiIntConsumer;
import com.ktar5.proclib.util.functional.TriIntConsumer;
import lombok.Getter;

import java.util.Random;

@Getter
public class ProceduralData extends Mutateable {
    //Generally an array of ones and zeroes
    private int[/*z*/][/*y*/][/*x*/] store;
    private int rows, columns;
    public final String name;

    public static final Random random = new Random();

    public ProceduralData(int width, int height, String name) {
        this.store = new int[2][height][width];
        this.rows = height;
        this.columns = width;
        this.name = name;
    }

    public ProceduralData(int width, int height, String name, int defaultValue) {
        this(width, height, name);
        forAll((row, col) -> store[0][row][col] = defaultValue);
    }

    public ProceduralData(ProceduralData proc, String name) {
        this(proc.getColumns(), proc.getRows(), name);
        //TODO setData()
        for (int row = 0; row < data().length; row++) {
            System.arraycopy(proc.data()[row], 0, data()[row], 0, proc.data()[0].length);
        }
        //TODO need buffer?...
    }

    public ProceduralData flushBuffer() {
        for (int row = 0; row < data().length; row++) {
            System.arraycopy(buffer()[row], 0, data()[row], 0, buffer()[0].length);
        }
        return this;
    }

    public ProceduralData refreshBuffer() {
        for (int row = 0; row < data().length; row++) {
            System.arraycopy(data()[row], 0, buffer()[row], 0, data()[0].length);
        }
        return this;
    }

    public ProceduralData clearBuffer(int value) {
        forAll((row, col) -> store[1][row][col] = value);
        return this;
    }

    public ProceduralData clearData(int value) {
        forAll((row, col) -> store[0][row][col] = value);
        return this;
    }

    public int[][] data() {
        return store[0];
    }

    public int[][] buffer() {
        return store[1];
    }

    public void setData(int[][] newData) {
        this.rows = newData.length;
        this.columns = newData[0].length;
        store = new int[2][rows][columns];
        for (int row = 0; row < data().length; row++) {
            System.arraycopy(newData[row], 0, data()[row], 0, newData[row].length);
        }
        refreshBuffer();
    }

    @Override
    public ProceduralData getProc() {
        return this;
    }

    public void forAll(BiIntConsumer consumer) {
        for (int row = 0; row < data().length; row++)
            for (int col = 0; col < data()[0].length; col++)
                consumer.accept(row, col);
    }

    public void forAll(TriIntConsumer consumer) {
        for (int row = 0; row < data().length; row++)
            for (int col = 0; col < data()[0].length; col++)
                consumer.accept(row, col, data()[row][col]);
    }

    public void delete() {
        ProceduralDataHandler.getInstance().delete(this.name);
    }

    public ProceduralData store() {
        ProceduralDataHandler.getInstance().store(this, this.name);
        return this;
    }

    public boolean isInRange(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < columns;
    }

    public void set(int x, int y, int color) {
        data()[x][y] = color;
    }

    public static void setSeed(long seed) {
        random.setSeed(seed);
    }

}
