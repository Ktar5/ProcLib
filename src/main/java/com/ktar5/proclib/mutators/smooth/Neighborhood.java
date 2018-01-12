package com.ktar5.proclib.mutators.smooth;


import com.ktar5.proclib.util.MathUtils;
import com.ktar5.proclib.util.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.function.IntFunction;

public enum Neighborhood {
    MOORE(Pair.ofDiagram(true, new int[][]{
            {1, 1, 1},
            {1, 5, 1},
            {1, 1, 1}}
    )),
    VN(Pair.ofDiagram(true, new int[][]{
            {0, 1, 0},
            {1, 5, 1},
            {0, 1, 0}}
    )),
    VN_PLATFORMER(Pair.ofDiagram(false, new int[][]{
            {1, 5, 1},
            {0, 1, 0}}
    )),
    PLATFORMER2(Pair.ofDiagram(true, new int[][]{
            {0, 0, 1, 1, 1, 0, 0},
            {1, 1, 1, 5, 1, 1, 1},
            {0, 0, 0, 1, 0, 0, 0}}
    )),
    ANDREWS(Pair.ofDiagram(true, new int[][]{
            {1, 0, 0, 0, 1},
            {0, 1, 0, 1, 0},
            {0, 0, 5, 0, 0},
            {0, 1, 0, 1, 0},
            {1, 0, 0, 0, 1}}
    )),
    VNEXT(Pair.ofDiagram(true, new int[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 5, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}}
    )),
    //TODO note, this greek might be a 5x5 instead of a 7x7
    GREEK(Pair.ofDiagram(true, new int[][]{
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {1, 1, 1, 5, 1, 1, 1},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0}}

    )),
    TEUTONIC(Pair.ofDiagram(true, new int[][]{
            {0, 1, 1, 1, 0},
            {1, 0, 1, 0, 1},
            {1, 1, 5, 1, 1},
            {1, 0, 1, 0, 1},
            {0, 1, 1, 1, 0}}
    )),
    PLATFORMER(Pair.ofDiagram(true, new int[][]{
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {1, 0, 1, 5, 1, 1, 1}}

    )),
    VERTICAL(Pair.ofDiagram(true, new int[][]{
            {0, 1, 0},
            {0, 5, 0},
            {0, 1, 0}}
    )),
    HORIZONTAL(Pair.ofDiagram(true, new int[][]{
            {0, 0, 0},
            {1, 5, 1},
            {0, 0, 0}}
    )),
    RADIUS(i -> {
        Set<Pair> pairs = new HashSet<>();
        for (int z = 0; z < 360; z += 1) {
            int x = Math.round(i * MathUtils.cosDeg((float) (z)));
            int y = Math.round(i * MathUtils.sinDeg((float) (z)));
            pairs.add(new Pair(x, y));
        }
        for (int x = 0; x <= i; x++) {
            for (int y = 0; y <= i; y++) {
                if (x * x + y * y <= i * i) {
                    pairs.add(Pair.of(x, y));
                    pairs.add(Pair.of(-x, y));
                    pairs.add(Pair.of(x, -y));
                    pairs.add(Pair.of(-x, -y));
                }
            }
        }
        return pairs.toArray(new Pair[pairs.size()]);
    }),
    DONUT(i -> {
        Set<Pair> pairs = new HashSet<>();
        for (int z = 0; z < 360; z += 1) {
            int x = Math.round(i * MathUtils.cosDeg((float) (z)));
            int y = Math.round(i * MathUtils.sinDeg((float) (z)));
            pairs.add(new Pair(x, y));
        }

        int innerRadius = i / 2;
        //remove inner
        for (int z = 0; z < 360; z += 1) {
            int x = Math.round(innerRadius * MathUtils.cosDeg((float) (z)));
            int y = Math.round(innerRadius * MathUtils.sinDeg((float) (z)));
            pairs.remove(new Pair(x, y));
        }
        for (int x = 0; x <= i; x++) {
            for (int y = 0; y <= i; y++) {
                if (x * x + y * y <= i * i) {
                    //pairs.remove(Pair.of(x,y));
                    //pairs.remove(Pair.of(-x,y));
                    //pairs.remove(Pair.of(x,-y));
                    //pairs.remove(Pair.of(-x,-y));
                }
            }
        }

        return pairs.toArray(new Pair[pairs.size()]);
    }),
    TAXICAB(i -> { //TODO could be multiplied by two???
        Set<Pair> pairs = new HashSet<>();
        for (int x = 0; x <= i; x++) {
            for (int y = 0; y <= i; y++) {
                if (x + y <= i) {
                    pairs.add(Pair.of(x, y));
                    pairs.add(Pair.of(-x, y));
                    pairs.add(Pair.of(x, -y));
                    pairs.add(Pair.of(-x, -y));
                }
            }
        }

        return pairs.toArray(new Pair[pairs.size()]);
    });

    private Pair[] pairs;
    private IntFunction<Pair[]> pairFunction;

    Neighborhood(IntFunction<Pair[]> pairFunction) {
        this.pairFunction = pairFunction;
    }

    Neighborhood(Pair[] pairs) {
        this.pairs = pairs;
    }

    public Pair[] getPairs(int argument) {
        if (this == RADIUS || this == DONUT || this == TAXICAB) {
            return pairFunction.apply(argument);
        } else {
            return pairs;
        }
    }
}

