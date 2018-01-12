package com.ktar5.proclib.mutators;

import com.ktar5.proclib.ProceduralData;
import com.ktar5.proclib.util.Pair;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Partition {

    /**
     * @param shapePrintChance A percent (0-1)
     * @param shapeVariability A percent (0-1)
     */
    public static void partition(ProceduralData proc, int maxArea, float shapeVariability, float shapePrintChance,
                                 PartitionShape shape) {
        List<Bounds> spaces = new ArrayList<>();
        List<Bounds> validSpaces = new ArrayList<>();
        spaces.add(new Bounds(0, 0, proc.getColumns() - 1, proc.getRows() - 1));
        Bounds temp; //Used for temporarily accessing a bounds
        int i = 0;
        while (spaces.size() != i) {
            if (spaces.size() > 600) {
                System.out.println("DONE TOO BIG");
                return;
            }
            temp = spaces.get(i);
            if ((temp.x1 - temp.x0) * (temp.y1 - temp.y0) < maxArea) {
                validSpaces.add(spaces.get(i));
            } else if ((temp.x1 - temp.x0) > (temp.y1 - temp.y0)) {
                int xVar = temp.xVariability(shapeVariability);
                spaces.add(new Bounds(xVar, temp.y0, temp.x1, temp.y1));
                spaces.add(new Bounds(spaces.get(i).x0, temp.y0, xVar, temp.y1));
            } else {
                int yVar = temp.yVariability(shapeVariability);
                spaces.add(new Bounds(temp.x0, yVar, temp.x1, temp.y1));
                spaces.add(new Bounds(temp.x0, spaces.get(i).y0, temp.x1, yVar));
            }
            i++;
        }
        for (i = 0; i < validSpaces.size(); i++) {
            if (ProceduralData.random.nextFloat() < shapePrintChance) {
                temp = validSpaces.get(i);
                if (shape == PartitionShape.RANDOM) createShape(proc, PartitionShape.getRandom(), temp);
                else if (shape == PartitionShape.QUAD) createShape(proc, PartitionShape.getQuads(), temp);
                else createShape(proc, shape, temp);
            }
        }

    }

    //TODO FIX DONUT
    private static void createShape(ProceduralData proc, PartitionShape shape, Bounds bounds) {
        System.out.println(3);
        for (int x = (int) Math.min(bounds.x0, bounds.x1); x < Math.max(bounds.x0, bounds.x1); x++) {
            for (int y = (int) Math.min(bounds.y0, bounds.y1); y < Math.max(bounds.y0, bounds.y1); y++) {
                boolean skipBounds = true;
                System.out.println("-------------");
                switch (shape) {
                    case CIRCLE:
                        float distSquared = bounds.distSquared(x, y);
                        float min = Math.min(((bounds.x1 - bounds.x0)) / 2f, ((bounds.y1 - bounds.y0)) / 2f);
                        if (distSquared < (min * min)) {
                            skipBounds = false;
                        }
                        break;
                    case DONUT:
                        distSquared = bounds.distSquared(x, y);
                        distSquared = (float) Math.sqrt(distSquared);
                        min = Math.min((bounds.x1 - bounds.x0) / 2f, (bounds.y1 - bounds.y0) / 2f);
                        System.out.println(x + ", " + y);
                        System.out.println("Dist2: " + distSquared);
                        System.out.println("Min: " + min * min);
                        //if (distSquared < (min * min) && distSquared > (min / 2f) * (min / 2f)) {
                        if(distSquared < min && distSquared > min / 2f){
                            System.out.println(true); //TODO
                            skipBounds = false;
                        }
                        break;
                    case SQUARE:
                        skipBounds = false;
                        break;
                    case BOX: //TODO ???? What's the difference between this and square
                        if (x > bounds.x0 + ((bounds.x1 - bounds.x0) * .25)
                                || x > bounds.x0 + ((bounds.x1 - bounds.x0) * .75)
                                || y > bounds.y0 + ((bounds.y1 - bounds.y0) * .25)
                                || y > bounds.y0 + ((bounds.y1 - bounds.y0) * .75)) {
                            skipBounds = false;
                        }
                        break;
                    case TRIANGLE:
                        proc.data()[y][x] = x - bounds.x0 > y - bounds.y0 ? 0 : 1;
                        break;
                }
                if (!skipBounds) {
                    if (x == bounds.x0 || y == bounds.y0 || x == bounds.x1 || y == bounds.y1) {
                        proc.data()[y][x] = 0;
                    } else {
                        proc.data()[y][x] = 1;
                    }
                }
            }
        }
    }

    @AllArgsConstructor
    public static class Bounds {
        final float x0, y0, x1, y1;

        public Bounds(Pair p0, Pair p1) {
            this(p0.x, p0.y, p1.x, p1.y);
        }

        public int yVariability(float variability) {
            float var = ProceduralData.random.nextFloat() * variability;
            return (int) Math.floor(y0 + ((y1 - y0) * (0.5 + var - (variability / 2))));
        }

        public int xVariability(float variability) {
            float var = ProceduralData.random.nextFloat() * variability;
            return (int) Math.floor(x0 + ((x1 - x0) * (0.5 + var - (variability / 2))));
        }

        //Distance formula
        public float distSquared(float x, float y) {
            float yY = y - ((y0 + y1) / 2f);
            yY = yY * yY;
            float xX = x - ((x0 + x1) / 2f);
            xX = xX * xX;
            System.out.println("Xx: " + xX);
            System.out.println("Yy: " + yY);
            return xX + yY;
        }

    }

    public enum PartitionShape {
        CIRCLE,
        DONUT,
        SQUARE,
        BOX,
        TRIANGLE,
        RANDOM,
        QUAD;

        public static PartitionShape[]
                QUADS = {SQUARE, BOX},
                RAND = {TRIANGLE, BOX, SQUARE, DONUT, CIRCLE};

        public static PartitionShape getRandom() {
            return RAND[ProceduralData.random.nextInt(RAND.length)];
        }

        public static PartitionShape getQuads() {
            return QUADS[ProceduralData.random.nextInt(QUADS.length)];
        }
    }

}

