package com.ktar5.proclib.util;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class Pair {
    public final int x, y;

    public static Pair of(int x, int y) {
        return new Pair(x, y);
    }

    public static Pair subtract(Pair pair, int x, int y) {
        return new Pair(x - pair.x, y - pair.y);
    }

    public static Pair[] ofMultiple(int... numbers) {
        if (numbers.length % 2 != 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Pair[] pairs = new Pair[numbers.length / 2];
        for (int i = 0; i < numbers.length; i += 2) {
            pairs[i / 2] = Pair.of(numbers[i], numbers[i + 1]);
        }
        return pairs;
    }

    public static Pair[] ofDiagram(boolean addCenter, int[][] diagram) {
        Pair center = null;
        List<Pair> pairList = new ArrayList<>();
        if (addCenter) {
            pairList.add(new Pair(0, 0)); //add center
        }
        all:
        for (int i = 0; i < diagram.length; i++) {
            for (int k = 0; k < diagram[0].length; k++) {
                if (diagram[i][k] == 5) {
                    center = new Pair(k, i);
                    break all;
                }
            }
        }
        if (center == null) {
            throw new NullPointerException("Need to place a 5 on center of diagram (0,0)");
        }
        for (int i = 0; i < diagram.length; i++) {
            for (int k = 0; k < diagram[0].length; k++) {
                if (diagram[i][k] == 1) {
                    pairList.add(Pair.subtract(center, k, i));
                }
            }
        }
        return pairList.toArray(new Pair[pairList.size()]);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair compare = (Pair) obj;
            //System.out.println(compare.x + " " + this.x + " -- " + compare.y + " " + this.y);
            //System.out.println(compare.x == this.x && compare.y == this.y);
            return compare.x == this.x && compare.y == this.y;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
