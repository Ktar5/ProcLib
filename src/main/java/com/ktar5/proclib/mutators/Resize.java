package com.ktar5.proclib.mutators;


import com.ktar5.proclib.ProceduralData;
import com.ktar5.proclib.util.Pair;

public class Resize {

    public static void resize(ProceduralData proc, Pair newDimensions, int infill, ResizeDirection xDir, ResizeDirection yDir) {
        if (yDir == ResizeDirection.VERTICAL && (newDimensions.y - proc.getRows()) % 2 != 0)
            throw new RuntimeException("Cannot evenly pad Top & Bottom of array when required padding is odd");
        if (xDir == ResizeDirection.HORIZONTAL && (newDimensions.x - proc.getColumns()) % 2 != 0)
            throw new RuntimeException("Cannot evenly pad Left & Right of array when required padding is odd");
        if (!xDir.isX())
            throw new RuntimeException(xDir.name() + " is not a direction on the xAxis.");
        if (!yDir.isY())
            throw new RuntimeException(yDir.name() + " is not a direction on the yAxis.");

        int[][] newArray = new int[newDimensions.y][newDimensions.x];
        int xPad = 0, yPad = 0;
        if (xDir == ResizeDirection.HORIZONTAL) {
            xPad = (proc.getColumns() - newDimensions.x) / 2;
        } else if (xDir == ResizeDirection.LEFT) {
            xPad = (proc.getColumns() - newDimensions.x);
        }

        if (yDir == ResizeDirection.VERTICAL) {
            yPad = (proc.getRows() - newDimensions.y) / 2;
        } else if (yDir == ResizeDirection.BOTTOM) {
            yPad = (proc.getRows() - newDimensions.y);
        }

        for (int x = 0; x < newDimensions.x; x++) {
            for (int y = 0; y < newDimensions.y; y++) {
                if (proc.isInRange(y + yPad, x + xPad)) {
                    newArray[y][x] = proc.data()[y + yPad][x + xPad];
                } else {
                    newArray[y][x] = infill;
                }
            }
        }

        proc.setData(newArray);
    }

    public enum ResizeDirection {
        TOP,
        LEFT,
        BOTTOM,
        RIGHT,
        HORIZONTAL,
        VERTICAL,
        NONE;

        public static final ResizeDirection[]
                X = {LEFT, RIGHT, HORIZONTAL, NONE},
                Y = {TOP, BOTTOM, VERTICAL, NONE};

        public boolean isX() {
            for (ResizeDirection dir : X)
                if (dir == this) return true;

            return false;
        }

        public boolean isY() {
            for (ResizeDirection dir : Y)
                if (dir == this) return true;

            return false;
        }
    }

    public static ProceduralData stretch(ProceduralData proc, float xDir, float yDir) {
        xDir = Math.abs(xDir);
        yDir = Math.abs(yDir);
        ProceduralData result = new ProceduralData(
                (int) (proc.getColumns() * xDir),
                (int) (proc.getRows() * yDir),
                proc.getName() + ".stretch").store();

        int maxX = Math.max(proc.getColumns(), result.getColumns());
        int maxY = Math.max(proc.getRows(), result.getRows());
        int newRow, newCol;
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                newRow = (int) Math.floor((y / (double) maxY) * (double) proc.getRows());
                newCol = (int) Math.floor((x / (double) maxX) * (double) proc.getColumns());
                int value = proc.data()[newRow][newCol];
                newRow = (int) Math.floor((y / (double) maxY) * (double) result.getRows());
                newCol = (int) Math.floor((x / (double) maxX) * (double) result.getColumns());
                result.data()[newRow][newCol] = value;
            }
        }
        return result;
    }

}
