package com.ktar5.proclib;

import com.ktar5.proclib.mutators.Display.PrintMethod;
import com.ktar5.proclib.mutators.Partition.PartitionShape;
import com.ktar5.proclib.mutators.Produce;
import com.ktar5.proclib.mutators.Resize;
import com.ktar5.proclib.mutators.blob.Blob;
import com.ktar5.proclib.mutators.blob.BlobData;
import com.ktar5.proclib.mutators.smooth.Neighborhood;
import com.ktar5.proclib.util.Pair;

import java.util.List;

public class MutatorTests {

    public static void main(String[] args) {
        testStretch();
    }

    public static void print(ProceduralData proceduralData, PrintMethod printMethod) {
        proceduralData.printSeparator();
        proceduralData.print(printMethod);
    }

    public static void testStretch() {
        ProceduralData procArray = new ProceduralData(20, 20, "stretch");
        Pair[] pairs = Neighborhood.DONUT.getPairs(5);
        for (Pair pair : pairs) {
            procArray.set(pair.x + 10, pair.y + 10, 1);
        }
        print(procArray, PrintMethod.SYMBOLS);
        procArray = Resize.stretch(procArray, 1.0f, 1.5f);
        print(procArray, PrintMethod.SYMBOLS);
    }

    public static void testBlobs() {
        ProceduralData procArray = new ProceduralData(10, 10, "blobs");
        procArray.noise(.45f, 1);
        print(procArray, PrintMethod.SYMBOLS);
        procArray.findBlobs(new BlobData()
                .filter(BlobData.BlobFilter.ABOVE, 3)
                .overwriteGiven(true)
                .render(BlobData.BlobRender.ONE));
        print(procArray, PrintMethod.SYMBOLS);
    }

    public static void testPartitions() {
        ProceduralData procArray = new ProceduralData(25, 25, "partition");
        System.out.println(1);
        procArray.partition(PartitionShape.BOX, 50, .99f, 1f);
        print(procArray, PrintMethod.SYMBOLS);
    }

    public static void testCircles() {
        ProceduralData procArray = new ProceduralData(15, 15, "circles");
        Pair[] pairs = Neighborhood.DONUT.getPairs(5);
        for (Pair pair : pairs) {
            procArray.set(pair.x + 7, pair.y + 7, 55);
        }
        print(procArray, PrintMethod.VALUES);
    }

    public static void testClockwiseRotation() {
        ProceduralData procArray = new ProceduralData(3, 5, "rotations");
        procArray.generate(Produce.GenerateMethod.RANDOM_INT);
        print(procArray, PrintMethod.VALUES);
        procArray.rotateClockwise();
        print(procArray, PrintMethod.VALUES);
        procArray.rotateCounterClockwise();
        print(procArray, PrintMethod.VALUES);
    }

    public static void testConnections() {
        ProceduralData procArray = new ProceduralData(30, 15, "connections");
        procArray.noise(.25f, 1);
        print(procArray, PrintMethod.SYMBOLS);
        List<Blob> blobs = procArray.findBlobs(new BlobData().filter(BlobData.BlobFilter.ABOVE, 3).overwriteGiven(true));
        print(procArray, PrintMethod.SYMBOLS);
        procArray.connectTunnel(blobs, 1, 1);
        procArray.print(PrintMethod.SYMBOLS);
    }

    public static void testFlips() {
        ProceduralData procArray = new ProceduralData(3, 5, "flips");
        procArray.generate(Produce.GenerateMethod.RANDOM_INT);
        print(procArray, PrintMethod.VALUES);
        procArray.flipOnHorizontal();
        print(procArray, PrintMethod.VALUES);
        procArray.flipOnVertical();
        print(procArray, PrintMethod.VALUES);
    }

    public static void testMirror() {
        ProceduralData procArray = new ProceduralData(3, 5, "mirror");
        procArray.generate(Produce.GenerateMethod.RANDOM_INT);
        print(procArray, PrintMethod.VALUES);
        procArray.mirrorHorizontal();
        print(procArray, PrintMethod.VALUES);
        procArray.mirrorVertical();
        print(procArray, PrintMethod.VALUES);
    }

    public static void testSmooth() {
        ProceduralData procArray = new ProceduralData(10, 10, "smooth");
        procArray.noise(.25f, 1);
        print(procArray, PrintMethod.VALUES);
        procArray.smooth(Neighborhood.MOORE, 3, 2, 1);
        print(procArray, PrintMethod.VALUES);
    }

}
