package com.ktar5.proclib.mutators;

import com.ktar5.proclib.ProceduralData;

public class Produce {
//Maybe split this up, idk.

    //color should be 1 or 0
    public static void noise(ProceduralData proc, float chance, int color) {
        proc.forAll((row, col) -> {
            if (ProceduralData.random.nextFloat() < chance) {
                proc.data()[row][col] = color;
            }
        });
    }

    // = is if
    public static void generate(ProceduralData proc, GenerateMethod generateMethod) {
        switch (generateMethod) {
            case RANDOM_INT:
                proc.forAll((row, col) -> proc.data()[row][col] = ProceduralData.random.nextInt(10));
                break;
            case COLUMN:
                proc.forAll((row, col) -> proc.data()[row][col] = col);
                break;
            case ROW:
                proc.forAll((row, col) -> proc.data()[row][col] = row);
                break;
            case ZERO:
                proc.forAll((row, col) -> proc.data()[row][col] = 0);
                break;
            case ONE:
                proc.forAll((row, col) -> proc.data()[row][col] = 1);
                break;
            case INCREMENT:
                proc.forAll((row, col) -> proc.data()[row][col] = row + col);
                break;
        }
    }


    public enum GenerateMethod {
        INCREMENT,
        RANDOM_INT,
        COLUMN,
        ROW,
        ZERO,
        ONE
    }


}
