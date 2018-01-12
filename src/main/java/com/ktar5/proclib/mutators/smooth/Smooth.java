package com.ktar5.proclib.mutators.smooth;

import com.ktar5.proclib.ProceduralData;
import com.ktar5.proclib.util.Pair;

public class Smooth {

    //And symbol builds strings
    //TODO make sure this operates the same as the given
    //TODO generally, test this.
    //TODO amount > or >= threshold?
    public static void smooth(ProceduralData proc, Neighborhood neighborhood, int parameter, int iterations, int threshold) {
        Pair[] pairs = neighborhood.getPairs(parameter);
        int amount = 0;
        for (int i = 0; i < iterations; i++) {
            proc.refreshBuffer();
            for (int row = 0; row < proc.data().length; row++) {
                for (int col = 0; col < proc.data()[0].length; col++) {
                    for (Pair pair : pairs) {
                        if (proc.isInRange(pair.y + col, pair.x + row)) {
                            amount += proc.buffer()[pair.y + col][pair.x + row] == 1 ? 1 : 0;
                        }
                    }
                    proc.data()[row][col] = (amount >= threshold ? 1 : 0);
                    amount = 0;
                }
            }
        }
    }

}
