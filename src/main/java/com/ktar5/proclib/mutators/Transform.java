package com.ktar5.proclib.mutators;

import com.ktar5.proclib.ProceduralData;

public class Transform {

    //http://blog.icodejava.com/1833/matrix-2d-array-clockwise-and-counterclockwise-rotation-with-extra-buffer-java-implementation/
    public static void rotate(ProceduralData proc, CircularDirection spinDirection) {
        int[][] rotated = new int[proc.data()[0].length][proc.data().length];
        proc.forAll((row, col) -> {
            //Reverse col and row since we need to adjust for the rotation
            if (spinDirection == CircularDirection.COUNTER_CLOCKWISE) {
                rotated[col][row] = proc.data()[proc.data().length - row - 1][col];
            } else { //Clockwise
                rotated[col][row] = proc.data()[row][proc.data()[0].length - col - 1];
            }
        });
        proc.setData(rotated);
    }

    public static void flipOnAxis(ProceduralData proc, Axis axis) {
        proc.refreshBuffer();
        if (axis == Axis.HORIZONTAL) {
            proc.forAll((row, col) -> {
                proc.buffer()[row][col] = proc.data()[proc.getRows() - row - 1][col];
            });
        } else { //Vertical
            proc.forAll((row, col) -> {
                proc.buffer()[row][col] = proc.data()[row][proc.getColumns() - col - 1];
            });
        }
        proc.flushBuffer();
    }


    //Goes positive to negative
    //Right to left
    public static void mirror(ProceduralData proc, Axis axis) {
        if (axis == Axis.HORIZONTAL) {
            for (int row = 0; row < proc.getRows() / 2; row++) {
                proc.data()[row] = proc.data()[proc.getRows() - row - 1];
            }
        } else { //Vertical
            for (int row = 0; row < proc.getRows(); row++) {
                for (int col = 0; col < proc.getColumns() / 2; col++) {
                    proc.data()[row][col] = proc.data()[row][proc.getColumns() - col - 1];
                }
            }
        }
    }

    public static void invert(ProceduralData proc) {
        proc.forAll((row, col) -> {
            int value = proc.data()[row][col];
            if (value == 1) {
                proc.data()[row][col] = 0;
            } else if (value == 0) {
                proc.data()[row][col] = 1;
            }
        });
    }

    public enum Axis {
        HORIZONTAL,
        VERTICAL
    }

    public enum CircularDirection {
        CLOCKWISE,
        COUNTER_CLOCKWISE
    }

}
